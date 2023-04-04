package cn.jokeo.lovepig.entity.request;

import lombok.Data;

/**
 * @author joke
 * @version 1.0
 * @date 2021/6/19 19:13
 */
@Data
public class PageRequest {
    private int currPage = 1;
    private int pageSize = 10;

}
