package jpabook.jpashop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Hibernate;

import jpabook.jpashop.domain.Child;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.Parent;
import jpabook.jpashop.domain.Team;
import jpabook.jpashop.domain.item.Movie;

public class JpaMain {
    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        final EntityManager em = emf.createEntityManager();

        final EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            ex1(em);
//            ex5_4_1(em);
//            ex7_1_1(em);
//            ex7_1_2(em);
//            ex8_1(em);
//            ex8_2(em);
//            ex8_3(em);
//            ex8_4(em);
//            ex8_5(em);
//            ex8_6(em);
//            ex8_7(em, emf);
//            ex8_8(em, emf);
//            ex8_9(em);
//            ex8_10(em);
            ex8_11(em);


            System.out.println("======");
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void ex8_11(EntityManager em) {
        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        parent.addChild(child1);
        parent.addChild(child2);

        em.persist(parent);

        em.remove(parent);
    }

    private static void ex8_10(EntityManager em) {
        Team team = new Team();
        team.setName("team");
        em.persist(team);

        Team teamb = new Team();
        teamb.setName("teamb");
        em.persist(teamb);

        Member member = new Member();
        member.setName("hello1");
        member.setTeam(team);
        em.persist(member);

        Member member2 = new Member();
        member2.setName("hello2");
        member2.setTeam(teamb);
        em.persist(member);

        em.flush();
        em.clear();

        final List<Member> membrs =
                em.createQuery("select m from Member m join fetch m.team", Member.class)
                  .getResultList();

        // SQL: select * from Member
        // SQL: select * from Team where TEMA_ID = xxx
    }

    private static void ex8_9(EntityManager em) {
        Team team = new Team();
        team.setName("team");
        em.persist(team);

        Member member = new Member();
        member.setName("hello1");
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        Member m = em.find(Member.class, member.getId());
        System.out.println("m = " + m.getTeam().getClass());

        System.out.println("before");
        System.out.println("team name: "+ m.getTeam().getName());
        System.out.println("after");
    }

    private static void ex8_8(EntityManager em, EntityManagerFactory emf) {
        Member member1 = new Member();
        member1.setName("hello1");
        em.persist(member1);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member1.getId());

        System.out.println("refMember = " + refMember.getClass());
        Hibernate.initialize(refMember);
    }

    private static void ex8_7(EntityManager em, EntityManagerFactory emf) {
        Member member1 = new Member();
        member1.setName("hello1");
        em.persist(member1);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member1.getId());

        System.out.println("isLoaded = " +
                           emf.getPersistenceUnitUtil().isLoaded(refMember));
        System.out.println("refMember = " + refMember.getName());
        System.out.println("isLoaded = " +
                           emf.getPersistenceUnitUtil().isLoaded(refMember));
    }

    // org.hibernate.LazyInitializationException
    private static void ex8_6(EntityManager em) {
        Member member1 = new Member();
        member1.setName("hello1");
        em.persist(member1);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member1.getId());
        System.out.println("refMember = " + refMember.getClass());

//        em.detach(refMember);
//        em.close();
        em.clear();

        System.out.println("refMember = " + refMember.getName());
    }

    private static void ex8_5(EntityManager em) {
        Member member1 = new Member();
        member1.setName("hello1");
        em.persist(member1);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member1.getId());
        System.out.println("refMember = " + refMember.getClass());

        final Member findMember = em.find(Member.class, member1.getId());
        System.out.println("findMember = " + findMember.getClass());

        System.out.println("a == b : " + (refMember == findMember));
    }

    private static void ex8_4(EntityManager em) {
        Member member1 = new Member();
        member1.setName("hello1");
        em.persist(member1);

        em.flush();
        em.clear();

        Member m1 = em.find(Member.class, member1.getId());
        System.out.println("m1 = " + m1.getClass());

        final Member reference = em.getReference(Member.class, member1.getId());
        System.out.println("m1 = " + reference.getClass());

        System.out.println("a == b : " + (m1 == reference));
    }

    private static void ex8_3(EntityManager em) {
        Member member1 = new Member();
        member1.setName("hello1");
        em.persist(member1);

        Member member2 = new Member();
        member2.setName("hello2");
        em.persist(member2);

        em.flush();
        em.clear();

        Member m1 = em.find(Member.class, member1.getId());
//        Member m2 = em.find(Member.class, member2.getId());
//        System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass()));

        Member m2 = em.getReference(Member.class, member2.getId());
        logic(m1, m2);
    }

    private static void logic(Member m1, Member m2) {
        System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass()));
        System.out.println("m1 == m2 : " + (m1 instanceof Member));
        System.out.println("m1 == m2 : " + (m2 instanceof Member));
    }

    private static void ex8_2(EntityManager em) {
        Member member = new Member();
        member.setName("hello");

        em.persist(member);

        em.flush();
        em.clear();

//        Member findMember = em.find(Member.class, member.getId());
        Member findMember = em.getReference(Member.class, member.getId());
        System.out.println("before findMember = " + findMember.getClass());
        System.out.println("findMember.id = " + findMember.getId());
        System.out.println("findMember.name = " + findMember.getName());
        System.out.println("findMember.name = " + findMember.getName());
        System.out.println("after findMember = " + findMember.getClass());
    }

    private static void ex8_1(EntityManager em) {
        final Member member = em.find(Member.class, 1L);
        printMember(member);
        printMemberAndTeam(member);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }

    private static void printMember(Member member) {
        System.out.println("username = " + member.getName());
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getName();
        System.out.println("username = " + username);

        Team team = member.getTeam();
    }

    private static void ex7_1_2(EntityManager em) {
        final Movie movie = new Movie();
        movie.setDirector("aaaa");
        movie.setActor("bbbb");
        movie.setName("ㅂㅏ람");
        movie.setPrice(1000);

        em.persist(movie);

        em.flush();
        em.clear();

        final Item findMovie = em.find(Item.class, movie.getId());
        System.out.println("findMovie = " + findMovie);
    }

    private static void ex7_1_1(EntityManager em) {
        final Movie movie = new Movie();
        movie.setDirector("aaaa");
        movie.setActor("bbbb");
        movie.setName("ㅂㅏ람");
        movie.setPrice(1000);

        em.persist(movie);

        em.flush();
        em.clear();

        final Movie findMovie = em.find(Movie.class, movie.getId());
        System.out.println("findMovie = " + findMovie);
    }

    private static void ex5_4_1(EntityManager em) {
        final Order order = new Order();
        em.persist(order);

        final OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        em.persist(orderItem);
    }

    private static void ex1(EntityManager em) {
        Order order = em.find(Order.class, 1L);
        final Long memberId = order.getMemberId();

        final Member member = em.find(Member.class, memberId);

        final Member findMember = order.getMember();
    }

}
