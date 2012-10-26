package es.finnapps.piggybank.model;

public class UserInfo {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String street;
    private String number;
    private String city;
    private String postalCode;
    private String country;


    public UserInfo(String userName, String password, String firstName, String lastName, String street, String number,
            String city, String postalCode, String country) {
        super();
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getStreet() {
        return street;
    }


    public String getNumber() {
        return number;
    }


    public String getCity() {
        return city;
    }


    public String getPostalCode() {
        return postalCode;
    }


    public String getCountry() {
        return country;
    }
}
