package xyz.hcworld.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.hcworld.gotool.security.entity.EncryptedInfo;

/**
 * 继承加密信息类的账号密码类
 * @ClassName: Account
 * @Author: 张红尘
 * @Date: 2021-05-08
 * @Version： 1.0
 */
@Data
public class Account extends EncryptedInfo {

    /**
     * 昵称
     */
    private String account;

    /**
     * 密码
     */
    private String password;

}
