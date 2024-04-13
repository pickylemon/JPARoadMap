package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

//        try{ //v1
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            Team team2 = new Team();
//            team.setName("TeamB");
//            em.persist(team2);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//            //아래 em.find는 원래는 select문이 안 나간다(영속성 컨텍스트의 캐시에서 가져오므로)
//            //DB에서 날아가는 쿼리를 확인해보려면 flush로 SQL을 다 날리고 영속성 컨텍스트를 clear하는 코드 필요
//            //그래야 캐싱된게 없어서 다시 DB에서 가져오느라 select쿼리가 나감
//
//            Member findMember = em.find(Member.class, member.getId());
//            System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());
//
//            Team findTeam = em.find(Team.class, team2.getId());
//            findMember.setTeam(findTeam);
//
//            tx.commit();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);
            em.persist(member);

            //team.getMembers().add(member); changeTeam() 메서드로 대체
//            em.flush();
//            em.clear();
//            System.out.println("====================");

            Member findMember = em.find(Member.class, member.getId());

//            List<Member> members = findMember.getTeam().getMembers();
//            System.out.println("===================");
//            for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//                System.out.println("m = " + m);
//            }
            System.out.println("==================");

            tx.commit();

        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
