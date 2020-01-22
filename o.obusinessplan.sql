/*
 * 自动创建职员的标识
 * */
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

/**
 * 章节插入的触发器
 * 目的是修改关联的分卷记录的合计章节数和字数，修改关联的小说记录的合计字数，修改小说的状态为连载中,更新小说的最新章更新为本章
 */
DROP TRIGGER IF EXISTS alterForInsertSection;
DELIMITER &
CREATE TRIGGER alterForInsertSection AFTER INSERT ON section FOR EACH ROW
BEGIN
UPDATE portion SET wordsNum = wordsNum + new.wordsNum, sectionNum = sectionNum + 1 WHERE id = new.portionId;
UPDATE novel SET wordsNum = wordsNum + new.wordsNum, sectionsNum = sectionsNum + 1, lastetSectionId = new.id WHERE id = new.novelId;
UPDATE novel SET novelState = 1 WHERE id = new.novelId AND novelState != 3;
END &
DELIMITER ;

/**
 * 章节更新的触发器
 * 如果章节的字数修改了，则对应更新分卷的总字数和小说的总字数，此时不更新小说状态
 */
DROP TRIGGER IF EXISTS alterForUpdateSection;
DELIMITER &
CREATE TRIGGER alterForUpdateSection AFTER UPDATE ON section FOR EACH ROW
BEGIN
DECLARE wordsDis INT;
SET wordsDis = new.wordsNum - old.wordsNum;
IF wordsDis != 0 THEN
UPDATE portion SET wordsNum = wordsNum + wordsDis WHERE id = new.portionId;
UPDATE novel SET wordsNum = wordsNum + wordsDis WHERE id = new.novelId;
END IF;
END &
DELIMITER ;

/**
 * 章节删除的触发器
 * 目的是修改关联的分卷记录的合计章节数和字数，修改关联的小说记录的合计字数,备份删除的章节
 */
DROP TRIGGER IF EXISTS alterForDeleteSection;
DELIMITER &
CREATE TRIGGER alterForDeleteSection AFTER DELETE ON section FOR EACH ROW
BEGIN
UPDATE portion SET wordsNum = wordsNum - old.wordsNum, sectionNum = sectionNum - 1 WHERE id = old.portionId;
UPDATE novel SET wordsNum = wordsNum - old.wordsNum, sectionsNum = sectionsNum - 1 WHERE id = old.novelId;
INSERT INTO section_del VALUES(old.id, old.title, old.content, old.novelId, old.portionId, old.wordsNum, old.creator, old.createTime, old.modifier, old.modifierTime, old.remark, old.number, old.lastSection, old.nextSection);
END &
DELIMITER ;