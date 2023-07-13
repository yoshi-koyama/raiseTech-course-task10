package com.example.raiseTechcoursetask10.controller.form;

public class SkiresortCreateForm {
    private int id;
    private String name;
    private String area;
    private String customerEvaluation;

    public SkiresortCreateForm(int id, String name, String area, String customerEvaluation) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.customerEvaluation = customerEvaluation;
    }

    public int getId() {
        return this.id;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCustomerEvaluation() {
        return this.customerEvaluation;
    }

    public void setCustomerEvaluation(String customerEvaluation) {
        this.customerEvaluation = customerEvaluation;
    }
}
