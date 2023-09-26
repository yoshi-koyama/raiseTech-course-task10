## テスト結果表示方法

- テスト結果レポートを作成したい

```
./gradlew test --tests "com.example.raisetechcoursetask10.RaiseTechCourseTask10ApplicationTests" --info
```

## テストのグループ化

- 共通のデータで分ける
- 共通の状態で分ける
- コンストラクタのテストを分ける

### Enclosed

- Enclosed:JUnit4で使用され、テストの構造を整理するために使われる。テストクラスをグループ化する。
- `@RunWith(Enclosed.class)`:クラスにアノテーションをつける

### NestedRunner

- NestedRunner:JUnit5で使用され、ネストしたテストをサポートする
- `@Nested`:クラスにアノテーションをつける

### テストケースの命名について

単体テストの場合、メソッド名をテスト名に含めた方が良い

- `Given`,`When`,`Then`を意識して考える
- `Given`(入力): データの生成やモックの設定のようなテストの準備
- `When`(実行): テスト対象のメソッドや動作の呼び出し
- `Then`(出力): 出力や振る舞いが正しいかどうか検証するためのアサーションの実行

## 折りたたみ

```
<details><summary>Hello</summary><blockquote>
  <details><summary>World</summary><blockquote>
    :smile:
  </blockquote></details>
</blockquote></details>
```
