package web.dao;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    EntityManager em;

    public UserDaoImp() {
    }

    @Override
    @Transactional
    public void addUser(User user) throws HibernateException {
     //  Session session = em.unwrap(Session.class);
     //  session.save(user);
        em.persist(user);
    }


    @Override
    @Transactional
    public void removeUser(User user) throws HibernateException {
        em.remove(getUserById(user.getId()));
    }

    @Override
    @Transactional
    public   void updateUser(User user){
      //  Session session = em.unwrap(Session.class);
       // session.update(user);
        em.merge(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() throws HibernateException {
      //  Session session = em.unwrap(Session.class);
      // return session.createQuery("from User").list();
        return em.createQuery("from User").getResultList();
    }

   @Override
   @Transactional
   public User getUserById(long id){
       return (User) em.find(User.class, id);
   }
    @Override
    @Transactional
    public User getUserByName(String lastName){
        return (User) em.find(User.class, lastName);
    }

    @Override
    @Transactional
    public boolean checkLastName(String lastName) {
      //  Session session = em.unwrap(Session.class);
      //  Query query;
       // query = session.createQuery("from User where lastName = :lastName");
       // query.setParameter("title", lastName);
        //return query.list().isEmpty();
        Query query;
        query = (Query) em.createQuery("from User where lastName = :lastName");
        query.setParameter("lastName", lastName);
        return query.list().isEmpty();

    }
}

