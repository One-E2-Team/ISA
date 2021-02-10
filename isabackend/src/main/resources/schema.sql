CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `test`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: test
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `SPRING_SESSION`
--




DROP TABLE IF EXISTS `working_calendars`;
DROP TABLE IF EXISTS `warehouses`;
DROP TABLE IF EXISTS `vacation_requests`;
DROP TABLE IF EXISTS `user_authority`;
DROP TABLE IF EXISTS `subscriptions`;
DROP TABLE IF EXISTS `requests_for_missing_medicines`;
DROP TABLE IF EXISTS `rated_pharmacies`;
DROP TABLE IF EXISTS `rated_medicines`;
DROP TABLE IF EXISTS `rated_health_workers`;
DROP TABLE IF EXISTS `promotions`;
DROP TABLE IF EXISTS `pricelists`;
DROP TABLE IF EXISTS `orders_medicines_with_quantity`;
DROP TABLE IF EXISTS `offers`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `medicine_reservations`;
DROP TABLE IF EXISTS `loyalty`;
DROP TABLE IF EXISTS `examinations_medicines`;
DROP TABLE IF EXISTS `examinations`;
DROP TABLE IF EXISTS `equivalent_medicines`;
DROP TABLE IF EXISTS `e_recipes_medicines_with_quantity`;
DROP TABLE IF EXISTS `e_recipes`;
DROP TABLE IF EXISTS `dermatologists_in_pharmacies`;
DROP TABLE IF EXISTS `complaints`;
DROP TABLE IF EXISTS `authority`;
DROP TABLE IF EXISTS `all_users_medicines_with_quantity`;
DROP TABLE IF EXISTS `medicines_with_quantity`;
DROP TABLE IF EXISTS `all_users_allergies`;
DROP TABLE IF EXISTS `medicines`;
DROP TABLE IF EXISTS `all_users`;
DROP TABLE IF EXISTS `pharmacies`;
DROP TABLE IF EXISTS `SPRING_SESSION_ATTRIBUTES`;
DROP TABLE IF EXISTS `SPRING_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SPRING_SESSION` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SPRING_SESSION_ATTRIBUTES`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SPRING_SESSION_ATTRIBUTES` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `SPRING_SESSION` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pharmacies`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pharmacies` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `all_users`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `all_users` (
  `type` varchar(31) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `user_type` int NOT NULL,
  `penalties` int DEFAULT NULL,
  `points` int DEFAULT NULL,
  `pharmacists_pharmacy_id` bigint DEFAULT NULL,
  `admins_pharmacy_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm4aiqlnq84ocgyatdbwja03jv` (`pharmacists_pharmacy_id`),
  KEY `FK9pwr2be1qvsvysbp9ihlriilq` (`admins_pharmacy_id`),
  CONSTRAINT `FK9pwr2be1qvsvysbp9ihlriilq` FOREIGN KEY (`admins_pharmacy_id`) REFERENCES `pharmacies` (`id`),
  CONSTRAINT `FKm4aiqlnq84ocgyatdbwja03jv` FOREIGN KEY (`pharmacists_pharmacy_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `medicines`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicines` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` bigint NOT NULL,
  `contexture` varchar(255) NOT NULL,
  `daily_intake` int NOT NULL,
  `manufacturer` varchar(255) NOT NULL,
  `medicine_form` varchar(255) NOT NULL,
  `medicine_type` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `points` int NOT NULL,
  `recipe_needed` bit(1) NOT NULL,
  `side_effects` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `all_users_allergies`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `all_users_allergies` (
  `patient_id` bigint NOT NULL,
  `allergies_id` bigint NOT NULL,
  PRIMARY KEY (`patient_id`,`allergies_id`),
  KEY `FKm9c9kcpmygqmc59x5vdi0jcv1` (`allergies_id`),
  CONSTRAINT `FKbv101mbndst6ueo1nx6vclxq2` FOREIGN KEY (`patient_id`) REFERENCES `all_users` (`id`),
  CONSTRAINT `FKm9c9kcpmygqmc59x5vdi0jcv1` FOREIGN KEY (`allergies_id`) REFERENCES `medicines` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `medicines_with_quantity`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicines_with_quantity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `medicine_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbigu61h2cn0o6fs3tghya4dcn` (`medicine_id`),
  CONSTRAINT `FKbigu61h2cn0o6fs3tghya4dcn` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `all_users_medicines_with_quantity`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `all_users_medicines_with_quantity` (
  `dealer_id` bigint NOT NULL,
  `medicines_with_quantity_id` bigint NOT NULL,
  KEY `FKnem71du3rf2tbaya9mlarr86t` (`medicines_with_quantity_id`),
  KEY `FKh9s1mvig6ic7jhb8ukodh1bid` (`dealer_id`),
  CONSTRAINT `FKh9s1mvig6ic7jhb8ukodh1bid` FOREIGN KEY (`dealer_id`) REFERENCES `all_users` (`id`),
  CONSTRAINT `FKnem71du3rf2tbaya9mlarr86t` FOREIGN KEY (`medicines_with_quantity_id`) REFERENCES `medicines_with_quantity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `authority`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `complaints`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaints` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `comment` varchar(255) NOT NULL,
  `complaint_entity_id` bigint NOT NULL,
  `handled` bit(1) NOT NULL,
  `type` int NOT NULL,
  `patient_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrirda7dtftcu4m06l7orqs2k0` (`patient_id`),
  CONSTRAINT `FKrirda7dtftcu4m06l7orqs2k0` FOREIGN KEY (`patient_id`) REFERENCES `all_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dermatologists_in_pharmacies`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dermatologists_in_pharmacies` (
  `dermatologist_id` bigint NOT NULL,
  `pharmacies_id` bigint NOT NULL,
  PRIMARY KEY (`dermatologist_id`,`pharmacies_id`),
  KEY `FKhmlgimrn78vg3wtk014v1fia` (`pharmacies_id`),
  CONSTRAINT `FKerxuv4rc49qfww5ejktii8hat` FOREIGN KEY (`dermatologist_id`) REFERENCES `all_users` (`id`),
  CONSTRAINT `FKhmlgimrn78vg3wtk014v1fia` FOREIGN KEY (`pharmacies_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `e_recipes`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `e_recipes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `status` int NOT NULL,
  `patient_id` bigint NOT NULL,
  `pharmacy_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK39lb023381f8w3c0np6fomngq` (`patient_id`),
  KEY `FKxomx0wa310rxjnee73wi4faq` (`pharmacy_id`),
  CONSTRAINT `FK39lb023381f8w3c0np6fomngq` FOREIGN KEY (`patient_id`) REFERENCES `all_users` (`id`),
  CONSTRAINT `FKxomx0wa310rxjnee73wi4faq` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `e_recipes_medicines_with_quantity`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `e_recipes_medicines_with_quantity` (
  `erecipe_id` bigint NOT NULL,
  `medicines_with_quantity_id` bigint NOT NULL,
  KEY `FKrmoh0ek84a2o0gfayq3oksftc` (`medicines_with_quantity_id`),
  KEY `FKmoge0irf8py1v70cm17xfpsl5` (`erecipe_id`),
  CONSTRAINT `FKmoge0irf8py1v70cm17xfpsl5` FOREIGN KEY (`erecipe_id`) REFERENCES `e_recipes` (`id`),
  CONSTRAINT `FKrmoh0ek84a2o0gfayq3oksftc` FOREIGN KEY (`medicines_with_quantity_id`) REFERENCES `medicines_with_quantity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `equivalent_medicines`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equivalent_medicines` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `primary_medicine_id` bigint NOT NULL,
  `similar_medicine_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `examinations`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examinations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `information` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `start_time` datetime NOT NULL,
  `status` int NOT NULL,
  `health_wokrer_id` bigint NOT NULL,
  `patient_id` bigint DEFAULT NULL,
  `pharmacy_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoj9oj7q0u6ktlk09boannxfte` (`health_wokrer_id`),
  KEY `FKg54gw4dkmraynsqbhukam1vfw` (`patient_id`),
  KEY `FKog9kt9jhgd5kf2b0lp0yej75o` (`pharmacy_id`),
  CONSTRAINT `FKg54gw4dkmraynsqbhukam1vfw` FOREIGN KEY (`patient_id`) REFERENCES `all_users` (`id`),
  CONSTRAINT `FKog9kt9jhgd5kf2b0lp0yej75o` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`),
  CONSTRAINT `FKoj9oj7q0u6ktlk09boannxfte` FOREIGN KEY (`health_wokrer_id`) REFERENCES `all_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `examinations_medicines`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examinations_medicines` (
  `examination_id` bigint NOT NULL,
  `medicines_id` bigint NOT NULL,
  KEY `FK6j36a1vthvlq1cmev75ynodqw` (`medicines_id`),
  KEY `FK4deqgrqo8qv3481tbnf9ruqwk` (`examination_id`),
  CONSTRAINT `FK4deqgrqo8qv3481tbnf9ruqwk` FOREIGN KEY (`examination_id`) REFERENCES `examinations` (`id`),
  CONSTRAINT `FK6j36a1vthvlq1cmev75ynodqw` FOREIGN KEY (`medicines_id`) REFERENCES `medicines` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loyalty`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loyalty` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `discount` double NOT NULL,
  `examination_points` int NOT NULL,
  `min_points` int NOT NULL,
  `type` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicine_reservations`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine_reservations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expire_date` datetime NOT NULL,
  `status` int NOT NULL,
  `medicine_id` bigint NOT NULL,
  `patient_id` bigint DEFAULT NULL,
  `pharmacy_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq83uf0xrpigb0eruxphvv7k4w` (`medicine_id`),
  KEY `FK2dexpcpaau65nwtukpft66o1` (`patient_id`),
  KEY `FK6ua0h9nuuf0j8mrvr0nnpq2na` (`pharmacy_id`),
  CONSTRAINT `FK2dexpcpaau65nwtukpft66o1` FOREIGN KEY (`patient_id`) REFERENCES `all_users` (`id`),
  CONSTRAINT `FK6ua0h9nuuf0j8mrvr0nnpq2na` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`),
  CONSTRAINT `FKq83uf0xrpigb0eruxphvv7k4w` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `orders`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `creator_id` bigint NOT NULL,
  `expire_date` datetime NOT NULL,
  `finished` bit(1) DEFAULT NULL,
  `pharmacy_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `offers`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `full_price` double NOT NULL,
  `status` int NOT NULL,
  `dealer_id` bigint NOT NULL,
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlpebw5khx46i81atgalhm5mmb` (`dealer_id`),
  KEY `FKeiohqgsubg6df80cqfgchfauq` (`order_id`),
  CONSTRAINT `FKeiohqgsubg6df80cqfgchfauq` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKlpebw5khx46i81atgalhm5mmb` FOREIGN KEY (`dealer_id`) REFERENCES `all_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orders_medicines_with_quantity`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_medicines_with_quantity` (
  `order_id` bigint NOT NULL,
  `medicines_with_quantity_id` bigint NOT NULL,
  KEY `FKolgji3jtwlanb3f21tjg9jc13` (`medicines_with_quantity_id`),
  KEY `FK933ltbrnrufitrj52by64529s` (`order_id`),
  CONSTRAINT `FK933ltbrnrufitrj52by64529s` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKolgji3jtwlanb3f21tjg9jc13` FOREIGN KEY (`medicines_with_quantity_id`) REFERENCES `medicines_with_quantity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `pricelists`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pricelists` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_date` datetime NOT NULL,
  `price` double NOT NULL,
  `start_date` datetime NOT NULL,
  `medicine_id` bigint NOT NULL,
  `pharmacy_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKccrpjg3n5cpdq8f3yo1tttwr1` (`medicine_id`),
  KEY `FKjsvi0x7ixyd5c4elgddx9jwdq` (`pharmacy_id`),
  CONSTRAINT `FKccrpjg3n5cpdq8f3yo1tttwr1` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`),
  CONSTRAINT `FKjsvi0x7ixyd5c4elgddx9jwdq` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `promotions`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  `pharmacy_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5yp0kuq2h9qyt4klni8oofgh7` (`pharmacy_id`),
  CONSTRAINT `FK5yp0kuq2h9qyt4klni8oofgh7` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rated_health_workers`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rated_health_workers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rate` int NOT NULL,
  `health_worker_id` bigint NOT NULL,
  `patient_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgwhecs4blrppoew3lub7b1b6k` (`health_worker_id`),
  KEY `FK8kfivomeptaydsi0smcfs2bf0` (`patient_id`),
  CONSTRAINT `FK8kfivomeptaydsi0smcfs2bf0` FOREIGN KEY (`patient_id`) REFERENCES `all_users` (`id`),
  CONSTRAINT `FKgwhecs4blrppoew3lub7b1b6k` FOREIGN KEY (`health_worker_id`) REFERENCES `all_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rated_medicines`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rated_medicines` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rate` int NOT NULL,
  `medicine_id` bigint NOT NULL,
  `patient_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt39eptrmyjovj0nhtobrvr433` (`medicine_id`),
  KEY `FK6scldv9tn801pqbhb762knbh1` (`patient_id`),
  CONSTRAINT `FK6scldv9tn801pqbhb762knbh1` FOREIGN KEY (`patient_id`) REFERENCES `all_users` (`id`),
  CONSTRAINT `FKt39eptrmyjovj0nhtobrvr433` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rated_pharmacies`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rated_pharmacies` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rate` int NOT NULL,
  `patient_id` bigint NOT NULL,
  `pharmacy_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf48to8ou1g983o27hyk3iliar` (`patient_id`),
  KEY `FKf0rstihvy78f7vx6vp5ypto5y` (`pharmacy_id`),
  CONSTRAINT `FKf0rstihvy78f7vx6vp5ypto5y` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`),
  CONSTRAINT `FKf48to8ou1g983o27hyk3iliar` FOREIGN KEY (`patient_id`) REFERENCES `all_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `requests_for_missing_medicines`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requests_for_missing_medicines` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `medicine_id` bigint NOT NULL,
  `pharmacy_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKios40pmow7bvoffleeoxpy1nm` (`medicine_id`),
  KEY `FKljbht5m3k363xnwbcccdye9cx` (`pharmacy_id`),
  CONSTRAINT `FKios40pmow7bvoffleeoxpy1nm` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`),
  CONSTRAINT `FKljbht5m3k363xnwbcccdye9cx` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscriptions`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriptions` (
  `pharmacy_id` bigint NOT NULL,
  `subscribed_patients_id` bigint NOT NULL,
  PRIMARY KEY (`pharmacy_id`,`subscribed_patients_id`),
  KEY `FKagrfnw4vvfu3o0l76af4l69bb` (`subscribed_patients_id`),
  CONSTRAINT `FK8yfxyh49qkpvwokxr2jmsx2ol` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`),
  CONSTRAINT `FKagrfnw4vvfu3o0l76af4l69bb` FOREIGN KEY (`subscribed_patients_id`) REFERENCES `all_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_authority`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_authority` (
  `user_id` bigint NOT NULL,
  `authority_id` bigint NOT NULL,
  KEY `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id`),
  KEY `FKq141mtsnvchpenlb0xuvlcayn` (`user_id`),
  CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKq141mtsnvchpenlb0xuvlcayn` FOREIGN KEY (`user_id`) REFERENCES `all_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vacation_requests`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vacation_requests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accepted` bit(1) DEFAULT NULL,
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  `type` int NOT NULL,
  `health_wokrer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs6vm14ucbqinsh6e5jqc7pv9l` (`health_wokrer_id`),
  CONSTRAINT `FKs6vm14ucbqinsh6e5jqc7pv9l` FOREIGN KEY (`health_wokrer_id`) REFERENCES `all_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `warehouses`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` int NOT NULL,
  `reserved_amount` int NOT NULL,
  `medicine_id` bigint NOT NULL,
  `pharmacy_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5beet23h3torura2ie6nj45q1` (`medicine_id`),
  KEY `FKgvs8n8hde3yax3nq20cl1v3f8` (`pharmacy_id`),
  CONSTRAINT `FK5beet23h3torura2ie6nj45q1` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`),
  CONSTRAINT `FKgvs8n8hde3yax3nq20cl1v3f8` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `working_calendars`
--


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `working_calendars` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_date` datetime NOT NULL,
  `end_hour` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  `start_hour` datetime NOT NULL,
  `health_wokrer_id` bigint NOT NULL,
  `pharmacy_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrasxfdhk4b1a1iessmqxbhrjx` (`health_wokrer_id`),
  KEY `FK7o82notuygr3x20ntsps49ok5` (`pharmacy_id`),
  CONSTRAINT `FK7o82notuygr3x20ntsps49ok5` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`),
  CONSTRAINT `FKrasxfdhk4b1a1iessmqxbhrjx` FOREIGN KEY (`health_wokrer_id`) REFERENCES `all_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-10 21:55:13

