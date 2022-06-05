package com.baeldung.web.controller;

import com.baeldung.persistence.model.Client;
import com.baeldung.persistence.model.Coach;
import com.baeldung.persistence.model.PotentialMatch;
import com.baeldung.persistence.model.User;
import com.baeldung.persistence.model.VerificationToken;
import com.baeldung.registration.OnRegistrationCompleteEvent;
import com.baeldung.security.ActiveUserStore;
import com.baeldung.security.ISecurityUserService;
import com.baeldung.security.LoggedUser;
import com.baeldung.service.ClientService;
import com.baeldung.service.CoachService;
import com.baeldung.service.IUserService;
import com.baeldung.service.PotentialMatchService;
import com.baeldung.web.dto.ClientDto;
import com.baeldung.web.dto.CoachDto;
import com.baeldung.web.dto.LoginInfoDto;
import com.baeldung.web.dto.PasswordDto;
import com.baeldung.web.dto.UserDto;
import com.baeldung.web.error.InvalidOldPasswordException;
import com.baeldung.web.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
public class RegistrationRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;
    
 
   
    @Autowired
    private ClientService clientService;
    @Autowired
    private CoachService coachService;
    @Autowired
    private PotentialMatchService potentialMatchService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ActiveUserStore activeUserStore;


    @Autowired
    private ISecurityUserService securityUserService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private Environment env;

    public RegistrationRestController() {
        super();
    }

    // Registration
    @PostMapping("/user/registration")
    public ResponseEntity registerUserAccount(  @RequestBody final UserDto accountDto, final HttpServletRequest request) {
        LOGGER.debug("Registering user account with information: {}", accountDto);
        
        final User registered = userService.registerNewUserAccount(accountDto);
       // userService.addUserLocation(registered, getClientIP(request));
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
      if(registered.getUserType().equals("Client")) {
        clientService.createEmptyClient(registered.getId(), registered.getEmail());
        
        try {
        	Iterable<Coach> allCoaches = coachService.getAllCoach();
        	 for(Coach coach: allCoaches) {
             	PotentialMatch match = new PotentialMatch(clientService.getClientByUserId(registered.getId()).get().getClientAutoId(),coach.getCoachAutoId(),0,0);
             	potentialMatchService.updatePotentialMatch(match);
             }
        } catch(Exception e) {
        	
        }
        
       
      }
      else if(registered.getUserType().equals("Coach")) {
    	  coachService.createEmptyCoach(registered.getId(),registered.getEmail());
      }
      
      
        
        return new ResponseEntity<>(userService.getVerificationTokenByUser(registered).getToken(), HttpStatus.OK);
    }
//    @GetMapping("/user/login")
//    public @ResponseBody ResponseEntity loginUserGet(@RequestBody LoginInfo loginInfo) {
//
//    	User user = userService.findUserByEmail(loginInfo.email);	
//    	if(	passwordEncoder.matches(loginInfo.password, user.getPassword())) {
//    		Optional<Client> userClientProfile = clientService.getClientByUserId(user.getId());
//    		return new ResponseEntity<>(userClientProfile, HttpStatus.OK);
//    	}
//    	else {
//    		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//    	}
//    }
	@PostMapping("/user/login")
	public @ResponseBody ResponseEntity loginUser(@RequestBody LoginInfoDto loginInfo) {
	
		Optional<User> user = Optional.of(userService.findUserByEmail(loginInfo.email));
		
		
		if(user.isPresent() && passwordEncoder.matches(loginInfo.password, user.get().getPassword())) {
			if(user.get().getUserType().equals("Client")) {
				Optional<Client> userClientProfile = clientService.getClientByUserId(user.get().getId());
				if (userClientProfile.isPresent()&&user.get().isEnabled()==true) {
					ClientDto clientDto = new ClientDto(userClientProfile.get());
					clientDto.setUserType(user.get().getUserType());
					
					List<String> loggedUsers = activeUserStore.getUsers();
					loggedUsers.add(clientDto.getEmail());
					activeUserStore.users = loggedUsers;
					return new ResponseEntity<>(clientDto, HttpStatus.OK);
				}
				
				
			}
			else if(user.get().getUserType().equals("Coach")) {
				Optional<Coach> userCoachProfile = coachService.getCoachByUserId(user.get().getId());
				if (userCoachProfile.isPresent()&&user.get().isEnabled()==true) {
					CoachDto coachDto = new CoachDto(userCoachProfile.get());
					coachDto.setUserType(user.get().getUserType());
					
					List<String> loggedUsers = activeUserStore.getUsers();
					loggedUsers.add(coachDto.getEmail());
					activeUserStore.users = loggedUsers;
					return new ResponseEntity<>(coachDto, HttpStatus.OK);
				}
				
				
			}
			
			
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
    // User activation - verification
    @GetMapping("/user/resendRegistrationToken")
    
    public GenericResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUser(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
        return new GenericResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    // Reset password
    @PostMapping("/user/resetPassword")
    public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
        }
        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    // Save password
    @PostMapping("/user/savePassword")
    public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {

        final String result = securityUserService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new GenericResponse(messages.getMessage("auth.message." + result, null, locale));
        }

        Optional<User> user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
        } else {
            return new GenericResponse(messages.getMessage("auth.message.invalid", null, locale));
        }
    }

    // Change user password
    @PostMapping("/user/updatePassword")
    public GenericResponse changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto) {
        final User user = userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

    // Change user 2 factor authentication
    @PostMapping("/user/update/2fa")
    public GenericResponse modifyUser2FA(@RequestParam("use2FA") final boolean use2FA) throws UnsupportedEncodingException {
        final User user = userService.updateUser2FA(use2FA);
        if (use2FA) {
            return new GenericResponse(userService.generateQRUrl(user));
        }
        return null;
    }

    // ============== NON-API ============

    private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale, final VerificationToken newToken, final User user) {
        final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
        final String message = messages.getMessage("message.resendToken", null, locale);
        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
    }

    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/user/changePassword?token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private String getClientIP(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
