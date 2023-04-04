package cn.jokeo.lovepig.entity.request;

import lombok.Data;

/**
 * @author joke
 * @version 1.0
 * @date 2021/6/19 18:58
 */
@Data
public class QueryStoryRequest extends PageRequest {
    private Long loveId;
    private Long storyId;
    /**
     * 文章类型
     */
    private Integer storyType;
    /**
     * 创作者userid
     */
    private Long originator = 0L;
    private Long promiseId;
    private Integer orderBy;
}
