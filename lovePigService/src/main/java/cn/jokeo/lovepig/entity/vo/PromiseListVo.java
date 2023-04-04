package cn.jokeo.lovepig.entity.vo;

import com.vdurmont.emoji.EmojiParser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author joke
 * @date 2021/7/15 10:03
 */
@Data
public class PromiseListVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long promiseId;
    /**
     * 承诺人
     */
    private UserBaseInfoVo user;
    /**
     * 承诺名称
     */
    private String promiseName;
    /**
     * 承诺备注
     */
    private String promiseRemarks;
    /**
     * 承诺开始时间,纪念日的时间
     */
    private Date promiseTime;
    /**
     * 子类型
     */
    private String promiseCategory;
    /**
     * 重复的周期
     */
    private String promiseRepeat;
    /**
     * 纪念是提前提醒时间-1代表不需要提醒单位为天0为当天
     */
    private String promiseRemind;

    /**
     * 承诺背景图片地址
     */
    private String promiseBackground;

    /**
     * 距离天数
     */
    private Long daysApart;
    /**
     * 展示效果
     * 1为还有多少天
     * 2为过去多少天
     */
    private Byte showType = 1;
    /**
     * 距离天数
     */
    private Long pastDays;
    /**
     * 当前是及周年
     */
    private Long annual;
    /**
     * 类型
     */
    private Integer promiseType;
    /**
     * 目标时间
     */
    private Date targetTime;
    /**
     * 承诺完成记录
     */
    private List<PromiseRecordVo> promiseRecords;

    public void setDaysApart(Long daysApart) {
        this.daysApart = daysApart;
        //如果未来天数为负数则显示为过去式
        if (daysApart < 0) {
            this.showType = 2;
        }
    }

    public void setPromiseName(String promiseName) {
        this.promiseName = EmojiParser.parseToUnicode(promiseName);
    }

    public void setPromiseRemarks(String promiseRemarks) {
        this.promiseRemarks = EmojiParser.parseToUnicode(promiseRemarks);
    }
}
