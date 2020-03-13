package io.github.jwee0330.springdatajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

@SpringBootTest
@Rollback(false)
@Transactional
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void test() {
        Team teamA = new Team("Team A");
        entityManager.persist(teamA);
        Team teamB = new Team("Team B");
        entityManager.persist(teamB);

        Member john = new Member("john", 30, teamA);
        Member doe = new Member("doe", 20, teamA);
        Member jack = new Member("jack", 10, teamB);
        Member kate = new Member("kate", 25, teamB);

        memberRepository.saveAll(Arrays.asList(john, doe, jack, kate));
    }


}