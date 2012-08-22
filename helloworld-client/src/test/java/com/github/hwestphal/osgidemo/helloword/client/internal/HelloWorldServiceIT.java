package com.github.hwestphal.osgidemo.helloword.client.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

import com.github.hwestphal.osgidemo.helloword.api.IHelloWorldService;

@RunWith(JUnit4TestRunner.class)
public class HelloWorldServiceIT {

	@Inject
	private IHelloWorldService helloWorldService;

	@Configuration
	public Option[] config() {
		return options(mavenBundle("com.github.hwestphal", "com.github.hwestphal.osgidemo.helloworld.api", "1.0.0-SNAPSHOT"),
				mavenBundle("com.github.hwestphal", "com.github.hwestphal.osgidemo.helloworld.impl", "1.0.0-SNAPSHOT"), junitBundles());
	}

	@Test
	public void serviceShouldReturnAGreeting() {
		String greeting = helloWorldService.hello("Harald");
		assertThat(greeting, notNullValue());
		assertThat(greeting.contains("Harald"), is(true));
	}

}
