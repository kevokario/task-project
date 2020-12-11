-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 11, 2020 at 10:17 AM
-- Server version: 8.0.22-0ubuntu0.20.04.2
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `education`
--

-- --------------------------------------------------------

--
-- Table structure for table `academiclevel`
--

CREATE TABLE `academiclevel` (
  `academiclevelid` int NOT NULL,
  `course` int DEFAULT NULL,
  `level` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `academicyear`
--

CREATE TABLE `academicyear` (
  `academicyearid` int NOT NULL,
  `academiclevel` int DEFAULT NULL,
  `year` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `courseid` int NOT NULL,
  `name` varchar(250) NOT NULL,
  `institution` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`courseid`, `name`, `institution`) VALUES
(37, 'Bsc Computer Science', 19),
(38, 'Bsc Information Technology', 19),
(39, 'DIT Computer Science', 19),
(40, 'Bsc Computer Science', 37),
(41, 'Bsc Computer Science and Mathematics', 37),
(42, 'CIT Information Technology', 37),
(43, 'Bsc Mathematics', 6),
(44, 'CIT Information Technology', 6),
(45, 'Bsc. computer Science', 6),
(48, 'Bsc. Aeronotical Engineering', 6),
(49, 'Mathews Course 101', 6),
(50, 'Bsc. Computer Science', 8),
(51, 'Masters Computer Science', 8),
(52, 'Bsc Computer Science', 55),
(56, 'Bsc Computer Science', 15),
(57, 'D Computer Science', 15),
(58, 'Bsc Computer Science', 47),
(59, 'Bsc Computer Science', 57),
(60, 'Bsc Information Technology', 57),
(61, 'xcxcc', 12),
(62, 'Dusk', 82),
(63, 'Computer Science', 82),
(64, 'Bsc Computer Science', 82),
(65, 'mock course', 57),
(66, 'Bsc Computer Science', 84),
(67, 'Bsc Information Technology', 84),
(68, 'Bsc Mathematics', 84),
(69, 'Bsc Computer Science', 89),
(70, 'Bsc Information Technology', 89),
(71, 'Bsc Chemistry', 6),
(72, 'Cit Computer Science', 6),
(73, 'Bed Education Science', 6),
(74, 'Bsc Education Science', 6),
(75, 'BIT Education Science', 6),
(76, 'MIT Information Technology', 6),
(77, 'Msc Computer Science', 6),
(80, 'Bsc Computer Science', 87),
(81, 'Bsc Comuter Science', 87),
(82, 'Bsc Information Technology', 87),
(83, 'CIT Information Technology', 87),
(84, 'Mit Information Technology', 87),
(85, 'DIT Information Technology', 87),
(87, 'Bsc computer science', 80),
(88, 'BIT Computer Science', 57),
(90, 'Bsc Information Technology', 37),
(91, 'Sample Course', 37),
(95, 'Bsc. Computer Science', 90),
(96, 'Sample 101', 37),
(97, 'Bsc Computer Science', 91),
(98, 'Bsc Information Technology', 91),
(99, 'Bsc Computer Science', 93),
(100, 'CIT Information technology', 93),
(101, 'Computer Science', 95),
(102, 'Bsc Information Technology', 95);

-- --------------------------------------------------------

--
-- Table structure for table `institution`
--

CREATE TABLE `institution` (
  `institutionid` int NOT NULL,
  `name` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `institution`
--

INSERT INTO `institution` (`institutionid`, `name`) VALUES
(6, 'University of Leinkister'),
(8, 'University of Eldoret'),
(12, 'University of East Africa'),
(15, 'Daystar University'),
(19, 'University of Nairobi'),
(21, 'University of Addis Ababa'),
(22, 'Stockholm University'),
(36, 'Oxford University'),
(37, 'Joan University'),
(38, 'Justin University'),
(39, 'Okalhoma University'),
(40, 'makerere university'),
(41, 'makerere university'),
(43, 'Strathmore University'),
(47, 'Kemri University'),
(50, 'Maseno University College'),
(55, 'Njeri University'),
(57, 'Zeraki University'),
(75, 'Sevi'),
(76, 'University of London'),
(77, 'Consolatta University'),
(78, 'Cameroon university'),
(79, 'Dedan Kimathi Unicersity'),
(80, 'JKUAT'),
(81, 'Meru University'),
(82, 'University of india'),
(84, 'Kibabii University'),
(87, 'Kabarak University'),
(88, 'Kambridge University'),
(89, 'Karatina University'),
(90, 'Karatina University College'),
(91, 'Zeraki Institute'),
(93, 'Zeraki Institute of Science'),
(95, 'Test University');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `studentid` int NOT NULL,
  `name` varchar(250) NOT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  `course` int NOT NULL,
  `updated_on` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentid`, `name`, `date_of_birth`, `course`, `updated_on`) VALUES
(63, 'Kelvin Kario Muthoni', '1995-01-18 00:00:00', 42, '2020-12-10 09:33:01'),
(64, 'Sean Kario', '2019-01-18 00:00:00', 44, '2020-11-30 02:16:01'),
(66, 'Welcome Home', '2010-12-04 00:00:00', 40, '2020-12-10 09:46:02'),
(67, 'Joan', '2012-03-21 00:00:00', 40, '2020-11-28 06:10:53'),
(68, 'Wesley', '2020-11-01 00:00:00', 41, '2020-11-28 06:19:47'),
(69, 'MrashMellow', '2020-11-11 00:00:00', 41, '2020-11-28 06:24:12'),
(70, 'john doe', '2013-06-11 00:00:00', 42, '2020-11-28 06:33:21'),
(71, 'Jane Doe', '2020-11-12 00:00:00', 40, '2020-11-28 06:35:50'),
(72, 'Johnathan Smith', '2000-12-04 00:00:00', 40, '2020-11-29 04:26:05'),
(73, 'John doe', '2000-01-02 00:00:00', 40, '2020-11-29 04:36:41'),
(76, 'John smith', '2009-02-12 00:00:00', 40, '2020-11-29 05:00:00'),
(78, 'Tomas', '2020-11-17 00:00:00', 42, '2020-11-29 04:39:45'),
(80, 'Jane Doe', '1973-01-01 00:00:00', 50, '2020-11-28 22:32:48'),
(84, 'Sean Kario Njeri', '2019-09-01 00:00:00', 38, '2020-12-10 10:43:12'),
(85, 'john doe', '2018-10-16 00:00:00', 51, '2020-12-07 18:12:14'),
(86, 'Smith Doe', '1987-10-07 00:00:00', 37, '2020-11-30 08:23:10'),
(87, 'jane doe', '1990-10-10 00:00:00', 39, '2020-11-30 08:35:42'),
(89, 'welcome', '1984-12-05 00:00:00', 49, '2020-12-01 09:34:40'),
(90, 'Kario', '1979-12-05 00:00:00', 45, '2020-12-02 03:31:02'),
(91, 'maths doe', '2018-11-14 00:00:00', 43, '2020-12-02 03:31:57'),
(92, 'Dit', '2013-01-17 00:00:00', 48, '2020-12-04 10:40:57'),
(93, 'nikie Junior', '2020-04-18 00:00:00', 43, '2020-12-02 03:35:28'),
(94, 'Smith Doe', '2020-09-09 00:00:00', 43, '2020-12-03 07:14:46'),
(95, 'Jane Doe', '2014-06-11 00:00:00', 58, '2020-12-03 05:19:27'),
(96, 'Samson Doe', '2015-02-04 00:00:00', 43, '2020-12-03 07:15:13'),
(97, 'Calvin Doe', '2020-04-14 00:00:00', 44, '2020-12-03 07:16:03'),
(98, 'Jane Doe', '2009-01-18 00:00:00', 42, '2020-12-04 13:44:57'),
(99, 'Pius Wachira', '2020-12-01 00:00:00', 66, '2020-12-08 07:03:53'),
(100, 'Samuel Ndegwa', '2020-12-02 00:00:00', 66, '2020-12-08 07:04:05'),
(101, 'Kamau Ndungu', '2020-12-01 00:00:00', 67, '2020-12-08 07:04:20'),
(102, 'Victor Githiri', '2020-12-04 00:00:00', 66, '2020-12-08 07:04:38'),
(103, 'Kario Kelvin', '2020-12-05 00:00:00', 66, '2020-12-08 07:04:51'),
(104, 'doe deo', '2020-12-01 00:00:00', 43, '2020-12-09 05:33:23'),
(105, 'Jojo junior', '2020-12-01 00:00:00', 45, '2020-12-09 05:35:29'),
(106, 'Kiki Junior', '2020-12-02 00:00:00', 45, '2020-12-09 05:35:45'),
(107, 'John Doe', '2020-12-06 00:00:00', 80, '2020-12-09 07:19:37'),
(108, 'Jojo fifi', '2020-12-07 00:00:00', 81, '2020-12-09 06:06:26'),
(109, 'Smith Doe', '2020-12-24 00:00:00', 91, '2020-12-10 01:55:10');

-- --------------------------------------------------------

--
-- Table structure for table `unit`
--

CREATE TABLE `unit` (
  `unitid` int NOT NULL,
  `academicyear` int DEFAULT NULL,
  `semester` varchar(25) DEFAULT NULL,
  `unitcode` varchar(10) DEFAULT NULL,
  `unitname` varchar(30) DEFAULT NULL,
  `unitpoint` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `academiclevel`
--
ALTER TABLE `academiclevel`
  ADD PRIMARY KEY (`academiclevelid`),
  ADD KEY `course` (`course`);

--
-- Indexes for table `academicyear`
--
ALTER TABLE `academicyear`
  ADD PRIMARY KEY (`academicyearid`),
  ADD KEY `academiclevel` (`academiclevel`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`courseid`),
  ADD KEY `institution` (`institution`);

--
-- Indexes for table `institution`
--
ALTER TABLE `institution`
  ADD PRIMARY KEY (`institutionid`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentid`),
  ADD KEY `course` (`course`);

--
-- Indexes for table `unit`
--
ALTER TABLE `unit`
  ADD PRIMARY KEY (`unitid`),
  ADD KEY `unit_ibfk_1` (`academicyear`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `academiclevel`
--
ALTER TABLE `academiclevel`
  MODIFY `academiclevelid` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `academicyear`
--
ALTER TABLE `academicyear`
  MODIFY `academicyearid` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `courseid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- AUTO_INCREMENT for table `institution`
--
ALTER TABLE `institution`
  MODIFY `institutionid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `studentid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=115;

--
-- AUTO_INCREMENT for table `unit`
--
ALTER TABLE `unit`
  MODIFY `unitid` int NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `academiclevel`
--
ALTER TABLE `academiclevel`
  ADD CONSTRAINT `academiclevel_ibfk_1` FOREIGN KEY (`course`) REFERENCES `course` (`courseid`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `academicyear`
--
ALTER TABLE `academicyear`
  ADD CONSTRAINT `academicyear_ibfk_1` FOREIGN KEY (`academiclevel`) REFERENCES `academiclevel` (`academiclevelid`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `course_ibfk_1` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionid`);

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`course`) REFERENCES `course` (`courseid`);

--
-- Constraints for table `unit`
--
ALTER TABLE `unit`
  ADD CONSTRAINT `unit_ibfk_1` FOREIGN KEY (`academicyear`) REFERENCES `academicyear` (`academicyearid`) ON DELETE RESTRICT ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
