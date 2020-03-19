# Getting Started
This is an implementation of a TinyURL service using SpringBoot and PostGRES.
The service and the DB are expected to exist in the same system.
Right now, an incoming URL is used to create a SHA1 and is stored in the DB
alongwith a short code that is created internally.

There are many TODOs:
* SHA1 is used to deduplicate URLs. Is there a better way?
* A short code of length=8 is generated and stored along with the SHA1 and the original URL.
  The short code is quite predictable because it goes in a sequence.
* Once the system restarts, the short codes start from the first again. This is not good.
**** Move the short code to its own service?
**** Randomize the short codes that are retrieved
* Fetching the original URL needs to happen via the short code. Right now, we need the SHA1.
* Redirect the short URL to the stored (long) URL
* Security
* Validate the inputs and sanitize the URLs
* Create a Front-end to complete the functionality 
* More...

To run the service:
./gradlew -s bootRun

For a POST use:
curl -XPOST -H "Content-Type: application/json" --data '{"url":"http://firstpenguin.org/somereallylongurlthat_cannot-be-easliy-shared/120"}' http://localhost:8080/short
which returns:
{"id":"65cc4db42cdd40c4e2c60f280b954d662cf08e4e","url":"http://firstpenguin.org/somereallylongurlthat_cannot-be-easliy-shared/120","shortUrl":"aaaaaaas"}

For a GET use something similar to:
curl -XGET http://localhost:8080/short/65cc4db42cdd40c4e2c60f280b954d662cf08e4e
The 65cc4db42cdd40c4e2c60f280b954d662cf08e4e is obtained from the POST call above.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/gradle-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data Redis (Access+Driver)](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#boot-features-redis)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#production-ready)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

