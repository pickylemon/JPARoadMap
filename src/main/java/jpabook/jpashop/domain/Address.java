package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {
    @Column(length = 10)
    String city;
    @Column(length = 20)
    String street;
    @Column(length = 5)
    String zipcode;
    //값타입의 장점 : 의미있는 비즈니스 메서드를 만들 수 있다.
    public String getFullAddress(){
        return getCity() + "-" + getStreet() + "-" + getZipcode();
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    //값타입 -> 불변객체로 만들기 위해 setter는 private으로 설정
    private void setCity(String city) {
        this.city = city;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
    } //using getter 옵션을 사용하기 (proxy고려)

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
