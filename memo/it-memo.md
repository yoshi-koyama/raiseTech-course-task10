- テストケース名：レスポンスのステータス、JSONを意識して決める
- `@Nested`でのクラス名:APIごとに分ける
    - Read
        - ReadAllTest
        - ReadByIdTest

テストケースを考える時振る舞い駆動`Given/When/Then`を意識して文章を組み立てる
振る舞いとはAPIがどんなレスポンスを返すか

(`Given`(前提条件):テストケースに入る前のシステムの状態は -> なくてもよい)
`When`:どのような操作や入力があるのか
`Then`:その操作や入力のあとに期待すべき結果は何か

`MockMvc`:リクエストの検証
`AutoConfigureMockMvc`:自動でMockMvcを行う
`.content()`:リクエストボディを設定する

テキストブロック:`"""`ダブルウォーと3つでラップした文字列をひとつの塊として扱う(Java13以降)

### すべてのスキーリゾートを全件取得したときステータスコードが200を返すこと

- `@DataSet(value = "datasets/it-skiresort.yml")`:テスト初期データ。データセットなのでSkiresortで定義しているデータを設定すること
- `JSONAssert`:初期データとのJSONデータの比較。期待値を定義するが、HTTPレスポンスなのでSkiresortResponseで定義しているデータのみを設定すること

### 存在しないIDのスキーリゾートを取得した時ステータスコードが404エラーを返すこと

- `NOT_FOUND`(404):サーバがリクエストされたリソースを見つけられない。存在しないエンドポイント、リソースをリクエストした場合
- リクエストを行いHTTPステータスコードのみ検証する：レスポンスの内容を検証する必要がないので、テスト初期値のみの設定で良い
- `/skiresorts/{id},99`:エンドポイントid99(存在しないID99)でスキーリゾートをリクエストしていている->`isNotFound()`400が適している

### 新しいスキーリゾートを登録する

- `@DataSet`: createの場合idは自動採番されるため、指定するとkey重複を起こす場合がある
- `@ExpectedDataSet` :テスト期待値
- `ignoreCols = "id"`: idの列を比較対象から除外する
- `isCreated()`: 登録は201

- SkiresortControllerのcreateSkiresortメソッドに設定しているreturn部分を確認して、レスポンスする内容を設定する

リクエストボディを書く

- `JSONAssert.assertEquals`: JSON形式で新規登録する情報を全て記述する(SkiresortResponseのフィールドに合わせること)

### IDを指定してスキーリゾート情報を更新する

- `isOK()`: リクエスト成功
- SkiresortUpdateFormのフィールドの値を設定する

リクエストボディ

- `JSONAssert.assertEquals`: SkiresortControllerのReturnに定義されているレスポンス内容をJSON形式で書く

### 存在しないIDを指定してスキーリゾートを更新すると期待通りのエラーが返ってくる

- ISO 8601形式:日付と時刻を「T」で区切る
- 表記法:yyyy-MM-ddThh:mm:ss:SSSSSSSSSXXX
- yyyy:年（4文字）
- MM:月（2文字）
- dd:日（2文字）
- T:T文字（日付と時刻を区別するための文字）
- HH:時（2文字）
- mm:分（2文字）
- ss:秒（2文字）
- SSSSSSSSS:ナノ秒（9文字）
- XXX:タイムゾーンオフセット
- 時差:日本はUTC(協定世界時)から+9
- JST:日本標準時

リアルタイムでテスト不可の理由
->timestampの比較:テスト実行のタイミングや実行速度がリアルタイムでは困難なため、厳密な一致を求めることが難しい

- LocalDateTime nowDate = LocalDateTime.now(); 特定のタイムゾーンに依存しない場合の現在自刻の取得
- ZoneDataTime now = ZonedDateTime now(); プログラムを実行した場所（国）での現在自刻の取得

- `"/skiresorts/{id}", 100`:存在しないIDのパスを指定
- assert.Equalsの内容
    - "path":期待する値のパス->/skiresorts/100
    - "status":期待するステータスコード
    - "message":ステータスコードの対応したエラーメッセージ
    - "timestamp":今回は適当。ISO 8601形式にしたがって記載
    - "error":ステータスコードに対応したエラー
- `JSONCompareMode.STRICT`:JSON比較モードで、全てのフィールドが一致していること
- `((o1, o2) -> true)`:object1(期待値),object2(実際の値)は常にtrueを返す->timestampの値は比較除外される

### IDを指定してスキーリゾートを削除する

- @dataset:初期値設定
- @ExpectedDataSet:期待値の全てのデータ設定

### よく出るエラー

- Could not create dataset for test:テスト実行中datasetを作成できなかったor正しく読み込めなかった->パスが合ってるか？
