package com.jdr.maven.mongo.selector;

import com.jdr.maven.mongo.selector.collections.ProductCollection;
import com.jdr.maven.mongo.selector.collections.UserCollection;
import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 修改
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoUpdateTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 将java编程思想这本书籍价格加10
     * db.product.update(
     * {'name':'java编程思想'},
     * {'$inc':{'price':10}}
     * )
     */
    @Test
    public void update1() {
        Query query = new Query(Criteria.where("name").is("java编程思想"));
        Update update = new Update().inc("price", 10);
        mongoTemplate.updateFirst(query, update, ProductCollection.class);
    }

    /**
     * 更新java编程思想这本书名为java编程思想第四版
     * db.product.update(
     * {'name':'java编程思想'},
     * {'$set':{'name':'java编程思想第四版'}}
     * )
     */
    @Test
    public void update2() {
        Query query = new Query(Criteria.where("name").is("java编程思想"));
        Update update = new Update().set("name", "java编程思想第四版");
        mongoTemplate.updateFirst(query, update, ProductCollection.class);
    }

    /**
     * 将姓名为路飞，评论电影为战狼的那条数据修改评论为战狼电影的评论
     * db.users.update(
     * {'username':'路飞', 'comments.movies':'战狼'},
     * {'$set':{'comments.$.content':'战狼电影的评论'}}
     * )
     */
    @Test
    public void update3() {
        Query query = new Query(Criteria.where("username").is("路飞").and("comments.movies").is("战狼"));
        Update update = new Update().set("comments.$.content", "战狼电影的评论");
        mongoTemplate.updateFirst(query, update, UserCollection.class);
    }

    /**
     * 为书籍java编程思想第四版添加两个标签值
     * db.product.update(
     * {'name':'java编程思想第四版'},
     * {'$push':{'tags':{'$each':['标签1', '标签2']}}}
     * )
     */
    @Test
    public void update4() {
        Query query = new Query(Criteria.where("name").is("java编程思想第四版"));
        Update update = new Update().push("tags").each("标签1", "标签2");
        mongoTemplate.updateFirst(query, update, ProductCollection.class);
    }
}
