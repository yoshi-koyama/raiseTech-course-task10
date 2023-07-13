package com.example.raiseTechcoursetask10.controller;

import com.example.raiseTechcoursetask10.controller.form.SkiresortCreateForm;
import com.example.raiseTechcoursetask10.controller.response.SkiresortResponse;
import com.example.raiseTechcoursetask10.entity.Skiresort;
import com.example.raiseTechcoursetask10.service.SkiresortService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @PostMapping("/skiresorts")
    public ResponseEntity<SkiresortResponse> createSkiresort(@RequestBody SkiresortCreateForm skiresortCreateForm) {
        Skiresort skiresort = skiresortService.createSkiresort(skiresortCreateForm);

        // skiresortを作成する処理
        SkiresortResponse skiresortResponse = new SkiresortResponse(skiresort);
        URI url = UriComponentsBuilder.fromUriString("http://localhost8080")
                .path("/skiresorts/{id}")
                .buildAndExpand(skiresort.getId())
                .toUri();
        return ResponseEntity.created(url).body(skiresortResponse);
    }
}
