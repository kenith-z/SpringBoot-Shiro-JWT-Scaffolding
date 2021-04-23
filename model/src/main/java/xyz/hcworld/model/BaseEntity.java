package xyz.hcworld.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 实体类基类
 * @ClassName: BaseEntity
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
@Data
public class BaseEntity {

    /**
     * 基类版本号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 自增数据库id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 修改时间
     */
    private LocalDateTime modified;
}
