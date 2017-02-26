package ni.edu.ucem.webapi.auth;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
/*
 * Importa el módulo de seguridad web de spring.
 */
@EnableWebSecurity
/*
 *  Habilita las anotaciones @PreAuthorize and @PostAuthorize para configurar 
 *  un control de acceso por método.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true )
public class ConfigSeguridad extends WebSecurityConfigurerAdapter
{
    private final DataSource dataSource;
    
    @Autowired
    public ConfigSeguridad(final DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
    
    /**
     * Configuración centralizada de políticas de seguridad.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
             .antMatchers(HttpMethod.GET).permitAll()
             .anyRequest().authenticated()
        .and()
             .httpBasic()
        .and()
             .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
             .csrf().disable();
    }

    /**
     * {@link SecurityBuilder} usado para crear un {@link AuthenticationManager}.Permite
     * crear un método de seguridad en memoria, autenticación por LDAP, autenticación basada en JDBC 
     * ,agregar un servicio {@link UserDetailsService}, y agregar un
     * {@link AuthenticationProvider}'s.
     *
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.jdbcAuthentication().dataSource(dataSource)
        .passwordEncoder(new BCryptPasswordEncoder(10))
        .usersByUsernameQuery(
                "select username, password, enabled from usuarios where username = ?")
        .authoritiesByUsernameQuery(
                "select username, role from usuarios_roles where username = ?");
    }
}
