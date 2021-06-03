package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// immutable object
@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode // 값 비교를 해야할 때가 있음 ex> Map<Address, String>
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
