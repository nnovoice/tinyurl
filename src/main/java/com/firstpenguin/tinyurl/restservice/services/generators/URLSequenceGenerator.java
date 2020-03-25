package com.firstpenguin.tinyurl.restservice.services.generators;

import java.util.List;

import com.firstpenguin.tinyurl.restservice.util.SequenceGenerationException;

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
	List<Character> choices;
	
	@Setter
	boolean abort;
	
	public abstract String getSequence() throws SequenceGenerationException; 
}
