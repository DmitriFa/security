package web.service;



import web.model.User;

import java.util.List;


public interface UserService {

    void addUser(User user) throws Exception;
    void removeUser(User user) throws Exception;
    void updateUser(User user)throws Exception;
    List<User> getAllUsers()throws Exception;
    User getUserById(long id)throws Exception;
    boolean checkLastName(String lastName)throws Exception;

}
