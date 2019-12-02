package com.jdr.maven.mongo.selector;

import com.alibaba.fastjson.JSONObject;
import com.jdr.maven.mongo.selector.collections.PartnerCollection;
import com.jdr.maven.mongo.selector.collections.UserCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * 基本查询
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoBaseFindTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * db.users.find({'favorites.movies':'无双'})
     */
    @Test
    public void find1() {
        Query query = new Query(Criteria.where("favorites.movies").is("无双"));
        List<UserCollection> userCollections = mongoTemplate.find(query, UserCollection.class);
        System.err.println(JSONObject.toJSONString(userCollections));
    }

    /**
     * db.users.find({'favorites.movies':['无双','钢铁侠','蝙蝠侠']},{'favorites.movies':1})
     */
    @Test
    public void find2() {
        Query query = new Query(Criteria.where("favorites.movies").is(Arrays.asList("无双", "钢铁侠", "蝙蝠侠")));
        query.fields().include("favorites.movies");
        List<UserCollection> userCollections = mongoTemplate.find(query, UserCollection.class);
        System.err.println(JSONObject.toJSONString(userCollections));
    }

    /**
     * db.users.find({'favorites.movies':{'$all':['阿凡达','战狼']}},{'favorites.movies':1})
     */
    @Test
    public void find3() {
        Query query = new Query(Criteria.where("favorites.movies").all(Arrays.asList("阿凡达", "战狼")));
        query.fields().include("favorites.movies");
        List<UserCollection> userCollections = mongoTemplate.find(query, UserCollection.class);
        System.err.println(JSONObject.toJSONString(userCollections));
    }

    /**
     * db.users.find({},{"favorites.movies":{"$slice":[1,2]},"favorites":1})
     */
    @Test
    public void find4() {
        Query query = new Query();
        query.fields().slice("favorites.movies", 1, 2).include("favorites");
        List<UserCollection> userCollections = mongoTemplate.find(query, UserCollection.class);
        System.err.println(JSONObject.toJSONString(userCollections));
    }

    /**
     * db.users.find({"favorites.cites":{$in:["东莞","东京"]}})
     */
    @Test
    public void find5() {
        Query query = new Query(Criteria.where("favorites.cites").in(Arrays.asList("东莞", "东京")));
        List<UserCollection> userCollections = mongoTemplate.find(query, UserCollection.class);
        System.err.println(JSONObject.toJSONString(userCollections));
    }

    /**
     * db.users.find({"comments":{"$elemMatch":{"movies":"倩女幽魂","content":"评论3"}}}).pretty()
     */
    @Test
    public void find6() {
        Query query = new Query(Criteria.where("comments")
                .elemMatch(Criteria.where("movies").is("倩女幽魂").and("content").is("评论3")));
        List<UserCollection> userCollections = mongoTemplate.find(query, UserCollection.class);
        System.err.println(JSONObject.toJSONString(userCollections));
    }

    /**
     * sort()
     * skip()
     * limit()
     */
    @Test
    public void find7() {
        Sort sort = new Sort(Sort.Direction.DESC, "length");
        Query query = new Query().with(sort).skip(1).limit(3);
        List<UserCollection> userCollections = mongoTemplate.find(query, UserCollection.class);
        System.err.println(JSONObject.toJSONString(userCollections));
    }

    /**
     * DBRef
     */
    @Test
    public void find8() {
        Query query = new Query(Criteria.where("username").is("小五"));
        UserCollection userCollection = mongoTemplate.findOne(query, UserCollection.class);
        Query query2 = new Query(Criteria.where("username").is("路飞"));
        UserCollection userCollection2 = mongoTemplate.findOne(query2, UserCollection.class);
        System.err.println(JSONObject.toJSONString(userCollection));
        System.err.println(JSONObject.toJSONString(userCollection2));

        PartnerCollection partnerCollection = new PartnerCollection();
        partnerCollection.setName("jdr");
        partnerCollection.setUsers(Arrays.asList(userCollection, userCollection2));
        mongoTemplate.insert(partnerCollection);

        Query query3 = new Query(Criteria.where("name").is("jdr"));
        List<PartnerCollection> partnerCollections = mongoTemplate.find(query3, PartnerCollection.class);
        partnerCollections.forEach(item -> System.err.println(JSONObject.toJSONString(item)));
    }
}
