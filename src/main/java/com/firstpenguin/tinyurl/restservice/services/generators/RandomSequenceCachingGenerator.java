package com.firstpenguin.tinyurl.restservice.services.generators;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.firstpenguin.tinyurl.restservice.exception.SequenceGenerationException;

@Component
public class RandomSequenceCachingGenerator extends URLSequenceGenerator {
	private BlockingQueue<String> cache;
	Random randomGenerator = new Random();
	private static final int cacheCapacity = 5;
	private static final int cacheWait = 1000;
	
	public RandomSequenceCachingGenerator() {
		cache = new ArrayBlockingQueue<String>(cacheCapacity);
		//TODO. Inject it through Spring
		this.seqLen = 8;
		//TODO. Inject it through Spring
		this.abort = false;
		//TODO. Inject it through Spring
		this.allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+@".toCharArray();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!this.abort) {
			String candidate = generate();
			try {
				while(!cache.offer(candidate, cacheWait, TimeUnit.MILLISECONDS));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String generate() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < seqLen; i++) {
            int index = randomGenerator.nextInt(allowedChars.length);
            sb.append(allowedChars[index]);
        }
		return sb.toString();
	}
	
	@Override
	public synchronized String getSequence() throws SequenceGenerationException{
		// TODO Auto-generated method stub
		if(abort) {
			throw new SequenceGenerationException("Generator aborted!!");
		}
		String sequence = cache.peek();
		cache.remove();
		return sequence;
	}

}
