package io.github.jwee0330.springdatajpa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@ToString(of = {"id", "username", "age"})
@Table(name = "member")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username, Integer age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeMember(team);
        }
    }

    private void changeMember(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

}
