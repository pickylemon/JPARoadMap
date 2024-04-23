package hellojpa.valueType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Test {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Address address = new Address("aaa","bb","CCc");

            Member member = new Member();
            member.setUsername("hello1");
            member.setHomeAddress(address);
            member.setWorkPeriod(new Period());
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("hello2");
//            member2.setHomeAddress(address); //인스턴스 값을 공유하는 것은 위험
            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
            member2.setHomeAddress(copyAddress); //값을 복사해서 새로운 인스턴스를 대입해야 한다.
            member2.setWorkPeriod(new Period());
            em.persist(member2);

//            member.getHomeAddress().setCity("paris");
            //의도 : member의 city만 바꾸고 싶다.
            //실제 결과 : member와 member2의 city가 모두 변경됨
            //이런 side-effect에 의한 버그는 정말 정말 잡기 어렵다.
            //값 객체는 immutable로 설계 (밖에서 호출할 수 있는 setter가 없어야 한다)

            //값을 바꾸고 싶다면, 새로 만들어 넣어주어야 한다.
            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);
//
            System.out.println("member.getHomeAddress().getCity() = " + member.getHomeAddress().getCity());
            System.out.println("member2.getHomeAddress().getCity() = " + member2.getHomeAddress().getCity());

            //공유하는걸 의도했다면 Embeded type을 쓸 게 아니라 Entity로 썼어야 한다.
            //어떤 경우에서도 이런 side-effect는 의도해서는 안됨.

            tx.commit();

        } catch  (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

    }
}
