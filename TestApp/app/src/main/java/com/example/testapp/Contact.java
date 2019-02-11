package com.example.testapp;

public class Contact {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private int id;
    private String name;
    private String lastname;
    private String cellphone;
    private String email;

    public Contact(int id, String name, String lastname, String cellphone, String email){
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.cellphone = cellphone;
        this.email = email;
    }
}
