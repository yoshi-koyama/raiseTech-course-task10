package com.example.raisetechcoursetask10.mapper;

import com.example.raisetechcoursetask10.entity.Skiresort;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SkiresortMapper {
    @Select("SELECT * FROM skiresort")
    List<Skiresort> findAll();

    @Select("SELECT * FROM skiresort WHERE id = #{id}")
    Optional<Skiresort> findById(int id);

    @Insert("INSERT INTO skiresort (id, name, area, customerEvaluation) VALUES (#{id}, #{name}, #{area}, #{customerEvaluation})")
    // idを自動生成する
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertSkiresort(Skiresort skiresort);

    @Update("UPDATE skiresort SET name = #{name}, area = #{area}, customerEvaluation = #{customerEvaluation} WHERE id = #{id}")
    void updateSkiresort(Skiresort skiresort);
}
