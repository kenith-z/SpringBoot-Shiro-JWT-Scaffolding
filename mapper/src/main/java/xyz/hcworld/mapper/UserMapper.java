package xyz.hcworld.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import xyz.hcworld.model.User;

/**
 * 用户表sql接口
 * @ClassName: UserMapper
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
@Component
public interface UserMapper extends BaseMapper<User> {
    /**
     * 登录查询账户
     * @param account 账户
     * @return
     */
   User selectUserWhereAccount(String account);

}
