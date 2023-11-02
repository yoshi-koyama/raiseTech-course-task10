package com.example.raisetechcoursetask10.integrationtest;

import com.example.raisetechcoursetask10.controller.form.SkiresortCreateForm;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SkiresortRestApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private static Validator validator;

    // 一番最初に一度だけ実行される
    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    class ReadAllTest {
        @Test
        @DataSet(value = "datasets/it/skiresort.yml")
        @Transactional
        void スキーリゾートを全件取得したときステータスコードが200を返すこと() throws Exception {
            String response = mockMvc.perform(MockMvcRequestBuilders.get("/skiresorts"))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            JSONAssert.assertEquals("""
                    [
                        {
                            "name": "LakeLouise",
                            "area": "Canada"
                        },
                        {
                            "name": "Vail",
                            "area": "Colorado"
                        },
                        {
                            "name": "Zermatt",
                            "area": "Swiss"
                        }
                    ]
                    """, response, JSONCompareMode.STRICT);
        }
    }

    @Nested
    class ReadByIdTest {

        @Test
        @DataSet(value = "datasets/it/skiresort.yml")
        @Transactional
        void 存在するIDのスキーリゾートを取得した時ステータスコードが200を返すこと() throws Exception {
            String response = mockMvc.perform(MockMvcRequestBuilders.get("/skiresorts/{id}", 3))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            JSONAssert.assertEquals("""
                        {
                            "name": "Zermatt",
                            "area": "Swiss"
                        }
                    """, response, JSONCompareMode.STRICT);
        }


        @Test
        @DataSet(value = "datasets/it/skiresort.yml")
        @Transactional
        void 存在しないIDのスキーリゾートを取得した時ステータスコードが404エラーを返すこと() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/skiresorts/{id}", 99))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class CreateTest {
        @Test
        @DataSet(value = "datasets/it/skiresort.yml")
        @ExpectedDataSet(value = "datasets/it/create-skiresort.yml", ignoreCols = {"id"})
        @Transactional
        void 新規のスキーリゾートを登録した時ステータスコードが201を返すこと() throws Exception {
            String response = mockMvc.perform(MockMvcRequestBuilders.post("/skiresorts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "name": "Whistler",
                                        "area": "Canada",
                                        "customerEvaluation": "Obtained Canadian snowboard instructor license"
                                    }
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            JSONAssert.assertEquals("""
                    {
                        "name": "Whistler",
                        "area": "Canada"
                    }                  
                    """, response, JSONCompareMode.STRICT);
        }

        @Test
        @Transactional
        void nameに21文字登録した時ステータスコードが400を返すこと() throws Exception {
            String response = mockMvc.perform(MockMvcRequestBuilders.post("/skiresorts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "name": "aaaaaaaaaaaaaaaaaaaaa",
                                        "area": "Canada",
                                        "customerEvaluation": "The scenery was very beautiful"   
                                    }
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            JSONAssert.assertEquals("""
                    {
                        "status": "400",
                        "message": "1文字以上20文字以下で入力してください",
                        "timestamp": "2023-11-02T06:00.123456789+09:00[JST/Tokyo]",
                        "error": "Bad Request"
                    }
                    // timestampは比較対象外
                    """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", ((o1, o2) -> true))));
        }

        @Test
        public void mameに0文字登録した時バリデーションエラーとなること() throws Exception {
            SkiresortCreateForm createForm = new SkiresortCreateForm("", "Canada", "The scenery was very beautiful");
            var violations = validator.validate(createForm);
            // バリデーションが1つ発生することの検証
            assertThat(violations).hasSize(2);
            assertThat(violations)
                    .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder(
                            tuple("name", "空白は許可されていません"),
                            tuple("name", "1文字以上20文字以下で入力してください")
                    );
        }

        @Test
        public void nameに1文字登録した時バリデーションエラーとならないこと() throws Exception {
            SkiresortCreateForm createForm = new SkiresortCreateForm("a", "Canada", "The scenery was very beautiful");
            var violations = validator.validate(createForm);
            assertThat(violations).isEmpty();
        }

        @Test
        public void nameに20文字登録した時バリデーションエラーとならないこと() throws Exception {
            SkiresortCreateForm createForm = new SkiresortCreateForm("aaaaaaaaaaaaaaaaaaaa", "Canada", "The scenery was very beautiful");
            var violations = validator.validate(createForm);
            assertThat(violations).isEmpty();
        }

        @Test
        public void nameに21文字登録した時バリデーションエラーとなること() throws Exception {
            SkiresortCreateForm createForm = new SkiresortCreateForm("aaaaaaaaaaaaaaaaaaaaa", "Canada", "The scenery was very beautiful");
            var violations = validator.validate(createForm);
            // バリデーションエラーが1つ発生することの検証
            assertThat(violations).hasSize(1);
            assertThat(violations)
                    .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder(tuple("name", "1文字以上20文字以下で入力してください"));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        @DataSet(value = "datasets/it/skiresort.yml")
        @ExpectedDataSet(value = "datasets/it/update-skiresort.yml")
        @Transactional
        void 存在するIDを指定してスキーリゾート情報を更新するとステータスコード200を返すこと() throws Exception {
            String response = mockMvc.perform(MockMvcRequestBuilders.patch("/skiresorts/{id}", 3)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                          "id": 3,
                                          "name": "Treble Cone",
                                          "area": "NewZealand",
                                          "customerEvaluation": "Features a long course with views of Lake Wanaka"
                                    }                                      
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            JSONAssert.assertEquals("""
                    {
                        "message": "successfully update"
                    }                   
                    """, response, JSONCompareMode.STRICT);
        }

        @Test
        @DataSet(value = "datasets/it/skiresort.yml")
        @Transactional
        void 存在しないIDのスキーリゾートを更新した時ステータスコード404を返すこと() throws Exception {
            String response = mockMvc.perform(MockMvcRequestBuilders.patch("/skiresorts/{id}", 100)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "id": 100,
                                        "name": "Blue Mountain",
                                        "area": "Canada",
                                        "customerEvaluation": "All of the lodges and ski houses are cute, like a dreamland"
                                    }                                                              
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            JSONAssert.assertEquals("""
                    {
                        "path": "/skiresorts/100",
                        "status": "404",
                        "message": "resource not found",
                        "timestamp": "2023-10-20T20:48.123456789+09:00[JST/Tokyo]",
                        "error": "Not Found"
                    }
                    // timestampは比較対象外
                    """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", ((o1, o2) -> true))));
        }
    }

    @Nested
    class DeleteTest {
        @Test
        @DataSet(value = "datasets/it/skiresort.yml")
        @ExpectedDataSet(value = "datasets/it/delete-skiresort.yml")
        @Transactional
        void 存在するIDを指定してスキーリゾートを削除したときステータスコード200を返すこと() throws Exception {
            String response = mockMvc.perform(MockMvcRequestBuilders.delete("/skiresorts/{id}", 3))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            JSONAssert.assertEquals("""
                    {
                        "message": "successfully deleted"
                    }
                    """, response, JSONCompareMode.STRICT);
        }

        @Test
        @DataSet(value = "datasets/it/skiresort.yml")
        @Transactional
        void 存在しないIDのスキーリゾートを削除した時ステータスコードは404を返すこと() throws Exception {
            String response = mockMvc.perform(MockMvcRequestBuilders.delete("/skiresorts/{id}", 5))
                    .andExpect(status().isNotFound())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            JSONAssert.assertEquals("""
                    {
                        "path": "/skiresorts/5",
                        "status": "404",
                        "message": "resource not found",
                        "timestamp": "2023-10-26T07:00:00:123456789+09:00[JST/Tokyo]",
                        "error": "Not Found"
                    }
                    // timestampは比較対象外                        
                    """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", ((o1, o2) -> true))));
        }
    }
}
