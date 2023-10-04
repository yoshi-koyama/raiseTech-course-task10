## @PostMapping

- 新しいスキーリゾートを作成してそれに対応するURLを生成して返す
- `ResponseEntity<SkiresortResponse>`:SkiresortResponseという情報を持ったResponseEntityを返す
- `createSkiresort`:このメソッドが呼ばれる時、新しいスキーリゾートが作成される
- `@RequestBody SkiresortCreateForm skiresortCreateForm`:HTTPリクエストの中にあるSkiresortCreateForm(データ)を受け取る
- `HttpServletRequest request`:このメソッドが呼ばれた際のHTTPリクエストを持っている
- `@Valid`:バリデーションを行い、フォームデータが正しくない場合はエラーメッセージが生成される
