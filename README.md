# Crowdevents

Crowdevents is a crowdfunding system for organizing and holding events.

## Getting Started

This is a resource and authorization server written in Java that doesn't have any GUI. For React web application see [crowdevents-web](https://github.com/Seregy/crowdevents-web).

### Prerequisites

To clone, build and run this application, you'll need [Git](https://git-scm.com/), [JDK](http://www.oracle.com/technetwork/java/javase/overview/index.html) 9 or newer and [Maven](https://maven.apache.org/). Depending on launch method you may also need [Docker](https://www.docker.com/) and [PostgreSQL](https://www.postgresql.org/).


### Method #1: Launch with Docker

You can use [Docker](https://www.docker.com/) to launch the application. Launch Docker and execute commands from your command line:

```bash
# Clone the repository
$ git clone https://github.com/Seregy/crowdevents

# Go into the repository
$ cd crowdevents

# Package the application
$ mvn install

# Launch docker containers
$ docker-compose up
```

Now you should have 2 running docker containers: crowdevents-app(with application deployed to Tomcat) and crowdevents-db(with PostgreSQL database).

### Method #2: Launch as jar with in-memory database

From your command line:

```bash
# Clone the repository
$ git clone https://github.com/Seregy/crowdevents

# Go into the repository
$ cd crowdevents

# Package the application
$ mvn install

# Go into directory with created war
$ cd target

# Launch the application
$ java -jar "-Dspring.profiles.active=development" "-Dfile.encoding=UTF-8" crowdevents.war
```

### Method #3: Launch as jar with PostgreSQL database 

For this method you'll need your own PostgreSQL database up and running. You also need to create tables for OAuth authorization server(see [oauth-schema.sql](https://github.com/Seregy/crowdevents/tree/master/docker/db/scripts/oauth-schema.sql) for example).

From your command line:

```bash
# Clone the repository
$ git clone https://github.com/Seregy/crowdevents

# Go into the repository
$ cd crowdevents

# Package application
$ mvn install

# Go into directory with created war
$ cd target

# Replace values of environment variables if needed and launch the application
$ java -jar "-Dspring.profiles.active=postgres" "-Dfile.encoding=UTF-8" "-DPOSTGRES_USER=server-user" "-DPOSTGRES_PASSWORD=server-pass" "-DPOSTGRES_DB=crowdevents" "-DPOSTGRES_HOSTNAME=127.0.0.1" "-DPOSTGRES_PORT=5432" crowdevents.war
```

### Using the application

After launching the application server should be running on [localhost:8080](http://127.0.0.1:8080/).

Main API endpoints:
 - [/categories](http://127.0.0.1:8080/v0/categories)
 - [/comments](http://127.0.0.1:8080/v0/comments)
 - [/persons](http://127.0.0.1:8080/v0/persons)
 - [/projects](http://127.0.0.1:8080/v0/projects)

OAuth endpoint for getting access token:
 - [/oauth/token](http://127.0.0.1:8080/oauth/token)

## Built With

* [Java](https://java.com/en/) - Programming language
* [Spring](https://spring.io/) - Main application framework
* [Hibernate](http://hibernate.org/) - ORM framework
* [Maven](https://maven.apache.org/) - Dependency management
