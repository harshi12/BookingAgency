INSERT INTO slot (id, date, start_hour, end_hour) VALUES
('2024-01-01_0_1', '2024-01-01', 0, 1),
('2024-01-01_1_2', '2024-01-01', 1, 2),
('2024-01-01_2_3', '2024-01-01', 2, 3),
('2024-01-01_3_4', '2024-01-01', 3, 4),
('2024-01-01_4_5', '2024-01-01', 4, 5),
('2024-01-01_5_6', '2024-01-01', 5, 6),
('2024-01-01_6_7', '2024-01-01', 6, 7),
('2024-01-01_7_8', '2024-01-01', 7, 8),
('2024-01-01_8_9', '2024-01-01', 8, 9),
('2024-01-01_9_10', '2024-01-01', 9, 10),
('2024-01-01_10_11', '2024-01-01', 10, 11),
('2024-01-01_11_12', '2024-01-01', 11, 12),
('2024-01-01_12_13', '2024-01-01', 12, 13),
('2024-01-01_13_14', '2024-01-01', 13, 14),
('2024-01-01_14_15', '2024-01-01', 14, 15),
('2024-01-01_15_16', '2024-01-01', 15, 16),
('2024-01-01_16_17', '2024-01-01', 16, 17),
('2024-01-01_17_18', '2024-01-01', 17, 18),
('2024-01-01_18_19', '2024-01-01', 18, 19),
('2024-01-01_19_20', '2024-01-01', 19, 20),
('2024-01-01_20_21', '2024-01-01', 20, 21),
('2024-01-01_21_22', '2024-01-01', 21, 22),
('2024-01-01_22_23', '2024-01-01', 22, 23),
('2024-01-01_23_24', '2024-01-01', 23, 24);

insert into service_operator(id, location, name, status, timezone) values(1, 'India', 'ServiceOperator1', 'ENABLED', 'Asia/Kolkata');
insert into service_operator(id, location, name, status, timezone) values(2, 'India', 'ServiceOperator2', 'ENABLED', 'Asia/Kolkata');
insert into service_operator(id, location, name, status, timezone) values(3, 'India', 'ServiceOperator3', 'ENABLED', 'Asia/Kolkata');

insert into customer(id, name, phone) values(1, 'Customer1', '1234567890');