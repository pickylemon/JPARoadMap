
package hellojpa.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class jpqlMain3 {
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
            member2.changeTeam(team);

            em.persist(member);
            em.persist(member2);

            //1.
//            String query = "select m.team.name from Member m";
//            List<String> resultList = em.createQuery(query, String.class).getResultList();
//            for (String s : resultList) {
//                System.out.println("s = " + s);
//            }

//            em.flush();
//            em.clear();

            //2. 컬렉션 값 연관 경로는 더이상 탐색이 불가 -> 명시적 조인으로 해결
//            String query = "select size(t.members) from Team t";
//            Integer singleResult = em.createQuery(query, Integer.class).getSingleResult();
//            System.out.println("singleResult = " + singleResult);

            String query = "select m.username from Team t join t.members m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();
            System.out.println("resultList = " + resultList);

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
