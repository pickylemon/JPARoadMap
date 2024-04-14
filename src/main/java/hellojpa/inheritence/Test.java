package hellojpa.inheritence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
//            Movie movie = new Movie();
//            movie.setDirector("abc");
//            movie.setActor("bbb");
//            movie.setName("라이프오브파이");
//            movie.setPrice(15000);
//
//            em.persist(movie);
//
//            em.flush();
//            em.clear();
//
//            Item item = em.find(Item.class, movie.getId());
//            System.out.println("item = " + item);
            System.out.println("==================");

            Member member = new Member();
            member.setCreatedBy("kim");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

            em.flush();
            em.clear();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}
