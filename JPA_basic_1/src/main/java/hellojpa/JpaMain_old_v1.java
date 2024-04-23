package hellojpa;

import jakarta.persistence.*;

public class JpaMain_old_v1 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //emf는 application 로딩 시점에 딱 하나만 만든다.
        EntityManager em = emf.createEntityManager();
        //고객 요청이 올때마다 사용하고 버린다(thread간에 절대 공유x)
        EntityTransaction tx = em.getTransaction();
        //jpa의 모든 데이터 변경은 트랜잭션 안에서 실행해야 함.
        tx.begin();

        try {
            Member_old_v1 member = new Member_old_v1();
            member.setId(1L);
            member.setName("helloB"); //여기까진 비영속

            System.out.println("===BEFORE===");
            em.persist(member); // persist를 호출하면 영속
            System.out.println("===AFTER==="); //persist호출시 바로 DB에 쿼리가 날라가는게 아님을 확인하려는 코드
//            //1. 회원 저장하기

            Member_old_v1 findMember = em.find(Member_old_v1.class, 1L);
            System.out.println("findMember = " + findMember);
//            findMember.setName("helloJ");
              // 2. 회원 수정하기
            //jpa를 통해 entity를 가져오면 jpa가 관리를 한다.
            //스냅샷을 가지고 있다가 변경사항이 있으면 commit시점에 update쿼리를 날림


            //JPQL : 1.객체를 대상으로 하는 객체지향쿼리. 2.persistence.xml 설정을 Oracle, MySQL 어떤 방언이든 다 번역함
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(5)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.name = " + member.name);
//            }
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
