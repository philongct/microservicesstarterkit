# webappquickstart

## Prequisites
Need JDK >=8. To enable HTTP2 client, require JDK 9.
In Java 9, start jar files with:
```
java -jar --ad-modules java.se.ee
```


## Build 
```
mvn clean install -DskipTests
```

## Start services
* ConfigServer
* RegistryServer
* OAuth2Server
* ApiGateway
* AuthCodeApplication
* CreditCardTransaction

Notice: In this example I'm using HTTP2 (via HTTPS with a self-signed certificate) for Microservices RPCs, HTTPS is disabled for Config Server, Registry Server, OAuth2Server.

## Test
### Access Resource using OAuth2 token
1. Use grant type = password
   * curl http://anyclient:clientsecret@localhost:9999/uaa/oauth/token -d "password=user_abc&username=user_abc&grant_type=password&scope=openid"
   * curl -k -I -H "Authorization: Bearer <access_token>" https://localhost:6000/authcode/merchantId
2. use grant type = authorization_code
   * In browser go to http://localhost:9999/uaa/oauth/authorize?response_type=code&client_id=anyclient&redirect_uri=http://stupidurl
   * Fill user_abc/user_abc to login
   * Get "code" param from redirected page
   * curl http://localhost:9999/uaa/oauth/token -d "grant_type=authorization_code&client_id=anyclient&client_secret=clientsecret&redirect_uri=http://stupidurl&code=[code]"
   * curl -k -I -H "Authorization: Bearer <access_token>" https://localhost:6000/authcode/merchantId
3. Via API Gateway
   * In Firefox open Network Monitor (ctrl + shift + e)
   * Go to https://localhost:4000/authcode-service/authcode/amerchant
   * API Gateway automatically redirect to OAuth2 Server login (Because of unauthenticated)
   * Login with user_abc/user_abc
   * API Gateway automatically redirect to https://localhost:4000/authcode-service/authcode/amerchant. Access success
   * Open Storage and delete APIGWSESSID cookie
   * Go to https://localhost:4000/authcode-service/authcode/amerchant, API Gateway should get authorized automatically and go back to /authcode-service/authcode/amerchant. This is because JSESSIONID of OAuth Server still valid so user no need to relogin.
   * Logout of OAuth2 server: http://localhost:9999/uaa/logout
   * Go to https://localhost:4000/authcode-service/authcode/amerchant, access success because APIGWSESSID is still valid (due automatically login before logging out of OAuth2 Server)
   * Open Storage and delete APIGWSESSID cookie
   * Go to https://localhost:4000/authcode-service/authcode/amerchant, login page should appear
   * For more details about OAuth2 flows, read more here: http://blog.monkey.codes/how-to-use-jwt-and-oauth-with-spring-boot/
   * TODO: Invalidate access_token after user logged out of OAuth2 Server
  
### Microservices call using client_credentials
Microservices automatically sign in OAuth2 server and authenticate using client_credentials
```
curl -k "https://localhost:7000/transactions/any"
```