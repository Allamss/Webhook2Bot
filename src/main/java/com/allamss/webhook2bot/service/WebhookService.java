package com.allamss.webhook2bot.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.allamss.webhook2bot.dto.MalimawaiReqDTO;
import com.allamss.webhook2bot.util.WeComSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 回调服务
 *
 * @author Allamss
 * @date 2025/6/7 03:32
 */
@RequiredArgsConstructor
@Service
public class WebhookService {

    @Value("${webhook.key}")
    private String webhookKey;

    private final WeComSender weComSender;

    public void send(String key, MalimawaiReqDTO reqDTO) {
        Assert.equals(webhookKey, key, RuntimeException::new);
        var markdownMessage = StrUtil.format("""
                **订单编号**: {}
                **起点地址**: {}
                **终点地址**: {}
                **通知标题**: {}
                **通知描述**: {}
                **跳转链接**: [跳转链接]({})
                **接单收益**: {}
                **用户支付金额**: {}
                """,
            reqDTO.getOrderNo(), reqDTO.getStartAddress(), reqDTO.getEndAddress(), reqDTO.getTitle(), reqDTO.getDesc(),
            reqDTO.getUrl(), reqDTO.getTakerIncome(), reqDTO.getPayAmount());
        weComSender.send(markdownMessage);
    }
}
