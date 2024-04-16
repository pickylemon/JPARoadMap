
package hellojpa.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

/**
 * fetch join
 */
public class jpqlMain4 {
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

            System.out.println("======== @ManyToOne Fetch 시작============");

//            String sql = "select m from Member m join fetch m.team";

            String sql = "select m from Member m";
            //전제 : 현재 영속성 컨텍스트에 member와 team 없음(em.flush(), em.clear())
            //LAZY로 설정되어 있을 경우(권장),
            //select문에서는 member테이블만 조회하고,
            //mem.getTeam()을 만났을 때 Team테이블을 조회하는 쿼리 발생
                //회원1- 팀 조회 쿼리 나감
                //회원2(회원1과 같은 팀) - 영속성 컨텍스트에서 가져옴
                //회원3 - 팀 조회 쿼리 나감
            //전형적인 N+1문제 (회원이 100만명이었다면..?)

            List<Member> resultList = em.createQuery(sql, Member.class).getResultList();
            for (Member mem : resultList) {
                System.out.println("mem.getTeam().getClass() = " + mem.getTeam().getClass());
                //LAZY일 경우 team은 proxy이다. (영속성 컨텍스트에 없다는 전제)
                //fetch join으로 명시적으로 가져온 경우엔 순수 클래스
                System.out.println("member = " + mem.getUsername() + "-" + mem.getTeam().getName());
            }

            System.out.println("======== @ManyToOne Fetch 끝============");

            em.flush();
            em.clear();

            System.out.println("======== @OneToMany Fetch 시작============");

            //하이버네이트 6부터는 select distinct t 안써도 자동으로 중복 제거됨
//            String sql2 = "select t from Team t join fetch t.members";
//            String sql2 = "select t from Team t";
            String sql2 = "select t from Team t join t.members m"; //일반 조인
            List<Team> teamList = em.createQuery(sql2, Team.class).getResultList();
            System.out.println("teamList.size() = " + teamList.size());
            for (Team team1 : teamList) {
                System.out.println("team1.getMembers().getClass() = " + team1.getMembers().getClass());
                System.out.println("team.getName() = " + team1.getName() + " & " + "team.getMembers.size=" + team1.getMembers().size());
            }


            System.out.println("======== @OneToMany Fetch 끝============");
            em.flush();
            em.clear();
            System.out.println("======== @OneToMany Fetch join Paging 시작 ============");

            String sql3 = "select t from Team t";
            List<Team> teams = em.createQuery(sql3, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("teams.size() = " + teams.size());
            for (Team team1 : teams) {
                System.out.println("team1.getMembers().getClass() = " + team1.getMembers().getClass());
                System.out.println("team.getName() = " + team1.getName() + " & " + "team.getMembers.size=" + team1.getMembers().size());
            }

            System.out.println("======== @OneToMany Fetch join Paging 끝============");
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
