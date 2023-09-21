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
- 検査例外と非検査例外
    - 検査例外：プログラマが予想して対応できるエラー。Exceptionクラス配下のRuntime Exception以外のクラスが対象。
    - 例外（exception）のうち、メソッドの呼び出し側に`try-catch`文による例外処理の記述が要請されるもの
    - 非検査例外：プログラマが予測できないエラー
- RuntimeException:実行時の例外を拾うことができる

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

- テスト結果レポートを作成したい

```
./gradlew test --tests "com.example.raisetechcoursetask10.RaiseTechCourseTask10ApplicationTests" --info
```

- コマンド実行結果から探す

```agsl
Generating HTML test report...
Finished generating test html results (0.026 secs) into: /Users/yoko/git/raiseTech-course/raiseTech-course-task10/build/reports/tests/test
```

- htmlファイルが作成されているので、ディレクトリで`index.html`を探す

# Serviceテスト

### `Skiresort`:スキーリゾートの情報を保持するためのクラス

### `SkiresortServiceImpl`:スキーリゾート情報を操作するためのサービスクラス

### `SkiresortService`:Skiresort(Entity)を操作するインターフェース

- モック：偽物。本物のフリをする
- スタブ：代理。代わりのものを使う

## 考え方

- ServiceがMapperに依存している状態なので、そのまま書くとエラー

  -> 単体テストなので、Mapperに依存できない
- 依存しているクラスをモック化する

- スタブ化（モック化）したMapperの状態を確認する
- DBは無関係
- doReturn：値を返すメソッドをスタブ化（モック化）したときに返す値を定義するためのメソッド
- 検査例外と非検査例外：`throws Exception`をメソッドにつけないとコンパイルエラーになるのは、検査例外を`throw`するかもしれないメソッドを呼び出すときのみ

モックオブジェクトが特定のメソッド呼び出しに対して、特定の値を返すように指定するために使用される

モックオブジェクトがgetSomeValue()メソッドが呼ばれたときに42を返すように設定する例

```doReturn(42).when(someMock).getSomeValue();```

### 準備

- `@ExtendWith(MockitoExtension.class)`

-> Mockitoを使用できるようにclassに付ける

- classに`@ExtendWith(MockitoExtension.class)` // JUnit5でMockitoを使うため
- `@Mock` // モック化（スタブ化）する対象に定義
- `@InjectMocks` // テスト対象に定義 @Mockでモックにしたインスタンスの注入先となるインスタンスに定義（インターフェースに付けるとエラー）

### 実装概要

- `throws Exception`:テスト対象が検査例外をthrowするかどうかで必要不要を判断する。Mapperの返す値によって例外を起こしそうな場合は必要
- `doReturn`：Mapperの動作をスタブ化している仮のデータを定義している
- `Skiresort actual`:実際の値が入る
- `assertThat`:テスト対象の実行結果やオブジェクトの状態を期待する値や条件と比較する
- `assertThat(actual)`:`assertThat`の引数`(actual)`に実際の値を定義する。Serviceが返した実際の値をassertThat(検証/比較)している
- `assertThat(actual).isEqualTo(期待値となる値)`:期待値は`.isEqualTo`の引数に定義する
- アサーションのimport文に気を付ける

### エラー

- `staticでないメソッド deleteSkiresort(int)をstaticコンテキストから参照することはできません`:インスタンスメソッドをstaticメソッドの呼び出し方で呼び出していたのでエラー
  -> staticメソッド = クラスメソッド
  

### doReturnの書き方

- ` when(モックインスタンス.メソッド(引数)).thenReturn(戻り値)`;

- ` doReturn(戻り値).when(モックインスタンス).メソッド(引数);`

- 存在するidを指定した時、正常にデータが返されること
    - `doReturn -when`:スタブ化したid1のデータを定義する
    - `assertThat(actual).isEqualTo()`：.isEqualToの引数に、期待値データを定義する
    - `verify`：1回だけid1が呼び出されたかを確認する

- 全てのデータを取得する
    - リスト化する
    - `actual`:テストしたい実際の値をリスト型のactualに代入する

- skiresortMapperに存在しないIDを指定した時エラーメッセージが返されること
  - `throws Exception`:テストケース内で呼び出すメソッドが検査例外をthrowしうる場合必要
  - `assertThatThrownBy()`: 例外の検証ができる
  - `isInstanceof()`:対象のメソッドを実行した時にthrowされる例外が、何インスタンスか？を検証している
  - `ResourceNotFoundException`:指定したIDに該当するリソースがないことを通知する例外

- 指定したIDの情報を更新できること

1. `skiresortMapper`の`findById`メソッドを使って更新前のデータを取得する ->`doReturn -when`:whenに更新前データを定義する
2. `skiresortServiceImpl`オブジェクトの`updateSkiresort`メソッドを呼び出す。このメソッドは、指定したIDのスキーリゾート情報を更新する->`Lake Louise`
3. `verify`:skiresortMapperオブジェクトのID1が1回呼ばれたことの検証。
4. 新しい`Skiresort`インスタンスを作成し、変数`updateSkiresort`に更新後データ`Lake Louise`を設定する。-> Skiresortのインスタンス化updateSkiresortを定義しないとエラーになる
5. `updateSkiresort`: 戻り値がvoidなのでassertThatできない -> 代替としてverifyを使って検証する
   - `verify`:skiresortMapperオブジェクトのupdateSkiresortメソッドが1回呼ばれたことの検証
   - verifyの検証時に`updateSkiresort`を渡す-> `MockitoはskiresortMapper.updateSkiresort`に更新後の`Lake Louise`
     の情報が渡されたのだよねという検証までしてくれる

- updateSkiresortに存在しないIdを指定したらエラーメッセージが返されること
  - `assertThatThrownBy`:例外の検証ができる。`ResourceNotFoundException`をthrowされることを期待している
  - `isInstanceOf`:throwされた例外が`ResourceNotFoundException`のインスタンスであることを検証する
  
- 指定したIDのスキー場情報を削除する
  - `doReturn`:対象のIDのスキー場情報をモック化して`when`でskiresortMapperで対象IDを検索する
  - `void`：deleteSkiresortはvoidのため、assertThatは使えない
  - staticでないメソッド deleteSkiresort(int)をstaticコンテキストから参照することはできませんエラーが表示->インスタンスメソッドをstaticメソッドの呼び出し方で呼び出していたのでエラー->staticメソッド = クラスメソッド


【折りたたみ】

```
<details><summary>Hello</summary><blockquote>
  <details><summary>World</summary><blockquote>
    :smile:
  </blockquote></details>
</blockquote></details>
```
