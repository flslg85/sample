package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();

        final EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            createMember(em);
//            updateMemberName(em);
//            findJpql(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void findJpql(EntityManager em) {
        final List<Member> resultList =
                em.createQuery("select m from Member as m", Member.class)
                  .setFirstResult(1)
                  .setMaxResults(5)
                  .getResultList();

        resultList.forEach(result -> System.out.println("member.name: " + result.getName()));
    }

    private static void updateMemberName(EntityManager em) {
        final Member member = em.find(Member.class, 1L);
        member.setName("helloJPA");
    }

    private static void createMember(EntityManager em) {
        final Member member = new Member();
        member.setId(1L);
        member.setName("helloA");

        em.persist(member);
    }
}
