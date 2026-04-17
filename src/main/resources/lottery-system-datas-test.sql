USE `lottery_system`;

-- 1. 插入用户数据 (管理员 + 普通参与者)
INSERT INTO `user` (`user_name`, `email`, `phone_number`, `password`, `identity`) VALUES
                                                                                      ('张三', 'zhangsan@example.com', '13800138001', 'e10adc3949ba59abbe56e057f20f883e', 'ADMIN'),
                                                                                      ('李四', 'lisi@example.com', '13800138002', 'e10adc3949ba59abbe56e057f20f883e', 'USER'),
                                                                                      ('王五', 'wangwu@example.com', '13800138003', 'e10adc3949ba59abbe56e057f20f883e', 'USER'),
                                                                                      ('赵六', 'zhaoliu@example.com', '13800138004', 'e10adc3949ba59abbe56e057f20f883e', 'USER');

-- 2. 插入奖品池
INSERT INTO `prize` (`name`, `description`, `price`, `image_url`) VALUES
                                                                      ('iPhone 15 Pro', '256GB 钛金属', 8999.00, 'https://example.com/iphone.jpg'),
                                                                      ('华为 Mate 60', '雅丹黛', 6999.00, 'https://example.com/mate60.jpg'),
                                                                      ('100元京东卡', '电子券码', 100.00, 'https://example.com/jd_card.jpg'),
                                                                      ('参与奖-谢谢惠顾', '下次好运', 0.00, NULL);

-- 3. 创建抽奖活动
INSERT INTO `activity` (`activity_name`, `description`, `status`) VALUES
                                                                      ('2024春季大促抽奖', '年度回馈活动', 'OPEN'),
                                                                      ('双11狂欢预热', '提前锁定优惠', 'DRAFT');

-- 4. 为活动配置奖品 (假设活动ID为1)
-- 对应活动ID 1，关联不同奖品和等级
INSERT INTO `activity_prize` (`activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES
                                                                                                      (1, 1, 1, '一等奖', 'ACTIVE'),
                                                                                                      (1, 2, 2, '二等奖', 'ACTIVE'),
                                                                                                      (1, 3, 50, '三等奖', 'ACTIVE');

-- 5. 圈选参与用户 (假设活动ID为1)
INSERT INTO `activity_user` (`activity_id`, `user_id`, `user_name`, `status`) VALUES
                                                                                  (1, 2, '李四', 'QUALIFIED'),
                                                                                  (1, 3, '王五', 'QUALIFIED'),
                                                                                  (1, 4, '赵六', 'QUALIFIED');