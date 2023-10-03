package com.example.raisetechcoursetask10.mapper;

import com.example.raisetechcoursetask10.entity.Skiresort;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Nested;
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

    @Nested
    class FindAllTest {
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
                            new Skiresort(4, "イエティ", "静岡県", "10月オープンで日本一早い。激混み。")
                    );
        }

        @Nested
        class FindByIdTest {
            @Test
            @DataSet(value = "datasets/skiresort.yml")
            @Transactional
            void 指定したidのスキーリゾートが取得できること() {
                assertThat(skiresortMapper.findById(1))
                        .contains(new Skiresort(1, "安比高原", "岩手県", "いつも天気が悪い。"));
            }
        }

        @Nested
        class CreateTest {
            @Test
            @DataSet(value = "datasets/create-skiresort.yml")
            @Transactional
            void 新規のスキーリゾートが登録できること() {
                assertThat(skiresortMapper.findById(1))
                        .contains(new Skiresort(1, "安比高原", "岩手県", "いつも天気が悪い。"));
            }
        }

        @Nested
        class UpdateTest {
            @Test
            @DataSet(value = "datasets/before-update-skiresort.yml")
            @ExpectedDataSet(value = "datasets/update-skiresort.yml")
            @Transactional
            void 指定したidのスキーリゾートが更新できること() {
                Skiresort skiresort = new Skiresort(1, "天元台", "山形県", "圧雪していないようで、ほぼ不正地");
                skiresortMapper.updateSkiresort(skiresort);
            }

            @Test
            @DataSet(value = "datasets/skiresort.yml")
            @ExpectedDataSet(value = "datasets/after-update-skiresort.yml")
            @Transactional
            void 更新時に指定したidが存在しないときテーブルのレコードが更新されないこと() {
                Skiresort skiresortUpdate = new Skiresort(4, "栂池高原", "長野県", "幅1kmの初心者に最適なコースがある");
                skiresortMapper.updateSkiresort(skiresortUpdate);
            }
        }

        @Test
        @DataSet(value = "datasets/empty-skiresort.yml")
        @Transactional
        void レコードが存在しない場合に空のListが取得できること() {
            assertThat(skiresortMapper.findAll().isEmpty());
        }
    }

    @Nested
    class DeleteTest {
        @Test
        @DataSet(value = "datasets/skiresort.yml")
        @ExpectedDataSet(value = "datasets/delete-skiresort.yml")
        @Transactional
        void 指定したIDのスキーリゾート情報を削除すること() {
            skiresortMapper.deleteSkiresort(3);
        }

        @Test
        @DataSet(value = "datasets/skiresort.yml")
        @ExpectedDataSet(value = "datasets/after-delete-skiresort.yml")
        @Transactional
        void 削除時に指定したidが存在しないときテーブルのレコードが削除されないこと() {
            skiresortMapper.deleteSkiresort(4);
        }
    }
}
