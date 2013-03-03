package de.puzzles.core;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @author Patrick Groß-Holtwick
 * Date: 03.03.13
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class Transaction implements Serializable {

    private Integer id;
    private Integer requestId;
    private String description;
    private String description1;
    private String description2;
    private Double value;

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
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
