package website.chatx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@RequiredArgsConstructor
public class Application extends SpringBootServletInitializer {
//    private final Log LOGGER = LogFactory.getLog(getClass());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

//    @Bean
//    CommandLineRunner run(UserRepository userRepository) {
//        return args -> {
//            try {
//                userRepository.save(UserEntity.builder()
//                        .id("5978e017-9099-46bb-b855-df8e092f1545")
//                        .roles(Arrays.asList(RoleEnum.USER, RoleEnum.ADMIN))
//                        .local(LocalEntity.builder()
//                                .email("admin@reviewx.top")
//                                .password("12345aA@")
//                                .name("admin")
//                                .isActive(true)
//                                .build())
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .build());
//            } catch (Exception e) {
//                LOGGER.error(e);
//            }
//        };
//    }
}
