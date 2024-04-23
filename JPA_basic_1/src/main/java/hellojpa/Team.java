package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "TEAM_SEQ_GENERATOR",
        sequenceName = "TEAM_SEQ",
        initialValue = 1, allocationSize = 50
)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_SEQ_GENERATOR")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    //하나의 팀은 여러 멤버를 가질 수 있다. 팀 입장에서는 1:N
    //연결되어 있는 객체의 변수명을 mappedBy에 적어주기
//    @OneToMany
//    @JoinColumn(name = "TEAM_ID")
    private List<Member> members = new ArrayList<>();
    //ArrayList로 초기화를 해두기(관례) add시 NPE가 발생하지 않는다.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
}
