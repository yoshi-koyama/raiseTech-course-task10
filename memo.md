<details><summary>DBテスト</summary><div>

<details><summary>@DBRiderの場合</summary><div>


@DataSet：更新前のテーブル[引数にvalue:パスを書く]

@ExpectedDataSet：期待値データ[引数にvalue:パスを書く]

updateSkiresortはSkiresortクラスを引数に取るわけですから、呼び出し方を変えないといけないですね。

```
Skiresort skiresort = new Skiresort(1, "田沢湖", "秋田県", "大会バーンの垂直に見える急斜面が面白かった。");
skiresortMapper.updateSkiresort(skiresort);
```