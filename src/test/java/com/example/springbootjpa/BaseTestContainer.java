package com.example.springbootjpa;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseTestContainer {
    private final static String MYSQL_VERSION = "mysql:8";
    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer(MYSQL_VERSION);
}
