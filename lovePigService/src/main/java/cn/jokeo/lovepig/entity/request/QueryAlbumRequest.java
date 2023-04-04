package cn.jokeo.lovepig.entity.request;

import lombok.Data;

/**
 * @author joke
 * @version 1.0
 * @date 2021/6/19 18:58
 */
@Data
public class QueryAlbumRequest extends PageRequest {
    private Long loveId;
}
