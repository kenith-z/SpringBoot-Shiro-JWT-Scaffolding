package xyz.hcworld.service;


import xyz.hcworld.common.lang.Result;
import xyz.hcworld.model.User;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 用户服务接口
 * @ClassName: UserService
 * @Author: 张红尘
 * @Date: 2021-04-13
 * @Version： 1.0
 */
public interface UserService extends IService<User> {

    /**
     * 注册
     * @param user 用户实体
     * @return
     */
    Result register(User user);

    /**
     * 登录
     * @param username 账号
     * @param password 密码
     * @return
     */
    Result login(String username,String password);


}
