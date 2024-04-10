package hellojpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//@Entity //JPA가 관리하는 대상임을 명시
//@Table(name = "member")
public class Member_old {
    //@Id
    Long id;
    String name;

    public Member_old() {
    }

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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
