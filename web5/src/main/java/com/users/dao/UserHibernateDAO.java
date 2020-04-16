package com.users.dao;

import com.users.exception.DBException;
import com.users.model.User;
import com.users.util.DBHelper;
import org.hibernate.*;

import java.sql.SQLException;
import java.util.List;

public class UserHibernateDAO implements UserDao {

    private SessionFactory sessionFactory = DBHelper.getInstance().getSessionFactory();

    public UserHibernateDAO() {

    }

    @Override
    public void createTable() throws DBException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("create table users (id bigint not null auto_increment, name varchar(255), password varchar(255), role varchar(255), surName varchar(255), primary key (id))");
        transaction.commit();
        session.close();
    }

    @Override
    public User getUserByName(String name) throws SQLException, DBException {
        User user = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("SELECT id FROM User WHERE name =:name");
            query.setParameter("name", name);
            List<Long> id = query.list();
            user = (User) session.get(User.class, id.get(0));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                throw new DBException(e);
            }
        }
        return user;
    }

    @Override
    public List<User> getAllUser() throws SQLException, DBException {
        Session session = null;
        List<User> userList = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            userList = session.createQuery("FROM User").list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                throw new DBException(e);
            }
        }
        return userList;
    }

    @Override
    public void addUser(User user) throws SQLException, DBException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                throw new DBException(e);
            }
        }
    }

    @Override
    public void deleteUser(User user) throws SQLException, DBException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE User WHERE id=:id");
            query.setParameter("id", user.getId());
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                throw new DBException(e);
            }
        }
    }

    @Override
    public void updateUser(User user) throws SQLException, DBException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                throw new DBException(e);
            }
        }
    }
}
