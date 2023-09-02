<details><summary>DBテスト</summary><div>

<details><summary>@DBRiderの場合</summary><div>


@DataSet：更新前のテーブル[引数にvalue:パスを書く]

@ExpectedDataSet：期待値データ[引数にvalue:パスを書く]

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

<details><summary>考え方</summary><div>

- テストコードに書くメソッドはMapper.jsに書いてあること必須！！
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

<details><summary>テスト結果表示方法</summary><div>


テスト結果を確認したい

`./gradlew test --tests "com.example.raiseTechcoursetask10.RaiseTechCourseTask10ApplicationTests" --info`