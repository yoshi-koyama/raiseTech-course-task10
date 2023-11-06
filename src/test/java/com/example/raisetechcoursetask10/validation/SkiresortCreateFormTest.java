package com.example.raisetechcoursetask10.validation;

import com.example.raisetechcoursetask10.controller.form.SkiresortCreateForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

public class SkiresortCreateFormTest {

    private static Validator validator;

    // 一番最初に一度だけ実行される
    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    class NameTest {
        @BeforeEach
        void setUp() {
            // テストケース内でのローカライズを設定（日本語を指定）
            Locale.setDefault(Locale.JAPANESE);
        }

        @Test
        public void nameに1文字未満を登録した時バリデーションエラーとなること() {

            SkiresortCreateForm createForm = new SkiresortCreateForm("", "Canada", "The scenery was very beautiful");
            var violations = validator.validate(createForm);
            // バリデーションが2つ発生することの検証
            assertThat(violations).hasSize(2);
            assertThat(violations)
                    .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder(
                            tuple("name", "空白は許可されていません"),
                            tuple("name", "1文字以上20文字以下で入力してください")
                    );

            Locale.setDefault(Locale.getDefault());
        }

        @Test
        public void nameに1文字登録した時バリデーションエラーとならないこと() {
            SkiresortCreateForm createForm = new SkiresortCreateForm("a", "Canada", "The scenery was very beautiful");
            var violations = validator.validate(createForm);
            assertThat(violations).isEmpty();
        }

        @Test
        public void nameに20文字登録した時バリデーションエラーとならないこと() {
            SkiresortCreateForm createForm = new SkiresortCreateForm("aaaaaaaaaaaaaaaaaaaa", "Canada", "The scenery was very beautiful");
            var violations = validator.validate(createForm);
            assertThat(violations).isEmpty();
        }

        @Test
        public void nameに21文字登録した時バリデーションエラーとなること() {
            SkiresortCreateForm createForm = new SkiresortCreateForm("aaaaaaaaaaaaaaaaaaaaa", "Canada", "The scenery was very beautiful");
            var violations = validator.validate(createForm);
            // バリデーションエラーが1つ発生することの検証
            assertThat(violations).hasSize(1);
            assertThat(violations)
                    .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder(tuple("name", "1文字以上20文字以下で入力してください"));
        }
    }
}
