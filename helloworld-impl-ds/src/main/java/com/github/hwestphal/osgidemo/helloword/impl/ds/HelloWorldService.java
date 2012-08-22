package com.github.hwestphal.osgidemo.helloword.impl.ds;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.osgi.service.log.LogService;

import com.github.hwestphal.osgidemo.helloword.api.IHelloWorldService;

@Component
@Service
@Property(name = Constants.SERVICE_RANKING, intValue = -1)
public class HelloWorldService implements IHelloWorldService {

	@Reference(cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC)
	private volatile LogService logService;

	public void bindLogService(LogService logService) {
		this.logService = logService;
	}

	public void unbindLogService(LogService logService) {
		this.logService = null;
	}

	@Override
	public String hello(String name) {
		log("argument: " + name);
		return "HELLO " + name + " !!!";
	}

	private void log(String msg) {
		if (logService != null) {
			logService.log(LogService.LOG_INFO, msg);
		}
	}

}
