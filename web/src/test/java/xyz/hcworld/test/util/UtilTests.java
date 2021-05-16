package xyz.hcworld.test.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.hcworld.util.RedisUtil;
import xyz.hcworld.web.Application;

import java.util.*;

/**
 * @ClassName: UtilTests
 * @Author: 张红尘
 * @Date: 2021-05-16
 * @Version： 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UtilTests {

    @Autowired
    RedisUtil redisUtil;

    @Before
    public void init() {

    }

    @After
    public void end() {

    }

    @Test
    public void test() {
        System.out.println("test");
        int size = 100;
        TestModel a = null, b = null;
        Map<Object, Long> aMap = new HashMap<>(size);
        Map<Object, Long> bMap = new HashMap<>(size);
        Map<Object, Long> cMap = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            TestModel tm = new TestModel();
            tm.setOne("测试" + i);
            tm.setTwo("test" + i);
            aMap.put(tm, (long) i);
            if (i == 20) {
                b = tm;
            }
            tm = new TestModel();
            tm.setOne("b" + i);
            tm.setTwo("b" + i);
            bMap.put(tm, (long) i);

            tm = new TestModel();
            tm.setOne("c" + i);
            tm.setTwo("c" + i);
            cMap.put(tm, (long) i);
            if (i == 20) {
                a = tm;
            }
        }
        /*
         * zset有序集合
         */
        //插入测试
        long aBatchSize = redisUtil.zBatchSet("test", aMap);
        System.out.println("批量插入成功数量：" + aBatchSize);
        long bBatchSize = redisUtil.zBatchSet("test1", bMap);
        long cBatchSize = redisUtil.zBatchSet("test2", cMap);
        //合并有序列表
        List list = new ArrayList();
        list.add("test1");
        list.add("test2");
        redisUtil.zUnionAndStore("test", list, "count");
        //获取权重
        long score = redisUtil.zGetScore("test2", a);
        System.out.println(a.getOne() + "的权重：" + score);
        //删除单个元素
        redisUtil.zRemove("test2", a);
        //获取排序测试
        Map rank = redisUtil.zGetRank("test", 0, size);
        System.out.println("排序读取数量：" + rank.size());
        System.out.println("排序读取数量：" + rank.toString());

        Map rank1 = redisUtil.zGetRank("test", 0, size,TestModel.class);
        System.out.println("排序读取数量：" + rank1.toString());

        //增加权重
        redisUtil.zIncrementScore("test", b, -5);
        long modify = redisUtil.zGetScore("test", b);
        System.out.println("修改后的权重:" + modify);
        //新增元素（如果该key有相同的value则更新权重）
        redisUtil.zSet("test1", b, 30);
        redisUtil.zSet("test1", b, 100);

        /*
         * list列表
         */
        List<TestModel> tempList = new ArrayList(100);
        for (int i = 0; i < size; i++) {
            TestModel tm = new TestModel();
            tm.setOne("测试" + i);
            tm.setTwo("test" + i);
            tempList.add(tm);
        }
        //存入单个。如带存活时间（设置的是整个list的存活时间）
        boolean setObj = redisUtil.lSet("list", tempList.get(0));
        boolean setObjtf = redisUtil.lSet("list", tempList.get(1), 1000);
        System.out.println("list存如单个：" + setObj + "|" + setObjtf);
        //批量存入，如带存活时间（设置的是整个list的存活时间）
        boolean setObjListTf = redisUtil.lBatchSetList("list", tempList);
        boolean setObjListTime = redisUtil.lBatchSetList("list", tempList, 1000);
        System.out.println("list批量存入：" + setObjListTf + "|" + setObjListTime);
        // 获取指定范围
        List<TestModel> listObj = redisUtil.lGet("list", 0, 100);
        System.out.println(listObj.size());
        System.out.println("list范围：" + listObj);
        // 获取指定类型和范围的list
        List<TestModel> listObjType = redisUtil.lGet("list", 0, 100, TestModel.class);
        TestModel tmt = listObjType.get(0);
        System.out.println("获取指定类型和范围的list的其中一个：" + tmt.toString());
        System.out.println("获取指定类型和范围的list：" + listObjType.toString());
        // 获取list长度
        long sizeList = redisUtil.lSize("list");
        System.out.println("list长度：" + sizeList);

        // 获取指定类型和map形式的单个value
        TestModel tm1 = (TestModel) redisUtil.lGetIndex("list", 0, TestModel.class);
        System.out.println(tm1.toString());
        Map obj = redisUtil.lGetIndex("list", 0);
        System.out.println("获取list指定位置：" + tm1.toString() + "\t" + obj.toString() + "|" + obj.get("one"));
        // 修改指定位置的balue
         redisUtil.lUpdateIndex("list", 1, tempList.get(20));
        // 删除N个相同的value
        boolean listRemoveObj = redisUtil.lRemove("list", 10, tempList.get(0));
        System.out.println(tempList.get(0).toString());
        System.out.println("list删除N个指定value：" + listRemoveObj);
        // 裁剪数组
         redisUtil.lTrim("list", 10, 30);

        /*
         * set集合
         */
        //查询set中当前key的value是否存在
        boolean setkey = redisUtil.sHasKey("set", tempList.get(10));
        System.out.println("set中当前key的value存在:" + setkey);
        //存对象
        boolean sSetObj = redisUtil.sSet("set", tempList.get(0), tempList.get(2));
        System.out.println("存入set两个对象：" + sSetObj);
        tempList.get(0).setOne("12");
        //批量存入
        boolean sSetObjList = redisUtil.sBatchSetList("set", tempList);
        System.out.println("存入set两个对象：" + sSetObjList);
        tempList.get(1).setOne("11");
        tempList.get(2).setOne("13");
        //存入内容并设置时间
        redisUtil.sSetAndTime("set", 1000, tempList.get(1), tempList.get(2));
        //获取所有set的内容
        Set<TestModel> set = redisUtil.sGet("set", TestModel.class);
        System.out.println("setClass："+set.toString());
        Set<Object> set1 = redisUtil.sGet("set");
        System.out.println("setObject："+set1.toString());
        //获取set集合的长度
        long setLong = redisUtil.sSize("set");
        System.out.println("set集合的长度:"+setLong);
        //移除指定的set的内容
        redisUtil.sRemove("set", tempList.get(3), tempList.get(4));
        List<TestModel> nList = new ArrayList(tempList.subList(10, 30));
        redisUtil.sBatchRemoveList("set", nList);

        /*
         * hash
         */
        //取hash表中的一个键值对数据
        String s = (String) redisUtil.hGet("hash", "name");
        System.out.println("单个值:" + s);
        //获取整张hash表
        Map hashMap = redisUtil.hAllGet("hash");
        System.out.println("整个表：" + hashMap.toString());
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhang");
        map.put("age", 20);
        map.put("age1", 20);
        //存入整个map
        redisUtil.hBatchSet("hash", map);
        s = (String) redisUtil.hGet("hash", "name");
        System.out.println("存入后的单个值:" + s);
        //存入整个map并设置时间
        redisUtil.hBatchSet("hash", map, 1000);
        //向一个hash表存入一个键值对数据
        redisUtil.hSet("hash", "phone", "13800013800");
        //向一个hash表存入一个键值对数据及时间
        redisUtil.hSet("hash", "phone2", "13900139000", 1200);
        //查询是否有键值对
        redisUtil.hHasKey("hash", "name");
        //删除知道的键值对
        redisUtil.hDel("hash", "name", "phone");
        //查询是否有键值对
        redisUtil.hHasKey("hash", "name");
        //递增
        redisUtil.hIncr("hash", "age", 2.2);
        //递减
        redisUtil.hDecr("hash", "age1", 2);

        /*
         * String
         */
        //存入
        redisUtil.set("str", 100);
        redisUtil.set("str1", 1000, 1000);
        redisUtil.set("str2", "zxc", 1000);

        //增加并返回结果
        long str = redisUtil.incr("str", 10);
        //减少并返回结果
        long str1 = redisUtil.decr("str1", 10);
        //获取值
        String str2 = redisUtil.get("str2");
        System.out.println("String："+str + "|" + str1 + "|" + str2);

        //剩余存活时间
        long expire = redisUtil.getExpire("str1");
        System.out.println("存活时间：" + expire);
        //模糊查询key
        redisUtil.set("str:123456", "asf");
        redisUtil.set("str:123457", "asf");
        List<String> keysList = redisUtil.keys("str");
        System.out.println(keysList.toString());
        //查询key是否存在
        boolean ex = redisUtil.hasKey("str10");
        boolean ex1 = redisUtil.hasKey("str1");
        System.out.println("key是否存在：" + ex + "|" + ex1);
        //删除key
        redisUtil.del("str", "str1");
        List<String> k = new ArrayList<>(10);
        k.add("str2");
        k.add("str1");
        redisUtil.delList(k);
    }


}
