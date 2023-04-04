package cn.jokeo.lovepig.entity.model;

import lombok.Data;

/**
 * 传递给dao层的实体
 *
 *
 * @author joke
 * @version 1.0
 * @date 2022/1/8 18:04
 */
@Data
public class BaseModel {
    private int offset;
    private int pageSize;
}
