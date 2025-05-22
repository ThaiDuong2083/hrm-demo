-- Nhóm phụ cấp cấp 1 (cha)
INSERT INTO group_allowance (code, name, parent_id, description, is_active, created_by)
VALUES
    ('nhom-phu-cap-001', 'Phụ cấp cơ bản', NULL, 'Nhóm phụ cấp cơ bản', TRUE, 1),
    ('nhom-phu-cap-002', 'Phụ cấp theo vị trí', NULL, 'Nhóm phụ cấp theo vị trí làm việc', TRUE, 1),
    ('nhom-phu-cap-003', 'Phụ cấp đặc biệt', NULL, 'Nhóm phụ cấp dành cho các trường hợp đặc biệt', TRUE, 1);

-- Nhóm phụ cấp cấp 2 (con của nhom-phu-cap-001)
INSERT INTO group_allowance (code, name, parent_id, description, is_active, created_by)
VALUES
    ('nhom-phu-cap-001-1', 'Phụ cấp xăng xe', 1, 'Phụ cấp xăng xe hàng tháng', TRUE, 1),
    ('nhom-phu-cap-001-2', 'Phụ cấp điện thoại', 1, 'Phụ cấp chi phí điện thoại', TRUE, 1);

-- Nhóm phụ cấp cấp 2 (con của nhom-phu-cap-002)
INSERT INTO group_allowance (code, name, parent_id, description, is_active, created_by)
VALUES
    ('nhom-phu-cap-002-1', 'Phụ cấp vị trí xa', 2, 'Phụ cấp dành cho nhân viên làm việc ở vùng xa', TRUE, 1),
    ('nhom-phu-cap-002-2', 'Phụ cấp nguy hiểm', 2, 'Phụ cấp cho vị trí có rủi ro cao', TRUE, 1);

-- Nhóm phụ cấp cấp 2 (con của nhom-phu-cap-003)
INSERT INTO group_allowance (code, name, parent_id, description, is_active, created_by)
VALUES
    ('nhom-phu-cap-003-1', 'Phụ cấp dự án đặc biệt', 3, 'Phụ cấp cho dự án đặc biệt', TRUE, 1),
    ('nhom-phu-cap-003-2', 'Phụ cấp khẩn cấp', 3, 'Phụ cấp hỗ trợ tình huống khẩn cấp', TRUE, 1);


-- Nhóm thưởng cấp 1 (cha)
INSERT INTO group_reward (code, name, parent_id, description, created_by)
VALUES
    ('nhom-thuong-001', 'Thưởng hiệu suất', NULL, 'Nhóm thưởng theo hiệu suất làm việc', 1),
    ('nhom-thuong-002', 'Thưởng lễ Tết', NULL, 'Nhóm thưởng theo dịp lễ, Tết', 1),
    ('nhom-thuong-003', 'Thưởng đột xuất', NULL, 'Thưởng cho các trường hợp đặc biệt hoặc đột xuất', 1);

-- Giả định ID của các nhóm cha là 1, 2, 3 sau khi insert — hãy điều chỉnh nếu cần dựa vào DB thực tế.

-- Nhóm thưởng con của 'nhom-thuong-001'
INSERT INTO group_reward (code, name, parent_id, description)
VALUES
    ('nhom-thuong-001-1', 'Thưởng tháng', 1, 'Thưởng hiệu suất hàng tháng'),
    ('nhom-thuong-001-2', 'Thưởng quý', 1, 'Thưởng hiệu suất theo quý');

-- Nhóm thưởng con của 'nhom-thuong-002'
INSERT INTO group_reward (code, name, parent_id, description)
VALUES
    ('nhom-thuong-002-1', 'Thưởng Tết dương lịch', 2, 'Thưởng dịp Tết dương lịch'),
    ('nhom-thuong-002-2', 'Thưởng Tết âm lịch', 2, 'Thưởng dịp Tết âm lịch');

-- Nhóm thưởng con của 'nhom-thuong-003'
INSERT INTO group_reward (code, name, parent_id, description)
VALUES
    ('nhom-thuong-003-1', 'Thưởng sáng kiến', 3, 'Thưởng cho các sáng kiến cải tiến'),
    ('nhom-thuong-003-2', 'Thưởng hoàn thành dự án', 3, 'Thưởng khi hoàn thành dự án lớn');
