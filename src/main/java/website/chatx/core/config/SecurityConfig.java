package website.chatx.core.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import website.chatx.core.advices.AuthenticationEntryPointAdvice;
import website.chatx.core.common.CommonAuthContext;
import website.chatx.core.common.StringWithoutSpaceDeserializerCommon;
import website.chatx.core.filters.SecurityFilter;
import website.chatx.service.impl.UserDetailServiceImpl;

import java.util.List;
import java.util.Optional;

@EnableAsync
@EnableJpaAuditing
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer, AuditorAware<String> {
    private final UserDetailServiceImpl userDetailServiceImpl;
    private final SecurityFilter securityFilter;
    private final AuthenticationEntryPointAdvice authenticationEntryPointAdvice;
    private final CommonAuthContext commonAuthContext;

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            return Optional.ofNullable(commonAuthContext.getUserEntity().getId());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/swagger-ui/**",
//                                "/v3/api-docs/**",
//                                "/basic/**").permitAll()
                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/user/**").hasAnyAuthority(RoleEnum.USER.name(), RoleEnum.ADMIN.name())
//                        .requestMatchers("/admin/**").hasAnyAuthority(RoleEnum.ADMIN.name())
                        .anyRequest().authenticated())
                .exceptionHandling((o) -> o.authenticationEntryPoint(authenticationEntryPointAdvice))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();

    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringWithoutSpaceDeserializerCommon(String.class));
        mapper.registerModule(module);

        converter.setObjectMapper(mapper);

        return converter;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
    }
}
