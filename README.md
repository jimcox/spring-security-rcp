## Project Synopsis

This project is an example in how to architect a Swing Client that accesses services over HTTP that are 
secured by [Spring Security](http://projects.spring.io/spring-security/).

The Swing client is a simple app that retrieves the current Date from the server when a JButton is selected.  The server provides a trivial date service that is only accessible to authenticated users.

## Motivation

You will find more than a few existing Swing Client demos/examples that access services secured by Spring Security.  I've found the pattern to be more or
less the same: Capture the users credentials in the client application and include them in the header with every service request.  Spring Security is then configured on the back end with Basic or Digest Authentication to parse the headers and perform an authentication when needed.

This project is different in that it emulates the more familiar Spring Security browser authentication flow.  An unauthenticated user who accesses a secure resource that responds with a 403 is presented with an authentication dialog.  Successful authentications will then replay the original request, ala form based login.  The authentication token is stored as a cookie by [HttpClient](https://hc.apache.org/httpcomponents-client-ga/index.html) allowing the user to make additional service requests until the session times out.   

## Secondary Motivations

I was also interested in using JAVA 8 language features with Swing.  There's not a lot of other examples out there.  Using Producer and Consumer lambdas fits in very well with tried and true SwingWorker.

There is also a web client that provides access to the Date service.  The primary motivation here was to show how a legacy Swing application and a web client could co-exist and leverage a common service tier within the same security framework.  

## Authentication Flow

1. The user selects the "Get Server Time" button causing the ServiceInvocator to send an HTTP request to the backend.
2. Spring Security intercepts the request for a secure resource on the server and responds with a 403.
3. The ServiceInvocator interprets the response and launches the LoginDialog. 
4. The user enters credentials and sends a request to the unprotected RealSecurityService.
5. The RealSecurityService invokes authenticate on Spring's AuthenticationManager.
6. Successful authentications are placed in the SecurityContextHolder so Spring Security will propogate them to the Session.
7. The server responds to the client with a valid Authentication
8. The LoginDialog will replay the original failed request to get the Server Time with a newly created session cookie.
9. Spring Security is able to retrieve the user out of the Session and allows the request to pass to the RealDateService.
10. The RealDateService responds with the current date/time.
11. The Consumer from the inital request updates the panel with the date/time returned from the server.

### Alterante Flows

* If the user enters bad credentials at step 5, the client will display a JDialog with the authentication error and allow the user to retry the authentication within the same LoginDialog. 
* The user can abandon the authentication attempt at step 4 by selecting cancel on the LoginDialog thus ending the flow.


## Building and Running the Example Project

All projects are built using [Gradle](http://gradle.org/).  JAVA 8 is required.  The projects are Eclipse friendly and should be easy to import using the Gradle Plugin.

There are several ways to run the server: you can build and deploy the war file to any servlet container, or you can use the gradle plugin to run under Tomcat or Jetty. Regardless, if your deployment URL + context is anything other than http://localhost:8080/web, you will need to make additional configuration changes.

I would suggest starting with something simple such as 

```
gradle tomcatStartWar
```

And to stop the server

```
gradle tomcatStop
```

The Swing client can be launched from within the client project with 

```
gradle run
```

## Credentials

| user      | password  |
|-----------|-----------|
| guest     | password  |

## Tests

There are several tests that use Mockito to verify expected behavior.  There is also a MockMVC-HTMLUnit test that exercises
the web client.


## Things That Are Not Implemented Here
* All traffic in a 'real' application should be over SSL/TLS
* There is no Logout functionality

## License

Copyright 2016 James Cox <james.s.cox@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.




