package com.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "src.main.java.com.baeldung.task" })
public class TestTaskConfig {

}
