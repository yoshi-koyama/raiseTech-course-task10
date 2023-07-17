package com.example.raisetechcoursetask10.service;

import com.example.raisetechcoursetask10.controller.form.SkiresortCreateForm;
import com.example.raisetechcoursetask10.controller.form.SkiresortUpdateForm;
import com.example.raisetechcoursetask10.entity.Skiresort;
import com.example.raisetechcoursetask10.exception.ResourceNotFoundException;
import com.example.raisetechcoursetask10.mapper.SkiresortMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Skiresort> skiresort = this.skiresortMapper.findById(id);
        return skiresort.orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }

    @Override
    public Skiresort createSkiresort(SkiresortCreateForm skiresortCreateForm) {
        Skiresort skiresort = new Skiresort(
                0, //idの仮初期値として設定
                skiresortCreateForm.getName(),
                skiresortCreateForm.getArea(),
                skiresortCreateForm.getCustomerEvaluation()
        );

        skiresortMapper.insertSkiresort(skiresort);

        return skiresort;
    }

    @Override
    public void updateSkiresort(SkiresortUpdateForm skiresortUpdateForm) {
        Optional<Skiresort> existingSkiresort = this.skiresortMapper.findById(skiresortUpdateForm.getId());

        // existingSkiresordのフィールドをskiresortUpdateFormの値で上書きする
        existingSkiresort.map(skiresort -> {
                    skiresort.setName(skiresortUpdateForm.getName());
                    skiresort.setArea(skiresortUpdateForm.getArea());
                    skiresort.setCustomerEvaluation(skiresortUpdateForm.getCustomerEvaluation());
                    return skiresort;
                }
        ).ifPresentOrElse(this.skiresortMapper::updateSkiresort, () -> {
            throw new ResourceNotFoundException("resource not found!");
        });
    }
}
