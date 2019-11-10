# Hotel Manager Modular Monolith

Project is Java implementation of concept described in Udi Dahan's "Advanced Distributed Systems Design using SOA & DDD" course.

[http://udidahan.com/training/](http://udidahan.com/training/)

## Key assumptions

Modules should not use request-response pattern to communicate between themselves.
The only part where request-response is allowed is itops module responsible for security and cross-domain processes which requires collecting data from many modules.
Each module has it's own frontend component and only this component can present and modify modules data.

## Module separation

To achieve separation of modules package-private accessor was used. 
Most of the classes has package private accessor which guarantees that they won't be used outside of the module.
Only events, DTOs and providers required by itops module are public.

## Modules

#### Reservation
Responsible for reserving a room, searching available rooms and searching for "no-show" reservations
#### Marketing
Holds and displays room types descriptions
#### Payment
Stores payment data and supports payment successes and failures
#### Guest
Stores guests information and supports searching by guest last name
#### Rate
Delivers information about rates for given room type in particular point of time
#### Occupancy
Supports check-in process and physical room allocation
#### ITOPS
Communicates with external service to charge card. Collects all of the required data using providers delivered by other modules.

## Prerequisites

```
Java 1.8 or higher
nodejs + npm
gradle
```

## Build process
1. Npm frontend build by gradle incremental task, build is started only when sources or package.json changes
2. Gradle copy of frontend project into static resources of spring boot aplication
3. Gradle creates bootJar

## Running

```
gradle bootRun
http://localhost:8080
```

## Tests

Project contains only backend tests

```
gradle test
```

## Built With

* [Gradle](https://gradle.org/) - Dependency Management

## Author

* **Tomasz Tokarczyk** (https://github.com/tomek39856)