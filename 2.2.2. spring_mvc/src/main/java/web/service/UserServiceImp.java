package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService {

   private UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImp() {
    }

    public void addUser(User user) throws Exception {
        userDao.addUser(user);
    }

    public void removeUser(User user) throws Exception {
        userDao.removeUser(user);
    }

    public void updateUser(User user) throws Exception {
        userDao.updateUser(user);
    }
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() throws Exception {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public User getUserById(long id) throws Exception {
     // return getAllUsers().get(id);
      return userDao.getUserById(id);
    }
    @Override
    @Transactional
    public boolean checkLastName(String lastName) throws Exception {
        return userDao.checkLastName(lastName);
    }

  /*  @Override
    @Transactional
    public UserDetails loadUserByUsername(String lastName) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userDao.getUserByName(lastName);
        } catch (Exception e) {
            e.printStackTrace();
        }

            return user;
    }*/
}
