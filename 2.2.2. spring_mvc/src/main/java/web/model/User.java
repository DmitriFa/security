package web.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "age")
    private Byte age;

    @Column(name = "password")
    private String password;

    @JoinTable(
            name="users_roles"
            , joinColumns={
            @JoinColumn(name="users_id")
    }
            , inverseJoinColumns={
            @JoinColumn(name="roles_id")
    }
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(String name,String lastName,Byte age,String password,Set<Role>roles) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.roles =roles;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString(){
        return getId()+ getName()+ getLastName()+ getAge()+ getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return getLastName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role>roles) {
        this.roles = roles;
    }

}

