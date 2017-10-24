package com.progresssoft.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
public class ClusterDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClusterDataApplication.class, args);

	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver createMultipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		resolver.setMaxInMemorySize(0);
		return resolver;
	}


}
