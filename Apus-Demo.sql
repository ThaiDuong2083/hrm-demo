create database hrm;
use hrm;

CREATE TABLE `group_allowance` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `code` varchar(20) UNIQUE NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint COMMENT 'ID nhóm cha nếu có',
  `description` text,
  `is_active` boolean NOT NULL,
  `created_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP),
  `updated_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP),
  `created_by` bigint,
  `updated_by` bigint
);

CREATE TABLE `allowance` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `code` varchar(20) UNIQUE NOT NULL,
  `name` varchar(255) NOT NULL,
  `include_type` varchar(50) NOT NULL COMMENT 'Tax/Insurance',
  `type` varchar(50) NOT NULL,
  `uom_id` bigint,
  `currency_id` bigint,
  `group_allowance_id` bigint NOT NULL,
  `description` text,
  `is_active` boolean NOT NULL,
  `created_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP),
  `updated_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP),
  `created_by` bigint,
  `updated_by` bigint
);

CREATE TABLE `allowance_policy` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `code` varchar(20) UNIQUE NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text,
  `type` varchar(50) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date,
  `applicable_type` varchar(50) NOT NULL COMMENT 'All/Department/Position/Employee',
  `state` varchar(50) NOT NULL,
  `created_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP),
  `updated_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP),
  `created_by` bigint,
  `updated_by` bigint
);

CREATE TABLE `allowance_policy_applicable_target` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `allowance_policy_id` bigint NOT NULL,
  `target_id` bigint
);

CREATE TABLE `allowance_policy_line` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `allowance_policy_id` bigint NOT NULL,
  `allowance_id` bigint NOT NULL,
  `cycle` varchar(50) NOT NULL,
  `amount` decimal(10,2) NOT NULL
);

CREATE TABLE `group_reward` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính, tự tăng',
  `code` varchar(20) UNIQUE NOT NULL COMMENT 'Mã nhóm thưởng',
  `name` varchar(255) NOT NULL COMMENT 'Tên nhóm thưởng',
  `parent_id` bigint COMMENT 'ID của nhóm thưởng cha nếu có',
  `description` text COMMENT 'Mô tả nhóm thưởng',
  `created_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP) COMMENT 'Ngày tạo',
  `updated_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP) COMMENT 'Ngày cập nhật',
  `created_by` bigint  COMMENT 'Người tạo',
  `updated_by` bigint COMMENT 'Người sửa cuối'
);

CREATE TABLE `reward` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính, tự tăng',
  `code` varchar(20) UNIQUE NOT NULL COMMENT 'Mã thưởng',
  `name` varchar(255) NOT NULL COMMENT 'Tên thưởng',
  `group_reward_id` bigint NOT NULL COMMENT 'FK đến nhóm thưởng',
  `include_type` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  `uom_id` bigint NOT NULL,
  `currency_id` bigint NOT NULL,
  `description` text COMMENT 'Mô tả phụ cấp',
  `created_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP) COMMENT 'Ngày tạo',
  `updated_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP) COMMENT 'Ngày cập nhật',
  `created_by` bigint NOT NULL COMMENT 'Người tạo',
  `updated_by` bigint COMMENT 'Người sửa cuối'
);

CREATE TABLE `reward_policy` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính, tự tăng',
  `code` varchar(20) UNIQUE NOT NULL COMMENT 'Mã chính sách',
  `name` varchar(255) NOT NULL COMMENT 'Tên chính sách',
  `description` text COMMENT 'Ghi chú chính sách',
  `type` varchar(50) NOT NULL,
  `start_date` date NOT NULL COMMENT 'Ngày áp dụng',
  `end_date` date COMMENT 'Ngày kết thúc',
  `state` varchar(50) NOT NULL,
  `applicable_type` varchar(50) NOT NULL COMMENT 'All/Department/Position/Employee',
  `created_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP) COMMENT 'Ngày tạo',
  `updated_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP) COMMENT 'Ngày cập nhật',
  `created_by` bigint NOT NULL COMMENT 'Người tạo',
  `updated_by` bigint COMMENT 'Người sửa cuối'
);

CREATE TABLE `reward_policy_applicable_target` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `reward_policy_id` bigint NOT NULL,
  `target_id` bigint
);

CREATE TABLE `reward_policy_line` (
  `id` int PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính, tự tăng',
  `reward_policy_id` bigint NOT NULL COMMENT 'FK liên kết với chính sách thưởng',
  `reward_id` bigint NOT NULL COMMENT 'FK liên kết với thưởng',
  `cycle` varchar(50) NOT NULL,
  `amount` decimal(10,2) NOT NULL
);

CREATE TABLE `payroll` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính, tự tăng',
  `employee_id` bigint NOT NULL COMMENT 'Id của nhân viên',
  `department_id` bigint NOT NULL COMMENT 'Id của phòng ban',
  `position_id` bigint NOT NULL COMMENT 'Id của chức vụ',
  `type` varchar(50) NOT NULL COMMENT 'Loại bảng lương',
  `cycle` varchar(50) NOT NULL COMMENT 'Chu kỳ trả',
  `start_date` date COMMENT 'Ngày bắt đầu tính lương',
  `total_allowance_amount` decimal(15,2),
  `note` text COMMENT 'Ghi chú (nếu có)',
  `created_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP) COMMENT 'Ngày tạo',
  `updated_at` timestamp(6) DEFAULT (CURRENT_TIMESTAMP) COMMENT 'Ngày cập nhật',
  `created_by` bigint NOT NULL COMMENT 'Người tạo',
  `updated_by` bigint COMMENT 'Người sửa cuối'
);

CREATE TABLE `payroll_line` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'Khóa chính, tự tăng',
  `payroll_id` bigint NOT NULL COMMENT 'FK liên kết với bảng ghi lương',
  `type` varchar(50) NOT NULL COMMENT "pbiet target",
  `group_target_id` bigint NOT NULL COMMENT 'Id của nhóm phụ cấp',
  `target_id` bigint NOT NULL COMMENT 'Id của phụ cấp',
  `amount_item` varchar(50) NOT NULL COMMENT 'Khoản cộng/Khoản trừ',
  `amount` decimal(15,2) NOT NULL DEFAULT 0 COMMENT 'Số tiền',
  `taxable_amount` decimal(15,2) NOT NULL DEFAULT 0 COMMENT 'Số tiền chịu thuế TNCN',
  `insurance_amount` decimal(15,2) NOT NULL DEFAULT 0 COMMENT 'Số tiền chịu BHXH'
);

ALTER TABLE `group_allowance` ADD FOREIGN KEY (`parent_id`) REFERENCES `group_allowance` (`id`);

ALTER TABLE `allowance` ADD FOREIGN KEY (`group_allowance_id`) REFERENCES `group_allowance` (`id`);

ALTER TABLE `allowance_policy_applicable_target` ADD FOREIGN KEY (`allowance_policy_id`) REFERENCES `allowance_policy` (`id`);

ALTER TABLE `allowance_policy_line` ADD FOREIGN KEY (`allowance_policy_id`) REFERENCES `allowance_policy` (`id`);

ALTER TABLE `allowance_policy_line` ADD FOREIGN KEY (`allowance_id`) REFERENCES `allowance` (`id`);

ALTER TABLE `group_reward` ADD FOREIGN KEY (`parent_id`) REFERENCES `group_reward` (`id`);

ALTER TABLE `reward` ADD FOREIGN KEY (`group_reward_id`) REFERENCES `group_reward` (`id`);

ALTER TABLE `reward_policy_applicable_target` ADD FOREIGN KEY (`reward_policy_id`) REFERENCES `reward_policy` (`id`);

ALTER TABLE `reward_policy_line` ADD FOREIGN KEY (`reward_policy_id`) REFERENCES `reward_policy` (`id`);

ALTER TABLE `reward_policy_line` ADD FOREIGN KEY (`reward_id`) REFERENCES `reward` (`id`);

ALTER TABLE `payroll_line` ADD FOREIGN KEY (`payroll_id`) REFERENCES `payroll` (`id`);
