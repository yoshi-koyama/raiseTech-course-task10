package com.example.raisetechcoursetask10.service;

import com.example.raisetechcoursetask10.controller.form.SkiresortCreateForm;
import com.example.raisetechcoursetask10.entity.Skiresort;

import java.util.List;

public interface SkiresortService {

    List<Skiresort> findAll();

    Skiresort findById(int id);

    Skiresort createSkiresort(SkiresortCreateForm skiresortCreateForm);

    void updateSkiresort(int id, String name, String area, String customerEvaluation);
}
