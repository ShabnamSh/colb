package com.niit.collaborative.config;

import javax.persistence.Entity;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@ComponentScan("com.niit")
public class WebConfig {

}
