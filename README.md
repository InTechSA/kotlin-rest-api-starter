### A Kotlin Rest API starter kit
&nbsp;

#### Get project source


```shell
$ git clone https://github.com/InTechSA/kotlin-rest-api-starter.git
```

#### Build project using maven

```shell
$ cd kotlin-rest-api-starter/
$ mvn install
$ mvn test
```

#### Run Hello World

```shell
$ java -jar hello-world/target/hello-world-1.0-SNAPSHOT-jar-with-dependencies.jar
```

```shell
$ curl localhost:7000
```

#### Run basic user api example (supposing you have a mongodb instance running at localhost:27017)

```shell
$ java -jar user-api/target/user-api-1.0-SNAPSHOT-jar-with-dependencies.jar
```

```shell
$ curl localhost:7000/users
```

#### Stuff used to make this:

 * [Kotlin](https://kotlinlang.org/) : Statically typed programming language
 * [Javalin](https://javalin.io/) : Simple REST APIs for Java and Kotlin
 * [Kmongo](https://litote.org/kmongo/) : A Kotlin toolkit for Mongo
 * [KotlinTest](https://github.com/kotlintest/kotlintest) : Flexible and comprehensive testing tool for Kotlin
