package xyz.hcworld.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: UnifiedSerializer
 * @Author: 张红尘
 * @Date: 2021-05-08
 * @Version： 1.0
 */
@Component
public class UnifiedSerializer  implements RedisSerializer<String> {

    private final Charset charset;
    @Value("${redis.prefix}")
    private String prefix ;

    public UnifiedSerializer() {
        this(StandardCharsets.UTF_8);
    }
    public UnifiedSerializer(Charset charset) {
        Assert.notNull ( charset, "Charset must not be null!" );
        this.charset = charset;
    }





    @Override
    public byte[] serialize(@Nullable String string) {
        return (string == null ? null : (prefix +":" +string).getBytes(charset));
    }

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset).replaceFirst(prefix+":", ""));
    }


}
