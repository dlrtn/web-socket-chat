//create_user_table
CREATE TABLE `TB_USER` (
  `USER_NO` int(11) NOT NULL AUTO_INCREMENT COMMENT '사용자번호',
  `USER_ID` varchar(255) NOT NULL COMMENT '아이디',
  `PASSWORD` varchar(256) COMMENT '비밀번호',
  `REAL_NAME` varchar(255) NOT NULL COMMENT '사용자명',
  `AUTH_ROLE` varchar(255) NOT NULL COMMENT '유저권한',
  `CREATED_AT` varchar(255) COMMENT '생성날짜',
  `UPDATED_AT` varchar(255) COMMENT '수정날짜',
  PRIMARY KEY (`USER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
