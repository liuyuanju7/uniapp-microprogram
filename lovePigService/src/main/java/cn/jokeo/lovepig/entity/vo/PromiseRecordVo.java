package cn.jokeo.lovepig.entity.vo;

import com.vdurmont.emoji.EmojiParser;
import lombok.Data;

import java.util.Date;

/**
 * 承诺完成结果
 *
 * @author v_chenjinbao_dxm
 * @date 2021/8/23 16:06
 */
@Data
public class PromiseRecordVo {
    /**
     * 承诺id
     */
    private String promiseId;

    /**
     * 承诺名称
     */
    private String promiseName;

    /**
     * 完成记录id
     */
    private Long id;

    /**
     * 第几个周期
     */
    private Integer annual;
    /**
     * 完成时间
     */
    private Date finishTime;
    public void setPromiseName(String promiseName) {
        this.promiseName = EmojiParser.parseToUnicode(promiseName);
    }
}
