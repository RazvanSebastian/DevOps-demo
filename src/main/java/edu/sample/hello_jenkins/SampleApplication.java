package edu.sample.hello_jenkins;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sample.maven_sample_jenkins_commons.JVMHelper;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@RestController
public class SampleApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);

	}

	@GetMapping("/")
	public ResponseEntity<?> greeting() {
		final Runtime runtime = Runtime.getRuntime();
		final String jvmMetadata = JVMHelper.getJvmInfo(runtime);
		return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(jvmMetadata);
	}
}
