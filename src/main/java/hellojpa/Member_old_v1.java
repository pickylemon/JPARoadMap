package hellojpa;

//@Entity //JPA가 관리하는 대상임을 명시
//@Table(name = "member")
public class Member_old_v1 {
    //@Id
    Long id;
    String name;

    public Member_old_v1() {
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
