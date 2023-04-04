package cn.jokeo.lovepig.wechat;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.hutool.core.date.DateUtil;
import cn.jokeo.lovepig.config.WxMaConfiguration;
import cn.jokeo.lovepig.entity.vo.PromiseRemindVo;
import com.google.common.collect.Lists;
import me.chanjar.weixin.common.error.WxErrorException;

import java.time.format.DateTimeFormatter;

/**
 * 发送微信消息
 *
 * @author joke
 * @version 1.0
 * @date 2022/3/11 20:52
 */
public class MessageService {


    /**
     * 发送承诺消息
     *
     * @return
     */
    public static boolean sendPromiseMessage(PromiseRemindVo promise) {
        WxMaSubscribeMessage message =
                WxMaSubscribeMessage.builder()
                        .templateId("YNB3mvQhHAq04UTnytKjdo1Ftw7Aj8sbGzHVLmw1JH0")
                        .data(Lists.newArrayList(
                                //活动事件
                                new WxMaSubscribeMessage.MsgData("thing1", promise.getPromiseName()),
                                //开始时间
                                new WxMaSubscribeMessage.MsgData("time2", DateUtil.format(promise.getTargetTime(), DateTimeFormatter.ISO_LOCAL_DATE)),
                                //备忘事项
                                new WxMaSubscribeMessage.MsgData("thing3", promise.getPromiseRemarks()),
                                //完成进度
                                new WxMaSubscribeMessage.MsgData("thing4", "待完成")))
                        .toUser(promise.getOpenId())
                        .page("/pages/")
                        .build();
        return sendTemplateMessage(message);
    }

    /**
     * 发送纪念日提醒
     *
     * @return
     */
    public static boolean sendAnniversaryMessage(PromiseRemindVo promise) {
        WxMaSubscribeMessage message =
                WxMaSubscribeMessage.builder()
                        .templateId("shVsiQSfHVlQ6pN8yrS-h-dAOk2_Coxb8aZg1MYVe7o")
                        .data(Lists.newArrayList(
                                //纪念日名称
                                new WxMaSubscribeMessage.MsgData("thing1", promise.getPromiseName()),
                                //纪念日时间
                                new WxMaSubscribeMessage.MsgData("time2", DateUtil.format(promise.getTargetTime(), DateTimeFormatter.ISO_LOCAL_DATE)),
                                //温馨提示
                                new WxMaSubscribeMessage.MsgData("thing3", promise.getPromiseRemarks()),
                                //临近时间(三天/一周)
                                new WxMaSubscribeMessage.MsgData("thing5", promise.getPromiseRemind()+"天后")
                        ))
                        .toUser(promise.getOpenId())
                        .page("/pages/")
                        .build();
        return sendTemplateMessage(message);
    }

    /**
     * 发送模板消息
     *
     * @return
     */
    public static boolean sendTemplateMessage(WxMaSubscribeMessage message) {
        final WxMaService wxService = WxMaConfiguration.getMaService("wx34dfb777ec6e2402");
        try {
            WxMaMsgService msgService = wxService.getMsgService();
            msgService.sendSubscribeMsg(message);
            return true;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return false;
    }


}
