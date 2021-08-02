package com.ligz.redis.controller;

import com.ligz.redis.service.DataStructService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/data-struct")
public class DataStructController {
    private final DataStructService dataStructService;

    @PostMapping("/string")
    public void storeString(@RequestParam("value") String value) {
        dataStructService.storeString(value);
    }

    @GetMapping("/string")
    public String getString(@RequestParam("key") String key) {
        return dataStructService.getString(key);
    }
}
