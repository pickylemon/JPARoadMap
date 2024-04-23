package hellojpa;

import hellojpa.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) //생략해도 기본 값 auto
    @Column(name = "MEMBER_ID")
    private Long id;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "TEAM_ID")
//    @JoinColumn(insertable = false, updatable = false) // team을 연관관계 주인으로 했을 경우.
    private Team team;
    private String city;
    private String street;
    private String zipcode;

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT") //joinColumn이 아니라 joinTable
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

//    @Override
//    public String toString() {
//        return "Member{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", team=" + team +
//                ", city='" + city + '\'' +
//                ", street='" + street + '\'' +
//                ", zipcode='" + zipcode + '\'' +
//                '}';
//    }
}
