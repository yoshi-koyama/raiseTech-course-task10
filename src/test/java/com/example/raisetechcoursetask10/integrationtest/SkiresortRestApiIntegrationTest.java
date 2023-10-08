package com.example.raisetechcoursetask10.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SkiresortRestApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/skiresort.yml")
    @Transactional
    void スキーリゾートを全件取得したときステータスコードが200を返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/skiresorts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                [
                    {
                        "id": 1,
                        "name": "安比高原",
                        "area": "岩手県",
                        "customerEvaluation": "いつも天気が悪い。"
                    },
                    {
                        "id": 2,
                        "name": "月山",
                        "area": "山形県",
                        "customerEvaluation": "5月から雪がなくなるまで営業していて、全面コブになる。"
                    },
                    {
                        "id": 3,
                        "name": "イエティ",
                        "area": "静岡県",
                        "customerEvaluation": "10月オープンで日本一早い。激混み。"
                    }
                ]
                """, response, JSONCompareMode.STRICT);
        System.out.println(response);
    }
}
