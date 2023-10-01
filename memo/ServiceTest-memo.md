# Serviceテスト

### `Skiresort`:スキーリゾートの情報を保持するためのクラス

### `SkiresortServiceImpl`:スキーリゾート情報を操作するためのサービスクラス

### `SkiresortService`:Skiresort(Entity)を操作するインターフェース

- モック：偽物。本物のフリをする。依存しているオブジェクトのメソッドの呼び出し回数の検証など、依存オブジェクトが正しく利用されているかの検証をするのが主な目的。
- スタブ：代理。代わりのものを使う。依存するオブジェクトの代用となるオブジェクトのこと。 任意の戻り値を設定して、予測可能な動作をするようにして使う。

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

### doNotingの書き方

```
// モックインスタンスが呼ばれた時、何も返さない
doNothing().when(モックインスタンス).メソッド(任意の引数);
```

### doReturn or doNothing(deleteとinsert：どちらもvoidを返す)

- スタブ化するものに戻り値がある->doReturn
- `delete`:`Mapper`の`findById`をスタブ化している（findByIdには戻り値がある）-> IDを使ってSkiresortを探している
- `insert`:`Mapper`の`findById`は呼ばれていないので、findByIdをスタブ化する必要はない

---

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
4. 新しい`Skiresort`インスタンスを作成し、変数`updateSkiresort`に更新後データ`Lake Louise`を設定する。->
   Skiresortのインスタンス化updateSkiresortを定義しないとエラーになる
5. `updateSkiresort`: 戻り値がvoidなのでassertThatできない -> 代替としてverifyを使って検証する
    - `verify`:skiresortMapperオブジェクトのupdateSkiresortメソッドが1回呼ばれたことの検証
    - verifyの検証時に`updateSkiresort`を渡す-> `MockitoはskiresortMapper.updateSkiresort`に更新後の`Lake Louise`
      の情報が渡されたのだよねという検証までしてくれる

- updateSkiresortに存在しないIDを指定したらエラーメッセージが返されること
    - `assertThatThrownBy`:例外の検証ができる。`ResourceNotFoundException`をthrowされることを期待している
    - `isInstanceOf`:throwされた例外が`ResourceNotFoundException`のインスタンスであることを検証する

- 指定したIDのスキー場情報を削除する
    - `doReturn`:対象のIDのスキー場情報をモック化して`when`でskiresortMapperで対象IDを検索する
    - `void`：deleteSkiresortはvoidのため、assertThatは使えない
    - staticでないメソッド deleteSkiresort(int)をstaticコンテキストから参照することはできませんエラーが表示->
      インスタンスメソッドをstaticメソッドの呼び出し方で呼び出していたのでエラー->staticメソッド = クラスメソッド

- 新規スキー場情報を登録できること
- ### insertSkiresortは戻り値がvoidなので、returnするべきものがない
  ### ->`doNothing`を使う

```
// モックインスタンスが呼ばれた時、何も返さない
doNothing().when(モックインスタンス).メソッド(任意の引数);
```

- ①`Skiresort`をインスタンス化。`createSkiresort`メソッドに渡すための実引数を定義する
- ①`SkiresortCreateForm`をインスタンス化してぞれぞれの属性と値を設定する
- ②`doNothing`で`skiresortMapper.insertSkiresort(skiresort);`をスタブ化する->実際には何も返さない
- ③`skiresortServiceImpl.createSkiresort`の戻り値を、新しく作成したskiresortをactualに代入する。新規登録のテスト実行
- ④`skiresortServiceImpl.createSkiresort`の戻り値である`Skiresort`の値が期待通りであるか->actualとinsertSkiresortが等しいか`assertThat`
  で検証する
- ⑤`verify`で`skiresortMapper.insertSkiresort`が1回呼ばれて引数に`Skiresort`が渡されていることを検証する

`skiresortCreateForm`：リクエストボディのため属性に`ID`が含まれない。`name`,`area`,`customerEvaluation`
`actual`:`skiresortServiceImpl.createSkiresort(skiresortCreateForm)`の呼び出し結果が代入される==Skiresortのオブジェクト(==skiresort)
`assertThat`:Skiresortのオブジェクトとactualが等しいかを検証->つまり新規作成されたスキー場情報が`Skiresort`オブジェクトであるか？を検証している

`Entity`クラス：DBのテーブルに対応するためIDが必要
`SkiresortCreateForm`クラス：FormはリクエストボディのためIDを持たない（FormはDBと対応していないため）

debug確認

- skiresortServiceImpl.createSkiresortが返すactualの値
  ->actual = {Skiresort@4285}
  id = 0
  name = "CoronetPeak"
  area = "NZ"
  customerEvaluation = "初中級者の時に行ったので初めてのTバーに撃沈。岩だらけの広い氷山には木が１本もなくて、ボードと心が折れる人続出。上手くなってから行くべきゲレンデ"
- asertThatで比較しているSkiresortオブジェクト
  ->skiresort = {Skiresort@4191}
  id = 0
  name = "CoronetPeak"
  area = "NZ"
  customerEvaluation = "初中級者の時に行ったので初めてのTバーに撃沈。岩だらけの広い氷山には木が１本もなくて、ボードと心が折れる人続出。上手くなってから行くべきゲレンデ"


- テストケースの命名について
  updateSkiresortとskiresortMapperメソッドに対するテスト
- テスト対象のメソッドが異なる->Whenが異なるが、存在しないIDを指定した時エラーメッセージが返されるかのテストをしている
- `Given`,`When`,`Then`を意識して考える

- `Given`(入力): データの生成やモックの設定のようなテストの準備
- `When`(実行): テスト対象のメソッドや動作の呼び出し
- `Then`(出力): 出力や振る舞いが正しいかどうか検証するためのアサーションの実行


- ![C8BAE4ED-127C-49D6-8A4D-A287DC7F0028_1_201_a.jpeg](..%2F..%2F..%2FPictures%2F%E5%86%99%E7%9C%9F%E3%83%A9%E3%82%A4%E3%83%96%E3%83%A9%E3%83%AA.photoslibrary%2Fresources%2Frenders%2FC%2FC8BAE4ED-127C-49D6-8A4D-A287DC7F0028_1_201_a.jpeg)
