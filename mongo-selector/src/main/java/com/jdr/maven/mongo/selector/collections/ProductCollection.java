package com.jdr.maven.mongo.selector.collections;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author zhoude
 * @date 2019/12/2 15:58
 */
@Data
@Document(collection = "product")
public class ProductCollection {

    @Id
    private ObjectId id;
    private String name;
    private int price;
    private String category;
    private AuthorBean author;
    private List<String> tags;

    @Data
    public static class AuthorBean {
        private String name;
        private String from;

    }
}
