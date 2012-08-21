package com.github.hwestphal.osgidemo.helloword.impl;

import com.github.hwestphal.osgidemo.helloword.api.IHelloWorldService;

public class HelloWorldService implements IHelloWorldService {

	@Override
	public String hello(String name) {
		return "Hello " + name + "!";
	}

}
