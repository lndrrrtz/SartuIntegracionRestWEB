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
	
	/**
	 * Sobrescribe el método configure para asignar la audiencia "Sartu" y solo permitir la autorización
	 * mediante tokens JWT que tienen esa audiencia
	 * 
	 * @throws Exception si ocurre un error en la configuración.
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("Sartu");
	}
	
	/**
	 * Configura la seguridad web, incluyendo el inicio de sesión y el cierre de sesión.
	 * 
	 * @param http el objeto {@link HttpSecurity} utilizado para construir la configuración de seguridad.
	 * @throws Exception si ocurre un error en la configuración de la seguridad web.
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**")
			.authorizeRequests()
				.antMatchers("/api/v1/usuarios").access("#oauth2.isClient() && #oauth2.hasScope('read') && #oauth2.clientHasRole('CONSULTA')")
				.antMatchers("/api/v1/clientes").access("#oauth2.isClient() && #oauth2.hasScope('read') && #oauth2.clientHasRole('CONSULTA')")
				.antMatchers("/api/v1/restricciones").access("#oauth2.isClient() && #oauth2.hasScope('write') && #oauth2.clientHasRole('RESTRICCIONES')")
				.antMatchers("/**").fullyAuthenticated();
	}
	
	/**
	 * Define ResourceServerTokenServices que se encarga de verificar la validez del token JWT recibido en la petición
	 * 
	 * @return {@link ResourceServerTokenServices} configurado con datos del cliente y el endpoint de verificación de Sartu (SSO)
	 */
	@Bean
	public ResourceServerTokenServices tokenService() {
		RemoteTokenServices tokenServices = new RemoteTokenServices();
		tokenServices.setClientId(clienid);
		tokenServices.setClientSecret(clientsecret);
		tokenServices.setCheckTokenEndpointUrl(checktokenuri);
		return tokenServices;
	}
}
