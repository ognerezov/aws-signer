package net.okhotnikov.aws_signer.config;


import net.okhotnikov.aws_signer.config.authentication.SecretAuthenticationFilter;
import net.okhotnikov.aws_signer.config.cors.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final SecretAuthenticationFilter secretAuthenticationFilter;

    public SecurityConfig(CorsFilter corsFilter, SecretAuthenticationFilter secretAuthenticationFilter) {
        this.corsFilter = corsFilter;
        this.secretAuthenticationFilter = secretAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(secretAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/distribution/**").permitAll()
                .antMatchers("/sign/**").authenticated();
    }
}
