## @PostMapping

- 新しいスキーリゾートを作成してそれに対応するURLを生成して返す
- `ResponseEntity<SkiresortResponse>`:SkiresortResponseという情報を持ったResponseEntityを返す
- `createSkiresort`:このメソッドが呼ばれる時、新しいスキーリゾートが作成される
- `@RequestBody SkiresortCreateForm skiresortCreateForm`:HTTPリクエストの中にあるSkiresortCreateForm(データ)を受け取る
- `HttpServletRequest request`:このメソッドが呼ばれた際のHTTPリクエストを持っている
- `@Valid`:バリデーションを行い、フォームデータが正しくない場合はエラーメッセージが生成される
- `Skiresort`のフィールドに合わせてJSONデータを定義しないとエラーになる

curlコマンド

```
{
  "id": 1,
  "name": "name",
  "area": "Skiresort Area",
  "customerEvaluation": "Skiresort Customer Evaluation"
}
```

`Skiresort`:id、name、area、customerEvaluation フィールドが4つある
`SkiresortResponse`:name、area フィールドにあるレスポンスを返す
