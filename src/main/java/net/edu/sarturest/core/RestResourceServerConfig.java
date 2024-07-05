package net.edu.sarturest.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class RestResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${oauth2.clienid}")
	private String clienid;
	
	@Value("${oauth2.clientsecret}")
	private String clientsecret;
	
	@Value("${oauth2.checktokenuri}")
	private String checktokenuri;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("Sartu");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**")
			.authorizeRequests()
//				.antMatchers("/api/v1/usuarios").permitAll()
				.antMatchers("/api/v1/usuarios").access("#oauth2.isClient() && #oauth2.hasScope('read') && #oauth2.clientHasRole('CONSULTA')")
//				.antMatchers("/api/v1/clientes").access("#oauth2.isClient() && #oauth2.hasScope('read') && #oauth2.clientHasRole('CONSULTA')")
				.antMatchers("/**").fullyAuthenticated();
	}
	
	@Bean
	public ResourceServerTokenServices tokenService() {
		RemoteTokenServices tokenServices = new RemoteTokenServices();
		tokenServices.setClientId(clienid);
		tokenServices.setClientSecret(clientsecret);
		tokenServices.setCheckTokenEndpointUrl(checktokenuri);
		return tokenServices;
	}
}
