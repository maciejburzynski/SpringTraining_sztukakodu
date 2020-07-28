package pl.maltoza.tasks.entity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class TasktreeSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/restapi/projects/**").authenticated()
            .antMatchers("/restapi/tasks/**").authenticated()
            .antMatchers("/").permitAll()
            .and()
            .formLogin();
    }
}
