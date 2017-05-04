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
Execute the following command from the parent directory:
```
mvn clean install
```

### Run
To run the menagerie application from the terminal,
```
java -jar service/target/swagger-deepdive-service-1.0.jar
```

[travis-badge]: https://travis-ci.org/indrabasak/swagger-deepdive.svg?branch=master
[travis-badge-url]: https://travis-ci.org/indrabasak/swagger-deepdive/