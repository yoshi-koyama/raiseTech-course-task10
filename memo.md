# 単体テスト

# DBテスト

## @DBRiderの場合

- @DataSet：更新前のテーブル[引数にvalue:パスを書く]

- @ExpectedDataSet：期待値データ[引数にvalue:パスを書く]

updateSkiresortはSkiresortクラスを引数に取るわけですから、呼び出し方を変えないといけないですね。

```
Skiresort skiresort = new Skiresort(1, "田沢湖", "秋田県", "大会バーンの垂直に見える急斜面が面白かった。");
skiresortMapper.updateSkiresort(skiresort);
```

id1->更新されること
id2->更新されないこと

【@DataSetで定義する事前データ】

```
id=1, area="北海道"
id=2, area="新潟"
```

【更新対象と更新内容】

```
id=1, area="山形"
```

【@ExpectedDataSetで定義する期待値データ】

```
id=1, area="山形"
id=2, area="新潟"
```

## 考え方

- テストコードに書くメソッドはMapper.javaに書いてあること必須！！
- 全てのスキーリゾートが取得できること
    - 比較データ（ymlファイル）は1つで良い
    - `hasSize()`内にデータのサイズを書く
    - `.contains()`内に、期待値データを全て書く


- 指定したidのスキーリゾートが取得できること
    - 初期値と期待値の比較ymlが必要
    - id指定したテストなので、`.contains()`内に対象idのデータを書く


- レコードが存在しない場合に空のListが取得できること
    - 期待値データ（空のymlファイル）のみで良い


- 指定したidのスキーリゾートを削除すること
    - 比較する必要があるため、初期値と期待値のymlファイルが必要
    - Mapperのメソッド名の引数に削除対象のidを書く


- 更新時に指定したidが存在しないときテーブルのレコードが更新されないこと
    - 比較する必要があるため、初期値と期待値のymlファイルが必要（同じデータが良い）
    - Mapperのメソッド名の引数に存在しないidを書く


- 削除時に指定したidが存在しないときテーブルのレコードが削除されないこと
    - 比較する必要があるため、初期値と期待値のymlファイルが必要（同じデータが良い）
    - Mapperのメソッド名の引数に存在しないidを書く

## テスト結果表示方法

- テスト結果を確認したい

```
./gradlew test --tests "com.example.raiseTechcoursetask10.RaiseTechCourseTask10ApplicationTests" --info
```

- コマンド実行結果から探す

```agsl
Generating HTML test report...
Finished generating test html results (0.026 secs) into: /Users/yoko/git/raiseTech-course/raiseTech-course-task10/build/reports/tests/test
```

- htmlファイルが作成されているので、ディレクトリで`index.html`を探す

# Serviceテスト

- モック：偽物。本物のフリをする
- スタブ：代理。代わりのものを使う

## 考え方

- ServiceがMapperに依存している状態なので、そのまま書くとエラー

  -> 単体テストなので、Mapperに依存できない
- 依存しているクラスをモック化する

- スタブ化（モック化）したMapperの状態を確認する
- DBは無関係
- doReturn：値を返すメソッドをスタブ化（モック化）したときに返す値を定義するためのメソッド

モックオブジェクトが特定のメソッド呼び出しに対して、特定の値を返すように指定するために使用される

モックオブジェクトがgetSomeValue()メソッドが呼ばれたときに42を返すように設定する例

```doReturn(42).when(someMock).getSomeValue();```

### 準備

- `@ExtendWith(MockitoExtension.class)`

-> Mockitoを使用できるようにclassに付ける

- classに`@ExtendWith(MockitoExtension.class)` // JUnit5でMockitoを使うため
- `@Mock` // モック化（スタブ化）する対象に定義
- `@InjectMocks` // テスト対象に定義 @Mockでモックにしたインスタンスの注入先となるインスタンスに定義（インターフェースに付けるとエラー）

### 実装

- `doReturn`：Mapperの動作をスタブ化しているので、テスト期待値と等しくなる
- `Skiresort actual`:doReturn -whenで定義した値が入る
- `assertThat(actual)`:Serviceが返した実際の値を検証している
- アサーションのimport文に気を付ける

折りたたみ

```
<details><summary>Hello</summary><blockquote>
  <details><summary>World</summary><blockquote>
    :smile:
  </blockquote></details>
</blockquote></details>
```