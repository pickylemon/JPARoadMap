package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity{

    //매핑시 애너테이션으로 메타정보를 적어주는 편이 좋다.(ex. indexes, length, unique 등)
    //테이블을 직접 까보지 않고 애너테이션만보고도 바로 알 수 있으므로
    //JPQL같은 쿼리짜기에 좋다. or 사용할 인덱스를 정확히 고를 수 있다.
    @Id @GeneratedValue(strategy = GenerationType.AUTO) //생략해도 기본 값 auto
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
//    private String city;
//    private String street;
//    private String zipcode;

    @Embedded
    private Address homeAddress;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); //관례상 ArrayList로 초기화 해줌(NPE 방지)

    public void addOrder(Order order){
        orders.add(order);
        order.setMember(this);
    } //연관관계 편의메서드

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

//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public String getZipcode() {
//        return zipcode;
//    }
//
//    public void setZipcode(String zipcode) {
//        this.zipcode = zipcode;
//    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
