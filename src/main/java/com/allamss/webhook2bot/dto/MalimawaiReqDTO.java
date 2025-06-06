package com.allamss.webhook2bot.dto;

import lombok.Data;

/**
 * 码里码外回调请求DTO
 */
@Data
public class MalimawaiReqDTO {
    /** 订单编号 */
    private String orderNo;
    
    /** 起点地址 */
    private String startAddress;
    
    /** 终点地址 */
    private String endAddress;
    
    /** 通知标题 */
    private String title;
    
    /** 通知描述 */
    private String desc;
    
    /** 跳转链接 */
    private String url;
    
    /** 接单收益 */
    private double takerIncome;
    
    /** 用户支付金额 */
    private double payAmount;
}