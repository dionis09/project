package com.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Address extends ObjectMongo{

    private String street = "";
    private String houseNumber = "";
    private String zipCode = "";
    private String city = "";
    private String country = "";

    @Override
    public String toString() {
        return
                street.replace(" ", "+") + ',' + houseNumber + ',' + zipCode + '+' + city + '+' + country;
    }
}
