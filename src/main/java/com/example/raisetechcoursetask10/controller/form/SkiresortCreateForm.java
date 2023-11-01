package com.example.raisetechcoursetask10.controller.form;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SkiresortCreateForm {

    @Size(min = 1, max = 20, message = "1文字以上20文字以下で入力してください")
    @NotBlank
    private String name;

    @Size(min = 1, max = 20, message = "areaは1文字以上20文字以下で入力してください")
    @NotBlank
    private String area;

    @Size(min = 1, max = 50, message = "customerEvaluationは1文字以上50文字以下で入力してください")
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
