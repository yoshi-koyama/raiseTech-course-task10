package com.example.raiseTechcoursetask10.mapper;

import com.example.raisetechcoursetask10.entity.Skiresort;
import com.example.raisetechcoursetask10.mapper.SkiresortMapper;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SkiresortMapperTest {

    @Autowired
    SkiresortMapper skiresortMapper;

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-skiresort.sql", "classpath:/sqlannotation/insert-skiresort.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 全てのスキーリゾートが取得できること() {
        List<Skiresort> skiresorts = skiresortMapper.findAll();
        assertThat(skiresorts)
                .hasSize(4)
                .contains(
                        new Skiresort(1, "rusutsu", "hokkaido", "雪質が良くて、カービング練習に最適。"),
                        new Skiresort(2, "zao", "yamagata", "凄く広くて、樹氷が綺麗だった。"),
                        new Skiresort(3, "marunuma-kogen", "gunma", "初心者から上級者まで楽しめる。ゲレ食のアクセスが悪い。"),
                        new Skiresort(4, "hakubaGoryu", "nagano", "GWまで滑れる。外国人が多くていつも混んでる。")
                );
    }
}