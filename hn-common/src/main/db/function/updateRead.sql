CREATE PROCEDURE `readProcedure`(IN `auid` varchar(32),IN `cat_id` int)
    COMMENT '用于给当前用户指定消息类型的未读消息加上已读记录'
BEGIN
	DECLARE messageId int ;
	DECLARE is_open int DEFAULT 1;
	DECLARE open_time VARCHAR(32);
	DECLARE del_flg int DEFAULT 0;
	DECLARE done int DEFAULT false;
	DECLARE appoint int ;

	DECLARE cur cursor for
			select
			t.id  messageId,t.appoint
			from app_message t
			WHERE
			t.category_id=cat_id and t.appoint=0
			AND NOT EXISTS (
				select rel.message_id from app_message_relation rel where rel.auid=auid and rel.is_open=1
			)
			group by t.id;



	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = true;
	SET open_time = date_format(now(),'%Y%m%d%H%i%s');

	update app_message_relation set open_time =open_time,is_open=1 where  app_message_relation.auid=auid
	and message_id in (select message.id from app_message message where message.category_id=cat_id and message.appoint=1 );

	open cur;
		REPEAT
			FETCH cur into messageId,appoint;

				if done=false then

					if appoint= 0 then

					INSERT INTO app_message_relation (message_id,auid,is_open,del_flg,open_time)values(messageId,auid,is_open,del_flg,open_time);


					end if;
				end if;
			UNTIL done
		end REPEAT;
	close cur;


END