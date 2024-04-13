package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain_old_v2 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //emf는 application 로딩 시점에 딱 하나만 만든다.
        EntityManager em = emf.createEntityManager();
        //고객 요청이 올때마다 사용하고 버린다(thread간에 절대 공유x)
        EntityTransaction tx = em.getTransaction();
        //jpa의 모든 데이터 변경은 트랜잭션 안에서 실행해야 함.
        tx.begin();

        try {
            Member_old_v2 member = new Member_old_v2();
            member.setUsername("abc");
            Member_old_v2 member2 = new Member_old_v2();
            member2.setUsername("def");
            Member_old_v2 member3 = new Member_old_v2();
            member3.setUsername("qwe");
            System.out.println("==============");
            em.persist(member);
            em.persist(member2);
            em.persist(member3);
            System.out.println("member.getId()="+member.getId());
            System.out.println("member2.getId()="+member2.getId());
            System.out.println("member3.getId()="+member3.getId());
            System.out.println("==============");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            //entity매니저는 connection을 가지고 작업하므로 꼭 닫아주어야 한다.
        }
        emf.close();
    }
}
