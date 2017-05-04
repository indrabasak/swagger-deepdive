[![Build Status][travis-badge]][travis-badge-url]

![](./img/menagerie.jpg)

Swagger Deep Dive
======================
This is an example Springfox example project to explore different features of Swagger and Springfox in depth. 
The project consist of multiple endpoints of different animals and birds which are part of a menagerie. According to wikipedia, 
"A menagerie is a collection of captive animals, frequently exotic, kept for display; or the place where such a 
collection is kept, a precursor to the modern zoological garden."

The menagerie project consists of multiple controllers exposing different REST endpoints: `ElephantController`, 
`LionController`, `ParrotController`, `TigerController`, and `ToucanController`. The backend services are supported by 
HSQLDB, an in-memory database, and JPA.

The menagerie application is based on Springfox 2.6.1 version. You can find more about the different Swagger 
customizations of this project [here.](https://github.com/indrabasak/swagger-deepdive/wiki)

### Build
Execute the following command from the parent directory to build the project:
```
mvn clean install
```

### Run
To run the menagerie application from the terminal,
```
java -jar service/target/swagger-deepdive-service-1.0.jar
```

You should notice the application starting:
```
 ███╗   ███╗ ███████╗ ███╗   ██╗  █████╗   ██████╗  ███████╗ ██████╗  ██╗ ███████╗
 ████╗ ████║ ██╔════╝ ████╗  ██║ ██╔══██╗ ██╔════╝  ██╔════╝ ██╔══██╗ ██║ ██╔════╝
 ██╔████╔██║ █████╗   ██╔██╗ ██║ ███████║ ██║  ███╗ █████╗   ██████╔╝ ██║ █████╗
 ██║╚██╔╝██║ ██╔══╝   ██║╚██╗██║ ██╔══██║ ██║   ██║ ██╔══╝   ██╔══██╗ ██║ ██╔══╝
 ██║ ╚═╝ ██║ ███████╗ ██║ ╚████║ ██║  ██║ ╚██████╔╝ ███████╗ ██║  ██║ ██║ ███████╗
 ╚═╝     ╚═╝ ╚══════╝ ╚═╝  ╚═══╝ ╚═╝  ╚═╝  ╚═════╝  ╚══════╝ ╚═╝  ╚═╝ ╚═╝ ╚══════╝

Running Menagerie: An Springfox/Swagger Deep Dive project.
2017-05-03 22:33:23.677  INFO 71259 --- [           main] c.b.e.m.boot.MenagerieApplication        : Starting MenagerieApplication on mycomputer with PID 71259
...

2017-05-03 22:34:32.104  INFO 71274 --- [           main] .d.s.w.r.o.CachingOperationNameGenerator : Generating unique operation named: updateUsingPUT_4
2017-05-03 22:34:32.172  INFO 71274 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-05-03 22:34:32.178  INFO 71274 --- [           main] c.b.e.m.boot.MenagerieApplication        : Started MenagerieApplication in 7.228 seconds (JVM running for 7.778)
```

[travis-badge]: https://travis-ci.org/indrabasak/swagger-deepdive.svg?branch=master
[travis-badge-url]: https://travis-ci.org/indrabasak/swagger-deepdive/