package web.dao;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    EntityManager em;

    public UserDaoImp() {
    }

    @Override
    @Transactional
    public void addUser(User user) throws HibernateException {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        em.persist(user);
    }


    @Override
    @Transactional
    public void removeUser(User user) throws HibernateException {
        em.remove(getUserById(user.getId()));
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLastName().equals(user.getLastName())) {
                user.setRoles(users.get(i).getRoles());

            }
        }
        if (user.getPassword().length() != 60) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            em.merge(user);
        }
        else {
            em.merge(user);
        }
    }
    @Override
    @Transactional
    public List<User> getAllUsers() throws HibernateException {
        return em.createQuery("from User").getResultList();
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        return (User) em.find(User.class, id);
    }

    @Override
    @Transactional
    public User getUserByName(String lastName) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLastName().equals(lastName)) {
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean checkLastName(String lastName) {
        Query query;
        query = (Query) em.createQuery("from User where lastName = :lastName");
        query.setParameter("lastName", lastName);
        return query.list().isEmpty();

    }
}

