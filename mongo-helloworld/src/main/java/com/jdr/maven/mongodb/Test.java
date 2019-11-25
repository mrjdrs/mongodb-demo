package com.jdr.maven.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.MessageFormat;

/**
 * @author zhoude
 * @date 2019/11/25 14:09
 */
public class Test {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("192.168.233.128", 27017);
        MongoDatabase db = client.getDatabase("local");
        MongoCollection<Document> user = db.getCollection("user");
        for (Document next : user.find()) {
            Object name = next.get("name");
            Object age = next.get("age");
            System.out.println(MessageFormat.format("name={0}, age={1}", name.toString(), age.toString()));
        }
    }
}
