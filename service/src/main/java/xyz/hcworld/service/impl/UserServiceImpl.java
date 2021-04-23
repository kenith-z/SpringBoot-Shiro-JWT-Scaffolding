package xyz.hcworld.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.hcworld.mapper.UserMapper;
import xyz.hcworld.model.User;
import xyz.hcworld.service.UserService;


/**
 * 用户服务类实现
 *
 * @ClassName: UserServiceImpl
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
