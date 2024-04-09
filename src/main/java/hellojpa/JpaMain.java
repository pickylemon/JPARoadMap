package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //emf는 application 로딩 시점에 딱 하나만 만든다.
        EntityManager em = emf.createEntityManager();
        //고객 요청이 올때마다 사용하고 버린다(thread간에 절대 공유x)
        EntityTransaction tx = em.getTransaction();
        //jpa의 모든 데이터 변경은 트랜잭션 안에서 실행해야 함.
        tx.begin();

        try {
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("helloB");
//            em.persist(member); // 1. 회원 저장하기

//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember = " + findMember);
//            findMember.setName("helloJ"); //2. 회원 수정하기
            //jpa를 통해 entity를 가져오면 jpa가 관리를 한다.
            //스냅샷을 가지고 있다가 변경사항이 있으면 commit시점에 update쿼리를 날림


            //JPQL : 1.객체를 대상으로 하는 객체지향쿼리. 2.persistence.xml 설정을 Oracle, MySQL 어떤 방언이든 다 번역함
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(5)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.name);
            }
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
