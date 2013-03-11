package de.puzzles.core.domain;

import java.io.Serializable;

/**
 * This class represents the <code>transaction</code> object.
 * This class is a model class. Instances of this class represent <code>transaction</code> objects.
 * The instance of the object stores the data of an <code>transaction</code> during the runtime.
 *
 * @author Patrick Groß-Holtwick
 *         Date: 03.03.13
 */
public class Transaction implements Serializable {

    private Integer id;
    private Integer requestId;
    private String description;
    private String description1;
    private String description2;
    private Double value = 0.0;

    public Transaction() {
    }

    public Transaction(Integer id, Integer requestId, String description, String description1, String description2, Double value) {
        this.id = id;
        this.requestId = requestId;
        this.description = description;
        this.description1 = description1;
        this.description2 = description2;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public Double getValue() {
        return value == null ? 0.0 : value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
