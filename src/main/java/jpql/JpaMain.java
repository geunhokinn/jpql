package jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            List<MemberDto> result = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                    .getResultList();

            MemberDto memberDto = result.get(0);
            System.out.println("memberDto.getUsername() = " + memberDto.getUsername());
            System.out.println("memberDto.getAge() = " + memberDto.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
