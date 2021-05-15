package com.me.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class User {

    private Integer userId;
    private String username;
    private String password;
    private int accountId;
    // 1 -> *
    private Set<Trade> trades = new LinkedHashSet<Trade>();

    public User(Integer userId, String username, int accountId) {
        super();
        this.userId = userId;
        this.username = username;
        this.accountId = accountId;
    }


}
