package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface RoleDao {

    void addRole(Role role) throws  Exception;

    void removeRole(Role role) throws Exception;

    void updateRole(Role role) throws  Exception;

    List<Role> getAllRoles() throws Exception;

    Role getRoleById(Long id) throws  Exception;

}
