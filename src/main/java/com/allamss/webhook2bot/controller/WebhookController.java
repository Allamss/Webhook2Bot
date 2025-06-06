package com.allamss.webhook2bot.controller;

import com.allamss.webhook2bot.dto.MalimawaiReqDTO;
import com.allamss.webhook2bot.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 回调控制器
 *
 * @author Allamss
 * @date 2025/6/7 03:29
 */
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
@RestController
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/malimawai/send/{key}")
    public void send(@PathVariable("key") String key, @RequestBody MalimawaiReqDTO reqDTO) {
        webhookService.send(key, reqDTO);
    }
}
