package com.github.hwestphal.osgidemo.helloword.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.github.hwestphal.osgidemo.helloword.api.IHelloWorldService;

public class HelloWorldServiceTest {

	private IHelloWorldService helloWorldService;

	@Before
	public void init() {
		helloWorldService = new HelloWorldService();
	}

	@Test
	public void shouldReturnCorrectGreeting() {
		assertThat(helloWorldService.hello("Harald"), equalTo("Hello Harald!"));
	}

}
