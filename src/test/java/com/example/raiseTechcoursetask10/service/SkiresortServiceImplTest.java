package com.example.raiseTechcoursetask10.service;

import com.example.raisetechcoursetask10.entity.Skiresort;
import com.example.raisetechcoursetask10.mapper.SkiresortMapper;
import com.example.raisetechcoursetask10.service.SkiresortServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void 存在するユーザのIDを指定したときに正常にデータが返されること() throws Exception {
        // doReturn -when :Mokietoの記述
        doReturn(Optional.of(new Skiresort(1, "たかつえ", "福島県", "いつも空いてて1枚バーンが気持ちいい"))).when(skiresortMapper).findById(1);

        // testの実行
        Skiresort actual = skiresortServiceImpl.findById(1);
        assertThat(actual).isEqualTo(new Skiresort(1, "たかつえ", "福島県", "いつも空いてて1枚バーンが気持ちいい"));
        verify(skiresortMapper, times(1)).findById(1);
    }
}
