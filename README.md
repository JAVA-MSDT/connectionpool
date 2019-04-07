# connectionpool
Java connection pool for JDBC, SQL

// Before you start running the program you need to make sure that you have a sql installed in your machine.

 // you nee to add your oqn password for the database in the class DBData in the util package.
            
Connection pooling is a mechanism to create and maintain a collection of JDBC connection objects. 

The primary objective of maintaining the pool of connection object is to leverage re-usability. 

A new connection object is created only when there are no connection objects available to reuse. 

This technique can improve overall performance of the application.

Establishing a database connection is a very resource-intensive process and involves a lot of overhead. 

Moreover, in a multi-threaded environment, opening and closing a connection can worsen the situation greatly. 

To get a glimpse of what actually may happen with each request for creating new database 

Order of Creating the pool:

1 - inside file pom.xml you need to add the following dependency ( mysql-connector-java ) check the file.

2 - Create the entity that you want to store in the Sql database.

3 - create SQL script file for creating and inserting the entity to the databse.

4 - Create class DBData to hold constants for your database information.

5 - Create DBConnector to initialize a connection with the database.

6 - Create ProxyConnection that will implement Connection interface which will give us a lot of functionality to use later.

7 . Create ConnetionPool as a singleton.

8 - Exception is up to you to create or not.

** That how i created after reading and searching over the internet for such a connection.


