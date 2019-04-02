package com.dorilahav.api.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
	
	String label();
	
	String[] aliases() default {};
	
	CommandType type() default CommandType.PUBLIC;
	
	boolean processFlags() default false;
	
	String usage() default "";
	
}
