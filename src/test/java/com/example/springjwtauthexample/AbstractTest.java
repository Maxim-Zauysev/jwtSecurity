package com.example.springjwtauthexample;

import com.example.springjwtauthexample.entity.RoleType;
import com.example.springjwtauthexample.repository.UserRepository;
import com.example.springjwtauthexample.security.SecurityService;
import com.example.springjwtauthexample.service.UserService;
import com.example.springjwtauthexample.web.model.request.CreateUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Testcontainers
public class AbstractTest {

    protected static PostgreSQLContainer postgreSQLContainer;

    static {
        DockerImageName postgres = DockerImageName.parse("postgres:12.3");
        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer<>(postgres)
                .withReuse(true);

        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry){
     String jdbcUrl = postgreSQLContainer.getJdbcUrl();

     registry.add("spring.datasource.username",postgreSQLContainer::getUsername);
     registry.add("spring.datasource.password",postgreSQLContainer::getPassword);
     registry.add("spring.datasource.url",()-> jdbcUrl);

    }

    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected SecurityService securityService;
    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        securityService.register(CreateUserRequest.builder()
                .username("user")
                .password("1234567")
                .roles(Collections.singleton(RoleType.ROLE_USER))
                .emails(Collections.singleton("user@mail.com"))
                .phones(Collections.singleton("89234517933"))
                .birthDate("1999-01-01")
                .build());
    }

    @AfterEach
    public void afterEach(){
        userRepository.deleteAll();
    }

}
