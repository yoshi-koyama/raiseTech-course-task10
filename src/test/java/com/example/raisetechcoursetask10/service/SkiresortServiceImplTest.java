package com.example.raisetechcoursetask10.service;

import com.example.raisetechcoursetask10.controller.form.SkiresortCreateForm;
import com.example.raisetechcoursetask10.entity.Skiresort;
import com.example.raisetechcoursetask10.exception.ResourceNotFoundException;
import com.example.raisetechcoursetask10.mapper.SkiresortMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // JUnit5でMockitoを使うために必要
class SkiresortServiceImplTest {

    @InjectMocks // テスト対象
    SkiresortServiceImpl skiresortServiceImpl;

    @Mock // モック化（スタブ化）したいインスタンスに定義
    SkiresortMapper skiresortMapper;

    @Nested // JUnit5におけるネストしたテスト
    class FindByIdTest { // テスト対象メソッド名でクラスを作成
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
        // skiresortMapperのfindByIdメソッドに対するテスト
        public void 存在しないIDを指定した場合findByIdメソッドはエラーメッセージを返すこと() {
            // モック化 ID100を指定したとき空かどうか
            doReturn(Optional.empty()).when(skiresortMapper).findById(100);

            // test実行　memberServiceImpl.findByIdメソッドにID100を渡した時、例外をスローすることを期待している
            assertThatThrownBy(() -> skiresortServiceImpl.findById(100)) // テスト対象メソッド
                    // throwされる例外がResourceNotFoundException（リソースがないことを通知する）を返す
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("resource not found");
            verify(skiresortMapper, times(1)).findById(100);
        }
    }

    @Nested
    class FindAllTest {
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
    }

    @Nested
    class UpdateSkiresortTest {
        @Test
        public void 指定したIDのスキー場情報を更新できること() {
            // モック化　returnするSkiresortは更新前のデータを設定
            doReturn(Optional.of(new Skiresort(1, "Whistler", "Canada", "11kmのロングランが楽しめる。次回は天気の良いハイシーズンに行きたい"))).when(skiresortMapper).findById(1);
            // updateSkiresortメソッドを呼び出して、ID1が持つ情報をLake Louiseに更新する
            skiresortServiceImpl.updateSkiresort(1, "Lake Louise", "Canada", "バンフから近くて無料シャトルバスがある。広大で美しいゲレンデ");

            // skiresortMapperオブジェクトのID1が1回呼ばれたことの検証（updateSkiresortは戻り値がvoidなのでassertThatでの検証ができない）
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
        public void 存在しないIDを指定した場合updateSkiresortメソッドはエラーメッセージを返すこと() {
            // skiresortMapperのfindByIdメソッドが100を指定したときモック化されたメソッドが存在しないため空のOptionalを返す->IDが100のスキーリゾートが存在しない状態
            doReturn(Optional.empty()).when(skiresortMapper).findById(100);

            // updateSkiresortメソッドを実行した時にthrowされる例外がResourceNotFoundExceptionクラスのインスタンスであることを期待している
            assertThatThrownBy(() -> skiresortServiceImpl.updateSkiresort(100, "Coronet Peak", "NZ", "海外遠征で初めて滑ったスキー場。すごく広くてクイーンズタウンからも近い")) // テスト対象メソッド)
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("resource not found");
            verify(skiresortMapper, times(1)).findById(100);
        }
    }

    @Nested
    class DeletedSkiresortTest {
        @Test
        public void 指定したIDのスキー場情報を削除できること() {
            doReturn(Optional.of(new Skiresort(1, "白馬乗鞍", "長野県", "初めてペンションに居候として山籠りし、初めて草大会に出場した思い出のゲレンデ"))).when(skiresortMapper).findById(1);

            // staticメソッド（クラスメソッド）の呼び出し方はNG
            // deleteSkiresortメソッドを呼び出す。引数は削除するIDのスキー場情報が渡される
            skiresortServiceImpl.deleteSkiresort(1);

            // deleteSkiresortはvoidなのでassertThat使用不可->verifyで検証する
            verify(skiresortMapper, times(1)).findById(1);
            verify(skiresortMapper, times(1)).deleteSkiresort(1);
        }
    }

    @Nested
    class InsertSkiresortTest {
        @Test
        public void 新規のスキー場情報を登録できること() {
            // skiresortCreateForm変数をインスタンス化して、それぞれの属性に値を設定
            SkiresortCreateForm skiresortCreateForm = new SkiresortCreateForm("CoronetPeak", "NZ", "初中級者の時に行ったので初めてのTバーに撃沈。岩だらけの広い氷山には木が１本もなくて、ボードと心が折れる人続出。上手くなってから行くべきゲレンデ");
            Skiresort skiresort = new Skiresort(0, "CoronetPeak", "NZ", "初中級者の時に行ったので初めてのTバーに撃沈。岩だらけの広い氷山には木が１本もなくて、ボードと心が折れる人続出。上手くなってから行くべきゲレンデ");

            // スタブ化 insertSkiresortの戻り値はvoidのためdoNothingを使用する
            doNothing().when(skiresortMapper).insertSkiresort(skiresort);

            // テスト実行 actualにはSkiresortのオブジェクト（skiresort）を代入する
            Skiresort actual = skiresortServiceImpl.createSkiresort(skiresortCreateForm);
            // skiresortServiceImple.createSkiresortの戻り値であるSkiresortオブジェクトの値が期待通りであるかを検証する
            assertThat(actual).isEqualTo(skiresort);
            // verifyでskiresortMapper.insertSkiresortが1回呼ばれて引数にSkiresortが渡されていることを検証する
            verify(skiresortMapper, times(1)).insertSkiresort(skiresort);
        }
    }
}
