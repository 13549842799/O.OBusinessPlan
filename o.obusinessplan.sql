DROP TRIGGER IF EXISTS opForInsertEmp;
DELIMITER &
CREATE TRIGGER opForInsertEmp AFTER INSERT ON employee FOR EACH ROW
BEGIN
DECLARE tree VARCHAR(20);
DECLARE i INT;
SELECT treeIds INTO tree FROM department WHERE did=new.departmentId;
UPDATE department SET `count`=`count`+1 WHERE FIND_IN_SET(did,treeIds);
SET i = 1000+new.eid;
UPDATE employee SET ecode=CONCAT('OB',i) WHERE eid=new.eid;
END &
DELIMITER ;