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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import web.model.Role;

@Configuration
@EnableWebSecurity
//@ComponentScan(value = "web")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
  //  private  SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
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

    // Необходимо для шифрования паролей
    // В данном примере не используется, отключен
  //  @Bean
   // public static NoOpPasswordEncoder passwordEncoder() {
   //    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
   // }


/*   @Bean
   @Override
   protected UserDetailsService userDetailsService() {
       return new InMemoryUserDetailsManager(
               User.builder().username("admin")
                       // Use without encode first
                       .password(passwordEncoder().encode("admin"))
                       .roles(Role.ADMIN.name())
                       .build(),
               User.builder().username("user")
                       // Use without encode first
                       .password(passwordEncoder().encode("user"))
                       .roles(Role.USER.name())
                       .build()
       );
       // Go to UserDetailsServiceImpl - InMemory
   }*/

 /* public SecurityConfig(@Qualifier("UserServiceImp")UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
       this.userDetailsService = userDetailsService;
       //this.successUserHandler = successUserHandler;
   }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }


 /*  @Override
   protected void configure(HttpSecurity http) throws Exception {

               http.csrf().disable()
               .authorizeRequests()
            //   .antMatchers("/").permitAll()
                        .antMatchers(HttpMethod.GET, "/").hasRole(Role.ADMIN.name())
                           .antMatchers(HttpMethod.GET, "/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                           .antMatchers(HttpMethod.POST, "/**").hasRole(Role.ADMIN.name())
                          // .antMatchers(HttpMethod.POST, "/add/**").hasRole(Role.ADMIN.name())
                           .antMatchers(HttpMethod.DELETE, "/**").hasRole(Role.ADMIN.name())
               .anyRequest()
               .authenticated()
               .and().formLogin();
              // .successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправления по ролям
             //  .httpBasic();


   }*/



    @Bean
    protected PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder(12);
    }
}
