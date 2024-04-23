
package hellojpa.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

/**
 * fetch join
 */
public class jpqlMain6 {
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

            em.persist(member); //member.setAge(65)인 상황
            em.persist(member2);
            em.persist(member3);

            System.out.println("========== 벌크연산 ===========");


            String qlString = "update Member m "+
                    "set m.age = m.age + 10 "+
                    "where m.type = :memberType";

            //쿼리 날아가면 jpa의 flush는 자동 호출된다. (영속성 컨텍스트 초기화는 따로 고려해줘야함)
            int resultCnt = em.createQuery(qlString)
                    .setParameter("memberType", MemberType.USER)
                    .executeUpdate();

            System.out.println("resultCnt = " + resultCnt);

            //현재 DB에는 age가 업데이트 된 상황인데, 영속성 컨텍스트는 초기화되지 않아서 값이 DB와 다르다.
            Member findMember1 = em.find(Member.class, member.getId()); //65
            System.out.println("findMember1.getAge() = " + findMember1.getAge());
            System.out.println("========== 벌크연산 끝 ===========");

            em.clear();

            Member findMember2 = em.find(Member.class, member.getId());
            System.out.println("findMember.getAge() = " + findMember2.getAge());
            //반영이 되어있다. 75

            //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.modifying-queries 참고

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
