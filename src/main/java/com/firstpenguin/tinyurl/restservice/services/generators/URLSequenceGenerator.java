package com.firstpenguin.tinyurl.restservice.services.generators;

import com.firstpenguin.tinyurl.restservice.exception.SequenceGenerationException;

import lombok.Getter;
import lombok.Setter;

public abstract class URLSequenceGenerator implements Runnable {
	
	@Setter
	String prefix;
	
	@Setter
	String postfix;
	
	@Setter
	@Getter
	Integer seqLen;
	
	@Setter
	char[] allowedChars;
	
	@Setter
	boolean abort;
	
	public abstract String getSequence() throws SequenceGenerationException; 
}
