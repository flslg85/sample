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
//            ex4_1_1(em);
//            ex4_1_2(em);
//            ex5_1_1(em);
//            ex5_1_2(em);
//            ex5_1_3(em);
//            ex5_2_1(em);
            ex5_3_1(em);


            System.out.println("======");
            tx.commit();
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void ex5_3_1(EntityManager em) {
//        final Member member = new Member();
//        member.setUsername("member1");
//        em.persist(member);
//
//        final Team team = new Team();
//        team.setName("TeamA");
//        역방향(주인이 아닌 방향)만 연관관계 설정
//        team.getMembers().add(member);
//        em.persist(team);

        final Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        final Member member = new Member();
        member.setUsername("member1");
        member.changeTeam(team);
        em.persist(member);

//        em.flush();
//        em.clear();

        team.addMember(member);

        final Team findTeam = em.find(Team.class, team.getId());
        final List<Member> members = findTeam.getMembers();

        System.out.println("=============");
        for (Member m: members) {
            System.out.println("m = " + m.getId());
        }
        System.out.println("=============");
    }

    private static void ex5_2_1(EntityManager em) {
        final Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        final Member member = new Member();
        member.setUsername("member1");
        member.changeTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        final Member findMember = em.find(Member.class, member.getId());
        final List<Member> members = findMember.getTeam().getMembers();

        members.forEach(m -> {
            System.out.println("findMember = " + m.getId());
        });
    }

    // team 변경
    private static void ex5_1_3(EntityManager em) {
        final Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        final Member member = new Member();
        member.setUsername("member1");
        member.changeTeam(team);
        em.persist(member);

        final Member findMember = em.find(Member.class, member.getId());
        final Team findTeam = findMember.getTeam();
        System.out.println("findTeam = " + findTeam.getId());

        final Team newTeam = em.find(Team.class, 100L);
        findMember.changeTeam(newTeam);
    }

    // 연관관계로 외래키 매핑
    private static void ex5_1_2(EntityManager em) {
        final Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        final Member member = new Member();
        member.setUsername("member1");
        member.changeTeam(team);
        em.persist(member);

        // 아래 findMember에서 select 쿼리를 보고 싶다할떄 flush, clear
//        em.flush();
//        em.clear();

        final Member findMember = em.find(Member.class, member.getId());
        final Team findTeam = findMember.getTeam();
        System.out.println("findTeam = " + findTeam.getId());
    }

    // 데이터베이스 중심적으로 설계
    private static void ex5_1_1(EntityManager em) {
        final Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        final Member member = new Member();
        member.setUsername("member1");
        member.setTeamId(team.getId());

        em.persist(member);

        final Member findMember = em.find(Member.class, member.getId());
        final Long findTeamId = findMember.getTeamId();
        final Team findTeam = em.find(Team.class, findTeamId);
    }

    private static void ex4_1_2(EntityManager em) {
        final Member member = em.find(Member.class, 1L);
        System.out.println(member.getId());
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
