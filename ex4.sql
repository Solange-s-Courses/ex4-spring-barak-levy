-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 11, 2025 at 11:24 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ex4`
--

-- --------------------------------------------------------

--
-- Table structure for table `base_user`
--

CREATE TABLE `base_user` (
  `user_type` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `access_locked` bit(1) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','COMPANY','USER') DEFAULT NULL,
  `user_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `base_user`
--

INSERT INTO `base_user` (`user_type`, `id`, `access_locked`, `email`, `password`, `role`, `user_name`) VALUES
('USER', 1, b'0', 'ron@gmail.com', '$2a$10$rMyb5qIM.JQsEeI34CD7FuO27TS8biBoJzcIx6tHct4O6XZkdsqB2', 'USER', 'Ron'),
('ADMIN', 2, b'0', 'admin@gmail.com', '$2a$10$mRXoE6IIbZzku1c50Vnkze6.nJkD2Kcm9NhKs4jGgmHDtDXu5TSDW', 'ADMIN', 'Admin'),
('COMPANY', 52, b'0', 'samsung@gmail.com', '$2a$10$glw8KiVgf4sP6BuXgLx7COEBWwgMHmF3GfFlCakEkbY.75f5namBy', 'COMPANY', 'Samsung'),
('USER', 53, b'0', 'tom@gmail.com', '$2a$10$VBZOWTHNCKG8vzBBH27uieINEb74RNqynaVet7gM6Vi0r5OI8kdKK', 'USER', 'Tom'),
('COMPANY', 102, b'0', 'intel@gmail.com', '$2a$10$xfcdoq7KJkSkcT.xrz25A.eFXjuDrmsZjM2oiRCHTaS5fFofXmb0y', 'COMPANY', 'Intel'),
('USER', 153, b'0', 'dana@gmail.com', '$2a$10$Cx6OxQyD0D76AjBq94dzkuXf.A/U350r01o3HEowVUtSfG6hk3qlS', 'USER', 'Dana'),
('COMPANY', 154, b'0', 'lenovo@gmail.com', '$2a$10$jd6LQrmhQgqZ9/fQbZSRa.qBaYDnj.G6QgRb1UW4pRmHL2mubHboa', 'COMPANY', 'Lenobo'),
('COMPANY', 202, b'0', 'apple@gmail.com', '$2a$10$W/eD786XCbCiHPbTiPndJeuT6BLh/BDJ1gzFOZryNpTP0cY8ZrYdC', 'COMPANY', 'Apple');

-- --------------------------------------------------------

--
-- Table structure for table `base_user_seq`
--

CREATE TABLE `base_user_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `base_user_seq`
--

INSERT INTO `base_user_seq` (`next_val`) VALUES
(451);

-- --------------------------------------------------------

--
-- Table structure for table `order_entity`
--

CREATE TABLE `order_entity` (
  `id` bigint(20) NOT NULL,
  `purchase_date` datetime(6) DEFAULT NULL,
  `quantity_purchased` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `buyer_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_entity`
--

INSERT INTO `order_entity` (`id`, `purchase_date`, `quantity_purchased`, `total_price`, `buyer_id`, `product_id`, `company_id`) VALUES
(1, '2025-07-03 11:06:01.000000', 1, 3000, 1, 1, 52),
(2, '2025-07-05 10:04:17.000000', 1, 3000, 1, 1, 52),
(3, '2025-07-09 14:52:31.000000', 4, 14400, 1, 2, 52),
(4, '2025-07-09 14:59:45.000000', 2, 6000, 1, 1, 52),
(5, '2025-07-09 14:59:45.000000', 1, 3600, 1, 2, 52),
(6, '2025-07-09 22:52:29.000000', 1, 2200, 53, 3, 52),
(7, '2025-07-09 23:26:51.000000', 2, 12800, 53, 4, 102),
(8, '2025-07-09 23:26:51.000000', 2, 7200, 53, 2, 52),
(9, '2025-07-10 10:29:56.000000', 1, 6400, 153, 4, 102),
(10, '2025-07-10 10:30:26.000000', 1, 3000, 153, 1, 52),
(11, '2025-07-10 12:59:18.000000', 2, 12800, 53, 8, 202),
(12, '2025-07-10 13:00:09.000000', 1, 7800, 53, 9, 202),
(13, '2025-07-10 13:00:09.000000', 3, 10800, 53, 2, 52);

-- --------------------------------------------------------

--
-- Table structure for table `product_entity`
--

CREATE TABLE `product_entity` (
  `id` bigint(20) NOT NULL,
  `category` enum('COMPUTERS','SMARTPHONES','TV') DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product_entity`
--

INSERT INTO `product_entity` (`id`, `category`, `description`, `image_url`, `is_deleted`, `name`, `price`, `quantity`, `company_id`, `owner_id`) VALUES
(1, 'SMARTPHONES', 'The new Galaxy S25 smartphone', 'https://d3m9l0v76dty0.cloudfront.net/system/photos/16055063/large/bbef84938836077679bd9db0cd07faac.png', b'0', 'Galaxy S25', 3000, 395, 52, NULL),
(2, 'TV', '60 TV Inch', 'https://zabilo.com/9531-product_page_img/lg-smart-tv-60-inches-4k-uhd-60uj630y.jpg', b'0', 'Samasung TV 60 Inch', 3600, 790, 52, NULL),
(3, 'SMARTPHONES', 'The S24+', 'https://media.ldlc.com/r1600/ld/products/00/06/20/43/LD0006204316.jpg', b'0', 'Galaxy S24+', 2200, 239, 52, NULL),
(4, 'COMPUTERS', 'The new i7 14700k Intel Computer', 'https://1pc.co.il/images/thumbs/0103104_-alef-gaming-advanced-intel-i7-12700-16gb-ddr5-1tb-ssd-rtx-4060-8gb.jpeg', b'0', 'i7 14700k Intel Computer', 6400, 197, 102, NULL),
(5, 'COMPUTERS', 'The new Lenovo computer with i7 13700 and RTX 4060', 'https://p4-ofp.static.pub//fes/cms/2024/09/13/dd4vhu710wcfkfupql12h9ez8in455698990.png', b'0', 'Lenovo Laptop i7', 5400, 120, 154, NULL),
(6, 'TV', 'The new Lenovo 42 inch TV Screen', 'https://p2-ofp.static.pub//fes/cms/2024/09/23/y95n0p2j2iatajdv6ntj28n6jpv8vs971291.png', b'0', 'Lenovo 42 inch TV Screen', 2600, 600, 154, NULL),
(7, 'COMPUTERS', 'The new Samsung Laptop i7 13700', 'https://m.media-amazon.com/images/I/616V0DjnYRL._UF894,1000_QL80_.jpg', b'0', 'Samsung Laptop i7', 4800, 280, 52, NULL),
(8, 'SMARTPHONES', 'The new Iphone pro max 15', 'https://d2d22nphq0yz8t.cloudfront.net/6cbcadef-96e0-49e9-b3bd-9921afe362db/www.shekem-electric.co.il/media/catalog/product/i/p/iphone_15_pro_blue_titanium_pdp_image_position-1__wwen_3_1_1.jpg', b'0', 'Iphone Pro Max 15', 6400, 598, 202, NULL),
(9, 'COMPUTERS', 'The New Mac Laptop', 'https://www.apple.com/newsroom/images/product/mac/standard/Apple_16-inch-MacBook-Pro_111319_big.jpg.large.jpg', b'0', 'The New Mac Laptop', 7800, 199, 202, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `review_entity`
--

CREATE TABLE `review_entity` (
  `id` bigint(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `rating` int(11) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `reviewer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `review_entity`
--

INSERT INTO `review_entity` (`id`, `comment`, `created_at`, `rating`, `product_id`, `reviewer_id`) VALUES
(1, '', '2025-07-03 11:06:15.000000', 5, 1, 1),
(2, 'This is a good computer.', '2025-07-10 10:30:14.000000', 4, 4, 153),
(3, 'This smartphone is great!', '2025-07-10 10:30:47.000000', 5, 1, 153),
(4, 'This is a good TV.', '2025-07-10 12:48:49.000000', 4, 2, 1),
(5, '', '2025-07-10 12:59:34.000000', 5, 8, 53),
(6, 'I love this big TV!', '2025-07-10 13:00:29.000000', 5, 2, 53);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `base_user`
--
ALTER TABLE `base_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_entity`
--
ALTER TABLE `order_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2uhd0lxhdqaotx3nfprjeuwtu` (`buyer_id`),
  ADD KEY `FKemr10n6per25uyrtkkjvckjk1` (`product_id`),
  ADD KEY `FKodaqobxivywoukbsbp66eyika` (`company_id`);

--
-- Indexes for table `product_entity`
--
ALTER TABLE `product_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfoghm35n6pimnv7sdamps4lo` (`company_id`),
  ADD KEY `FK47h9hyubmi4d22glt1vbe0tv6` (`owner_id`);

--
-- Indexes for table `review_entity`
--
ALTER TABLE `review_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg4ym5b02ri77hx2g4ukc09mga` (`product_id`),
  ADD KEY `FK4m1uy815yyjtv4hn1pog15mtx` (`reviewer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `order_entity`
--
ALTER TABLE `order_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `product_entity`
--
ALTER TABLE `product_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `review_entity`
--
ALTER TABLE `review_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order_entity`
--
ALTER TABLE `order_entity`
  ADD CONSTRAINT `FK2uhd0lxhdqaotx3nfprjeuwtu` FOREIGN KEY (`buyer_id`) REFERENCES `base_user` (`id`),
  ADD CONSTRAINT `FKemr10n6per25uyrtkkjvckjk1` FOREIGN KEY (`product_id`) REFERENCES `product_entity` (`id`),
  ADD CONSTRAINT `FKodaqobxivywoukbsbp66eyika` FOREIGN KEY (`company_id`) REFERENCES `base_user` (`id`);

--
-- Constraints for table `product_entity`
--
ALTER TABLE `product_entity`
  ADD CONSTRAINT `FK47h9hyubmi4d22glt1vbe0tv6` FOREIGN KEY (`owner_id`) REFERENCES `base_user` (`id`),
  ADD CONSTRAINT `FKfoghm35n6pimnv7sdamps4lo` FOREIGN KEY (`company_id`) REFERENCES `base_user` (`id`);

--
-- Constraints for table `review_entity`
--
ALTER TABLE `review_entity`
  ADD CONSTRAINT `FK4m1uy815yyjtv4hn1pog15mtx` FOREIGN KEY (`reviewer_id`) REFERENCES `base_user` (`id`),
  ADD CONSTRAINT `FKg4ym5b02ri77hx2g4ukc09mga` FOREIGN KEY (`product_id`) REFERENCES `product_entity` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
