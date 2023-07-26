package com.carrito.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
	
	@Bean
	public Cloudinary cloudinary() {
		Map<String,String> config = new HashMap<>();
		config.put("cloud_name", "YOUR_CLOUD_NAME");
		config.put("api_key", "YOUR_API_KEY");
		config.put("api_secret", "YOUR_API_SECRET");
		return new Cloudinary(config);
	}
}
