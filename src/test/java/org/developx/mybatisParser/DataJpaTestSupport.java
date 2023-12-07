package org.developx.mybatisParser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public abstract class DataJpaTestSupport {

    @PersistenceContext
    protected EntityManager entityManager;

    protected void persist(Object... objects) {
        for (Object object : objects) {
            entityManager.persist(object);
        }
        initEntityManager();
    }

    protected void initEntityManager(){
        entityManager.flush();
        entityManager.clear();
    }
}
