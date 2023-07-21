package com.example.raisetechcoursetask10.controller.form;


import jakarta.validation.constraints.NotNull;

public class SkiresortUpdateForm {
    @NotNull
    private final String name;
    @NotNull
    private String area;
    @NotNull
    private String customerEvaluation;

    public SkiresortUpdateForm(String name, String area, String customerEvaluation) {
        this.name = name;
        this.area = area;
        this.customerEvaluation = customerEvaluation;
    }

    // SkiresortUpdateFormクラスのインスタンスからidを取得するため、引数なし
    public String getName() {
        return name;
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
