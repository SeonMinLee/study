package io.github.jwee0330.springdatajpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "team")
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

}
