package xyz.hcworld.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 通用sql接口
 * @ClassName: CurrencyMapper
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
@Component
public interface CurrencyMapper {

    /**
     * 查询数据是否存在
     * @param tableName 表名
     * @param wrapper 条件
     * @return 存在有数据，不存在为null
     */
    Integer selectExistence(String tableName,@Param(Constants.WRAPPER) QueryWrapper wrapper);

}
