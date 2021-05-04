A Spring Boot JPA implementation has been done with RESTful APIs to upload 
bulk records into the in-memory database H2 provided by Spring Framework. 
The rationale behind using some of the open source technologies is mentioned below:

Spring Data JPA Batch Inserts:
In order to improve performance and consistency in this project, multiple inserts are being batched into one. 
For bulk upload of data via multipart file I have set the batch size to 20 in the application.properties file. 
spring.jpa.properties.hibernate.generate_statistics=true enables the statistics to be displayed in the IDE console. 
When a file is uploaded and the call hits the POST endpoint, the API calls the saveAll() method of Spring Data JPA
which finds the batch size from the application properties and saves the large amount of data into the database in batches.
For the dow_jones_index file which consists 750 records, you should be able to see in your console that the data was
inserted into the database in 38 batches.

Handling Concurrency:
In order to provide highly-consistent data in a concurrent environment I have enabled Transaction Locks in spring Data JPA.
When we are using Pessimistic Locking in a transaction and access an entity, it will be locked immediately. The transaction 
releases the lock either by committing or rolling back the transaction. To acquire a lock on an entity, we can annotate 
the target query method with a Lock annotation by passing the required lock mode type. This is an efficient way of 
avoiding dirty-reads when two transactions are trying to access the same database entity.

Spring Boot: 
Spring Boot helps developers to start coding right away without spending too much time on preparing and configuring 
the environment. It avoids writing lots of boilerplate Code, Annotations and XML Configuration. It provides many 
plugins to work with embedded and in-memory Databases very easily thus keeping the software solution robust and productive.

REST APIs:
REST APIs provide a great deal of flexibility. Data is not tied to resources or methods, so REST can handle multiple 
types of calls, and return different data formats. It uses HTTP methods for simplicity. Since it is Coupled with JSON,
it works better with data and offers faster parsing.

JPA:
Spring Boot JPA ORM approach has been used in order to avoid tight coupling between SQL querues and API data. It 
also permits the developer to work directly with objects rather than with SQL statements. This makes the code more 
maintainable and reusable. The mapping between Java objects and database tables is defined via JPA persistence metadata.

H2 Database:
Since it is an in-memory database it gets created on application atartup and destryoyed when the application is stopped. It
required very little configuration to connect with Spring Boot application, and also provides a web console to access
and maintain the database. The console can be accessed at http://localhost:8080/h2-console/

JUnit:
It allows us to write and run repeatable automated tests.JUnit can be employed to test a hierarchy of program 
code singularly or as multiple units.

Lombok: 
The Lombok Java library tool has been used to generate code for minimizing boilerplate code. 
The library replaces boilerplate code with easy-to-use annotations.


To be included in future iterations:
1. Using postgreSQL instead of H2 database. Instead of installing PostgreSQL directly in our machine,
use Docker to make the database disposable. Like this, if we need a newer version of PostgreSQL, or if we need
a completely different database server, we won't have to update or uninstall PostgreSQL.
2. Using a CSV parser library to measure the performance of bulk uploads.
3. Using Hibernate EntityManager with explicit flushing and clearing of batched data for further 
improving performance of bulk uploads.
4. Using ExecutorService for handling concurrency.




