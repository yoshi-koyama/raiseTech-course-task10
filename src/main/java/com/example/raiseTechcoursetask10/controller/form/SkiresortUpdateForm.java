package com.example.raisetechcoursetask10.controller.form;

public class SkiresortUpdateForm {
    private int id;
    private String name;
    private String area;
    private String customerEvaluation;

    public SkiresortUpdateForm(int id, String name, String area, String customerEvaluation) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.customerEvaluation = customerEvaluation;
    }

    // SkiresortUpdateFormクラスのいんすたんすからidを取得するため、引数なし
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCustomerEvaluation() {
        return customerEvaluation;
    }

    public void setCustomerEvaluation(String customerEvaluation) {
        this.customerEvaluation = customerEvaluation;
    }
}
