package com.jdr.maven.mongo.selector.collections;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author zhoude
 * @date 2019/11/28 11:21
 */
@Data
@Document(collection = "partner")
public class PartnerCollection {

    @Id
    private ObjectId id;
    private String name;
    @DBRef
    private List<UserCollection> users;
}
