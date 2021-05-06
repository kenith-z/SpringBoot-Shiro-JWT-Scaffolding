package xyz.hcworld.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.hcworld.mapper.CurrencyMapper;
import xyz.hcworld.service.CurrencyService;

import java.util.Map;


/**
 * 通用服务类实现
 *
 * @ClassName: CurrencyServiceImpl
 * @Author: 张红尘
 * @Date: 2021-04-23
 * @Version： 1.0
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {
    /**
     * 通用数据库操作类
     */
    @Autowired
    CurrencyMapper currencyMapper;


    @Override
    public Boolean selectExistence(String table, Map<String, Object> map) {
        Integer existenceCollection = currencyMapper.selectExistence(table, new QueryWrapper<>().allEq(map));
        return existenceCollection != null;
    }

}
