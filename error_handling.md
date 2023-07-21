# バリデーションエラー

- Controllerのレイヤーで実装する
- @NotNull、@NotBlank、@Size、@Min、@Emailなど
- HTTPステータスコード400（Bad Request）を返す
- @RestControllerAdvice を使用してグローバルなエラーハンドラを実装する
    - @RestControllerAdviceを使用してアプリケーション全体で発生するエラーをキャッチして処理するための、グローバルなエラーハンドリングを行うことが適切である
    - `CustomExceptionHandler`は、@RestControllerAdviceを使用して実装した例外ハンドラである
- MethodArgumentNotValidException をキャッチしてバリデーションエラーを処理する

    - SpringBootでは`MethodArgumentNotValidExceptiton`がスローされた際に実行する
    - コントローラメソッド（Controllerクラスの@Validが付いているメソッドの引数）が不正な場合に発生する
    - --> バリデーション情報を取得し、エラーレスポンスを返す
    - BindingResultを使用しない場合、`MethodArgumentNotValidException`メソッドを使用する

- 気をつけること
- @Valid
    - Controllerの引数で使用
    - @RequestBodyと共に引数の直前に記述し、引数のフィールドに対して、リクエストボディのバリデーションを行う
    - 主にJava標準のバリデーション（jakarta.validationパッケージ）を実行するために使用

- @Validated
    - ServiceやControllerクラスのメソッド内の引数）。Spring特有のバリデーションを実行するために使用
    - Java Bean Validationの場合に使用する
    - HTTPステータスコード404（Not Found）を返す

# 特定の例外

- Javaのカスタム例外であり、特定の条件やエラーが発生した際に定義する
- `ResourceNotFoundException`などに対してカスタムなエラーハンドリングを行う
- @ExceptionHandlerを使用して、個別の例外クラスに対するエラーハンドリングを実装する

    - Controllerクラスのメソッド内でリソースが見つからないなど
    - --> カスタムな例外をスローする（ResourceNotFoundExceptionなど）
    - --> 例外をキャッチし、適切なレスポンスを生成する
    - --> HTTPステータスコード404（Not Found）を返す

- 例外がスローするまで、例外ハンドリングが行われない


