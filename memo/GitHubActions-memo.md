# GitHub Actionとは

ワークフローを自動化する
自動でビルド->テスト->デプロイまで管理できる様になる仕組み

- CI（継続的インテグレーション）：ビルド＆テスト
- CD（継続的デリバリー）：デプロイ

## ワークフロー構文

- `name:`(省略可能)ワークフロー名。GitリポジトリのActionタブに表示される
- `on:`アクションのトリガー
    - `push`や`pull_request`でトリガーのタイミングを指定可能
    - `pull_request`:デフォルトで`types: [ opened, synchronize, reopened ] 3つのイベントが実行される By default
        - `opened`: Pull Requestがopenされたときにワークフローが動き出す
        - `synchronize`: Pull Requestに対して何らかの変更(=push)があったときにワークフローが動き出す
        - `reopen`:Pull Requestが再度openされたにワークフローが動く
- `branches`ブランチを指定可能
- `permissions:`アクション実行時のリポジトリコンテンツに対する権限
- `jabs:`ワークフローファイルで実行されるすべてのジョブをグループ化している
    - `build:`ジョブの名前。実行環境と手順を定義
    - `runs-on:`ジョブを実行するマシンの種類を設定(`ubuntu-latest`:最新のUbuntu環境)
    - `steps:`具体的なタスク(手順)
    - `uses:`ジョブで指定するリポジトリ
- `run:`具体的なシェルで | を使えばパイプライン処理も可能
- `run-name:`pushやpull_requestイベントによってトリガーされるワークフローの場合、コミットメッセージとして設定される

## タスク順序

- Hello worldするだけのワークフローを作成
- Pull Request をトリガーにして動くようワークフローを修正
- Gradle で test する方法を調べる
- GitHub Actions で Gradle で test する方法を調べてワークフローに修正
- GitHub Actionsでファイルをzipに固めてアップロードする方法を検索
- /gradlew test の実行結果のテストレポートをUploadするようにワークフローを修正

## gradleでtestする方法

- GradleでJavaのプロジェクトのビルドとテストを行う
- docker compose upする手順をワークフロー内に組み入れること

## 動作確認

- `./gradlew test`:Gradleを使ってプロジェクトをビルドし、ユニットテストを実行する
- `./gradlew clean test`:プロジェクトをクリーンにしてからテストする

##  

- `uses: actions/upload-artifact@v2`:`upload-artifact`を使用してアップロードを行う
- アップロード先：GitHubのストレージ

## artifact確認方法

- `Actions`一覧より左のサイドバーから該当のワークフローを選択->`workflow runs`から実行の名前を選択
  ![img.png](img.png)


- `Artifacts`：成果物が生成されている
  ![B396176C-EC74-484B-B95E-CF074DFB2E2C_1_105_c.jpeg](..%2F..%2F..%2F..%2FPictures%2F%E5%86%99%E7%9C%9F%E3%83%A9%E3%82%A4%E3%83%96%E3%83%A9%E3%83%AA.photoslibrary%2Fresources%2Fderivatives%2FB%2FB396176C-EC74-484B-B95E-CF074DFB2E2C_1_105_c.jpeg)


- zipを確認
  ![AF853ED8-C6B9-4F81-A556-0F89210E2B66_1_201_a.jpeg](..%2F..%2F..%2F..%2FPictures%2F%E5%86%99%E7%9C%9F%E3%83%A9%E3%82%A4%E3%83%96%E3%83%A9%E3%83%AA.photoslibrary%2Fresources%2Frenders%2FA%2FAF853ED8-C6B9-4F81-A556-0F89210E2B66_1_201_a.jpeg)
