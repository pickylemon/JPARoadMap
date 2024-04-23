package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            List<Member> members = em.createQuery(
                    "select m from Member m where m.username like '%k%'"
                    , Member.class
            ).getResultList();
            for (Member member : members) {
                System.out.println("member = " + member);
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);
            Root<Member> m = query.from(Member.class);
            CriteriaQuery<Member> cq = query.select(m);
            String username = "dasdfqwer";
            if(username !=null){ //criteria를 사용하면 jpql을 동적으로 작성 가능
                cq =  cq.where(cb.like(m.get("username"), "%k%"));
            }
            List<Member> resultList2 = em.createQuery(cq).getResultList();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
