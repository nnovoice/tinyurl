package com.firstpenguin.tinyurl.restservice.services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import org.springframework.core.task.TaskExecutor;

import com.firstpenguin.tinyurl.restservice.repository.URLRepository;
import com.firstpenguin.tinyurl.restservice.services.generators.RandomSequenceCachingGenerator;


@RunWith(MockitoJUnitRunner.class)
public class ShortURLCodeGenerationServiceTest {
	
	@InjectMocks
	ShortURLCodeGenerationService svc;
	
	@Mock
	TaskExecutor taskExecutor;
	
	@Mock
	RandomSequenceCachingGenerator generator;
	
	@Mock
	URLRepository urlRepository;
	
	@Test
	public void test_getShortURL() throws Exception {
		when(generator.getSequence()).thenReturn("abcd").thenReturn("efgh");
		when(urlRepository.findById("abcd").isPresent()).thenReturn(true);
		when(urlRepository.findById("efgh").isPresent()).thenReturn(false);
		assertEquals("efgh", svc.getShortURL());
	}
}
