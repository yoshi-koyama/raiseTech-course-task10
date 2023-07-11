package com.example.raiseTechcoursetask10.controller;

import com.example.raiseTechcoursetask10.controller.response.SkiresortResponse;
import com.example.raiseTechcoursetask10.entity.Skiresort;
import com.example.raiseTechcoursetask10.service.SkiresortService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkiresortController {
    // field(ControllerにMapper->Serviceを定義）
    private final SkiresortService skiresortService;

    // constructor(ControllerのコンストラクタとしてMapper->Serviceを定義）
    public SkiresortController(SkiresortService skiresortService) {
        this.skiresortService = skiresortService;
    }

    @GetMapping("/skiserots")
    public List<SkiresortResponse> skiresorts() {
        // skiresortの情報を取得する
        List<Skiresort> skiresorts = skiresortService.findAll();
        // 取得したskiresortの情報を、Response用のオブジェクトに変換する
        List<SkiresortResponse> skiresortResponses = skiresorts.stream().map(SkiresortResponse::new).toList();
        return skiresortResponses;
    }
}
