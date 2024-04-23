package hellojpa.valueType;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address() {
    } //기본 생성자는 필수

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    //불변객체로 만들기 위해 setter를 만들지 않는다(!) 중요!!
    //또는 private setter로만 설정하기
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

//    public void setStreet(String street) {
//        this.street = street;
//    }

    public String getZipcode() {
        return zipcode;
    }

//    public void setZipcode(String zipcode) {
//        this.zipcode = zipcode;
//    }
}
