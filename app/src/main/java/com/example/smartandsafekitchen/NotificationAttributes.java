package com.example.smartandsafekitchen;

public class NotificationAttributes {

    String tittle;
    String description;
    String datetime;
    String name;

    public NotificationAttributes() {
    }

    public NotificationAttributes(String tittle, String description, String datetime, String name) {
        this.tittle = tittle;
        this.description = description;
        this.datetime = datetime;
        this.name = name;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
