-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: eventdb
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Âm nhạc'),(3,'Công nghệ'),(4,'Giáo dục'),(5,'Kinh doanh'),(2,'Thể thao');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `location_id` int NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `available_tickets` int NOT NULL,
  `price` decimal(15,0) NOT NULL DEFAULT '0',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_event` (`location_id`,`start_time`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `event_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL,
  CONSTRAINT `event_location` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,3,'Hội thảo Công nghệ',1,'2025-04-10 09:00:00','2025-04-10 11:00:00',100,50000,'/images/event3.jpg','Hội thảo công nghệ là sự kiện chia sẻ kiến thức, xu hướng và giải pháp sáng tạo trong lĩnh vực công nghệ.',1),(2,1,'Lễ hội Âm nhạc',2,'2025-05-02 18:00:00','2025-05-02 22:30:00',499,20000,'/images/event1.jpeg','Lễ hội âm nhạc là sự kiện sôi động náo nhiệt, nơi khán giả thưởng thức các màn trình diễn trực tiếp của nhiều nghệ sĩ.',1),(7,5,'Triển lãm tranh',3,'2025-05-04 08:30:00','2025-05-04 16:30:00',49,75000,'/images/event4.jpg','Triển lãm tranh là sự kiện trưng bày các tác phẩm hội họa của một hoặc nhiều nghệ sĩ nhằm giới thiệu đến công chúng, tôn vinh nghệ thuật và kết nối người xem với cảm xúc, thông điệp mà bức tranh truyền tải.',1),(10,5,'Hội chợ thương mại',2,'2025-04-28 07:30:00','2025-04-29 17:30:00',299,0,'/images/event5.png','Hội chợ thương mại là sự kiện quy tụ nhiều doanh nghiệp tham gia trưng bày, giới thiệu và quảng bá sản phẩm, dịch vụ đến khách hàng và đối tác. Đây là cơ hội để giao lưu, tìm kiếm cơ hội hợp tác, mở rộng thị trường và thúc đẩy hoạt động kinh doanh.',1),(15,5,'Workshop vẽ tranh nghệ thuật',1,'2025-05-13 09:00:00','2025-05-13 14:45:00',29,30000,'/images/event6.jpg','Workshop vẽ tranh nghệ thuật là buổi học thực hành, nơi người tham gia được hướng dẫn vẽ tranh dưới sự chỉ dẫn của nghệ sĩ, học các kỹ thuật và phong cách nghệ thuật khác nhau, nhằm phát triển kỹ năng sáng tạo và thể hiện cảm xúc qua nghệ thuật.',1),(17,2,'Lễ khai mạc SEA Games',2,'2025-05-20 09:00:00','2025-05-20 11:30:00',0,10000,'/images/event7.jpg','Lễ khai mạc SEA Games là sự kiện mở đầu giải đấu thể thao Đông Nam Á, với diễu hành các đoàn vận động viên, màn trình diễn nghệ thuật và nghi thức tôn vinh văn hóa, tinh thần thể thao, cùng sự tham gia của các lãnh đạo và quan chức thể thao.',1),(18,2,'abcd',3,'2025-06-11 12:00:00','2025-06-19 15:00:00',32,12000,'/images/event2.png','dlkk;ak;dka',1);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `capacity` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Hội trường A','97 Võ Văn Tần, P6, Q3, TPHCM',100),(2,'Sân vận động B','2A Phan Đình Giót, P2, Q.Tân Bình, TPHCM',3000),(3,'Nhà thi đấu C','35 Hồ Hảo Hớn, P.Cô Giang, Q1,  TPHCM',400);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `event_id` int NOT NULL,
  `user_id` int NOT NULL,
  `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `sent_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `recipient_id` (`user_id`),
  KEY `notification_event_idx` (`event_id`),
  CONSTRAINT `noti_event` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `noti_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (8,2,2,'[Lễ hội Âm nhạc] Thông báo hủy sự kiện','Sự kiện [Lễ hội Âm nhạc] sẽ được hủy bỏ vì lý do phát sinh ngoài ý muốn.\nMọi khoản phí sẽ được hoàn lại trong thời gian sớm nhất.\nChúng tôi thành thật xin lỗi vì sự bất tiện này và chân thành cảm ơn sự ủng hộ của bạn.','2025-04-26 04:43:25','CANCEL'),(9,2,3,'[Lễ hội Âm nhạc] Thông báo hủy sự kiện','Sự kiện [Lễ hội Âm nhạc] sẽ được hủy bỏ vì lý do phát sinh ngoài ý muốn.\nMọi khoản phí sẽ được hoàn lại trong thời gian sớm nhất.\nChúng tôi thành thật xin lỗi vì sự bất tiện này và chân thành cảm ơn sự ủng hộ của bạn.','2025-04-26 04:43:25','CANCEL'),(10,2,2,'[Lễ hội Âm nhạc] Thông báo thay đổi thời gian','Thời gian tổ chức sự kiện \'Lễ hội Âm nhạc\' đã được thay đổi.\nThời gian mới: 18:00 - 02/05/2025 đến 22:30 - 02/05/2025\nChúng tôi thành thật xin lỗi vì sự bất tiện này và chân thành cảm ơn sự ủng hộ của bạn.','2025-04-26 20:23:28','CHANGE'),(11,10,10,'[Hội chợ thương mại] Nhắc nhở tham gia','Sự kiện \'Hội chợ thương mại\' sẽ diễn ra vào 07:30 - 28/04/2025 (ngày mai) tại Sân vận động B.\nHẹn gặp bạn vào ngày mai.','2025-04-27 02:35:33','REMINDER'),(12,15,11,'[Workshop vẽ tranh nghệ thuật] Thông báo hủy sự kiện','Sự kiện \'Workshop vẽ tranh nghệ thuật\' sẽ được hủy bỏ vì lý do phát sinh ngoài ý muốn.\nMọi khoản phí sẽ được hoàn lại trong thời gian sớm nhất.\nChúng tôi thành thật xin lỗi vì sự bất tiện này và chân thành cảm ơn sự ủng hộ của bạn.','2025-04-27 21:44:55','CANCEL');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `registration_id` int NOT NULL,
  `amount` decimal(15,0) NOT NULL,
  `payment_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` enum('SUCCESS','FAILED','REFUNDED') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'SUCCESS',
  PRIMARY KEY (`id`),
  KEY `registration_id` (`registration_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`registration_id`) REFERENCES `registration` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,50000,'2025-04-01 10:00:00','SUCCESS');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration` (
  `id` int NOT NULL AUTO_INCREMENT,
  `event_id` int NOT NULL,
  `user_id` int NOT NULL,
  `registration_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `payment_amount` decimal(15,0) NOT NULL,
  `payment_status` enum('PAID','REFUNDED') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PAID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_registration` (`event_id`,`user_id`,`payment_status`),
  KEY `reg_user_idx` (`user_id`),
  CONSTRAINT `reg_event` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `reg_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES (1,1,2,'2025-03-22 20:17:32',50000,'PAID'),(2,2,3,'2025-03-22 20:17:32',20000,'REFUNDED'),(4,2,2,'2025-04-23 18:20:37',20000,'REFUNDED'),(6,2,2,'2025-04-26 04:58:58',20000,'PAID'),(7,10,10,'2025-04-27 02:34:51',0,'PAID'),(8,15,11,'2025-04-27 21:07:59',30000,'REFUNDED'),(9,7,11,'2025-04-27 21:10:04',75000,'PAID');
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('ADMIN','USER') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','0123456789','admin@gmail.com','$2a$12$VV4QZId7xdSG0G7b9OluCuWZ.EgyNUFyFr4Pm6fQPIZPBi5xnueka','ADMIN'),(2,'user1','0987654321','user1@gmail.com','$2a$12$VV4QZId7xdSG0G7b9OluCuWZ.EgyNUFyFr4Pm6fQPIZPBi5xnueka','USER'),(3,'user2','0909090909','user2@gmail.com','$2a$12$VV4QZId7xdSG0G7b9OluCuWZ.EgyNUFyFr4Pm6fQPIZPBi5xnueka','USER'),(10,'abc','123','abc@gmail.com','$2a$12$VV4QZId7xdSG0G7b9OluCuWZ.EgyNUFyFr4Pm6fQPIZPBi5xnueka','USER'),(11,'tester','0342974912','nhungvo@gmail.com','$2a$12$vUbE5zSwsusjOBPe70olbOlFaVPTDv6Ew2wQY3ABkICceaqOD6oMC','USER'),(12,'test1','0989374613','nhung@gmail.com','$2a$12$/m3ecIj5XFCmtV/tKOTeKO8g0fJa7PuK3vf9taWT9LLBoIcIwH69i','USER'),(16,'tester2','000','nhung1@gmail.com','$2a$12$gVMK9T0hld9TNc7E2ix3cO97OF3/VuMA4Xgkry1ZHbX4inKJmzXPm','USER'),(17,'test3','0485973582','hla','$2a$12$qIYRc8XT.KBebme33/uch.bJSeR5kSP20Si0JGzVMr5C/YstNjz.K','USER'),(18,'test4','0342974912','nhung2@gmail.com','$2a$12$uKNz0ab3vSXhtScvl.6lE.vzCfYkZjgBlgmtSj0zmWFzDgw8GhalO','USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-27 23:13:04
