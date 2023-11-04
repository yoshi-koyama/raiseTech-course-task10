フロント側ですり抜けたエラーをバックエンドで拾うことがある->Javaでのバリデーションは必要

## バリデーションの目的

- 入力内容や記述内容が要件を満たしているか、妥当性を確認すること
- 001-create-table-and-load-data.sqlで定義されているバリデーションよりも優先的にエラーチェックをする(Mapperの処理より先にエラーチェックを行う)

|           | Null | 空文字 | 空白 | 全角スペース |
|:---------:|:----:|:---:|:--:|:------:|
| @NotNull  |  ×   |  ○  | ○  |   ○    | 
| @NotEmpty |  ×   |  ×  | ○  |   ○    |
| @NotBlanc |  ×   |  ×  | ×  |   ○    |

- @Not Null：変数やメソッドの引数がnullであってはならない。変数や引数には必ず値が入っていなければならない。
- @Not Blanc：文字列が空ではないこと。文字列を少なくとも1つ以上持っていること（名前が空白のままでは送信できないなど）
- @Not Empty：コレクション（リスト、セットなど）が空ではないこと。1つ以上の要素を持っていること。

- 空文字:文字列が0のこと。
- null:箱も中身もない。何も存在しない。
- blanc:箱はあるけど何も入ってない。半角スペース、全角スペースのこと。

@Validated:@Validを拡張したSpringの機能
@Validated:@RequestParamアノテーションを使用する場合
@Valid:Jakarta Bean Validationで定義される

### 仮説を立ててデバッグ実行

@ExceptionHandlerで通るか？（問題がなければ通らない->問題が0個なら通らない）
`MethodArgumentNotValidExceptjon`:Controllerに`@Valid`や`@Validated`をつけたuserPostrequestをentityでバリデーションした（@NotBlankなど）結果
->Not Valid（正しくない）例外になるハンドラーである
->postリクエストを送ると返ってくる（返ってくる＝失敗）
->正しい場合:例外は出ない
->ExceptionHandlerを通る場合またはControllerを通る場合に分かれる

### Validator

- SkiresortUpdateFormにバリデーション設定
- @Size:1文字〜20文字 エラーメッセージも記述する
- @BeforeAll:付与されたstaticメソッドは全テストが実行される前に実行されるメソッド

- `import static org.assertj.core.api.Assertions.assertThat;`:手動で追加
- `import jakarta.validation.ValidatorFactory;`:手動で追加
- `ValidatorFactory 変数 = Validation.buildDefaultValidatorFactory();`:バリデーションを扱うValidatorクラスを生成するファクトリークラス
- `Validator 変数 = [ValidatorFactory] .getValidator();`:ValidatorFactoryからgetValidatorメソッドでValidatorクラスのインスタンスを取得する->
  Validatorがバリデーションに関する各種の機能を提供してくれる

- `var`:Java10以降で変数宣言で型推論を利用する(変数定義の際自動的に型決定される)->動的型付けではないため一度varで定義した変数に別の方の値を再代入することはできない

- `@Size`:"サイズは{min}から{max}の間でなければなりません"
- `.extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)`
    - `extracting `:AssertJライブラリの一部で、リストやコレクションから特定の要素を抽出して検証する
    - `violations`:リストの各要素に対して、ConstraintViolationオブジェクト(nameやarea)を文字列に変換し、ConstraintViolationオブジェクト(
      バリデーションエラーが発生した際に生成されるオブジェクト)からエラーメッセージを取得する
    - `containsExactlyInAnyOrder`:期待されるプロパティパス(バリデーションエラーが発生した場所)、エラーメッセージと実際の結果が一致することを検証する

- STRICT:日付と時刻を厳密に解決する
- LENIENT:日付けと時刻を厳密ではない方法で解決する

### エラー対策

- 期待値と違うエラー: デバッグして`response`を確認する
