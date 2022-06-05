package com.baeldung.web.dto;




public class LoginInfoDto {
public String email;
public String password;
public LoginInfoDto(String email, String password) {
	
	this.email = email;
	this.password = password;
}
public LoginInfoDto() {
	
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

    

}