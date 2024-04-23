package hellojpa.jpql;

import jakarta.persistence.*;

import java.util.List;

public class jpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Team team = new Team();
            team.setName("teamA");

            Member member = new Member();
            String usernameParam = "member1";
            member.setUsername(usernameParam);
            member.setAge(10);
            member.changeTeam(team);
            member.setType(MemberType.USER);

            em.persist(team);
            em.persist(member);

            em.flush();
            em.clear();

//            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
//            TypedQuery<S ntring> query2 = em.createQuery("select m.username from Member m", String.class);
//            Query query3 = em.createQuery("select m.username, m.age from Member m");
//
//            query.setParameter("username", usernameParam); //보통 체이닝으로 엮는다.
//            Member result = query.getSingleResult();
//            //SpringDataJPA를 쓰면, JPQL과 달리 exception 터뜨리지 않고 Optional 반환
//            //Spring이 내부적으로 try-catch에서 noResultException을 잡아 준다.

//
            //inner join
//            String query = "select m from Member m inner join m.team t";
            //left outer join
//            String query = "select m from Member m left outer join m.team t";
            //세타 조인
//            String query = "select count(m) from Member m, Team t where m.username = t.name";

            //무관한 조건으로도 outer 조인 수행 가능
//            String query = "select m from Member m left join Team t on m.username = t.name";
            String query = "select m.username, 'HELLO', TRUE From Member m " +
                    "where m.type = hellojpa.jpql.MemberType.USER";
//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();
            List<Object[]> result = em.createQuery(query).getResultList();
            for (Object[] objects : result) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
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
