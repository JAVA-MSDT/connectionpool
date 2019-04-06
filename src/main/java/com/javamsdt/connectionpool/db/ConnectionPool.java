package com.javamsdt.connectionpool.db;

import com.javamsdt.connectionpool.util.DBData;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class ConnectionPool {

    private final static int POOL_SIZE = 5;
    private final static int ATTEMPTS_LIMIT = 5;
    private static LinkedBlockingQueue<ProxyConnection> connectionQueue;

    private static Logger logger = LogManager.getLogger();
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConnectionPool instance;

    /**
     * Logic of creating the pool, also attempting to create it for a several time if it is fails from the first time.
     *
     * @throws ConnectionPoolException if the attempts of creating the pool exceds the ATTEMPTS_LIMIT value.
     */
    private ConnectionPool() throws ConnectionPoolException {

        logger.log(Level.INFO, "Connecting to DataBase......");
        int currentPoolSize = 0;
        int attemptsCount = 0;
        connectionQueue = new LinkedBlockingQueue<>(POOL_SIZE);
        while (currentPoolSize < POOL_SIZE && attemptsCount < ATTEMPTS_LIMIT) {
            try {
                ProxyConnection proxyConnection = new ProxyConnection(DBConnector.getConnection());
                connectionQueue.offer(proxyConnection);
                currentPoolSize++;
            } catch (SQLException e) {
                attemptsCount++;
                logger.log(Level.WARN, "Can't get Connection.....", e);
            }
        }
        if (attemptsCount == ATTEMPTS_LIMIT) {
            throw new ConnectionPoolException("Attempting to connect the database several time fails.");
        }

    }

    /**
     * @return instance of the pool.
     */
    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (!isCreated.get()) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                    logger.log(Level.INFO, "Pool Created successfully.");
                }
            } catch (ConnectionPoolException e) {
                logger.log(Level.FATAL, "Can not create pool", e);
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     *
     * @return connection for =m the connectionQueue pool.
     * @throws ConnectionPoolException if we Can't get the connection from the connectionQueue.
     */
    public ProxyConnection getConnetion() throws ConnectionPoolException {
        ProxyConnection connection = null;

        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Can't get the connection from the connectionQueue", e);
        }
        return connection;
    }


    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DBData.DB_DRIVER);
            connection = DriverManager.getConnection(DBData.DB_URI + DBData.DB_NAME, DBData.LOGIN, DBData.PASSWORD);
        } catch (ClassNotFoundException e) {
            logger.log(Level.ERROR, e);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void returnConnection(ProxyConnection proxyConnection) {
        connectionQueue.offer(proxyConnection);
    }

    public static void closePool(){
        int attemptsCount = 0;
        while (!connectionQueue.isEmpty() && attemptsCount < ATTEMPTS_LIMIT){
            try {
                connectionQueue.take().closeConnection();
            } catch (SQLException e) {
                attemptsCount++;
                logger.log(Level.WARN,"Can not close the connection", e);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Can not close the pool", e);
            }
        }
        if(!connectionQueue.isEmpty()){
            logger.log(Level.ERROR, "Can not close the pool");
        }
    }
}
