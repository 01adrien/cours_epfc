


DROP TABLE IF EXISTS `block_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `block_fields` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `session_block_id` bigint(20) unsigned NOT NULL,
  `block_type_field_id` bigint(20) unsigned NOT NULL,
  `data` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `unit_id` bigint(20) unsigned DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `block_fields_session_block_id_foreign` (`session_block_id`),
  KEY `block_fields_block_type_field_id_foreign` (`block_type_field_id`),
  KEY `block_fields_unit_id_foreign` (`unit_id`),
  CONSTRAINT `block_fields_block_type_field_id_foreign` FOREIGN KEY (`block_type_field_id`) REFERENCES `block_type_fields` (`id`) ON DELETE CASCADE,
  CONSTRAINT `block_fields_session_block_id_foreign` FOREIGN KEY (`session_block_id`) REFERENCES `session_blocks` (`id`) ON DELETE CASCADE,
  CONSTRAINT `block_fields_unit_id_foreign` FOREIGN KEY (`unit_id`) REFERENCES `units` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64848 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `block_field_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `block_field_types` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `label` enum('DURATION','DISTANCE','REPETITION','RECOVERY_DISTANCE','RECOVERY_DURATION','RECOVERY_INTENSITY','INTENSITY','DISTANCE_OR_DURATION','EXERCICE') NOT NULL,
  `icon` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `block_field_type_unit`
--

DROP TABLE IF EXISTS `block_field_type_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `block_field_type_unit` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `block_field_type_id` bigint(20) unsigned NOT NULL,
  `unit_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `block_field_type_unit_block_field_type_id_foreign` (`block_field_type_id`),
  KEY `block_field_type_unit_unit_id_foreign` (`unit_id`),
  CONSTRAINT `block_field_type_unit_block_field_type_id_foreign` FOREIGN KEY (`block_field_type_id`) REFERENCES `block_field_types` (`id`) ON DELETE CASCADE,
  CONSTRAINT `block_field_type_unit_unit_id_foreign` FOREIGN KEY (`unit_id`) REFERENCES `units` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `block_results`
--

DROP TABLE IF EXISTS `block_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `block_results` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `session_block_id` bigint(20) unsigned NOT NULL,
  `duration` int(11) NOT NULL,
  `distance` int(11) NOT NULL,
  `index` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `block_results_session_block_id_foreign` (`session_block_id`),
  CONSTRAINT `block_results_session_block_id_foreign` FOREIGN KEY (`session_block_id`) REFERENCES `session_blocks` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `block_type_children`
--

DROP TABLE IF EXISTS `block_type_children`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `block_type_children` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `parent_type_id` bigint(20) unsigned NOT NULL,
  `child_type_id` bigint(20) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `block_type_children_parent_type_id_foreign` (`parent_type_id`),
  KEY `block_type_children_child_type_id_foreign` (`child_type_id`),
  CONSTRAINT `block_type_children_child_type_id_foreign` FOREIGN KEY (`child_type_id`) REFERENCES `block_types` (`id`) ON DELETE CASCADE,
  CONSTRAINT `block_type_children_parent_type_id_foreign` FOREIGN KEY (`parent_type_id`) REFERENCES `block_types` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `block_type_fields`
--

DROP TABLE IF EXISTS `block_type_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `block_type_fields` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `block_type_id` bigint(20) unsigned NOT NULL,
  `block_field_type_id` bigint(20) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `default_value` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `block_type_fields_block_type_id_foreign` (`block_type_id`),
  KEY `block_type_fields_block_field_type_id_foreign` (`block_field_type_id`),
  CONSTRAINT `block_type_fields_block_field_type_id_foreign` FOREIGN KEY (`block_field_type_id`) REFERENCES `block_field_types` (`id`) ON DELETE CASCADE,
  CONSTRAINT `block_type_fields_block_type_id_foreign` FOREIGN KEY (`block_type_id`) REFERENCES `block_types` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `block_types`
--

DROP TABLE IF EXISTS `block_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `block_types` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `color` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `coach_id` bigint(20) unsigned DEFAULT NULL,
  `share` tinyint(1) NOT NULL,
  `organization_id` bigint(20) unsigned DEFAULT NULL,
  `training_type` enum('RUNNING','CONDITIONING') NOT NULL DEFAULT 'RUNNING',
  PRIMARY KEY (`id`),
  KEY `block_types_organization_id_foreign` (`organization_id`),
  KEY `block_types_coach_id_foreign` (`coach_id`),
  CONSTRAINT `block_types_coach_id_foreign` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`id`) ON DELETE SET NULL,
  CONSTRAINT `block_types_organization_id_foreign` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `session_block_exercices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_block_exercices` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `session_block_id` bigint(20) unsigned NOT NULL,
  `order` int(11) NOT NULL DEFAULT 1,
  `label` varchar(255) NOT NULL,
  `repetitions` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `session_block_exercices_session_block_id_foreign` (`session_block_id`),
  CONSTRAINT `session_block_exercices_session_block_id_foreign` FOREIGN KEY (`session_block_id`) REFERENCES `session_blocks` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=323 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `session_block_options`
--

DROP TABLE IF EXISTS `session_block_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_block_options` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `session_block_id` bigint(20) unsigned NOT NULL,
  `heart_rate_zone` tinyint(3) unsigned DEFAULT NULL,
  `load` int(10) unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `session_block_options_session_block_id_unique` (`session_block_id`),
  CONSTRAINT `session_block_options_session_block_id_foreign` FOREIGN KEY (`session_block_id`) REFERENCES `session_blocks` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `session_block_templates`
--

DROP TABLE IF EXISTS `session_block_templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_block_templates` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `session_block_id` bigint(20) unsigned DEFAULT NULL,
  `coach_id` bigint(20) unsigned DEFAULT NULL,
  `organization_id` bigint(20) unsigned NOT NULL,
  `description` text DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `share` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `session_block_templates_session_block_id_unique` (`session_block_id`),
  KEY `session_block_templates_coach_id_foreign` (`coach_id`),
  KEY `session_block_templates_organization_id_foreign` (`organization_id`),
  CONSTRAINT `session_block_templates_coach_id_foreign` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`id`) ON DELETE SET NULL,
  CONSTRAINT `session_block_templates_organization_id_foreign` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE CASCADE,
  CONSTRAINT `session_block_templates_session_block_id_foreign` FOREIGN KEY (`session_block_id`) REFERENCES `session_blocks` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `session_blocks`
--

DROP TABLE IF EXISTS `session_blocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_blocks` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `sessionable_id` bigint(20) unsigned DEFAULT NULL,
  `sessionable_type` varchar(255) DEFAULT NULL,
  `block_type_id` bigint(20) unsigned NOT NULL,
  `parent_id` bigint(20) unsigned DEFAULT NULL,
  `order` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `is_restored` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `session_blocks_block_type_id_foreign` (`block_type_id`),
  KEY `session_blocks_parent_id_foreign` (`parent_id`),
  CONSTRAINT `session_blocks_block_type_id_foreign` FOREIGN KEY (`block_type_id`) REFERENCES `block_types` (`id`) ON DELETE CASCADE,
  CONSTRAINT `session_blocks_parent_id_foreign` FOREIGN KEY (`parent_id`) REFERENCES `session_blocks` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7181 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `session_types`
--

DROP TABLE IF EXISTS `session_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_types` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `color` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `coach_id` bigint(20) unsigned DEFAULT NULL,
  `share` tinyint(1) NOT NULL,
  `organization_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `session_types_organization_id_foreign` (`organization_id`),
  KEY `session_types_coach_id_foreign` (`coach_id`),
  CONSTRAINT `session_types_coach_id_foreign` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`id`) ON DELETE SET NULL,
  CONSTRAINT `session_types_organization_id_foreign` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `id` varchar(255) NOT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `payload` longtext NOT NULL,
  `last_activity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sessions_user_id_index` (`user_id`),
  KEY `sessions_last_activity_index` (`last_activity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `training_plans`
--

DROP TABLE IF EXISTS `training_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_plans` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `coach_id` bigint(20) unsigned DEFAULT NULL,
  `share` tinyint(1) NOT NULL,
  `organization_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `training_plans_organization_id_foreign` (`organization_id`),
  KEY `training_plans_coach_id_foreign` (`coach_id`),
  CONSTRAINT `training_plans_coach_id_foreign` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`id`) ON DELETE SET NULL,
  CONSTRAINT `training_plans_organization_id_foreign` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `training_session_templates`
--

DROP TABLE IF EXISTS `training_session_templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_session_templates` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `session_type_id` bigint(20) unsigned DEFAULT NULL,
  `coach_id` bigint(20) unsigned DEFAULT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `share` tinyint(1) NOT NULL,
  `organization_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `training_session_templates_session_type_id_foreign` (`session_type_id`),
  KEY `training_session_templates_organization_id_foreign` (`organization_id`),
  KEY `training_session_templates_coach_id_foreign` (`coach_id`),
  CONSTRAINT `training_session_templates_coach_id_foreign` FOREIGN KEY (`coach_id`) REFERENCES `coaches` (`id`) ON DELETE SET NULL,
  CONSTRAINT `training_session_templates_organization_id_foreign` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE CASCADE,
  CONSTRAINT `training_session_templates_session_type_id_foreign` FOREIGN KEY (`session_type_id`) REFERENCES `session_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `training_sessions`
--

DROP TABLE IF EXISTS `training_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_sessions` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `training_plan_id` bigint(20) unsigned DEFAULT NULL,
  `week_number` int(11) NOT NULL,
  `day_number` int(11) NOT NULL,
  `session_type_id` bigint(20) unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `training_sessions_training_plan_id_foreign` (`training_plan_id`),
  KEY `training_sessions_session_type_id_foreign` (`session_type_id`),
  CONSTRAINT `training_sessions_session_type_id_foreign` FOREIGN KEY (`session_type_id`) REFERENCES `session_types` (`id`),
  CONSTRAINT `training_sessions_training_plan_id_foreign` FOREIGN KEY (`training_plan_id`) REFERENCES `training_plans` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1483 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `units` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `symbol` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_training_plans`
--

DROP TABLE IF EXISTS `user_training_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_training_plans` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `vma` decimal(5,2) NOT NULL,
  `start_date` date NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `label` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `pulled_at` timestamp NULL DEFAULT NULL,
  `pushed_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_training_plans_user_id_foreign` (`user_id`),
  CONSTRAINT `user_training_plans_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_training_sessions`
--

DROP TABLE IF EXISTS `user_training_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_training_sessions` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_training_plan_id` bigint(20) unsigned DEFAULT NULL,
  `comment` text DEFAULT NULL,
  `session_type_id` bigint(20) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `week_number` int(11) DEFAULT NULL,
  `day_number` int(11) DEFAULT NULL,
  `feedback` int(11) DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `is_restored` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `user_training_sessions_user_training_plan_id_foreign` (`user_training_plan_id`),
  KEY `user_training_sessions_session_type_id_foreign` (`session_type_id`),
  CONSTRAINT `user_training_sessions_session_type_id_foreign` FOREIGN KEY (`session_type_id`) REFERENCES `session_types` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_training_sessions_user_training_plan_id_foreign` FOREIGN KEY (`user_training_plan_id`) REFERENCES `user_training_plans` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1612 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--DROP TABLE IF EXISTS `organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;

