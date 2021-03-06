# SpringSecurity
- Frank P Moley Tutorial, Exercises done are uploaded.

### Spring Security Concepts
>Introduction to Spring Security
  1. Good Security Layers
      - Hardware and media - Physical security
      - Network - Transportation layer security, Firewall etc
      - Operating System - ACL, Security patches etc
      - Application - Proper handling of data, authentication and authorization etc.
  2. What is Spring Security
      - Provides great abstractions in J2EE applications space.
      - It solves issues like portability and vendor lock-in.
      - Provides the authentication (who) and authorization (what)
>Understanding Authentication and Authorization
  1. Authentication
      - Determination of who.
      - Technically it is about determining whether the pricipal is who they say they are.
      - Principals can be humans or machines.
  2. Authentication Support
      - HTTP Basic, Digest, x509, Form Based Authentication.
      - LDAP and Active directory.
      - OpenID, Jasig CAS and JAAS
      - Kerberos and SAML
  3. Authorization
      - Determines what the principal can do.
      - Authorization is based on Authentication.
      - Authorization is also called access control.
  4. Authorization Support
      - Web Request
      - Method invocation
      - Domain Object instance access control
>Spring Security Projects
  1. Overview
      - Spring is broken up into many projects.
      - Projects themselves are often broken up.
      - Spring security is no exception.
      - The project are defined so you grab only what you need.
  2. Most Common Projects
      - spring-security-core - Core classes for authentication and authorization
      - spring-security-config - Need to for XML or Java configuration
      - spring-security-web - For servlet based apps
      - spring-security-test
  3. Specific Projects
      - spring-security-ldap
      - spring-security-oauth2-core
      - spring-security-oauth2-client
      - spring-security-openid
  4. Less Common Projects
      - spring-security-oauth2-jose - provides support for javascript object signing.
      - spring-security-remoting
      - spring-security-cas
      - spring-securty-acl
### Securing Web Application with Spring
>Implementing Basic Authentication
   1. Add dependency spring-boot-starter-security which by default provides form-based authentication with user id as 'user' and password being printed in the console as a UUID.
   2. To implement http basic authentication, we need to create a class that extends 'WebSecurityConfigurerAdapter',add @EnableWebSecurity and override method 'configure(HttpSecurity ..)'.<br>
        <img src="https://github.com/balaprojects/images/blob/master/HttpBasic.png"/>
>In-Memory Authentication
   1. 'UserDetailsservice' bean should be created using 'InMemoryUserDetailsManager' class.<br>
       <img src = "https://github.com/balaprojects/images/blob/master/InMemory_UserConfig.png"/>
>JDBC Authentication
   1. For Demo purpose we can add 'H2' and 'JPA' dependencies to pom. Create schema.sql and data.sql for USER table.
   2. Create a entity named 'User' that maps to USER table.
   3. Create JPARepository for 'User' entity.
   4. Create a class that extends 'UserDetails' and takes 'User' bean in constructor.
   5. Create a class that extends 'UserDetailsService' and override method 'loadUserByUserName()'. Autowire repository bean and call the method to load the user from database.
   6. Create a bean for class 'DaoAuthenticationProvider' that takes 'UserDetailsService' and 'PasswordEncoder'.
   7. In configurer class, override 'configure(AuthenticationManagerBuilder ..)' that takes provider.
>Leveraging BCrypt for hashing
   1. There is no reason for a password to be encrypted. So, never encrypt.
   2. Hashing is the preferred technique. MD5 is dead, SHA-256 is breakable, BCrypt is the best in market today.
>Authorization
   1. A 'AUTH' table is created and roles for users are added to this table.
   2. Create an entity class that maps to AUTH table.
   3. Create JPARepository for above entity class.
   4. Modify the custom 'UserDetails' class to take AUTH entity and set the GrantedAuthorities based on it.
   5. Add @EnableGlobalMethodSecurity(prePostEnabled = true).
   6. Add @PreAuthorize("hasRole('ROLE_ADMIN')") before methods. Roles are prefixed with ROLE_ as a convention.
      ![](https://github.com/balaprojects/images/blob/master/Method_Authorize.png)
   7. To prefix ROLE_ to the roles fetched from DB, we need to create a bean 'GrantedAuthoritiesMapper()'.
      ![](https://github.com/balaprojects/images/blob/master/Authorities_Mapper.png)
   8. Set the above mapper to authentication provider.
>Form Based Authentication
   1. Cannot support logging off.
   2. No risk of security implications with either, assuming TLS is used.
   3. Form based allows you to customize form, seamless with applcation and provides logout. Also, provides inherent remember-me feature.
      ![](https://github.com/balaprojects/images/blob/master/Form-Login.png)
### LDAP Authentication
>LDAP for Authentication
   1. Why LDAP?
      - Built into may operating systems.
      - Interoperable
      - Scalable
   2. Spring Security LDAP
      - spring-security-ldap
      - Full support for native LDAP operations.
      - Password Hashing algorithms included.
>Implementing LDAP Authentication
   1. Configure Embedded LDAP for demo purpose.
   2. In configurer, override method configure(AuthenticationManagerBuilder ..).
   3. Using the builder set the LDAP configurations like User Search DN, Group Search DN, Base DN, Context Source etc
      ![](https://github.com/balaprojects/images/blob/master/Ldap_Config.png)
### OAuth2 Authentication
>OAuth2
   1. What is OAuth2?
      - Protocol and framework for accessing HTTP services.
      - Often used for third-party access..
      - Can be used for system-to-system communication.
   2. Parts of OAuth2
      - Resource Owner - Often the user
      - Client - application requesting access
      - Resource Server - Hosts protected data and acoounts
      - Authoriztion Server - Service that grants tokens 
   3. Token Types
      - Access Token - the secret and often short liven token that identifies the user.
      - Renew Token - long lived token which gets renewed when access token expires.
      - Scopes - provides the rights associated with the token.
   4. Grant Types
      - There are several grant types.
      - Authorization code grant is the most common.
      - Implicit is common in web apps and mobile apps.
      - Client Credentials - system to system communication.
>Spring and OAuth2
   1. CommonOAuth2Provider
      - Used to connect with systems like okta, google, facebook, github and several other OAuth providers.
      - In Spring boot, we use this class by property based configuration.
      - Provides client side OAuth integration. It allows you to provide 3rd third party support for the log in operation.
   2. Authorization Server
      - Provides authentication and authorization services.
      - @EnableAuthorizationServer
      - AuthorizationServerConfigurerAdapter to do more configuration.
      - Supports various grant types.
   3. Resource Server
      - Provides the resources to be protected.
      - @EnableResourceServer
      - When you separate out Authorization and Resource server lot more configuration is required.
   4. OAuth2 Client
      - Full client side support often when you are not using facebook or github.
      - @EnableOAuth2Client
      - OAuth2RestTemplate provides much of the scaffolding. It manages all the tokens, manipulations and injecting header params.
      - Support for various grant types.
>Creating Authorization Server
   1. Add below dependency
      ```
      <dependency>
          <groupId>org.springframework.security.oauth</groupId>
          <artifactId>spring-security-oauth2</artifactId>
          <version>2.3.0.RELEASE</version>
      </dependency>
      ```
   2. Add @EnableAuthorizationServer on class that extends ***AuthorizationServerConfigurerAdapter***
   3. In configurer, override configure(AuthorizationServerSecurityConfigurer ..)
      ```
      @Override
      public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
            .checkTokenAccess("permitAll()")
            .tokenKeyAccess("permitAll()");
      }
      ```
  4. In configurer, override configure(ClientDetailsServiceConfigurer ..)
     ```
      @Override
      public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
            .inMemory()
            .withClient("bala_admin")
            .secret("secret")
            .scopes("READ", "WRITE", "DELETE")
            .autoApprove(true)
            .authorities("ROLE_ADMIN")
            .authorizedGrantTypes("client_credentials")
            .and()
            .withClient("bala_user")
            .secret("secret")
            .scopes("READ")
            .autoApprove(true)
            .authorities("ROLE_USER")
            .authorizedGrantTypes("client_credentials");
      }
     ```
  5. In configurer, override configure(AuthoriationServerEndpointsConfigurer ..)
     ```
      @Override
      public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(new InMemoryTokenStore());
      }
     ```
>Client-Side implementation of OAuth
   1. Add OAuthClient dependency in pom and add @EnableOAuth2Client on top of application class.
   2. Create ***OAuth2RestTemplate*** with below configurations.
      ```
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
      ```
   3. Now we can start our application and test the changes.

>OAuth with Third Party
   1. Basic flow
      - Authorize application
      - Setup redirect URL
      - Configure application (application.yml)
      - Run
   2. Example config
      spring:
        security:
          oauth2:
            client:
              registration:
                github:
                  client-id: ...
                  client-secret: ...
