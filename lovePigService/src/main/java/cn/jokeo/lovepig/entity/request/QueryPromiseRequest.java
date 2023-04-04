package cn.jokeo.lovepig.entity.request;

import lombok.Data;

/**
 * @author joke
 * @version 1.0
 * @date 2021/6/19 18:58
 */
@Data
public class QueryPromiseRequest extends PageRequest {
    /**
     * 承诺id
     */
    private long promiseId;
    private long loveId;
    /**
     * 承诺类型1.承诺(以后) 2.纪念日(以前)
     */
    private Integer promiseType;
    /**
     * 承诺状态
     */
    private Integer promiseState = 1;
}
