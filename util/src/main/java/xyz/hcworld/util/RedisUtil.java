package xyz.hcworld.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import xyz.hcworld.model.dao.RedisSerializable;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis操作类
 *
 * @ClassName: RedisUtil
 * @Author: 张红尘
 * @Date: 2020/8/16 16:56
 * @Version： 1.0
 */
@Component
public class RedisUtil {

    /**
     * Spring提供的Redis操作模板类
     */
    private final RedisTemplate redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public RedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        if (time <= 0) {
            return false;
        }
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public boolean del(String... key) {
        if (key != null && key.length > 0) {
            return false;
        }
        return redisTemplate.delete(CollectionUtils.arrayToList(key)) > 0;
    }

    /**
     * 批量删除key
     *
     * @param key key列表
     * @return true成功，false redis没有列表中的任何一条key。
     */
    public boolean delList(List<String> key) {
        if (key == null || key.isEmpty()) {
            return false;
        }
        return redisTemplate.delete(key) > 0;
    }

    /**
     * 模糊查找key
     *
     * @param key 可以传一个值 或多个
     * @return key列表
     */
    public List<String> keys(String key) {
        Set<String> keySet = redisTemplate.keys(key + ":*");
        return new ArrayList<>(keySet);
    }


    //============================String=============================

    /**
     * 获取String
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return String.valueOf(redisTemplate.opsForValue().get(key));
    }

    /**
     * 设置String
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long time) {
        Assert.isTrue(time > 0, "存活时间必须大于0");
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 键值对的值增加并返回结果
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 相加的结果
     */
    public long incr(String key, long delta) {
        Assert.isTrue(delta > 0, "递增因子必须大于0");
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 键值对的值减少
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return 减完的结果
     */
    public long decr(String key, long delta) {
        Assert.isTrue(delta > 0, "递减因子必须大于0");
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * 获取hash的数据
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hAllGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 存入hashKey对应的所有键值
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public void hBatchSet(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     * <br/>时间设置是整个集合，慎用
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     */
    public void hBatchSet(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public void hSet(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * <br/>时间设置是整个集合，慎用
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public void hSet(String key, String item, Object value, long time) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return 相加后的结果
     */
    public double hIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return 相减后的结果
     */
    public double hDecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================Set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return 值
     */
    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key  键
     * @param type 取出后要映射成的类
     * @return 继承了RedisSerializable接口的类的集合
     */
    public Set sGet(String key, Class<? extends RedisSerializable> type) {
        return (Set) redisTemplate.opsForSet().members(key).stream().map(date -> mapper.convertValue(date, type)).collect(Collectors.toSet());
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将set数据放入批量存入
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return true 存入一条以上的数据，false redis有没存进去
     */
    public boolean sSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values) > 0;
    }

    /**
     * 将set数据放入批量存入
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return true 存入一条以上的数据，false redis有没存进去
     */
    public boolean sBatchSetList(String key, List<? extends RedisSerializable> values) {
        return redisTemplate.opsForSet().add(key, values.toArray()) > 0;
    }

    /**
     * 将set数据放入批量存入并设置生存周期
     * <br/>时间设置是整个集合，慎用
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return true 存入一条以上的数据，false redis有没存进去
     */
    public boolean sSetAndTime(String key, long time, Object... values) {
        long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expire(key, time);
        }
        return 0 < count;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return set集合长度
     */
    public long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除一条及以上就位true，false时代表redis没有这样的值所以没移除成功
     */
    public boolean sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values) > 0;
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除一条及以上就位true，false时代表redis没有这样的值所以没移除成功
     */
    public boolean sBatchRemoveList(String key, List<? extends RedisSerializable> values) {
        return redisTemplate.opsForSet().remove(key, values.toArray()) > 0;
    }

    //===============================List=================================

    /**
     * 获取list缓存的内容，以对象形式
     * <br/>测试日期：2021年5月17日
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return 值
     */
    public List lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list指定范围的的内容，以对象形式
     * <br/>测试日期：2021年5月17日
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @param type  类型
     * @return 值
     */
    public List lGet(String key, long start, long end, Class<? extends RedisSerializable> type) {
        return (List) redisTemplate.opsForList().range(key, start, end).stream().map(date ->
                mapper.convertValue(date, type)
        ).collect(Collectors.toList());
    }

    /**
     * 获取list缓存的长度
     * <br/>测试日期：2021年5月17日
     *
     * @param key 键
     * @return list的长度
     */
    public long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     * <br/>测试日期：2021年5月17日
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 值
     */
    public Map lGetIndex(String key, long index) {
        return (Map) redisTemplate.opsForList().index(key, index);
    }

    /**
     * 通过索引 获取list中的值
     * <br/>测试日期：2021年5月17日
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @param type  获取的对象类型
     * @return 指定类型的对象
     */
    public Object lGetIndex(String key, long index, Class<? extends RedisSerializable> type) {
        return mapper.convertValue(redisTemplate.opsForList().index(key, index), type);
    }

    /**
     * list新增一个元素
     * <br/>测试日期：2021年5月18日
     *
     * @param key   键
     * @param value 值
     * @return 插入成功true，false为失败
     */
    public boolean lSet(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value) > 0;
    }

    /**
     * list新增一个元素(设置的时间是整个list的时间)
     * <br/>(慎用)
     * <br/>测试日期：2021年5月18日
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 插入成功true，false为失败
     */
    public boolean lSet(String key, Object value, long time) {
        long size = redisTemplate.opsForList().rightPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return size > 0;
    }

    /**
     * 将list放入缓存
     * <br/>测试日期：2021年5月18日
     *
     * @param key   键
     * @param value 值
     * @return 插入成功true，false为失败
     */
    public boolean lBatchSetList(String key, List<? extends RedisSerializable> value) {
        return redisTemplate.opsForList().rightPushAll(key, value) > 0;
    }

    /**
     * 将list放入缓存(设置的时间是整个list的时间)
     * <br/>(慎用)
     * <br/>测试日期：2021年5月18日
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 插入成功true，false为失败
     */
    public boolean lBatchSetList(String key, List<? extends RedisSerializable> value, long time) {
        long size = redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return size > 0;
    }

    /**
     * 根据索引修改list中的某条数据
     * <br/>测试日期：2021年5月18日
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public void lUpdateIndex(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除N个值为value
     * <br/>测试日期：2021年5月18日
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除成功true，false为失败
     */
    public boolean lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value) > 0;
    }

    /**
     * 裁减list，保留指定的长度
     * <br/>测试日期：2021年5月18日
     *
     * @param key   键
     * @param start 要保留的左区间
     * @param stop  要保留的右区间
     * @return 移除成功true，false为失败
     */
    public void lTrim(String key, long start, long stop) {
        redisTemplate.opsForList().trim(key, start, stop);
    }
    //================有序集合 sort set(zset)===================

    /**
     * 有序set添加元素（如果该key有相同的value则更新权重）
     * <br/>测试日期：2021年5月16日
     *
     * @param key   键
     * @param value 值
     * @param score 权重
     * @return 添加成功true，false为失败
     */
    public boolean zSet(String key, Object value, long score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 批量添加有序列表
     * <br/>测试日期：2021年5月16日
     *
     * @param key            有序列表key
     * @param valuesAndScore 内容与权重
     * @return 添加成功true，false为失败
     */
    public long zBatchSet(String key, Map<Object, Long> valuesAndScore) {
        Set<ZSetOperations.TypedTuple> vas = valuesAndScore.entrySet()
                .stream()
                .map(date -> new DefaultTypedTuple<>(date.getKey(), date.getValue().doubleValue()))
                .collect(Collectors.toSet());
        return redisTemplate.opsForZSet().add(key, vas);
    }

    /**
     * 根据key和value修改有序列表中的权重（原有基础进行+-）
     * 正数为+权重，负数为-权重
     * <br/>测试日期：2021年5月16日
     *
     * @param key   有序列表的key
     * @param value 列表的变量
     * @param delta 变量的权重
     */
    public void zIncrementScore(String key, Object value, long delta) {
        redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 将一个或多个有序集合进行并集
     * <br/>测试日期：2021年5月16日
     *
     * @param key       要合并的集合的key
     * @param otherKeys 要合并的集合的key列表
     * @param destKey   并集的key
     */
    public void zUnionAndStore(String key, List otherKeys, String destKey) {
        redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取zset指定的值的权重
     * <br/>测试日期：2021年5月16日
     *
     * @param key   有序列表的key
     * @param value 指定的值
     * @return 权重
     */
    public long zGetScore(String key, Object value) {
        Double score = redisTemplate.opsForZSet().score(key, value);
        return score != null ? score.longValue() : -1L;
    }

    /**
     * 删除有序列表的单个元素
     * <br/>测试日期：2021年5月16日
     *
     * @param key   有序列表的key
     * @param value 指定的值
     */
    public void zRemove(String key, Object value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 获取有序集 key 中成员 member 的排名 。
     * 其中有序集成员按 score 值递减 (从大到小) 排序。
     * <br/>如0-10，则长度为11
     * <br/>测试日期：2021年5月16日
     *
     * @param key   有序列表的key
     * @param start 开始的值
     * @param end   结束的值（包含）
     * @return key是有序列表的内容，value是权重
     */
    public Map zGetRank(String key, long start, long end) {
        Set<ZSetOperations.TypedTuple> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        if (set == null) {
            return new HashMap<>(1);
        }
        return set.stream().collect(Collectors.toMap(
                //Map的key填充
                ZSetOperations.TypedTuple::getValue,
                //Map的value填充
                typedTuple -> typedTuple.getScore().longValue(),
                //排序（降序）
                (oldVal, currVal) -> oldVal, LinkedHashMap::new)
        );
    }

    /**
     * 获取有序集 key 中成员 member 的排名 。
     * 其中有序集成员按 score 值递减 (从大到小) 排序。
     * <br/>如0-10，则长度为11
     * <br/>测试日期：2021年5月16日
     *
     * @param key   有序列表的key
     * @param start 开始的值
     * @param end   结束的值（包含）
     * @return key是有序列表的内容(转成对象)，value是权重
     */
    public Map zGetRank(String key, long start, long end, Class<? extends RedisSerializable> type) {
        Set<ZSetOperations.TypedTuple> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        if (set == null) {
            return new HashMap<>(1);
        }
        return set.stream().collect(Collectors.toMap(
                typedTuple -> mapper.convertValue(typedTuple.getValue(), type),
                typedTuple -> typedTuple.getScore().longValue(),
                (oldVal, currVal) -> oldVal, LinkedHashMap::new)
        );
    }


}
