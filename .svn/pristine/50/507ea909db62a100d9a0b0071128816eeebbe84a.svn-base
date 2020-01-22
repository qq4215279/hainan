ALTER TABLE `cms_article` DROP INDEX `search_index`;
ALTER TABLE `cms_article`
ADD INDEX `search_index` (`title` ASC, `published_time` ASC, `create_time` ASC,`district_code` ASC,`sort` ASC,`status` ASC);
ALTER TABLE `api_sort_index` DROP INDEX `search_index`;
ALTER TABLE `cms_article`
ADD INDEX `api_sort_index` (`sort` DESC, `published_time` DESC);

ALTER TABLE `app_message` DROP INDEX `search_index` ;
ALTER TABLE `app_message`
ADD INDEX `search_index` (`category_id` ASC, `operation_mode` ASC, `appoint` ASC);

ALTER TABLE `app_integral` DROP INDEX `search_index` ;
ALTER TABLE `app_integral`
ADD INDEX `search_index` (`auid` ASC, `type` ASC);

ALTER TABLE `app_integral_task_log` DROP INDEX `search_index` ;
ALTER TABLE `app_integral_task_log`
ADD INDEX `search_index` (`auid` ASC, `task_id` ASC);

ALTER TABLE `app_message_relation` DROP INDEX `search_index` ;
ALTER TABLE `app_message_relation`
ADD INDEX `search_index` (`message_id` ASC, `auid` ASC);


ALTER TABLE `app_user` DROP INDEX `search_index` ;
ALTER TABLE `app_user`
ADD INDEX `search_index` (`account` ASC);

ALTER TABLE `dept_member` DROP INDEX `search_index` ;
ALTER TABLE `dept_member`
ADD INDEX `search_index` (`station_card` ASC);

ALTER TABLE `dept_member_apply` DROP INDEX `search_index` ;
ALTER TABLE `dept_member_apply`
ADD INDEX `search_index` (`auid` ASC);

ALTER TABLE `dept_member_transfer` DROP INDEX `search_index` ;
ALTER TABLE `dept_member_transfer`
ADD INDEX `search_index` (`member_id` ASC, `auid` ASC);

ALTER TABLE `dept_organization` DROP INDEX `search_index` ;
ALTER TABLE `dept_organization`
ADD INDEX `search_index` (`unit_name` ASC, `unit_org_code` ASC, `others_org_code` ASC, `p_name` ASC, `union_simple_name` ASC, `union_name` ASC);

ALTER TABLE `person_info` DROP INDEX `certificate_num_index` ;
ALTER TABLE `person_info`
ADD INDEX `search_index` (`certificate_num` ASC, `name` ASC);

ALTER TABLE `r_article_category` DROP INDEX `certificate_num_index` ;
ALTER TABLE `r_article_category`
ADD INDEX `search_index` (`article_id` ASC, `category_id` ASC, `published_time` ASC, `pub_status` ASC);


ALTER TABLE `cms_article_operation_log` DROP INDEX `search_index` ;
ALTER TABLE `cms_article_operation_log`
ADD INDEX `search_index` (`auid` ASC, `article_id` ASC, `create_time` ASC, `type` ASC);