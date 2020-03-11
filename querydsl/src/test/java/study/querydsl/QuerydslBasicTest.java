package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.*;

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

    @DisplayName("다양한 검색 조건을 이용한 쿼리")
    @Test
    public void variousSearchConditions() {
        // given & when
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();

        // 결과 없으면 널, 둘 이상이면 NonUniqueResultException
        Member fetchOne = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        Member fetchFirst = queryFactory
                .selectFrom(member)
                .fetchFirst();

        List<Member> equalFetchFirst = queryFactory
                .selectFrom(member)
                .limit(1).fetch();

        // 결과, count 쿼리 2번 나감, 성능이 중요한 이슈일때 사용하면 안됨
        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .fetchResults();

        results.getTotal();
        List<Member> content = results.getResults();

        //  count 쿼리 한번만 침
        long count = queryFactory
                .selectFrom(member)
                .fetchCount();
    }

    /**
     * 정렬 순서
     * 1. 나이 내림차순
     * 2. 이름 올림차순
     * 단 2에서 이름이 없으면 마지막에 출력(nulls last)
     */
    @DisplayName("정렬")
    @Test
    public void sort() {
        //given
        Member nullMember = new Member(null, 100);
        Member member5 = new Member("member5", 100);
        Member member6 = new Member("member6", 100);

        em.persist(nullMember);
        em.persist(member5);
        em.persist(member6);

        //when
        List<Member> fetchResult = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(
                        member.age.desc(),
                        member.username.asc().nullsLast())
                .fetch();

        //then
        assertThat(fetchResult)
                .containsSequence(member5, member6, nullMember);
    }

    /**
     * 쿼리 결과
     * select
     *         member0_.member_id as member_i1_1_,
     *         member0_.age as age2_1_,
     *         member0_.team_id as team_id4_1_,
     *         member0_.username as username3_1_
     *     from
     *         member member0_
     *     order by
     *         member0_.username desc limit ? offset ?
     */
    @DisplayName("페이징 - 1")
    @Test
    public void paging1() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    @DisplayName("페이징 - 2")
    @Test
    public void test() {
        //given & when
        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        //then
        assertThat(results.getTotal()).isEqualTo(2);
        assertThat(results.getLimit()).isEqualTo(2);
        assertThat(results.getOffset()).isEqualTo(1);
        assertThat(results.getResults().size()).isEqualTo(2);
    }

    /**
     * Querydsl 이 제공하는 Tuple
     *
     * [query]
     * select
     *         count(member0_.member_id) as col_0_0_,
     *         sum(member0_.age) as col_1_0_,
     *         avg(cast(member0_.age as double)) as col_2_0_,
     *         max(member0_.age) as col_3_0_,
     *         min(member0_.age) as col_4_0_
     *     from
     *         member member0_
     */
    @DisplayName("집계 함수")
    @Test
    public void aggregation() {
        //given & when
        List<Tuple> result = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();
        //then
        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구하기.
     */
    @Test
    public void group() {
        //given & when
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();
        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        //then
        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15); // (10 + 20) / 2

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35); // (30 + 40) / 35
    }
}
