package com.github.hwestphal.osgidemo.helloword.client.internal;

import org.apache.commons.codec.digest.DigestUtils;

import com.github.hwestphal.osgidemo.helloword.api.IHelloWorldService;

public class HelloWorldDigester implements IHelloWorldServiceConsumer {

	private volatile IHelloWorldService helloWorldService;

	@Override
	public void setHelloWorldService(IHelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

	public String digest(String name) {
		if (helloWorldService == null) {
			throw new IllegalStateException("no HelloWorld service is configured");
		}
		return DigestUtils.sha256Hex(helloWorldService.hello(name));
	}

}
