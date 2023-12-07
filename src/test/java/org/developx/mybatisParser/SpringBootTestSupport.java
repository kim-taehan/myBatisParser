package org.developx.mybatisParser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@ActiveProfiles("test")
public abstract class SpringBootTestSupport {
    @PersistenceContext
    protected EntityManager entityManager;

//    @Autowired
//    protected PasswordEncoder passwordEncoder;
}
