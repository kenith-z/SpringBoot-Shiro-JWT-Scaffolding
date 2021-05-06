package xyz.hcworld.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Kenith-Zhang
 * @since 2020-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("m_user")
public class User extends BaseEntity {

    /**
     * 版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String username;

    /**
     * 密码
     */
    @Length(min = 6, max = 64, message = "密码长度请在6-64位")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 密码偏移量
     */

    private String iv;
    /**
     * 邮箱
     */
    @Email
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 手机电话
     */
    @NotBlank(message = "电话不能为空")
    @Length(min = 11, max = 11, message = "手机号格式有误")
    @Pattern(regexp = "^[1][3456789][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    /**
     * 性别
     */
    @Range(min = 0, max = 2, message = "未知性别")
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 最后的登陆时间
     */
    private LocalDateTime lasted;

    /**
     * 状态
     */
    private Integer status;


}
