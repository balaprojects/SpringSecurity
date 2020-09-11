package com.frankmoley.security.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.ArrayList;


@EnableOAuth2Client
@SpringBootApplication
public class GuestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestAppApplication.class, args);
	}

	private static final String TOKEN_URL = "/oauth/token";
	@Value("${landon.guest.service.url}")
	private String BASE_URL;

	@Bean
	public OAuth2RestTemplate restTemplate() {
		ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
		clientCredentialsResourceDetails.setAccessTokenUri(BASE_URL.concat(TOKEN_URL));
		clientCredentialsResourceDetails.setClientId("guest_app");
		clientCredentialsResourceDetails.setClientSecret("secret");
		clientCredentialsResourceDetails.setAuthenticationScheme(AuthenticationScheme.form);
		clientCredentialsResourceDetails.setScope(new ArrayList<String>() {{ add("READ_ALL_GUESTS"); add("WRITE_GUEST"); add("UPDATE_GUEST"); }});
		clientCredentialsResourceDetails.setGrantType("client_credentials");
		AccessTokenRequest accessTokenRequest = new DefaultAccessTokenRequest();
		return new OAuth2RestTemplate(clientCredentialsResourceDetails, new DefaultOAuth2ClientContext(accessTokenRequest));
	}

}
