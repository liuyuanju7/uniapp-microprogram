package cn.jokeo.lovepig.entity.model;

import lombok.Data;

/**
 * @author joke
 * @version 1.0
 * @date 2022/1/8 18:03
 */
@Data
public class SelectStoryModel extends BaseModel {
    private Long loveId;
    private Long storyId;
    private Integer storyType;
    private Long originator;
    private Long promiseId;
}
