package com.firstpenguin.tinyurl.restservice.services;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firstpenguin.tinyurl.restservice.repository.URLRepository;
import com.firstpenguin.tinyurl.restservice.services.generators.RandomSequenceCachingGenerator;
import com.firstpenguin.tinyurl.restservice.util.SequenceGenerationException;

@Service
public class ShortURLCodeGenerationService {
	
	@Autowired
	RandomSequenceCachingGenerator generator;
	
	@Autowired
    URLRepository urlRepository;
	
	public String getShortURL() throws SequenceGenerationException {
		String candidate = "";
		do
		{
			candidate = generator.getSequence();
		}
		while(isURLInUse(candidate));
		return candidate;
	}
	
	private boolean isURLInUse(String shortUrl) {
		try {
			urlRepository.findById(shortUrl).get();
			return true;
		}
		catch(NoSuchElementException ex) {
			return false;
		}
	}
}
