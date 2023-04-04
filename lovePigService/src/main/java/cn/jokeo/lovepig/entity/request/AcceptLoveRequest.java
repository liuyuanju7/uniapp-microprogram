package cn.jokeo.lovepig.entity.request;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author v_chenjinbao_dxm
 * @date 2022/3/24 16:37
 */
@Data
public class AcceptLoveRequest {

    @ApiModelProperty(value = "恋爱空间id")
    @NotNull
    private Long loveId;

    @ApiModelProperty(value = "接受者")
    @NotNull
    private Long acceptUser;

    @ApiModelProperty(value = "发起者")
    @NotNull
    private Long inviteUser;

}
