package xyz.hcworld.test.util;


import lombok.Data;
import xyz.hcworld.model.dao.RedisSerializable;

/**
 * @ClassName: TestModel
 * @Author: 张红尘
 * @Date: 2021-05-18
 * @Version： 1.0
 */
@Data
public class TestModel implements RedisSerializable {

    private String one;

    private String two;
}
