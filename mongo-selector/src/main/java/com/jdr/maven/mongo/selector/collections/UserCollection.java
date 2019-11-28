package com.jdr.maven.mongo.selector.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhoude
 * @date 2019/11/28 11:21
 */
@Data
@Document(collection = "users")
public class UserCollection {

    private String username;
    private AddressBean address;
    private FavoritesBean favorites;
    private Integer age;
    private BigDecimal salary;
    private Double length;
    private List<CommentsBean> comments;

    @Data
    public static class AddressBean {
        private String aCode;
        private String add;
    }

    @Data
    public static class FavoritesBean {
        private List<String> movies;
        private List<String> cites;
    }

    @Data
    public static class CommentsBean {
        private String movies;
        private String content;
        private Date commentTime;
    }
}
