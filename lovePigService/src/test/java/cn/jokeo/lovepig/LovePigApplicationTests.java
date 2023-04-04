package cn.jokeo.lovepig;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.jokeo.lovepig.config.LoveConfig;
import cn.jokeo.lovepig.security.TokenService;
import cn.jokeo.lovepig.security.model.LoginUserInfo;
import cn.jokeo.lovepig.service.ILovePromiseService;
import cn.jokeo.lovepig.utils.MailClient;
import cn.jokeo.lovepig.utils.TencentOssUtil;
import com.auth0.jwt.interfaces.Claim;
import com.vdurmont.emoji.EmojiParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Map;

@SpringBootTest
class LovePigApplicationTests {

    @Autowired
    private TencentOssUtil tencentOssUtil;

    @Autowired
    private LoveConfig loveConfig;
    @Autowired
    private TokenService tokenService;

    @Resource
    private ILovePromiseService lovePromiseService;

    @Resource
    private MailClient mailClient;

    @Test
    void contextLoads() {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setUserId(10L);
        String token = tokenService.createToken(null);
        Map<String, Claim> stringClaimMap = tokenService.parseToken(token);
        stringClaimMap.forEach((k, v) -> System.out.println(k + "   " + v.asLong()));
        loginUserInfo.setUuid("e2e2eqe");
//        tokenService.setLoginUserInfo(null);
        tokenService.verifyToken(loginUserInfo);
    }


    @Test
    void dateBetween() {
        DateTime now = DateTime.now();
        DateTime lastParse = DateUtil.parse("2018-10-13");
        DateTime afterParse = DateUtil.parse("2022-10-13");
        System.out.println(DateUtil.betweenDay(now, lastParse, true));
        System.out.println(DateUtil.betweenDay(lastParse, now, true));
        System.out.println(DateUtil.betweenDay(now, afterParse, true));
        System.out.println(DateUtil.betweenDay(afterParse, now, true));
    }

    @Test
    void getMonthLastDay() {
        int lastDay = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(lastDay);
    }

    @Test
    void delUserRedis() {
//        tokenService.delLoginUserInfo(22L);
//        tokenService.delLoginUserInfo(23L);
//        tokenService.delLoginUserInfo(24L);
//        tokenService.delLoginUserInfo(25L);
    }

    @Test
    void emoji() {
        // String str = "\\xF0\\x9F\\x90\\xAF";
        // String result = EmojiParser.parseToUnicode(str);
        // System.out.println(result);
        String str = "Here is a boy: \uD83D\uDC66\uD83C\uDFFF!";
        //转成别名存库里
        EmojiParser.parseToAliases(str);
        //查询出来的数据转换回来
        EmojiParser.parseToUnicode(str);
    }
    @Test
    void emoji1() {
        String str = "An 😀awesome 😃string with a few 😉emojis!";

        String resultDecimal = EmojiParser.parseToHtmlDecimal(str);
        System.out.println(resultDecimal);

        String resultHexadecimal = EmojiParser.parseToHtmlHexadecimal(str);
        System.out.println(resultHexadecimal);

        System.out.println(EmojiParser.parseToUnicode(resultDecimal));
        System.out.println(EmojiParser.parseToUnicode(resultHexadecimal));
    }
    @Test
    void queryRemindPromise() {
        lovePromiseService.queryNeedRemind();
    }
    @Test
    void email() {
        String s = mailClient.generateMailActivation("10000@qq.com", "www.baidu.com");
        mailClient.sendMail("1049028590@qq.com","爱窝-承诺提示",s);
    }

}
