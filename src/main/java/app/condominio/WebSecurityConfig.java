package app.condominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","/js/**","/css/**","/imagens/**","/webfonts/**").permitAll()
			.antMatchers("/sindico/**", "/conta/cadastro/**", "/api/**", "/swagger-ui/**").authenticated()
			//.antMatchers("/sindico/**", "/conta/cadastro/**").hasAuthority("SINDICO")// .access("hasRole('ROLE_SINDICO')")
			//.antMatchers("/condomino/**").hasAuthority("CONDOMINO")// .access("hasRole('ROLE_CONDOMINO')")
			//.antMatchers("/admin/**", "/api/**", "/swagger-ui/**").hasAuthority("ADMIN")// .access("hasRole('ROLE_ADMIN')")
			.antMatchers("/autenticado/**").authenticated()
		.and().formLogin()
		  	.loginPage("/entrar")
		  	.failureUrl("/login?erro")
		  	.defaultSuccessUrl("/autenticado")
		  	.usernameParameter("username").passwordParameter("password")
		.and().logout()
			.logoutSuccessUrl("/login?sair")
			.logoutUrl("/sair")
			.invalidateHttpSession(true)
			.clearAuthentication(true)
		.and().rememberMe()
		  	.tokenRepository(persistentTokenRepository())
		  	.tokenValiditySeconds(120960)
		.and().csrf();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password,ativo from usuarios where username=?")
                .authoritiesByUsernameQuery(
                        "select username,autorizacao from usuarios join autorizacoes on id = id_usuario where username=?");
    }
    // LATER implementar meu próprio UserDetailsService para mostrar primeiro nome do usuário no site

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("targetUrl");
        return auth;
    }
}
