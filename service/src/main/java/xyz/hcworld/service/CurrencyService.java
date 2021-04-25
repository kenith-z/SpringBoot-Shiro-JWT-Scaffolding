package xyz.hcworld.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 通用服务接口
 * @ClassName: CurrencyService
 * @Author: 张红尘
 * @Date: 2021-04-13
 * @Version： 1.0
 */
public interface CurrencyService {
    /**
     * 检出该数据在数据库中是否存在
     * @param table 要查询的表
     * @param map 查询条件
     * @return 存在true、不存在false
     */
     Boolean selectExistence(String table, Map<String,Object> map);
}
