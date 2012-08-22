package com.github.hwestphal.osgidemo.helloword.impl.ds;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;

@Component
public class ConsoleLogger implements LogListener {

	private static final String[] LEVELS = { "?????", "ERROR", " WARN", " INFO", "DEBUG" };

	@SuppressWarnings("unused")
	@Reference(cardinality = ReferenceCardinality.OPTIONAL_UNARY, policy = ReferencePolicy.DYNAMIC)
	private LogReaderService logReaderService;

	public void bindLogReaderService(LogReaderService logReaderService) {
		logReaderService.addLogListener(this);
	}

	public void unbindLogReaderService(LogReaderService logReaderService) {
		logReaderService.removeLogListener(this);
	}

	@Override
	public void logged(LogEntry entry) {
		String bundleName = entry.getBundle().getSymbolicName();
		if ("org.apache.felix.http.bundle".equals(bundleName)) {
			return;
		}

		int level = entry.getLevel();
		String msg = entry.getMessage();
		Throwable exception = entry.getException();
		StringBuilder builder = new StringBuilder(String.format("[%s] <%s>%s", level < 5 ? LEVELS[level] : LEVELS[0], bundleName,
				msg == null ? "" : " " + msg.trim()));
		if (exception != null) {
			builder.append('\n');
			StringWriter writer = new StringWriter();
			exception.printStackTrace(new PrintWriter(writer));
			builder.append(writer.toString().trim());
		}
		System.out.println(builder.toString());
	}

}
