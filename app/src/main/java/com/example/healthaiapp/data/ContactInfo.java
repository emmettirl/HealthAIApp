package com.example.healthaiapp.data;

import java.io.Serializable;

public class ContactInfo implements Serializable {
    String name;
    String phone;
    String email;

    public ContactInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String fname) {
        this.name = fname;
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
}
