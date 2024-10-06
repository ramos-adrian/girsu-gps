MERGE INTO truck (id, plate) KEY (id) VALUES (99, 'AA 123 BB');
MERGE INTO truck (id, plate) KEY (id) VALUES (100, 'CC 456 DD');
MERGE INTO truck (id, plate) KEY (id) VALUES (101, 'EE 789 FF');
MERGE INTO truck (id, plate) KEY (id) VALUES (102, 'GG 012 HH');
MERGE INTO truck (id, plate) KEY (id) VALUES (103, 'II 345 JJ');
MERGE INTO truck (id, plate) KEY (id) VALUES (104, 'KK 678 LL');
MERGE INTO truck (id, plate) KEY (id) VALUES (105, 'MM 901 NN');
MERGE INTO truck (id, plate) KEY (id) VALUES (106, 'OO 234 PP');
MERGE INTO truck (id, plate) KEY (id) VALUES (107, 'QQ 567 RR');
MERGE INTO truck (id, plate) KEY (id) VALUES (108, 'SS 890 TT');
MERGE INTO truck (id, plate) KEY (id) VALUES (109, 'UU 123 VV');
MERGE INTO truck (id, plate) KEY (id) VALUES (110, 'WW 456 XX');
MERGE INTO truck (id, plate) KEY (id) VALUES (111, 'YY 789 ZZ');
MERGE INTO truck (id, plate) KEY (id) VALUES (112, 'AB 012 CD');
MERGE INTO truck (id, plate) KEY (id) VALUES (113, 'EF 345 GH');
MERGE INTO truck (id, plate) KEY (id) VALUES (114, 'IJ 678 KL');
MERGE INTO truck (id, plate) KEY (id) VALUES (115, 'MN 901 OP');
MERGE INTO truck (id, plate) KEY (id) VALUES (116, 'QR 234 ST');
MERGE INTO truck (id, plate) KEY (id) VALUES (117, 'UV 567 WX');
MERGE INTO truck (id, plate) KEY (id) VALUES (118, 'YZ 890 AB');
MERGE INTO truck (id, plate) KEY (id) VALUES (119, 'CD 123 EF');
MERGE INTO truck (id, plate) KEY (id) VALUES (120, 'GH 456 IJ');
MERGE INTO truck (id, plate) KEY (id) VALUES (121, 'KL 789 MN');
MERGE INTO truck (id, plate) KEY (id) VALUES (122, 'OP 012 QR');
MERGE INTO truck (id, plate) KEY (id) VALUES (123, 'ST 345 UV');
MERGE INTO truck (id, plate) KEY (id) VALUES (124, 'WX 678 YZ');
MERGE INTO truck (id, plate) KEY (id) VALUES (125, 'AA 901 BB');
MERGE INTO truck (id, plate) KEY (id) VALUES (126, 'CC 234 DD');
MERGE INTO truck (id, plate) KEY (id) VALUES (127, 'EE 567 FF');
MERGE INTO truck (id, plate) KEY (id) VALUES (128, 'GG 890 HH');
MERGE INTO truck (id, plate) KEY (id) VALUES (129, 'II 123 JJ');
MERGE INTO truck (id, plate) KEY (id) VALUES (130, 'KK 456 LL');
MERGE INTO truck (id, plate) KEY (id) VALUES (131, 'MM 789 NN');
MERGE INTO truck (id, plate) KEY (id) VALUES (132, 'OO 012 PP');
MERGE INTO truck (id, plate) KEY (id) VALUES (133, 'QQ 345 RR');
MERGE INTO truck (id, plate) KEY (id) VALUES (134, 'SS 678 TT');
MERGE INTO truck (id, plate) KEY (id) VALUES (135, 'UU 901 VV');
MERGE INTO truck (id, plate) KEY (id) VALUES (136, 'WW 234 XX');
MERGE INTO truck (id, plate) KEY (id) VALUES (137, 'YY 567 ZZ');
MERGE INTO truck (id, plate) KEY (id) VALUES (138, 'AB 890 CD');
MERGE INTO truck (id, plate) KEY (id) VALUES (139, 'EF 123 GH');
MERGE INTO truck (id, plate) KEY (id) VALUES (140, 'IJ 456 KL');
MERGE INTO truck (id, plate) KEY (id) VALUES (141, 'MN 789 OP');
MERGE INTO truck (id, plate) KEY (id) VALUES (142, 'QR 012 ST');
MERGE INTO truck (id, plate) KEY (id) VALUES (143, 'UV 345 WX');
MERGE INTO truck (id, plate) KEY (id) VALUES (144, 'YZ 678 AB');
MERGE INTO truck (id, plate) KEY (id) VALUES (145, 'CD 901 EF');
MERGE INTO truck (id, plate) KEY (id) VALUES (146, 'GH 234 IJ');
MERGE INTO truck (id, plate) KEY (id) VALUES (147, 'KL 567 MN');
MERGE INTO truck (id, plate) KEY (id) VALUES (148, 'OP 890 QR');
MERGE INTO truck (id, plate) KEY (id) VALUES (149, 'ST 123 UV');
MERGE INTO truck (id, plate) KEY (id) VALUES (150, 'WX 456 YZ');

MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (30, 99, -27.3450, -65.5920, 1694822400000);  -- 2024-09-16 00:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (31, 99, -27.3455, -65.5925, 1694851200000);  -- 2024-09-16 06:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (32, 99, -27.3460, -65.5930, 1694865600000);  -- 2024-09-16 12:00:00

MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (33, 100, -27.3465, -65.5935, 1694908800000);  -- 2024-09-16 18:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (34, 100, -27.3470, -65.5940, 1694947200000);  -- 2024-09-17 00:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (35, 100, -27.3475, -65.5945, 1694961600000);  -- 2024-09-17 06:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (36, 100, -27.3480, -65.5950, 1694976000000);  -- 2024-09-17 12:00:00

MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (37, 101, -27.3485, -65.5955, 1695019200000);  -- 2024-09-17 18:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (38, 101, -27.3490, -65.5960, 1695040800000);  -- 2024-09-18 00:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (39, 101, -27.3495, -65.5965, 1695062400000);  -- 2024-09-18 06:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (40, 101, -27.3500, -65.5970, 1695084000000);  -- 2024-09-18 12:00:00

MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (41, 102, -27.3505, -65.5975, 1695105600000);  -- 2024-09-18 18:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (42, 102, -27.3510, -65.5980, 1695127200000);  -- 2024-09-19 00:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (43, 102, -27.3515, -65.5985, 1695148800000);  -- 2024-09-19 06:00:00

MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (44, 103, -27.3520, -65.5990, 1695170400000);  -- 2024-09-19 12:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (45, 103, -27.3525, -65.5995, 1695192000000);  -- 2024-09-19 18:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (46, 103, -27.3530, -65.6000, 1695213600000);  -- 2024-09-20 00:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (47, 103, -27.3535, -65.6005, 1695235200000);  -- 2024-09-20 06:00:00
MERGE INTO POSITION_RECORD (id, truck_id, latitude, longitude, timestamp) VALUES (48, 103, -27.3540, -65.6010, 1695256800000);  -- 2024-09-20 12:00:00