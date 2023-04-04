package cn.jokeo.lovepig.autotask;

import cn.jokeo.lovepig.entity.vo.PromiseRemindVo;
import cn.jokeo.lovepig.service.ILovePromiseService;
import cn.jokeo.lovepig.utils.MailClient;
import cn.jokeo.lovepig.wechat.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用来处理承诺纪念日数据计算处理
 *
 * @author joke
 * @date 2022/2/18 16:41
 */
@Component
@Log4j2
public class PromiseTask {
    @Resource
    private ILovePromiseService lovePromiseService;
    @Resource
    private MailClient mailClient;

    /**
     * 检查承诺时效
     * 1.承诺过期状态改为失效
     * 2.计算承诺目标时间(方便数据查询)
     * <p>
     * <p>
     * 时间:每天0点0分钟
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkPromiseAging() {
        log.info("===每天0点1分钟检查一次性承诺状态开始===");
        Boolean aBoolean = lovePromiseService.queryAndUpdatePromiseState();
        log.info("===每天0点1分钟检查一次性承诺状态结束===结果:{}", aBoolean);

        log.info("===每天1点更新已经过时的承诺目标日期-开始===");
        Boolean aBoolean1 = lovePromiseService.queryAndUpdatePromiseTargetTime();
        log.info("===每天1点更新已经过时的承诺目标日期-结束===结果:{}", aBoolean1);
    }

    /**
     * 每天1点更新承诺以及纪念日的目标时间
     */
    @Scheduled(cron = "0 0 1 * * ?")
    private void updatePromiseTargetTime() {
//        log.info("===每天1点更新已经过时的承诺目标日期-开始===");
//        Boolean aBoolean = lovePromiseService.queryAndUpdatePromiseTargetTime();
//        log.info("===每天1点更新已经过时的承诺目标日期-结束===");
    }

    /**
     * 时间:每个月1号3点
     */
    @Scheduled(cron = "0 0 3 1 * ?")
    private void checkPromiseAging1() {
        System.out.println("时间:每个月1号3点");

    }

    /**
     * 时间:每年1月1日 4点
     */
    @Scheduled(cron = "0 0 4 1 1 ?")
    private void checkPromiseAging2() {
        System.out.println("时间:每年1月1日 4点");

    }

    /**
     * 提醒用户
     * 时间:每天10点
     */
    @Scheduled(cron = "0 0 10 * * ?")
    private void remindPromise() {
        System.out.println("时间:每天10点");
        //查询需要通知的数据
        List<PromiseRemindVo> promiseListVos = lovePromiseService.queryNeedRemind();
        //发送微信小程序通知
        promiseListVos.forEach(p -> {
            if (p.getPromiseType() == 1) {
                MessageService.sendPromiseMessage(p);
                String s = mailClient.generateMailActivation("10000@qq.com", "www.baidu.com");
                mailClient.sendMail("jokezc@qq.com","爱窝-承诺提示",s);
            } else if (p.getPromiseType() == 2) {
                MessageService.sendAnniversaryMessage(p);
                String s = mailClient.generateMailActivation("10000@qq.com", "www.baidu.com");
                mailClient.sendMail("1049028590@qq.com","爱窝-承诺提示",s);
            }
        });
        //发送邮件通知
    }
}
