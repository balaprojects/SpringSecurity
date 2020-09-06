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
      - Technically it is about deermining whether the pricipal is who they say they are.
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
   1. 
