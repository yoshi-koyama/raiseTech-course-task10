package com.example.raiseTechcoursetask10.mapper;

import com.example.raiseTechcoursetask10.entity.Skiresort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SkiresortMapper {
    @Select("SELECT * FROM skiresort")
    List<Skiresort> findAll();
}
