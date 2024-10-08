package com.taskone.delivery.server;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email, password, name, phone, address;
    private String agreement = "false";

    public User(){}
    public User(String address, String agreement, String email, String name, String password, String phone){
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.agreement = agreement;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ",\nemail='" + email + '\'' +
                ",\npassword='" + password + '\'' +
                ",\nname='" + name + '\'' +
                ",\nphone='" + phone + '\'' +
                ",\naddress='" + address + '\'' +
                ",\nagreement='" + agreement + '\'' + "}\n--------\n";
    }


}
