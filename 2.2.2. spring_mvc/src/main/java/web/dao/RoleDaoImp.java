package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImp implements RoleDao {

     @PersistenceContext
     EntityManager em;

      public RoleDaoImp() {
     }

    @Override
    public void addRole(Role role) throws Exception {
          em.persist(role);
    }

    @Override
    public void removeRole(Role role) throws Exception {
        em.remove(getRoleById(role.getId()));
    }

    @Override
    public List<Role> getAllRoles() throws Exception {
         return em.createQuery("from Role").getResultList();
    }

    @Override
    public Role getRoleById(Long id) throws Exception {
         return (Role) em.find(Role.class, id);
    }
    @Override
    public   void updateRole(Role role){
        em.merge(role);

    }
}
