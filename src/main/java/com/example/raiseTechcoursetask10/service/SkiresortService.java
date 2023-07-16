package com.example.raisetechcoursetask10.service;

import com.example.raisetechcoursetask10.controller.form.SkiresortCreateForm;
import com.example.raisetechcoursetask10.controller.form.SkiresortUpdateForm;
import com.example.raisetechcoursetask10.entity.Skiresort;

import java.util.List;

public interface SkiresortService {

    List<Skiresort> findAll();

    Skiresort findById(int id);

    Skiresort createSkiresort(SkiresortCreateForm skiresortCreateForm);

    Skiresort updateSkiresort(SkiresortUpdateForm skiresortUpdateForm);
}
