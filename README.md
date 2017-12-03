# webappquickstart


## Build
```
mvn clean install -DskipTests
```

## Start services
* ConfigServer
* RegistryServer
* OAuth2Server
* AuthCodeApplication
* CreditCardTransaction

## Test
### Access Resource using OAuth2 token
1. Use grant type = password
   * curl http://anyclient:clientsecret@localhost:9999/uaa/oauth/token -d "password=user_abc&username=user_abc&grant_type=password&scope=openid"
   * curl -X GET -H "Authorization: Bearer <access_token>" -H "Content-Type: application/json" -d '{"cardNumber":"123456","paymentReason":"noreason","moneyAmount":2.0,"transactionDt":"2017-07-13 10:20:15"}' http://localhost:6000/transaction/authcode/xxxbank
2. use grant type = authorization_code
   * In browser go to http://localhost:9999/uaa/oauth/authorize?response_type=code&client_id=anyclient&redirect_uri=http://stupidurl
   * Fill user_abc/user_abc to login
   * Get "code" param from redirected page
   * curl http://localhost:9999/uaa/oauth/token -d "grant_type=authorization_code&client_id=anyclient&client_secret=clientsecret&redirect_uri=http://stupidurl&code=[code]"
   * curl -H "Authorization: Bearer <access_token>" -H "Content-Type: application/json" -d '{"cardNumber":"123456","paymentReason":"noreason","moneyAmount":2.0,"transactionDt":"2017-07-13 10:20:15"}' http://localhost:6000/transaction/authcode/xxxbank

### Microservices call using client_credentials
Microservices automatically sign in OAuth2 server and authenticate using client_credentials
```
curl "http://localhost:7000/settlement/any"
```
3. Via API Gateway
  * In Firefox open Network Monitor (ctrl + shift + e)
  * Go to http://localhost:4000/transaction-service/transaction/authcode/xxxbank
  * API Gateway automatically redirect to OAuth2 Server login (Because of unauthenticated)
  * Login with user_abc/user_abc
  * API Gateway automatically redirect to http://localhost:4000/transaction-service/transaction/authcode/xxxbank. Access success (even with error message saying bad status but it's OK)
  * Open Storage and delete APIGWSESSID cookie
  * Go to http://localhost:4000/transaction-service/transaction/authcode/xxxbank, API Gateway should get authorized automatically and go back to /transaction-service/transaction/authcode/xxxbank. This is because JSESSIONID of OAuth Server still valid so user no need to relogin.
  * Logout of OAuth2 server: http://localhost:9999/uaa/logout
  * Go to http://localhost:4000/transaction-service/transaction/authcode/xxxbank, access success because JSESSIONID of API Gateway still valid (due automatically login before logging out of OAuth2 Server)
  * Open Storage and delete APIGWSESSID cookie
  * Go to http://localhost:4000/transaction-service/transaction/authcode/xxxbank, login page should appear
  * For more details about OAuth2 flows, read more here: http://blog.monkey.codes/how-to-use-jwt-and-oauth-with-spring-boot/
  * TODO: Invalidate access_token after user logged out of OAuth2 Server