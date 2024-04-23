package hellojpa.inheritence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity(name = "member2")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

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

    //    private String createdBy;
//    private LocalDateTime createdDate;
//    private String lastModifiedBy;
//    private LocalDateTime lastModifiedDate;
}
