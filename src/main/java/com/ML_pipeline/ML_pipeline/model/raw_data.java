package com.ML_pipeline.ML_pipeline.model;

import jakarta.persistence.*;

@Entity
@Table(name = "raw_data")
public class raw_data {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "employee_name")
    private String employee_name;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "position")
    private String position;

    @Column(name = "years_of_exp")
    private Integer years_of_exp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getYears_of_exp() {
        return years_of_exp;
    }

    public void setYears_of_exp(Integer years_of_exp) {
        this.years_of_exp = years_of_exp;
    }
}
