package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;

@Transactional
@SpringBootTest
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        this.queryFactory = new JPAQueryFactory(em);
    }

    @DisplayName("JPQL :: findOne")
    @Test
    public void startJPQL() {
        //given & when
        Member foundOne = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        //then
        assertThat(foundOne.getUsername()).isEqualTo("member1");
    }

    @DisplayName("QueryDSL :: findOne")
    @Test
    public void startQuerydsl() {
        //given
        QMember m = new QMember("m");

        //when
        Member foundOne = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();
        //then
        assertThat(foundOne.getUsername()).isEqualTo("member1");
    }

    @DisplayName("Q타입 확인")
    @Test
    public void basicQEntity() {
        //given && when
        Member foundOne = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member2"))
                .fetchOne();

        // then
        assertThat(foundOne.getUsername()).isEqualTo("member2");
    }

    @DisplayName("검색 조건 쿼리")
    @Test
    public void search() {
        // given & when
        Member foundOne = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.between(10, 30)))
                .fetchOne();

        // then
        assertThat(foundOne.getUsername()).isEqualTo("member1");
    }

    /**
     * where 절 안의 조건을 , 로 나열 가능
     * null 이 있으면 where 조건을 무시한다.
     * 이를 활용하여 동적쿼리를 편리하게 작성할 수 있다.
     */
    @DisplayName("검색 조건 쿼리 AND PARAM 의 다른 용법")
    @Test
    public void searchAndParam() {
        // given & when
        Member foundOne = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1"),
                        member.age.between(10, 30), null
                )
                .fetchOne();

        // then
        assertThat(foundOne.getUsername()).isEqualTo("member1");
    }
}
