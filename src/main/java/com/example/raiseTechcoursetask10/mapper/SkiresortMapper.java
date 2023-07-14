package com.example.raisetechcoursetask10.mapper;

import com.example.raisetechcoursetask10.entity.Skiresort;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SkiresortMapper {
    @Select("SELECT * FROM skiresort")
    List<Skiresort> findAll();

    @Select("SELECT * FROM skiresort WHERE id = #{id}")
//    Skiresort findById(int id);
    Optional<Skiresort> findById(int id);

    @Insert("INSERT INTO skiresort (id, name, area, customerEvaluation) VALUES (#{id}, #{name}, #{area}, #{customerEvaluation})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertSkiresort(Skiresort skiresort);
}
