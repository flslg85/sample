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
//            ex3_2_1(em);
//            ex3_2_2(em);
//            ex3_2_3(em);
//            ex3_2_4(em);
//            ex3_2_5(em);
//            ex3_2_6(em);
//            ex3_3_1(em);
//            ex3_3_1_ex(em);
//            ex3_3_2(em);
//            ex3_4_1(em);
//            ex3_4_2(em);

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

    // 준영속 상태로 변경 - clear
    // clear 후 동일한 id 로 호출시 다시 select
    private static void ex3_4_2(EntityManager em) {
        Member member = em.find(Member.class, 7L);
        member.setName("BBB");

        em.clear();

        Member member2 = em.find(Member.class, 7L);
    }

    // 준영속 상태로 변경 - detach
    // update 쿼리 실행되지 않음
    private static void ex3_4_1(EntityManager em) {
        Member member = em.find(Member.class, 6L);
        member.setName("BBB");

        em.detach(member);
    }

    // createQuery 호출시 flush
    // 2개 insert 후 select
    private static void ex3_3_2(EntityManager em) {
        em.persist(createMember(10L, "hello10"));
        em.persist(createMember(11L, "hello11"));

        final List<Member> list =
                em.createQuery("select m from Member m", Member.class)
                  .getResultList();
    }

    // flush 사용
    // ==== 앞에 insert 쿼리
    private static void ex3_3_1(EntityManager em) {
        final Member member = createMember(9L, "hello9");
        em.persist(member);
        em.flush();
    }

    // flush 사용 하고 exception 발생?
    // flush 를 해도 디비에 동기화를 하지만 exception 발생시 transaction 이 다 롤백되기 때문에 저장되지 않음
    private static void ex3_3_1_ex(EntityManager em) {
        final Member member = createMember(100L, "helloee");
        em.persist(member);
        em.flush();

        throw new RuntimeException("exception!!!");
    }

    // update 수동으로 하지말라는 예제
    private static void ex3_2_6(EntityManager em) {
        final Member member = em.find(Member.class, 6L);
        member.setName("ZZZ");

//        if (member.getName().equals("ZZZ")) {
//            em.update(member);
//        }
    }

    // select 후 delete 쿼리
    private static void ex3_2_5(EntityManager em) {
        final Member member = em.find(Member.class, 6L);
        member.setName("ZZZ");

        em.remove(member);
    }

    // select 후 변경 내역에 따라 update 쿼리
    private static void ex3_2_4(EntityManager em) {
        final Member member = em.find(Member.class, 6L);
        member.setName("ZZZ");

//        em.persist(member);
    }

    // ==== 이후에 insert 쿼리
    private static void ex3_2_3(EntityManager em) {
        final Member memberA = createMember(6L, "HelloA");
        final Member memberB = createMember(7L, "HelloB");

        em.persist(memberA);
        em.persist(memberB);

        System.out.println("======");
    }

    // 영속 상태에서 객체 비교시 true
    private static void ex3_2_2(EntityManager em) {
        final Member findMember1 = em.find(Member.class, 5L);
        final Member findMember2 = em.find(Member.class, 5L);

        System.out.println("RESULT = " + (findMember1 == findMember2));
    }

    // 영속 상태 find 시 select 쿼리 실행하지 않음
    private static void ex3_2_1(EntityManager em) {
        // 비영속
        final Member member = createMember(5L, "HelloA");

        // 영속
        System.out.println("=== BEFORE === ");
        em.persist(member);
        System.out.println("=== AFTER === ");

        final Member findMember = em.find(Member.class, 5L);
        System.out.println("findMember.id = " + findMember.getId());
        System.out.println("findMember.name = " + findMember.getName());

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
        member.setId(4L);
        member.setName("helloA");
        em.persist(member);
    }

    private static Member createMember(Long id, String name) {
        final Member member = new Member();
        member.setId(id);
        member.setName(name);
        return member;
    }

}
