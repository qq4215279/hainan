delimiter $$

CREATE  FUNCTION `getMessageUnreadCount`(auid varchar(32),createTime varchar(14),categoryId int ) RETURNS int(11)
BEGIN
	 DECLARE unReadCount1 int DEFAULT 0;
   DECLARE unReadCount2 int DEFAULT 0;

      SELECT
		COUNT(0) INTO unReadCount1
      FROM
      app_message_relation AS R
      INNER JOIN app_message AS M ON R.message_id = M.id
      WHERE
      IFNULL(R.is_open, 0) = 0
      AND R.auid = auid
      AND M.category_id =categoryId
      AND M.appoint = 1;



	select
	COUNT(0) INTO unReadCount2
	from app_message AS M
	WHERE M.create_time>=createTime
	AND M.appoint=0
	AND M.category_id =categoryId
	AND NOT EXISTS(SELECT 1 FROM app_message_relation AS R WHERE R.auid=auid AND IFNULL(R.is_open, 0) = 1 AND R.message_id=M.id );


RETURN  unReadCount1+ unReadCount2;


END$$
delimiter ;