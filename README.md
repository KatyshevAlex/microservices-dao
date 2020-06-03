# microservices-dao
DAO layer in an application that built as a microservices system.

Service is protected by Spring Security.(spring-boot-starter-security)

Other services can get access by useing @FeingClient with authentication (spring-cloud-starter-openfeign)

Service uses PostgreSQL, JPA (spring-boot-starter-data-jpa | postgresql)

Service registers itself as Eureka client (spring-cloud-starter-netflix-eureka-client)

Service doesn't have any test cases except manual ones during code writing. Sorry I'm ashamed...
