package com.example.raiseTechcoursetask10.service;

import com.example.raiseTechcoursetask10.controller.form.SkiresortCreateForm;
import com.example.raiseTechcoursetask10.entity.Skiresort;
import com.example.raiseTechcoursetask10.mapper.SkiresortMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkiresortServiceImpl implements SkiresortService {

    // field(MapperをServiceで使えるようにする)
    private final SkiresortMapper skiresortMapper;

    // constructor(MapperをServiceで使えるようにする)
    public SkiresortServiceImpl(SkiresortMapper skiresortMapper) {
        this.skiresortMapper = skiresortMapper;
    }

    @Override
    public List<Skiresort> findAll() {
        return skiresortMapper.findAll();
    }

    @Override
    public Skiresort findById(int id) {
        return skiresortMapper.findById(id);
    }

    @Override
    public Skiresort createSkiresort(SkiresortCreateForm skiresortCreateForm) {
        Skiresort skiresort = new Skiresort(
                skiresortCreateForm.getId(),
                skiresortCreateForm.getName(),
                skiresortCreateForm.getArea(),
                skiresortCreateForm.getCustomerEvaluation()
        );


        skiresortMapper.insertSkiresort(skiresort);

        return skiresort;
    }
}
