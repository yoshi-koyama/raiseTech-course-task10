package com.example.raisetechcoursetask10.controller.form;


import jakarta.validation.constraints.NotBlank;

public class SkiresortCreateForm {

    @NotBlank
    private String name;

    @NotBlank
    private String area;

    @NotBlank
    private String customerEvaluation;

    public SkiresortCreateForm(String name, String area, String customerEvaluation) {
        this.name = name;
        this.area = area;
        this.customerEvaluation = customerEvaluation;
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
