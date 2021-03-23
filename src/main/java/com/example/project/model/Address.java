package com.example.project.model;

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
