package com.jiurong.scrum.config;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig {

	@Value("${my.cors.allowed:false}")
	private boolean allowCors;
	@Value("${my.cors.origin:#{null}}")
	private String corsOrigin;
	@Value("${my.file.location:#{null}}")
	private String outerFileLocation;
	private static String serverAddress;

	@Autowired
	public void setServerAddress(@Value("${my.server.address:#{null}}") String serverAddress) {
		WebMvcConfig.serverAddress = serverAddress;
	}

	public static final String OutersideFilePrefix = "/upload";
	public static final String OutersideFilePattern = OutersideFilePrefix + "/**";

	public static String getUploadFilePath(String path) {
		if(path == null) {
			return null;
		}
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return serverAddress + OutersideFilePrefix + path;
	}

	@Bean
	public WebMvcConfigurerAdapter webMvc() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				if (outerFileLocation != null) {
					String location = outerFileLocation;
					if (!location.endsWith("/")) {
						location = location + "/";
					}
					if (!location.startsWith("classpath")) {
						location = "file:" + location;
					}
					registry
					.addResourceHandler(OutersideFilePattern)
					.addResourceLocations(location)
					.setCacheControl(CacheControl.maxAge(600, TimeUnit.SECONDS));
				}
			}

			@Override
			public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
				configurer.enable();
			}

			@Override
			public void configurePathMatch(PathMatchConfigurer configurer) {
				configurer.setUseSuffixPatternMatch(false);
			}
		};
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		if (allowCors && corsOrigin != null) {
			configuration.setAllowedOrigins(Arrays.asList(corsOrigin));
			configuration.setAllowedMethods(
					Arrays.asList("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE"));
			configuration.setAllowCredentials(true);
			configuration.setMaxAge(3600L);
			configuration.setAllowedHeaders(Arrays.asList("x-requested-with",
					"Content-Type", "Content-Range", "Content-Disposition", "Content-Description"));
		}
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
