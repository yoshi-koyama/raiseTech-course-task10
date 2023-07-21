# 第1段階
### バリデーションエラー
- Javaのカスタム例外であり、第1段階で回避しなければならない
- @NotNull、@NotBlank、@Size、@Min、@Emailなど
- @RestControllerAdvice を使用してグローバルなエラーハンドラを実装する
  - @RestControllerAdviceを使用してアプリケーション全体で発生するエラーをキャッチして処理するための、グローバルなエラーハンドリングを行うことが適切である
  - `CustomExceptionHandler`は、@RestControllerAdviceを使用して実装した例外ハンドラである
- MethodArgumentNotValidException をキャッチしてバリデーションエラーを処理する
 
  - SpringBootでは`MethodArgumentNotValidExceptiton`がスローされた際に実行する
  - コントローラメソッド（Controllerクラスの@Validが付いているメソッドの引数）が不正な場合に発生する
  - ->バリデーション情報を取得し、エラーレスポンスを返す
  - BindingResultを使用しない場合、`MethodArgumentNotValidException`メソッドを使用する

- 気をつけること
- @Valid
  - Controllerの引数で使用
  - @RequestBodyと共に引数の直前に記述し、引数のフィールドに対して、リクエストボディのバリデーションを行う
  - 主にJava標準のバリデーション（javax.validationパッケージ）を実行するために使用
  
- @Validated
  - ServiceやControllerクラスのメソッド内の引数）: Spring特有のバリデーションを実行するために使用
  - Java Bean Validation場合に使用する


# 第2段階
### 特定の例外
- `ResourceNotFoundException`などに対してカスタムなエラーハンドリングを行う
- @ExceptionHandlerを使用して、個別の例外クラスに対するエラーハンドリングを実装する
  
   - リソースが見つからないなど

- 例外がスローするまで、例外ハンドリングが行われない


