--创建fullstackdemo Schema

CREATE TABLE fullstackdemo.tuser (
  `ID` varchar(45) NOT NULL,
  `CREATEDATETIME` timestamp NULL DEFAULT NULL,
  `MODIFYDATETIME` timestamp NULL DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `PWD` varchar(45) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

INSERT INTO fullstackdemo.tuser VALUES ('1',NULL,NULL,'张三','zhangsanpassword',NULL,NULL),('2',NULL,NULL,'李四','lisipassword',NULL,NULL);

CREATE TABLE fullstackdemo.trole (
  `ID` varchar(45) NOT NULL,
  `TEXT` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

INSERT INTO fullstackdemo.trole VALUES ('1','公司领导'),('2','部门领导');


CREATE TABLE fullstackdemo.tuser_trole (
  `ID` varchar(45) NOT NULL,
  `ROLE_ID` varchar(45) DEFAULT NULL,
  `USER_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

INSERT INTO fullstackdemo.tuser_trole VALUES ('1','1','1'),('2','1','2'),('3','2','2');

