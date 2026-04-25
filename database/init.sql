USE nursing_home;

SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM notice;
DELETE FROM payment_record;
DELETE FROM payment;
DELETE FROM nursing_record;
DELETE FROM health_record;
DELETE FROM bed;
DELETE FROM room_bed;
DELETE FROM room;
DELETE FROM staff_info;
DELETE FROM staff;
DELETE FROM elderly;
DELETE FROM sys_user;

ALTER TABLE notice AUTO_INCREMENT = 1;
ALTER TABLE payment_record AUTO_INCREMENT = 1;
ALTER TABLE payment AUTO_INCREMENT = 1;
ALTER TABLE nursing_record AUTO_INCREMENT = 1;
ALTER TABLE health_record AUTO_INCREMENT = 1;
ALTER TABLE bed AUTO_INCREMENT = 1;
ALTER TABLE room_bed AUTO_INCREMENT = 1;
ALTER TABLE room AUTO_INCREMENT = 1;
ALTER TABLE staff_info AUTO_INCREMENT = 1;
ALTER TABLE staff AUTO_INCREMENT = 1;
ALTER TABLE elderly AUTO_INCREMENT = 1;
ALTER TABLE sys_user AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO sys_user (id, username, password, real_name, role, phone, status) VALUES
(1, 'admin', '123456', '系统管理员', 'admin', '13800000000', 1);

INSERT INTO elderly (id, name, gender, age, id_card, phone, family_phone, address, check_in_date, status, remark) VALUES
(1, '张建国', '男', 78, '110101194801012311', '13900010001', '13910010001', '北京市朝阳区幸福小区', '2025-03-12', '在住', '高血压，生活基本自理'),
(2, '李秀兰', '女', 82, '110101194403152322', '13900010002', '13910010002', '北京市海淀区安康路', '2025-05-20', '在住', '需关注血糖'),
(3, '王德福', '男', 75, '110101195001203333', '13900010003', '13910010003', '北京市丰台区康乐街', '2025-08-01', '在住', '康复训练中'),
(4, '赵桂英', '女', 80, '110101194608183344', '13900010004', '13910010004', '北京市西城区平安里', '2025-09-10', '在住', '饮食清淡');

INSERT INTO staff (id, name, gender, age, phone, position, entry_date, status, remark) VALUES
(1, '刘敏', '女', 32, '13820010001', '护士', '2024-02-01', '在职', '护理经验丰富'),
(2, '陈强', '男', 38, '13820010002', '护理员', '2023-09-15', '在职', '负责康复护理');

INSERT INTO staff_info (id, name, gender, age, phone, position, department, hire_date, status, remark) VALUES
(1, '刘敏', '女', 32, '13820010001', '护士', '护理部', '2024-02-01', '在职', '负责日常护理和健康记录'),
(2, '陈强', '男', 38, '13820010002', '护理员', '护理部', '2023-09-15', '在职', '负责康复护理'),
(3, '周丽', '女', 29, '13820010003', '财务', '财务部', '2024-06-10', '在职', '负责缴费登记'),
(4, '孙伟', '男', 41, '13820010004', '医生', '医务室', '2022-11-01', '在职', '负责健康检查');

INSERT INTO room (id, room_no, floor, room_type, bed_count, status, remark) VALUES
(1, '101', 1, '双人间', 2, '正常', '近护士站'),
(2, '102', 1, '双人间', 2, '正常', '采光较好');

INSERT INTO bed (id, room_id, bed_no, elderly_id, status) VALUES
(1, 1, 'A', 1, '已入住'),
(2, 1, 'B', 2, '已入住'),
(3, 2, 'A', 3, '已入住'),
(4, 2, 'B', NULL, '空闲');

INSERT INTO room_bed (id, building_no, room_no, bed_no, bed_type, status, elderly_id, elderly_name, price, remark) VALUES
(1, '1号楼', '101', 'A', '普通床位', '已入住', 1, '张建国', 1800.00, '近窗床位'),
(2, '1号楼', '101', 'B', '护理床位', '已入住', 2, '李秀兰', 2200.00, '护理床位'),
(3, '1号楼', '102', 'A', '普通床位', '已入住', 3, '王德福', 1800.00, '康复护理区'),
(4, '1号楼', '102', 'B', '普通床位', '空闲', NULL, NULL, 1800.00, '可安排入住'),
(5, '2号楼', '201', 'A', '护理床位', '维修中', NULL, NULL, 2200.00, '床位检修');

INSERT INTO health_record (id, elderly_id, elderly_name, gender, age, blood_pressure, blood_sugar, heart_rate, weight, medical_history, allergy_history, medication, health_status, check_date, doctor_name, remark) VALUES
(1, 1, '张建国', '男', 78, '135/85', '5.8mmol/L', 78, 66.50, '高血压', '无', '每日服用降压药', '观察中', '2026-04-20', '孙伟', '血压略高，继续观察'),
(2, 2, '李秀兰', '女', 82, '128/78', '8.2mmol/L', 82, 58.20, '糖尿病', '青霉素过敏', '降糖药', '需重点关注', '2026-04-21', '孙伟', '血糖偏高，控制饮食'),
(3, 3, '王德福', '男', 75, '120/76', '5.4mmol/L', 74, 70.10, '腿部术后康复', '无', '康复辅助用药', '良好', '2026-04-22', '孙伟', '恢复情况良好'),
(4, 4, '赵桂英', '女', 80, '125/80', '5.7mmol/L', 76, 60.00, '轻度胃炎', '无', '胃药', '良好', '2026-04-23', '孙伟', '饮食规律');

INSERT INTO nursing_record (id, elderly_id, elderly_name, staff_id, staff_name, nursing_type, nursing_content, nursing_result, nursing_date, nursing_time, status, remark) VALUES
(1, 1, '张建国', 1, '刘敏', '日常护理', '协助整理床铺，测量血压', '血压记录完成，状态稳定', CURDATE(), '09:00:00', '正常', '上午护理'),
(2, 2, '李秀兰', 1, '刘敏', '用药提醒', '提醒按时服用降糖药', '已按医嘱服药', CURDATE(), '10:30:00', '需跟进', '晚餐后复测血糖'),
(3, 3, '王德福', 2, '陈强', '康复护理', '协助完成腿部康复训练', '训练完成，无不适', CURDATE(), '15:00:00', '正常', '康复训练30分钟'),
(4, 4, '赵桂英', 1, '刘敏', '饮食护理', '记录午餐进食情况', '进食正常', DATE_SUB(CURDATE(), INTERVAL 1 DAY), '12:20:00', '正常', '少油少盐');

INSERT INTO payment_record (id, elderly_id, elderly_name, fee_type, amount, paid_amount, payment_status, payment_method, payment_date, billing_month, operator_name, remark) VALUES
(1, 1, '张建国', '床位费', 1800.00, 1800.00, '已缴费', '微信', '2026-04-05', '2026-04', '周丽', '4月床位费'),
(2, 2, '李秀兰', '护理费', 1200.00, 600.00, '部分缴费', '现金', '2026-04-06', '2026-04', '周丽', '剩余费用待补缴'),
(3, 3, '王德福', '餐饮费', 900.00, 0.00, '未缴费', NULL, NULL, '2026-04', '周丽', '待家属缴费'),
(4, 4, '赵桂英', '医疗费', 320.00, 320.00, '已缴费', '支付宝', '2026-04-10', '2026-04', '周丽', '药品费用');

INSERT INTO notice (id, title, content, notice_type, publish_status, publisher, publish_time, sort_order, remark) VALUES
(1, '五一文娱活动通知', '养老院将于五一期间组织文娱活动，请护理人员提前协助老人报名。', '活动通知', '已发布', '系统管理员', '2026-04-20 09:00:00', 10, '首页展示'),
(2, '春季健康检查安排', '本周将进行春季健康检查，请各护理人员通知老人按时参加。', '护理提醒', '已发布', '系统管理员', '2026-04-18 10:00:00', 8, '健康检查'),
(3, '4月缴费提醒', '请未完成4月缴费的家属及时办理缴费手续。', '缴费通知', '已发布', '系统管理员', '2026-04-15 14:30:00', 6, '缴费提醒'),
(4, '系统维护公告', '系统将于周日晚进行例行维护，维护期间可能短暂无法访问。', '系统公告', '草稿', '系统管理员', NULL, 1, '暂未发布');
