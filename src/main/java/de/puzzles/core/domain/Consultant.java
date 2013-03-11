package de.puzzles.core.domain;

import java.io.Serializable;

/**
 * This class represents the <code>consultant</code> object.
 * This class is a model class. Instances of this class represent <code>consultant</code> objects.
 * The instance of the object stores the data of an <code>consultant</code> during the runtime.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 03.03.13
 */
public class Consultant implements Serializable {

    private Integer id;
    private String firstname;
    private String lastname;
    private String username;

    private String passwordHash;

    public Consultant(Integer id, String firstname, String lastname, String username, String passwordHash) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.passwordHash = passwordHash;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
