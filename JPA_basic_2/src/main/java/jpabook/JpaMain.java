package jpabook;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{
//            Order order = em.find(Order.class, 1L);
//            Long memberId = order.getMemberId();
//            Member member = em.find(Member.class, memberId);
            //해당 주문을 한 회원을 가져오는 과정이 객체지향적이지 않다.
            //바로 getMember가 아니라, memberId를 얻고 다시 em.find를 하는 부분.
            //원인 : 설계를 관계형DB에 맞췄기 때문.

            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}