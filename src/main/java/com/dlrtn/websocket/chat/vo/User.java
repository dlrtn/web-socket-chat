package com.dlrtn.websocket.chat.vo;

import lombok.Builder;
import lombok.Data;

@Builder
public class User {
    private int userNO;
    private String userId;
    private String userPw;
    private String userName;
    private String userAuth;
    private String appendDate;
    private String updateDate;

    private User(Builder builder) {
        this.userNO = builder.userNO;
        this.userId = builder.userId;
        this.userPw = builder.userPw;
        this.userName = builder.userName;
        this.userAuth = builder.userAuth;
        this.appendDate = builder.appendDate;
        this.updateDate = builder.updateDate;
    }

    public static Builder builder(){
        return new Builder();
    }

    protected static class Builder {
        private int userNO;
        private String userId;
        private String userPw;
        private String userName;
        private String userAuth;
        private String appendDate;
        private String updateDate;
    }
    User.Builder.builder();
}