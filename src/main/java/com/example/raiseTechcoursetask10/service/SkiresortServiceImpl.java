package com.example.raiseTechcoursetask10.service;

import com.example.raiseTechcoursetask10.entity.Skiresort;
import com.example.raiseTechcoursetask10.mapper.SkiresortMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkiresortServiceImpl {

    // field(MapperをServiceで使えるようにする)
    private final SkiresortMapper skiresortMapper;

    // constructor(MapperをServiceで使えるようにする)
    public SkiresortServiceImpl(SkiresortMapper skiresortMapper) {
        this.skiresortMapper = skiresortMapper;
    }

    public List<Skiresort> findAll() {
        return skiresortMapper.findAll();
    }
}
