package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*класс для приема данных от клиента в виде строк*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usr {
    private String name;
    private String lastName;
    private String date;
    private String authorisation;

    private String pass;

    public void setAuthorisation(String authorisation) {
        this.authorisation = authorisation;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAuthorisation() {
        return authorisation;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthority(String authority) {
        this.authorisation = authority;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDate() {
        return date;
    }

    public String getAuthority() {
        return authorisation;
    }
}
