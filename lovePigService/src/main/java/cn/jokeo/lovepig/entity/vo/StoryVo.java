package cn.jokeo.lovepig.entity.vo;

import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 查询故事信息实体类
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/19 17:05
 */
@Data
public class StoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long storyId;

    @ApiModelProperty(value = "恋爱id")
    private Long loveId;

    @ApiModelProperty(value = "故事类型1.动态2.文章3.大图文章4.承诺5.视频6.相册")
    private Integer storyType;

    @ApiModelProperty(value = "故事标题（文章使用）")
    private String title;

    @ApiModelProperty(value = "头图地址（文章或者承诺使用）")
    private String banner;

    @ApiModelProperty(value = "故事摘要")
    private String summary;

    @ApiModelProperty(value = "故事内容")
    private String content;

    @ApiModelProperty(value = "查看权限0公开1我们俩2私密")
    private Boolean seePermissions;

    @ApiModelProperty(value = "是否开放评论0.不开启 1.开启")
    private Integer commentState;

    @ApiModelProperty(value = "评论数量")
    private Integer commentNum;

    @ApiModelProperty(value = "点赞数量")
    private Integer likeNum;

    @ApiModelProperty(value = "发布的位置，经纬度")
    private String location;

    @ApiModelProperty(value = "天气")
    private String weather;

    @ApiModelProperty(value = "故事发生时间")
    private Date storyTime;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "故事状态0.待发布1.正常2.隐藏3.删除")
    private Integer state;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    //===============

    @ApiModelProperty(value = "发布人基本信息")
    private UserBaseInfoVo userBase;
    /**
     * 故事所对应的资源
     */
    private List<ResourceVo> resources;
    /**
     * 承诺记录
     */
    private PromiseRecordVo promiseRecord;

    public void setSummary(String summary) {
        if (summary.length() >= 100) {
            summary += "...";
        }
        this.summary = EmojiParser.parseToUnicode(summary);
    }

    public void setTitle(String title) {
        this.title = EmojiParser.parseToUnicode(title);
    }

    public void setContent(String content) {
        this.content = EmojiParser.parseToUnicode(content);
    }
}
