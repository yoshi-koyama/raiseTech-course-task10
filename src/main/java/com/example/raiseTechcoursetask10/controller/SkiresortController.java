package com.example.raisetechcoursetask10.controller;

import com.example.raisetechcoursetask10.controller.form.SkiresortCreateForm;
import com.example.raisetechcoursetask10.controller.form.SkiresortUpdateForm;
import com.example.raisetechcoursetask10.controller.response.SkiresortResponse;
import com.example.raisetechcoursetask10.entity.Skiresort;
import com.example.raisetechcoursetask10.exception.ResourceNotFoundException;
import com.example.raisetechcoursetask10.service.SkiresortService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class SkiresortController {
    private final SkiresortService skiresortService;

    public SkiresortController(SkiresortService skiresortService) {
        this.skiresortService = skiresortService;
    }

    @GetMapping("/skiresorts")
    public List<SkiresortResponse> skiresorts() {
        List<Skiresort> skiresorts = skiresortService.findAll();
        List<SkiresortResponse> skiresortResponses = skiresorts.stream().map(SkiresortResponse::new).toList();
        return skiresortResponses;
    }

    @GetMapping("/skiresorts/{id}")
    public SkiresortResponse getSkiresortById(@PathVariable("id") int id) {
        Skiresort skiresort = skiresortService.findById(id);
        return new SkiresortResponse(skiresort);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> noResourceFound(
            ResourceNotFoundException e, HttpServletRequest request) {

        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());

        // 404エラーを返す
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/skiresorts")
    public ResponseEntity<SkiresortResponse> createSkiresort(@RequestBody SkiresortCreateForm skiresortCreateForm, HttpServletRequest request) {
        Skiresort skiresort = skiresortService.createSkiresort(skiresortCreateForm);

        // skiresortオブジェクトを元にレスポンス用のオブジェクトを生成
        SkiresortResponse skiresortResponse = new SkiresortResponse(skiresort);
        // HttpServletRequestのインスタンスでリクエストの中身を取得し、動的なURLを生成
        URI url = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .path("/skiresorts/{id}")
                .buildAndExpand(skiresort.getId())
                .toUri();
        return ResponseEntity.created(url).body(skiresortResponse);
    }

    @PatchMapping("/skiresorts/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable("id") int id, @RequestBody @Validated SkiresortUpdateForm form) {

        // id以外のSkiresortUpdateFormの情報を使用してレコードを更新する
        skiresortService.updateSkiresort(id, form.getName(), form.getArea(), form.getCustomerEvaluation());
        return ResponseEntity.ok(Map.of("message", "successfully update"));
    }
}
