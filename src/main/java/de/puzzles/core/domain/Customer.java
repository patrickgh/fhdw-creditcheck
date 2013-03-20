package de.puzzles.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents the <code>customer</code> object.
 * This class is a model class. Instances of this class represent <code>customer</code> objects.
 * The instance of the object stores the data of a <code>customer</code> during the runtime.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 03.03.13
 */

public class Customer implements Serializable {

    private Integer id;
    private String firstname;
    private String lastname;
    private Date birthday;
    private String street;
    private String city;
    private String zipcode;
    private String telephone;
    private String email;
    private String accountnumber;
    private String bankcode;

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        if (birthday != null) {
            return new Date(birthday.getTime());
        }
        return null;
    }

    public void setBirthday(Date birthday) {
        if (birthday != null) {
            this.birthday = new Date(birthday.getTime());
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }
}
