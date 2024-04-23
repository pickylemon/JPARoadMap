
package hellojpa.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

/**
 * fetch join
 */
public class jpqlMain5 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");

            Team team2 = new Team();
            team2.setName("teamB");

            Team team3 = new Team();
            team3.setName("teamC");

            em.persist(team);
            em.persist(team2);
            em.persist(team3);

            Member member = new Member();
            member.setAge(65);
            member.setUsername("김땡땡");
            member.setType(MemberType.USER);
            member.changeTeam(team);

            Member member2 = new Member();
            member2.setAge(10);
            member2.setUsername("김당당");
            member2.setType(MemberType.USER);
            member2.changeTeam(team);

            Member member3 = new Member();
            member3.setAge(28);
            member3.setUsername("김둥둥");
            member3.setType(MemberType.ADMIN);
            member3.changeTeam(team2);

            em.persist(member);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            System.out.println("========== namedQuery 시작 ===========");
            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "김둥둥")
                    .getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

            System.out.println("========== namedQuery 끝 ===========");
//            em.flush();
//            em.clear();


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
