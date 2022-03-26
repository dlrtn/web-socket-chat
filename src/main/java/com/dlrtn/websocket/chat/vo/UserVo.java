package com.dlrtn.websocket.chat.vo;

import lombok.Data;

@Data //@Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode
public class UserVo {
    private int userNO;
    private String userId;
    private String userPw;
    private String userName;
    private String userAuth;
    private String appendDate;
    private String updateDate;
}