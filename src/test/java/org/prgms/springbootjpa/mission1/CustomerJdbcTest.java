package org.prgms.springbootjpa.mission1;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerJdbcTest {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:test";
    static final String USER = "sa";
    static final String PWD = "";

    static final String DROP_TABLE_SQL = "DROP TABLE customers IF EXISTS";
    static final String CREATE_TABLE_SQL = "CREATE TABLE customers (id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))";

    static final String INSERT_SQL = "INSERT INTO customers(id, first_name, last_name) VALUES (1, 'hyeonji', 'park')";
    static final String SELECT_SQL = "SELECT * FROM customers WHERE id = 1";
    static final String UPDATE_SQL = "UPDATE customers SET first_name = 'hyeonz' WHERE id = 1";
    static final String DELETE_SQL = "DELETE FROM customers WHERE id = 1";

    static Connection connection;
    static Statement statement;
    static ResultSet resultSet;

    @BeforeAll
    static void setUp() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, PWD);

        statement = connection.createStatement();
        statement.executeUpdate(DROP_TABLE_SQL);
        statement.executeUpdate(CREATE_TABLE_SQL);
    }

    @AfterAll
    static void close() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    @Order(1)
    @DisplayName("customer를 추가할 수 있다.")
    void save() throws SQLException {
        int row = statement.executeUpdate(INSERT_SQL);

        assertThat(row, is(1));
    }

    @Test
    @Order(2)
    @DisplayName("id로 customer를 조회할 수 있다.")
    void findById() throws SQLException {
        resultSet = statement.executeQuery(SELECT_SQL);

        while (resultSet.next()) {
            String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
            assertThat(fullName, is("hyeonji park"));
        }
    }

    @Test
    @Order(3)
    @DisplayName("customer를 수정할 수 있다.")
    void update() throws SQLException {
        int row = statement.executeUpdate(UPDATE_SQL);

        resultSet = statement.executeQuery(SELECT_SQL);

        while (resultSet.next()) {
            String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
            assertThat(fullName, is("hyeonz park"));
        }

        assertThat(row, is(1));
    }

    @Test
    @Order(4)
    @DisplayName("id로 customer를 삭제할 수 있다.")
    void deleteById() throws SQLException {
        int row = statement.executeUpdate(DELETE_SQL);
        boolean checked = false;

        resultSet = statement.executeQuery(SELECT_SQL);

        while (resultSet.next()) {
            checked = true;
        }

        assertThat(row, is(1));
        assertThat(checked, is(false));
    }
}
