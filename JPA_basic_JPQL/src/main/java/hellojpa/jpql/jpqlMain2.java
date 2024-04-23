package hellojpa.jpql;

import jakarta.persistence.*;

import java.util.List;
import java.util.Queue;

public class jpqlMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Team team = new Team();
            team.setName("teamB");

            em.persist(team);

            Member member = new Member();
            member.setAge(65);
            member.setUsername("김땡땡");
            member.setType(MemberType.USER);
            member.changeTeam(team);

            Member member2 = new Member();
            member2.setAge(10);
            member2.setType(MemberType.ADMIN);

            em.persist(member);
            em.persist(member2);

            //1.
//            String query = "select " +
//                                "case when m.age <= 10 then '학생요금' "+
//                                "       when m.age >=60 then '경로요금' " +
//                                "       else '일반요금'" +
//                    "end "+
//                    "from Member m";
//            List<String> result = em.createQuery(query, String.class).getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }


            //2.
//            String query = "select coalesce(m.username, '이름 없는 회원') from Member m";
//            List<String> result = em.createQuery(query, String.class).getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }


            //3. nullif(if다음 조건을 만족하면 null) 와 ifnull(== nvl 기능)은 다르다.

//            String query = "select nullif(m.type, :memberType) " +
//                    "from Member m";
//            List<Object> result2 = em.createQuery(query, Object.class)
//                    .setParameter("memberType", MemberType.ADMIN)
//                    .getResultList();
//            for (Object s : result2) {
//                System.out.println("s = " + s);
//            }

            //4.사용자 정의 함수.
//            String query = "select function('group_concat', ifnull(m.username,'홍길동')) From Member m";
            String query = "select group_concat(ifnull(m.username,'홍길동')) From Member m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
            }

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
