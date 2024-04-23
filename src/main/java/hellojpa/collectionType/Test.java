package hellojpa.collectionType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;



@Slf4j
public class Test {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity","street","124zs"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("햄버거");
            member.getFavoriteFoods().add("피자");

            //값타입 컬렉션 예제
//            member.getAddressHistory().add(new Address("old1", "oldstreet1", "oldzip"));
//            member.getAddressHistory().add(new Address("new1", "newstreet1", "newzip"));

            //값타입 컬렉션을 엔티티로 변경한 예제(연관관계 주인 : 1 (member))
            member.getAddressList().add(new AddressEntity("old1", "oldstreet1", "oldzip"));
            member.getAddressList().add(new AddressEntity("new1", "newstreet1", "newzip"));

            //값타입 컬렉션을 엔티티로 변경한 예제(연관관계 주인 : n (addressEntity))
//            AddressEntity address1 = new AddressEntity("old1", "oldstreet1", "oldzip");
//            AddressEntity address2 = new AddressEntity("new1", "newstreet1", "newzip");
//            address1.changeMember(member);
//            address2.changeMember(member);
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=======START========");
            Member findMember = em.find(Member.class, member.getId());

            //homeCity : old -> new
            //address에서 바로 setCity할 수 없다. 값타입은 불변객체이므로!
            //값타입은 아예 새 것으로 갈아껴야한다.
            Address old = findMember.getHomeAddress();
            Address newAddress = new Address("new city", old.getStreet(), old.getZipcode());
            findMember.setHomeAddress(newAddress);

            //값 타입 컬렉션 변경하기
            //1.치킨 -> 한식 (String 자체도 값 타입이라 이렇게 갈아껴야한다.)
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //2.
            boolean deleted = findMember.getAddressList().remove(new AddressEntity("old1", "oldstreet1", "oldzip"));
            log.info("deleted={}", deleted);
            findMember.getAddressList().add(new AddressEntity("newAddress1", "newstreet1", "newzip"));


//            findMember.getAddressHistory().remove(new Address("old1", "oldstreet1", "oldzip"));
//            //List의 remove(Object o)는 equals를 바탕으로 동작하므로
//            //값 타입은 equals가 미리 적절하게 오버라이딩이 되어 있어야 한다(참조값이 아닌 "값"을 비교하게끔)
//            findMember.getAddressHistory().add(new Address("newAddress1", "newstreet1", "newzip"));
//            //위에서 제대로 remove가 동작하지 않으면 add에 의한 추가만 발생(주의)

            List<AddressEntity> addressHistory = findMember.getAddressList();
            for (AddressEntity address : addressHistory) {
                System.out.println("address = " + address);
            }
            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }
            tx.commit();



            //List<Address> addressHistory = findMember.getAddressList();
//            //값타입 컬렉션을 엔티티로 변환 후(연관관계 주인은 addressEntity인 상황)
//            AddressEntity findAddress = em.find(AddressEntity.class, address1.getId());
//            findAddress.setCity("old2");
//            findAddress.changeMember(member);
//
//            em.remove(findMember);


        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
