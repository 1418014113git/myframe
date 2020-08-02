-- ----------------------------
-- Table structure for db_operator
-- ----------------------------
CREATE TABLE IF NOT EXISTS `db_operator` (
  `statement` varchar(64) NOT NULL COMMENT '操作别名',
  `opt_type` varchar(10) NOT NULL COMMENT '操作数据库类型  RMDB MONGO',
  `opt_statement_type` varchar(32) NOT NULL COMMENT '操作方式类型',
  `opt_target` varchar(32) DEFAULT NULL COMMENT '操作对象',
  `opt_value` varchar(1024) NOT NULL COMMENT '操作语句',
  `opt_pk` varchar(32) DEFAULT NULL,
  `before_opt` varchar(32) DEFAULT NULL,
  `after_opt` varchar(32) DEFAULT NULL,
  `opt_desc` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`statement`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;;

ALTER TABLE db_operator ADD opt_value_type varchar(1) DEFAULT '0' COMMENT '操作语句类型 0:SQL 1:存储过程';;
ALTER TABLE db_operator ADD opt_rules varchar(1024) DEFAULT NULL COMMENT '校验规则';;

-- ----------------------------
-- Table structure for db_sequence
-- ----------------------------
CREATE TABLE IF NOT EXISTS `db_sequence` (
  `seq_name` varchar(64) NOT NULL COMMENT '序列名',
  `tab_name` varchar(64) DEFAULT NULL COMMENT '表名',
  `init_value` int(11) DEFAULT NULL COMMENT '初始值',
  `increase_value` int(11) DEFAULT NULL COMMENT '增加值',
  `sequence_id` int(11) DEFAULT NULL COMMENT '当前值',
  `last_id` varchar(64) DEFAULT NULL COMMENT '修改人',
  `last_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表id信息表';;