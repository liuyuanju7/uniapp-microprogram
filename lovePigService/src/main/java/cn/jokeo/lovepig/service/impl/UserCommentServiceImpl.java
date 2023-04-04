package cn.jokeo.lovepig.service.impl;

import cn.jokeo.lovepig.entity.UserComment;
import cn.jokeo.lovepig.mapper.UserCommentMapper;
import cn.jokeo.lovepig.service.IUserCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
@Service
public class UserCommentServiceImpl extends ServiceImpl<UserCommentMapper, UserComment> implements IUserCommentService {

}
