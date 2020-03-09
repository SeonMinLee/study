package io.github.jwee0330.studyjpa.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*; import java.util.ArrayList; import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue @Column(name = "member_id") private Long id;
    private String name;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public static Member createMember(String name, String city, String street, String zipcode) {
        Member member = new Member();
        member.setName(name);
        Address address = new Address(city, street, zipcode);
        member.setAddress(address);
        return member;
    }
}