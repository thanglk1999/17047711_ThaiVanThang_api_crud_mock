package com.thang.a17047711_thaivanthang_api_crud_mock;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String gender;
    private String salary;

    public User() {
    }

    public User(int id, String firstname, String lastname, String gender, String salary) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getSalary() {
        return salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id+" | "+firstname+" - "+lastname+" | "+gender+" | "+salary;
    }
}
