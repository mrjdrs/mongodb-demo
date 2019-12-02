package com.jdr.maven.mongo.selector;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 聚合查询
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoAggregationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 统计不同住址的用户人数：
     * db.users.aggregate({
     * '$group':{'_id':'$address', 'number':{'$sum':1}}
     * })
     */
    @Test
    public void find1() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("address").count().as("number"));
        AggregationResults<Object> users = mongoTemplate.aggregate(aggregation, "users", Object.class);
        List<Object> result = users.getMappedResults();
        result.forEach(item -> System.err.println(JSONObject.toJSONString(item)));
    }

    /**
     * 分别拿到每个住址区域中升高最高的
     * db.users.aggregate({
     * '$group':{'_id':'$address', 'number':{'$max':'$length'}}
     * })
     */
    @Test
    public void find2() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("address").max("length").as("length"));
        AggregationResults<Object> users = mongoTemplate.aggregate(aggregation, "users", Object.class);
        List<Object> result = users.getMappedResults();
        result.forEach(item -> System.err.println(JSONObject.toJSONString(item)));
    }

    /**
     * 分组用户住址，并显示此区域下所有的用户姓名
     * db.users.aggregate({
     * '$group':{'_id':'$address', 'username':{'$push':'$username'}}
     * })
     */
    @Test
    public void find3() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("address").push("username").as("name"));
        AggregationResults<Object> users = mongoTemplate.aggregate(aggregation, "users", Object.class);
        List<Object> result = users.getMappedResults();
        result.forEach(item -> System.err.println(JSONObject.toJSONString(item)));
    }

    /**
     * 分组用户住址，并显示此区域下最后一名用户的姓名
     * db.users.aggregate({
     * '$group':{'_id':'$address', 'lastUsername':{'$last':'$username'}}
     * })
     */
    @Test
    public void find4() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("address").last("username").as("name"));
        AggregationResults<Object> users = mongoTemplate.aggregate(aggregation, "users", Object.class);
        List<Object> result = users.getMappedResults();
        result.forEach(item -> System.err.println(JSONObject.toJSONString(item)));
    }

    /**
     * 获取每个用户的年龄及身高
     * db.users.aggregate({
     * '$project':{'age':1, 'length':1}
     * })
     */
    @Test
    public void find5() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.project("age", "length"));
        AggregationResults<Object> users = mongoTemplate.aggregate(aggregation, "users", Object.class);
        List<Object> result = users.getMappedResults();
        result.forEach(item -> System.err.println(JSONObject.toJSONString(item)));
    }

    /**
     * 获取升高大于1.8的用户姓名及身高
     * db.users.aggregate(
     * {'$match':{'length':{'$gt':1.8}}},
     * {'$project':{'username':1, 'length':1}}
     * )
     */
    @Test
    public void find6() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("length").gt(1.8)),
                Aggregation.project("username", "length"));
        AggregationResults<Object> users = mongoTemplate.aggregate(aggregation, "users", Object.class);
        List<Object> result = users.getMappedResults();
        result.forEach(item -> System.err.println(JSONObject.toJSONString(item)));
    }

    /**
     * 获取身高第二和第三高的用户姓名
     * db.users.aggregate(
     * {'$sort':{'length':-1}},
     * {'$skip':1},
     * {'$limit':2},
     * {'$project':{'username':1}}
     * )
     */
    @Test
    public void find7() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sort(Sort.Direction.DESC, "length"),
                Aggregation.skip(1L),
                Aggregation.limit(2L),
                Aggregation.project("username"));
        AggregationResults<Object> users = mongoTemplate.aggregate(aggregation, "users", Object.class);
        List<Object> result = users.getMappedResults();
        result.forEach(item -> System.err.println(JSONObject.toJSONString(item)));
    }

    /**
     * 获取被评论次数最多的电影
     * db.users.aggregate(
     * {'$unwind':'$comments'},
     * {'$group':{'_id':'$comments.movies', 'count':{'$sum':1}}},
     * {'$sort':{'count':-1}},
     * {'$limit':1},
     * {'$project':{'_id':1}}
     * )
     */
    @Test
    public void find8() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("comments"),
                Aggregation.group("comments.movies").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC, "count"),
                Aggregation.limit(1L),
                Aggregation.project("_id"));
        AggregationResults<Object> users = mongoTemplate.aggregate(aggregation, "users", Object.class);
        List<Object> result = users.getMappedResults();
        result.forEach(item -> System.err.println(JSONObject.toJSONString(item)));
    }
}
