package com.example.raisetechcoursetask10.controller.response;

import com.example.raisetechcoursetask10.entity.Skiresort;

public class SkiresortResponse {
    // レスポンスに返したいフィールドだけを定義する
    private final String name;
    private final String area;

    public SkiresortResponse(Skiresort skiresort) {
        this.name = skiresort.getName();
        this.area = skiresort.getArea();
    }

    public String getName() {
        return name;
    }

    public String getArea() {
        return area;
    }
}
