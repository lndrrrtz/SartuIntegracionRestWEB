package net.edu.sarturest.core;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@EnableWebMvc
@ComponentScan({ "net.edu.sarturest.web.controllers" })
@Import({ RestCore.class })
public class RestWeb extends WebMvcConfigurerAdapter {

	public static final Locale LOCALE_ES = new Locale("es", "ES");
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("capacitor://localhost", "http://localhost:8100", "http://localhost", "http://localhost:9081")
			.allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "XMLHttpRequest")
			.allowCredentials(true)
			.maxAge((long)3600);
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(LOCALE_ES);
		return localeResolver;
	}
	
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("net/edu/sarturest/web/resources/ApplicationResources");
		source.setUseCodeAsDefaultMessage(Boolean.TRUE);
		source.setDefaultEncoding("UTF-8");
		return source;
	}
	
	@Bean(name = "messageAccessor")
	public MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(this.messageSource());
	}
}
