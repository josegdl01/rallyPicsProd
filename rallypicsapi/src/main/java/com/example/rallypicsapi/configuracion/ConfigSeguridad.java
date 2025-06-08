package com.example.rallypicsapi.configuracion;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
public class ConfigSeguridad {

    private final JWTProvider jwtProvider = new JWTProvider();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.csrf(csrf -> csrf.disable()).
           addFilterBefore(new FilterJWT(jwtProvider),BasicAuthenticationFilter.class).
           sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
           authorizeHttpRequests(authorize -> authorize
               .requestMatchers("auth/*", "publicacion/getPublicacionesValidadas", "configuracion/estadoSorteo", "puntuacion/rankingConcursantes").permitAll()
               .anyRequest().authenticated());

       return http.build();
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterConfiguration(){
        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuracion = new CorsConfiguration();
        

        configuracion.setAllowCredentials(true);
        configuracion.addAllowedOrigin("http://localhost:4200");
        configuracion.setMaxAge(3600L);

        configuracion.setAllowedHeaders(Arrays.asList(
            org.springframework.http.HttpHeaders.AUTHORIZATION,
            org.springframework.http.HttpHeaders.CONTENT_TYPE,
            org.springframework.http.HttpHeaders.ACCEPT
        ));

        configuracion.setAllowedMethods(Arrays.asList(
            org.springframework.http.HttpMethod.GET.name(),
            org.springframework.http.HttpMethod.POST.name(),
            org.springframework.http.HttpMethod.PUT.name(),
            org.springframework.http.HttpMethod.DELETE.name()
        ));

        fuente.registerCorsConfiguration("/**", configuracion);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(fuente));
        bean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);

        return bean;
    }


    @Bean
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(15);
    }
}

