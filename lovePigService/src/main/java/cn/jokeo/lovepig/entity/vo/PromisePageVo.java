package cn.jokeo.lovepig.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author joke
 * @date 2021/7/15 10:03
 */
@Data
public class PromisePageVo implements Serializable {
    /**
     * 完成的承诺
     */
    private Long complete;
    /**
     * 待完成的承诺
     */
    private Integer undone;
    /**
     * 承诺列表
     */
    private List<PromiseListVo> promiseList;
}
