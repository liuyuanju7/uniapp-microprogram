package cn.jokeo.lovepig.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * 发送邮件工具类
 *
 * @author joke
 * @version 1.0
 * @date 2021/2/26 1:31
 */
@Component
public class MailClient {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private TemplateEngine templateEngine;


    /**
     * 发送html邮件
     * <p>
     * 构建模板邮件(以下为示例)
     * Context context = new Context();
     * context.setVariable("msg", content);
     * //模板地址
     * String text = templateEngine.process("/mail/activation.html", context);
     *
     * @param toEmail
     * @param title
     * @param content
     * @return
     */
    public Boolean sendMail(String toEmail, String title, String content) {
        if (StringUtils.isEmpty(toEmail) || StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) {
            return false;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(emailFrom);
            messageHelper.setTo(toEmail);
            messageHelper.setSubject(title);
            messageHelper.setText(content, true);
            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 构建模板邮件内容
     *
     * @param map      传给模板的参数
     * @param template 模板地址
     * @return
     */
    public String generateMailByTemplate(Map<String, Object> map, String template) {
        Context context = new Context();
        context.setVariables(map);
        return templateEngine.process(template, context);
    }

    /**
     * 构建激活邮件...没必要
     *
     * @param email
     * @param href
     * @return
     */
    public String generateMailActivation(String email, String href) {
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("href", href);
        return templateEngine.process("/mail/promise", context);

    }
}
