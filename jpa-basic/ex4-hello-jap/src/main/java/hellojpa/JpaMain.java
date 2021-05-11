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
            ex4_1_1(em);

            System.out.println("======");
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void ex4_1_1(EntityManager em) {
        final Member member = createMember(40L, "40");
        member.setRoleType(RoleType.USER);

        System.out.println("BEFORE");
        em.persist(member);
        System.out.println("member.id = " + member.getId());
        System.out.println("AFTER");
    }

    private static Member createMember(Long id, String name) {
        final Member member = new Member();
//        member.setId(id);
        member.setUsername(name);
        return member;
    }

}
