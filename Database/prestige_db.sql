-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 26, 2026 at 03:02 PM
-- Server version: 8.4.3
-- PHP Version: 8.3.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `prestige_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `class_participants`
--

CREATE TABLE `class_participants` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `fitness_class_id` int NOT NULL,
  `enrollment_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('PENDING','CONFIRMED','CANCELLED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  `is_archived` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `fitness_class`
--

CREATE TABLE `fitness_class` (
  `id` int NOT NULL,
  `instructor_id` int NOT NULL,
  `class_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `category` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `duration_minutes` int NOT NULL,
  `class_fee` decimal(10,2) NOT NULL DEFAULT '0.00',
  `capacity` int NOT NULL,
  `schedule_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `status` enum('UPCOMING','ONGOING','COMPLETED','CANCELLED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'UPCOMING',
  `is_archived` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ;

-- --------------------------------------------------------

--
-- Table structure for table `membership_card`
--

CREATE TABLE `membership_card` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `card_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `balance` decimal(10,2) DEFAULT '0.00',
  `status` enum('ACTIVE','LOST','BLOCKED','REPLACED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ACTIVE',
  `is_archived` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `membership_plan`
--

CREATE TABLE `membership_plan` (
  `id` int NOT NULL,
  `plan_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `duration` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `is_archived` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `personal_training`
--

CREATE TABLE `personal_training` (
  `id` int NOT NULL,
  `instructor_id` int NOT NULL,
  `category` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `hourly_rate` decimal(10,2) NOT NULL,
  `is_archived` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ;

-- --------------------------------------------------------

--
-- Table structure for table `purchase_history`
--

CREATE TABLE `purchase_history` (
  `id` int NOT NULL,
  `membership_card_id` int NOT NULL,
  `purchase_reference` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `purchase_type` enum('CLASS','PERSONAL_TRAINING','MEMBERSHIP') COLLATE utf8mb4_unicode_ci NOT NULL,
  `item_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `status` enum('PENDING','COMPLETED','FAILED','CANCELLED','REFUNDED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  `purchase_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscribed_members`
--

CREATE TABLE `subscribed_members` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `membership_plan_id` int NOT NULL,
  `start_date` date NOT NULL,
  `expiration_date` date NOT NULL,
  `status` enum('PENDING','ACTIVE','EXPIRED','CANCELLED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  `is_archived` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `topup_history`
--

CREATE TABLE `topup_history` (
  `id` int NOT NULL,
  `membership_card_id` int NOT NULL,
  `topup_reference` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `payment_reference` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_method` enum('E_WALLET','BANK_TRANSFER') COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('PENDING','COMPLETED','FAILED','REFUNDED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  `topup_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ;

-- --------------------------------------------------------

--
-- Table structure for table `training_booking`
--

CREATE TABLE `training_booking` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `personal_training_id` int NOT NULL,
  `booking_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `total_hours` decimal(5,2) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `status` enum('PENDING','CONFIRMED','CANCELLED','COMPLETED') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING',
  `is_archived` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `first_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('ADMIN','INSTRUCTOR','MEMBER') COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_archived` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `class_participants`
--
ALTER TABLE `class_participants`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_class_participant` (`user_id`,`fitness_class_id`),
  ADD KEY `fk_participant_class` (`fitness_class_id`);

--
-- Indexes for table `fitness_class`
--
ALTER TABLE `fitness_class`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_fitness_class_instructor` (`instructor_id`);

--
-- Indexes for table `membership_card`
--
ALTER TABLE `membership_card`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `card_no` (`card_no`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `membership_plan`
--
ALTER TABLE `membership_plan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `personal_training`
--
ALTER TABLE `personal_training`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_personal_training_instructor` (`instructor_id`);

--
-- Indexes for table `purchase_history`
--
ALTER TABLE `purchase_history`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `purchase_reference` (`purchase_reference`),
  ADD KEY `fk_purchase_membership_card` (`membership_card_id`);

--
-- Indexes for table `subscribed_members`
--
ALTER TABLE `subscribed_members`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_subscribed_members_user` (`user_id`),
  ADD KEY `fk_subscribed_members_plan` (`membership_plan_id`);

--
-- Indexes for table `topup_history`
--
ALTER TABLE `topup_history`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `topup_reference` (`topup_reference`),
  ADD KEY `fk_topup_history_card` (`membership_card_id`);

--
-- Indexes for table `training_booking`
--
ALTER TABLE `training_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_training_booking_user` (`user_id`),
  ADD KEY `fk_training_booking_personal_training` (`personal_training_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `class_participants`
--
ALTER TABLE `class_participants`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fitness_class`
--
ALTER TABLE `fitness_class`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `membership_card`
--
ALTER TABLE `membership_card`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `membership_plan`
--
ALTER TABLE `membership_plan`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `personal_training`
--
ALTER TABLE `personal_training`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `purchase_history`
--
ALTER TABLE `purchase_history`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `subscribed_members`
--
ALTER TABLE `subscribed_members`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `topup_history`
--
ALTER TABLE `topup_history`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `training_booking`
--
ALTER TABLE `training_booking`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `class_participants`
--
ALTER TABLE `class_participants`
  ADD CONSTRAINT `fk_participant_class` FOREIGN KEY (`fitness_class_id`) REFERENCES `fitness_class` (`id`),
  ADD CONSTRAINT `fk_participant_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `fitness_class`
--
ALTER TABLE `fitness_class`
  ADD CONSTRAINT `fk_fitness_class_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `membership_card`
--
ALTER TABLE `membership_card`
  ADD CONSTRAINT `membership_card_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `personal_training`
--
ALTER TABLE `personal_training`
  ADD CONSTRAINT `fk_personal_training_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `purchase_history`
--
ALTER TABLE `purchase_history`
  ADD CONSTRAINT `fk_purchase_membership_card` FOREIGN KEY (`membership_card_id`) REFERENCES `membership_card` (`id`);

--
-- Constraints for table `subscribed_members`
--
ALTER TABLE `subscribed_members`
  ADD CONSTRAINT `fk_subscribed_members_plan` FOREIGN KEY (`membership_plan_id`) REFERENCES `membership_plan` (`id`),
  ADD CONSTRAINT `fk_subscribed_members_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `topup_history`
--
ALTER TABLE `topup_history`
  ADD CONSTRAINT `fk_topup_history_card` FOREIGN KEY (`membership_card_id`) REFERENCES `membership_card` (`id`);

--
-- Constraints for table `training_booking`
--
ALTER TABLE `training_booking`
  ADD CONSTRAINT `fk_training_booking_personal_training` FOREIGN KEY (`personal_training_id`) REFERENCES `personal_training` (`id`),
  ADD CONSTRAINT `fk_training_booking_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
