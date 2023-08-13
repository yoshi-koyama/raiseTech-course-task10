package com.example.raiseTechcoursetask10.mapper;

import com.example.raisetechcoursetask10.entity.Skiresort;
import com.example.raisetechcoursetask10.mapper.SkiresortMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
    @DataSet(value = "datasets/skiresort.yml")
    @Transactional
    void 全てのスキーリゾートが取得できること() {
        List<Skiresort> skiresorts = skiresortMapper.findAll();
        assertThat(skiresorts)
                .hasSize(3)
                .contains(
                        new Skiresort(1, "安比高原", "岩手県", "いつも天気が悪い。"),
                        new Skiresort(2, "月山", "山形県", "5月から雪がなくなるまで営業していて、全面コブになる。"),
                        new Skiresort(3, "イエティ", "静岡県", "10月オープンで日本一早い。激混み。")
                );
    }

    @Test
    @DataSet(value = "datasets/skiresort.yml")
    @Transactional
    void 指定したidのスキーリゾートが取得できること() {
        assertThat(skiresortMapper.findById(1))
                .contains(new Skiresort(1, "安比高原", "岩手県", "いつも天気が悪い。"));
    }

    @Test
    @DataSet(value = "datasets/empty-skiresort.yml")
    @Transactional
    void レコードが存在しない場合に空のListが取得できること() {
        assertThat(skiresortMapper.findAll().isEmpty());
    }
}