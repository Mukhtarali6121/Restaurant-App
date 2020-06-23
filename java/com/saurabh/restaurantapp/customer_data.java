package com.saurabh.restaurantapp;

public class customer_data {
    private String name1,phone,email,pass;
    public customer_data() {
    }

    public customer_data(String name1, String phone, String email, String pass) {
        this.name1 = name1;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
