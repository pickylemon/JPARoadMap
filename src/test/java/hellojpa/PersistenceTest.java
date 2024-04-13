package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class PersistenceTest {
    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction tx;


    @BeforeEach
    void beforeEach(){
        emf = Persistence.createEntityManagerFactory("hello");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @AfterEach
    void afterEach(){
        em.close();
        emf.close();
    }

    @Test
    void cacheTest(){
        try {
            tx.begin();
            Member_old_v1 member1 = em.find(Member_old_v1.class, 1L); //이것만 DB에서 가져오고 (쿼리가 한번 나감)
            Member_old_v1 member2 = em.find(Member_old_v1.class, 1L); //두번째는 영속성에 캐싱된 것을 가져온다. (같은 tx안이기 때문에) -- 쿼리가 나가지 않음

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        }
    }
    @Test
    void cacheTest2(){ //이 상황에서는 insert쿼리만 나가고 select쿼리는 나가지 않는다. 왜? 영속성 컨텍스트에서 조회했으므로.
        try {
            tx.begin();
            Member_old_v1 member = new Member_old_v1();
            member.setId(101L);
            member.setName("abc");
            em.persist(member); //persist를 호출하는 즉시 db에 insert쿼리 나가는 것은 아님. 영속상태가 되는 것일뿐

            Member_old_v1 findMember = em.find(Member_old_v1.class, 101L);
            log.info("\nfindMember={}\n", findMember);

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        }
    }

    @Test
    void cacheTest3(){ //이 상황에서는 insert쿼리만 나가고 select쿼리는 나가지 않는다. 왜? 영속성 컨텍스트에서 조회했으므로.
        try {
            tx.begin();
            Member_old_v1 member = new Member_old_v1();
            member.setId(200L);
            member.setName("abcd");

            em.persist(member);

            em.flush();
            System.out.println("==================");

            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }
    }
}
