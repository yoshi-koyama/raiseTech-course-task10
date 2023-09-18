package com.example.raisetechcoursetask10.service;

import com.example.raisetechcoursetask10.entity.Skiresort;
import com.example.raisetechcoursetask10.exception.ResourceNotFoundException;
import com.example.raisetechcoursetask10.mapper.SkiresortMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // JUnit5でMockitoを使うために必要
class SkiresortServiceImplTest {

    @InjectMocks // テスト対象
    SkiresortServiceImpl skiresortServiceImpl;

    @Mock // モック化（スタブ化）したいインスタンスに定義
    SkiresortMapper skiresortMapper;

    @Test
    public void 存在するスキー場のIDを指定したときに正常にデータが返されること() {
        // doReturn -when :Mokietoの記述
        doReturn(Optional.of(new Skiresort(1, "たかつえ", "福島県", "いつも空いてて1枚バーンが気持ちいい"))).when(skiresortMapper).findById(1);

        // testの実行
        Skiresort actual = skiresortServiceImpl.findById(1);
        assertThat(actual).isEqualTo(new Skiresort(1, "たかつえ", "福島県", "いつも空いてて1枚バーンが気持ちいい"));
        verify(skiresortMapper, times(1)).findById(1);
    }

    @Test
    public void 全てのスキー場情報を取得できること() {
        List<Skiresort> skiresorts = List.of(
                new Skiresort(1, "Cadrona", "NZ", "パイプのnationals公式大会で優勝して、副賞モルディブ1週間旅行だった！ゲレンデはコンクリートみたいに硬い"),
                new Skiresort(2, "Whistler", "Canada", "滞在2週間の半分以上雨で、記録的な少雪な年だった"),
                new Skiresort(3, "Mt.Hood", "Oregon", "標高が高すぎて高山病になった。ガスってる日に2000m以上続く急斜面で滑落した"));

        // 依存しているSkiresortMapperをモック化する
        // skiresortMapperのfindAll()メソッドが呼ばれた時に、skiresortsリストを返す
        doReturn(skiresorts).when(skiresortMapper).findAll();

        // test実行
        List<Skiresort> actual = skiresortServiceImpl.findAll();
        // actual(実際)の値がモック化した値(skiresorts)と等しいか検証する
        assertThat(actual).isEqualTo(skiresorts);
        verify(skiresortMapper, times(1)).findAll();
    }

    @Test
    // skiresortMapperメソッドに対するテスト
    public void 存在しないIDを指定した時エラーメッセージが返されること() {
        // モック化　id100を指定したとき空かどうか
        doReturn(Optional.empty()).when(skiresortMapper).findById(100);

        // test実行　memberServiceImpl.findByIdメソッドにid100を渡した時、例外をスローすることを期待している
        assertThatThrownBy(() -> skiresortServiceImpl.findById(100)) // テスト対象メソッド
                // throwされる例外がResourceNotFoundException（リソースがないことを通知する）を返す
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("resource not found");
        verify(skiresortMapper, times(1)).findById(100);
    }

    @Test
    public void 指定したidのスキー場情報を更新できること() {
        // モック化　returnするSkiresortは更新前のデータを設定
        doReturn(Optional.of(new Skiresort(1, "Whistler", "Canada", "11kmのロングランが楽しめる。次回は天気の良いハイシーズンに行きたい"))).when(skiresortMapper).findById(1);
        // updateSkiresortメソッドを呼び出して、id1が持つ情報をLake Louiseに更新する
        skiresortServiceImpl.updateSkiresort(1, "Lake Louise", "Canada", "バンフから近くて無料シャトルバスがある。広大で美しいゲレンデ");

        // skiresortMapperオブジェクトのID1が1回呼ばれたことの検証
        verify(skiresortMapper, times(1)).findById(1);

        // Skiresortのインスタンス定義
        // これが更新後のデータの期待値 Lake Louise->Skiresortのインスタンス化updateSkiresortを定義しないとエラー
        Skiresort updateSkiresort = new Skiresort(1, "Lake Louise", "Canada", "バンフから近くて無料シャトルバスがある。広大で美しいゲレンデ");
        // skiresortMapperオブジェクトのupdateSkiresortByIdメソッドが1回呼ばれたことの検証
        // skiresortMapperのupdateSkiresortメソッドの引数updateSkiresort変数が渡されて、更新後データであるLake Louiseであることを検証する
        verify(skiresortMapper, times(1)).updateSkiresort(updateSkiresort);
    }

    @Test
    // updateSkiresortメソッドに対するテスト
    public void 指定したIDが存在しない時にエラーメッセージが返されること() {

        doReturn(Optional.empty()).when(skiresortMapper).findById(100);

        assertThatThrownBy(() -> skiresortServiceImpl.updateSkiresort(100, "Coronet Peak", "NZ", "海外遠征で初めて滑ったスキー場。すごく広くてクイーンズタウンからも近い")) // テスト対象メソッド)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("resource not found");
        verify(skiresortMapper, times(1)).findById(100);
    }
}
