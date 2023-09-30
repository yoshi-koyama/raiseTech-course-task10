# 単体テスト

# DBテスト

## @DBRiderの場合

- `@DataSet`：更新前のテーブル[引数にvalue:パスを書く]
- `@ExpectedDataSet`：期待値データ[引数にvalue:パスを書く]
- `@Transactional`:例外が起こった時に自動でロールバックしてくれる

updateSkiresortはSkiresortクラスを引数に取るわけですから、呼び出し方を変えないといけないですね。

```
Skiresort skiresort = new Skiresort(1, "田沢湖", "秋田県", "大会バーンの垂直に見える急斜面が面白かった。");
skiresortMapper.updateSkiresort(skiresort);
```

ID1->更新されること
ID2->更新されないこと

【@DataSetで定義する事前データ】

```
ID=1, area="北海道"
ID=2, area="新潟"
```

【更新対象と更新内容】

```
ID=1, area="山形"
```

【@ExpectedDataSetで定義する期待値データ】

```
ID=1, area="山形"
ID=2, area="新潟"
```

## 考え方

- テストコードに書くメソッドはMapper.javaに書いてあること必須！！
- 検査例外と非検査例外
    - 検査例外：プログラマが予想して対応できるエラー。Exceptionクラス配下のRuntime Exception以外のクラスが対象。
    - 例外（exception）のうち、メソッドの呼び出し側に`try-catch`文による例外処理の記述が要請されるもの
    - 非検査例外：プログラマが予測できないエラー
- RuntimeException:コンパイル時ではなく、プログラム実行時に予期せぬ自体が起こった場合に発生する例外

- 全てのスキーリゾートが取得できること
    - 比較データ（ymlファイル）は1つで良い
    - `hasSize()`内にデータのサイズを書く
    - `contains()`内に、期待値データを全て書く


- 指定したIDのスキーリゾートが取得できること
    - 初期値と期待値の比較ymlが必要
    - ID指定したテストなので、`contains()`内に対象IDのデータを書く


- レコードが存在しない場合に空のListが取得できること
    - 期待値データ（空のymlファイル）のみで良い


- 指定したIDのスキーリゾートを削除すること
    - 比較する必要があるため、初期値と期待値のymlファイルが必要
    - Mapperのメソッド名の引数に削除対象のIDを書く


- 更新時に指定したIDが存在しないときテーブルのレコードが更新されないこと
    - 比較する必要があるため、初期値と期待値のymlファイルが必要（同じデータが良い）
    - Mapperのメソッド名の引数に存在しないIDを書く


- 削除時に指定したIDが存在しないときテーブルのレコードが削除されないこと
    - 比較する必要があるため、初期値と期待値のymlファイルが必要（同じデータが良い）
    - Mapperのメソッド名の引数に存在しないIDを書く

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
