package com.saurabh.restaurantapp;

import android.widget.EditText;

public class restaurant_data {
    String name,phone,add,email,pass;
    public restaurant_data() {
    }

    public restaurant_data(String name, String phone, String add, String email, String pass) {
        this.name = name;
        this.phone = phone;
        this.add = add;
        this.email = email;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
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
