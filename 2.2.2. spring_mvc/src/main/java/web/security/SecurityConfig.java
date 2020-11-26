package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import web.dao.UserDao;
import web.model.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private  SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

      @Autowired
      public void setUserDetailsService(UserDetailsService userDetailsService) {
          this.userDetailsService = userDetailsService;
      }
  // @Override
  //  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  //      super.configure(auth);
  //  }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance()); // конфигурация для прохождения аутентификации
    }

   @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/").permitAll() // доступность всем
              //  .antMatchers("/user").access("hasRole('ROLE_USER')")
             //   .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
               // .antMatchers(HttpMethod.GET, "/").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.GET, "/**").access("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
                .antMatchers(HttpMethod.POST, "/**").access("hasRole('ROLE_ADMIN')")
              // .antMatchers(HttpMethod.POST, "/add/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/**").access("hasRole('ROLE_ADMIN')")
                // разрешаем входить на /user пользователям с ролью User
                .and().formLogin();  // Spring сам подставит свою логин форму
               // .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправления по ролям
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }



  /*public SecurityConfig(@Qualifier("UserDetailsServiceImp")UserDetailsService userDetailsService) {
       this.userDetailsService = userDetailsService;
       this.successUserHandler = successUserHandler;
   }*/


  /*  @Bean
    protected PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder(12);
   }*/
}
