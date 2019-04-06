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
