-- 数据库初始化脚本
drop database if exists seckill;
create database seckill;

use seckill;

drop table if exists seckill;
create table seckill (
`seckill_id` bigint not null auto_increment comment '商品库存id',
`name` varchar(120) not null comment '商品名称',
`number` int not null comment '库存数量',
`start_time` timestamp not null comment '秒杀开启时间',
`end_time` timestamp not null comment '秒杀结束时间',
`create_time` timestamp not null default current_timestamp comment '秒杀创建时间',
primary key(seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
) engine=InnoDB auto_increment=1000 default charset=utf8mb4 comment '秒杀库存表';

-- 初始化数据
insert into
    seckill (name, number, start_time, end_time)
values
    ('500rmb秒杀iphone6s', 100, '2021-05-07 00:00:00', '2021-05-08 00:00:00'),
    ('100rmb秒杀xiaomi', 200, '2021-05-08 00:00:00', '2021-05-08 00:01:00'),
    ('1000rmb秒杀大疆', 300, '2021-05-08 00:00:00', '2021-05-08 01:00:00'),
    ('5000rmb秒杀macbook pro', 10, '2021-05-08 00:00:00', '2021-05-08 02:00:00');

-- 秒杀成功明细表
drop table if exists success_killed;
create table success_killed(
`seckill_id` bigint not null comment '秒杀商品id',
`user_phone` bigint not null comment '用户手机号',
`state` tinyint not null default -1 comment '秒杀状态：-1：无效 0：成功 1：已付款 2：已发货 3：已收货',
`create_time` timestamp not null default current_timestamp comment '创建时间',
primary key(seckill_id, user_phone),
key idx_create_time(create_time)
) engine=InnoDB auto_increment=1000 default charset=utf8mb4 comment '秒杀成功明细表';