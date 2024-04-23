package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain_old_v4 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("hello");
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("hi");
            em.persist(member2);

            em.flush();
            em.clear();

            System.out.println("========= 1 ===========");
            //
//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId());
            Member findMember2 = em.find(Member.class, member2.getId());

            System.out.println("logic() 결과");
            logic(findMember, findMember2);
            //매개변수만 봐서는 프록시가 들어갈지, 순수한 타입이 들어갈지 알 수가 없다.
            System.out.println("findMember == findMember2 = " + (findMember == findMember2));


            System.out.println("findMember.getClass() = " + findMember.getClass());
            System.out.println("findMember2.getClass() = " + findMember2.getClass());
            //정체 : $HibernateProxy$
            System.out.println("findMember.getId() = " + findMember.getId());
            //memberId값은 이미 가지고 있는 정보라서 쿼리를 날리지 않고,
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
            //DB로부터 가져와야 하는 정보를 호출하면, 실제 쿼리를 날린다.

            em.flush();
            em.clear();

            System.out.println("========= 2 ===========");

            Member m1 = em.find(Member.class, member.getId());
            System.out.println("m1.getClass() = " + m1.getClass());

            Member m2 = em.getReference(Member.class, member.getId());
            System.out.println("m2.getClass() = " + m2.getClass());
            //이미 find로 영속성 객체에 1차 캐싱되어 있으므로 프록시로 가져오지 않는다.

            //JPA에서는, 한 tx안에서 a == a 항상 성립한다.
            //자바 컬렉션처럼 작용하기 때문에, 프록시든 원본이든 관계없이 == 성립해야함.
            // -> 그래서 이미 영속성 컨텍스트에 객체가 있으면 getReference 호출해도
            //     굳이 프록시로 가져오지 않는다.
            System.out.println("a == a : " + (m1 == m2));

            em.flush();
            em.clear();
            System.out.println("========= 3 ===========");

            Member m3 = em.getReference(Member.class, member.getId());
            System.out.println("m3.getClass() = " + m3.getClass());

            Member m4 = em.find(Member.class, member.getId());
            System.out.println("m4.getClass() = " + m4.getClass());

            System.out.println("a == a : " + (m1 == m2));

            em.flush();
            em.clear();
//            System.out.println("========= 4 ===========");
//
//            Member refMem = em.getReference(Member.class, member.getId());
//            System.out.println("refMem.getClass() = " + refMem.getClass());
//
//            em.detach(refMem);
////            em.close();
////            em.clear();
//
//            refMem.getUsername();
//
//            em.flush();
//            em.clear();

            System.out.println("========= 5 ===========");
            //member의 team을 eager fetch하면,
            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            Member mem1 = new Member();
            mem1.setUsername("mem1");
             mem1.changeTeam(team);
            em.persist(mem1);

            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(Member m1, Member m2) {
        //실제 넘어오는 클래스가 순수 클래스인지, 프록시인지 알 수 없다.
        //System.out.println("m1 == m2: " + ((m1 instanceof Member) && (m2 instanceof Member)));
        System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
    }
}
