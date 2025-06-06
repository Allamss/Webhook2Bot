package com.allamss.webhook2bot.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * 企业微信发送器
 * @see <a href="https://developer.work.weixin.qq.com/document/path/91770">群机器人配置说明</a>
 *
 * @author Allamss
 * @date 2025/6/7 02:58
 */
@Slf4j
@Component
public class WeComSender {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${wecom.webhook}")
    private String webhook;

    @SneakyThrows
    @Retryable(retryFor = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 500, multiplier = 2))
    public void send(String content) {
        var reqDTO = new ReqDTO();
        reqDTO.setMarkdown(new ReqDTO.Markdown(content));

        try {
            var res = HttpUtil.post(webhook, MAPPER.writeValueAsString(reqDTO));
            Assert.isTrue(res.contains("\"errcode\":0"), "发送异常:" + res);
        } catch (Exception e) {
            log.error("发送失败", e);
            throw new RuntimeException();
        }
    }

    @Data
    public static class ReqDTO {

        /**
         * 消息类型
         */
        private String msgtype = "markdown";

        /**
         * text消息体
         */
        private Markdown markdown;

        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        public static class Markdown {
            private String content;
        }
    }
}
