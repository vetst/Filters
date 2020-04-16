package com.users.dao;

import com.users.exception.DBException;
import com.users.model.User;
import com.users.util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao implements UserDao {

    public MySqlUserDao() {

    }

    public User getUserByName(String name) throws SQLException, DBException {
        User user = new User();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBHelper.getInstance().getMysqlConnection();
            statement = connection.prepareStatement("SELECT * FROM users WHERE name =?");
            statement.setString(1, name);
            statement.execute();
            rs = statement.getResultSet();
            rs.next();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setSurName(rs.getString("surName"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
        } catch (Exception e) {
            throw new DBException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                throw new DBException(e);
            }
        }
        return user;
    }

    public List<User> getAllUser() throws SQLException, DBException {
        List<User> resultList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DBHelper.getInstance().getMysqlConnection();
            stmt = connection.createStatement();
            stmt.execute("select * from users");
            rs = stmt.getResultSet();
            while (rs.next()) {
                resultList.add(new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surName"),
                        rs.getString("password"),
                        rs.getString("role")));
            }
        } catch (Exception e) {
            throw new DBException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {

            }
        }
        return resultList;
    }

    public void addUser(User user) throws SQLException, DBException {
        String addQuery = "insert into users (name, surname, password, role) values (?, ?, ?, ?)";
        try (Connection connection = DBHelper.getInstance().getMysqlConnection();
             PreparedStatement statement = connection.prepareStatement(addQuery)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void deleteUser(User user) throws SQLException, DBException {
        String deleteQuery = "DELETE FROM users WHERE id =?";
        try (Connection connection = DBHelper.getInstance().getMysqlConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void updateUser(User user) throws SQLException, DBException {
        String updateQuery = "UPDATE users SET name =?, surname =?, password =?, role =? WHERE id =?";
        try (Connection connection = DBHelper.getInstance().getMysqlConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createTable() throws DBException {
        String createTable = "create table users (id bigint not null auto_increment, name varchar(255), password varchar(255), role varchar(255), surName varchar(255), primary key (id))";
        try (Connection connection = DBHelper.getInstance().getMysqlConnection(); PreparedStatement statement = connection.prepareStatement(createTable)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DBException(ex);
        }
    }
}
