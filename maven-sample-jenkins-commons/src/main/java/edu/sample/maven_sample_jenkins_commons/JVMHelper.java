package edu.sample.maven_sample_jenkins_commons;

import java.text.NumberFormat;

public class JVMHelper {

	private static final long MB_VAL = 1024 * 1024;
	private static final String MEGA_STRING = "MB";
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

	/**
	 * Get String with HTML tags
	 * 
	 * @param runtime
	 * @return
	 */
	public static String getJvmInfo(final Runtime runtime) {
		final long maxMemory = runtime.maxMemory();
		final long allocatedMemory = runtime.totalMemory();
		final long freeMemory = runtime.freeMemory();

		StringBuilder builder = new StringBuilder();
		builder.append("<ul> ========================== Memory Info ==========================")
				.append("<li> Free memory: " + NUMBER_FORMAT.format(freeMemory / MB_VAL) + MEGA_STRING + "</li>")
				.append("<li> Allocated memory: " + NUMBER_FORMAT.format(allocatedMemory / MB_VAL) + MEGA_STRING
						+ "</li>")
				.append("<li> Max memory: " + NUMBER_FORMAT.format(maxMemory / MB_VAL) + MEGA_STRING + "</li>")
				.append("<li> Total free memory: "
						+ NUMBER_FORMAT.format((freeMemory + (maxMemory - allocatedMemory)) / MB_VAL) + MEGA_STRING
						+ "</li>")
				.append("=================================================================<ul>");

		return builder.toString();
	}

}
