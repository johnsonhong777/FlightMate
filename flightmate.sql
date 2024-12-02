/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : flightmate

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 03/12/2024 03:05:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aircrafts
-- ----------------------------
DROP TABLE IF EXISTS `aircrafts`;
CREATE TABLE `aircrafts`  (
  `aircraft_id` int NOT NULL AUTO_INCREMENT,
  `aircraft_model` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `manufacture_date` date NOT NULL,
  `last_maintenance_date` date NOT NULL,
  `next_maintenance_date` date NOT NULL,
  `aircraft_notes` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `administrator_id` int NOT NULL,
  `airport_id` int NOT NULL,
  PRIMARY KEY (`aircraft_id`) USING BTREE,
  INDEX `administrator_id`(`administrator_id` ASC) USING BTREE,
  INDEX `airport_id`(`airport_id` ASC) USING BTREE,
  CONSTRAINT `aircrafts_ibfk_1` FOREIGN KEY (`administrator_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `aircrafts_ibfk_2` FOREIGN KEY (`airport_id`) REFERENCES `airports` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of aircrafts
-- ----------------------------

-- ----------------------------
-- Table structure for airports
-- ----------------------------
DROP TABLE IF EXISTS `airports`;
CREATE TABLE `airports`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `airport_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `airport_code` varchar(3) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `country` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `runways` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `airport_code`(`airport_code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of airports
-- ----------------------------
INSERT INTO `airports` VALUES (168, 'John F. Kennedy International Airport', 'JFK', 'New York', 'United States', 4, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (169, 'Los Angeles International Airport', 'LAX', 'Los Angeles', 'United States', 4, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (170, 'London Heathrow Airport', 'LHR', 'London', 'United Kingdom', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (171, 'Beijing Capital International Airport', 'PEK', 'Beijing', 'China', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (172, 'Tokyo Haneda Airport', 'HND', 'Tokyo', 'Japan', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (173, 'Dubai International Airport', 'DXB', 'Dubai', 'United Arab Emirates', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (174, 'Paris Charles de Gaulle Airport', 'CDG', 'Paris', 'France', 4, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (175, 'Sydney Kingsford Smith Airport', 'SYD', 'Sydney', 'Australia', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (176, 'Frankfurt Airport', 'FRA', 'Frankfurt', 'Germany', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (177, 'Hare International Airport', 'ORD', 'Chicago', 'United States', 4, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (178, 'Hong Kong International Airport', 'HKG', 'Hong Kong', 'China', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (179, 'Amsterdam Schiphol Airport', 'AMS', 'Amsterdam', 'Netherlands', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (180, 'Kuala Lumpur International Airport', 'KUL', 'Kuala Lumpur', 'Malaysia', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (181, 'Cairo International Airport', 'CAI', 'Cairo', 'Egypt', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (182, 'Changi Airport', 'SIN', 'Singapore', 'Singapore', 4, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (183, 'Madrid-Barajas Adolfo Suárez Airport', 'MAD', 'Madrid', 'Spain', 4, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (184, 'Los Angeles Ontario International Airport', 'ONT', 'Ontario', 'United States', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (185, 'Dubai Al Maktoum International Airport', 'DWC', 'Dubai', 'United Arab Emirates', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (186, 'Seoul Incheon International Airport', 'ICN', 'Seoul', 'South Korea', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (187, 'San Francisco International Airport', 'SFO', 'San Francisco', 'United States', 4, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (188, 'Mexico City International Airport', 'MEX', 'Mexico City', 'Mexico', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (189, 'Indira Gandhi International Airport', 'DEL', 'New Delhi', 'India', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (190, 'Bangkok Suvarnabhumi Airport', 'BKK', 'Bangkok', 'Thailand', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (191, 'Toronto Pearson International Airport', 'YYZ', 'Toronto', 'Canada', 5, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (192, 'Rome Fiumicino Airport', 'FCO', 'Rome', 'Italy', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (193, 'Moscow Sheremetyevo International Airport', 'SVO', 'Moscow', 'Russia', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (194, 'Sydney Newcastle Airport', 'NTL', 'Newcastle', 'Australia', 1, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (195, 'Sao Paulo Guarulhos International Airport', 'GRU', 'São Paulo', 'Brazil', 4, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (196, 'Istanbul Airport', 'IST', 'Istanbul', 'Turkey', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (197, 'Los Angeles Long Beach Airport', 'LGB', 'Long Beach', 'United States', 1, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (198, 'Miami International Airport', 'MIA', 'Miami', 'United States', 4, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (199, 'Munich Airport', 'MUC', 'Munich', 'Germany', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (200, 'Hong Kong Kai Tak Airport', 'HKT', 'Hong Kong', 'China', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (201, 'Taipei Taoyuan International Airport', 'TPE', 'Taipei', 'Taiwan', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (202, 'Newark Liberty International Airport', 'EWR', 'Newark', 'United States', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (203, 'Chennai International Airport', 'MAA', 'Chennai', 'India', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (204, 'Rio de Janeiro-Galeão International Airport', 'GIG', 'Rio de Janeiro', 'Brazil', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (205, 'Jeddah King Abdulaziz International Airport', 'JED', 'Jeddah', 'Saudi Arabia', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (206, 'San Diego International Airport', 'SAN', 'San Diego', 'United States', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (207, 'Oslo Gardermoen Airport', 'OSL', 'Oslo', 'Norway', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (208, 'Auckland Airport', 'AKL', 'Auckland', 'New Zealand', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (209, 'Seoul Gimpo International Airport', 'GMP', 'Seoul', 'South Korea', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (210, 'Milan Malpensa Airport', 'MXP', 'Milan', 'Italy', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (211, 'Berlin Brandenburg Airport', 'BER', 'Berlin', 'Germany', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (212, 'Porto Airport', 'OPO', 'Porto', 'Portugal', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (213, 'Vienna Schwechat Airport', 'VIE', 'Vienna', 'Austria', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (214, 'Düsseldorf International Airport', 'DUS', 'Düsseldorf', 'Germany', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (215, 'Lima Jorge Chávez International Airport', 'LIM', 'Lima', 'Peru', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (216, 'Zurich Airport', 'ZRH', 'Zurich', 'Switzerland', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (217, 'Kiev Boryspil International Airport', 'KBP', 'Kiev', 'Ukraine', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (218, 'Copenhagen Airport', 'CPH', 'Copenhagen', 'Denmark', 3, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (219, 'Warsaw Chopin Airport', 'WAW', 'Warsaw', 'Poland', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (220, 'Bangkok Don Mueang International Airport', 'DMK', 'Bangkok', 'Thailand', 2, '2024-12-03 02:08:28');
INSERT INTO `airports` VALUES (221, 'Istanbul Sabiha Gökçen International Airport', 'SAW', 'Istanbul', 'Turkey', 2, '2024-12-03 02:08:28');

-- ----------------------------
-- Table structure for flights
-- ----------------------------
DROP TABLE IF EXISTS `flights`;
CREATE TABLE `flights`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `flight_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `departure_time` timestamp NOT NULL,
  `arrival_time` timestamp NOT NULL,
  `origin` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `destination` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Scheduled',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `flight_number`(`flight_number` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flights
-- ----------------------------
INSERT INTO `flights` VALUES (1, 'CA1234', '2024-12-01 08:00:00', '2024-12-01 12:00:00', 'Beijing', 'Shanghai', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (2, 'CZ5678', '2024-12-01 09:30:00', '2024-12-01 13:30:00', 'Guangzhou', 'Beijing', 'Delayed', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (3, 'MU1234', '2024-12-02 07:00:00', '2024-12-02 10:30:00', 'Shanghai', 'Guangzhou', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (4, 'ZH2345', '2024-12-02 14:00:00', '2024-12-02 16:30:00', 'Shenzhen', 'Chengdu', 'Cancelled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (5, 'FM6789', '2024-12-03 10:00:00', '2024-12-03 14:00:00', 'Beijing', 'Shenzhen', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (6, 'HU2345', '2024-12-03 12:00:00', '2024-12-03 16:30:00', 'Shanghai', 'Xi\'an', 'Delayed', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (7, 'CA6789', '2024-12-04 08:30:00', '2024-12-04 12:30:00', 'Beijing', 'Chengdu', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (8, 'CZ1234', '2024-12-04 11:00:00', '2024-12-04 15:00:00', 'Guangzhou', 'Shanghai', 'Cancelled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (9, 'MU5678', '2024-12-05 06:45:00', '2024-12-05 11:00:00', 'Shanghai', 'Beijing', 'Delayed', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (10, 'ZH3456', '2024-12-05 13:15:00', '2024-12-05 16:45:00', 'Shenzhen', 'Guangzhou', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (11, 'FM1234', '2024-12-06 09:00:00', '2024-12-06 13:30:00', 'Beijing', 'Chengdu', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (12, 'HU6789', '2024-12-06 11:30:00', '2024-12-06 14:30:00', 'Shanghai', 'Xi\'an', 'Delayed', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (13, 'CA2345', '2024-12-07 14:00:00', '2024-12-07 18:00:00', 'Beijing', 'Shenzhen', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (14, 'CZ3456', '2024-12-07 08:30:00', '2024-12-07 12:00:00', 'Guangzhou', 'Shanghai', 'Cancelled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (15, 'MU6789', '2024-12-08 07:15:00', '2024-12-08 10:45:00', 'Shanghai', 'Guangzhou', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (16, 'ZH4567', '2024-12-08 10:30:00', '2024-12-08 13:30:00', 'Shenzhen', 'Beijing', 'Delayed', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (17, 'FM2345', '2024-12-09 11:00:00', '2024-12-09 15:30:00', 'Beijing', 'Xi\'an', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (18, 'HU3456', '2024-12-09 13:00:00', '2024-12-09 17:00:00', 'Shanghai', 'Chengdu', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (19, 'CA1235', '2024-12-10 09:00:00', '2024-12-10 12:00:00', 'Beijing', 'Guangzhou', 'Cancelled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');
INSERT INTO `flights` VALUES (20, 'CZ5679', '2024-12-10 10:30:00', '2024-12-10 13:30:00', 'Guangzhou', 'Shenzhen', 'Scheduled', '2024-12-03 01:41:37', '2024-12-03 01:41:37');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `role_description` varchar(240) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'PILOT', 'Default user. Can log flight hours and schedule own flights.');
INSERT INTO `roles` VALUES (2, 'ADMINISTRATOR', 'Can approve/reject flight hours, manage aircrafts and airports.');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `email_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `email_address`(`email_address` ASC) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 2, 'andy_hulibo@163.com', 'andy', 'hu', '123456', '2024-12-02 11:13:08', NULL, NULL);
INSERT INTO `users` VALUES (2, 1, 'zhangsan@qq.com', 'andy', 'hu', '123456', '2024-12-02 11:20:06', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
