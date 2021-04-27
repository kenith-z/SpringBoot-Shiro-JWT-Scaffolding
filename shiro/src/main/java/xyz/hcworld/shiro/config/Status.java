package xyz.hcworld.shiro.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统权限表
 * @ClassName: Status
 * @Author: 张红尘
 * @Date: 2021-04-26
 * @Version： 1.0
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "status")
public class Status {
    /**
     * 权限列表
     */
    private Map<Integer,String> statusMap = new HashMap<>();


    /**
     * 返回权限信息
     * @param code 权限码
     * @return 有返回对应权限，没有返回封禁ban
     */
    public String getStatus(Integer code){
        return statusMap.getOrDefault(code,statusMap.get(-1));
    }


}
