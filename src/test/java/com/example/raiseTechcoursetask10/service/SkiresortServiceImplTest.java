package com.example.raiseTechcoursetask10.service;

import com.example.raisetechcoursetask10.entity.Skiresort;
import com.example.raisetechcoursetask10.mapper.SkiresortMapper;
import com.example.raisetechcoursetask10.service.SkiresortServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void 存在するスキー場のIDを指定したときに正常にデータが返されること() throws Exception {
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
                new Skiresort(2, "Whistler", "canada", "滞在2週間の半分以上雨で、記録的な少雪な年だった"),
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
