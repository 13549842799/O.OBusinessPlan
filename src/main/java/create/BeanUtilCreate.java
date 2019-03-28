package create;

import com.oo.businessplan.admin.pojo.entity.AcceptMessage;
import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.pojo.entity.SendMessage;
import com.oo.businessplan.article.pojo.entity.Classify;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.article.pojo.entity.Inspiration;
import com.oo.businessplan.article.pojo.entity.Novel;
import com.oo.businessplan.article.pojo.entity.Portion;
import com.oo.businessplan.article.pojo.entity.Section;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.Resource;

public class BeanUtilCreate {

	public static void main(String[] args) throws Exception {
		BeanUtilCreate beanUtilCreate = new BeanUtilCreate();
		BeanUtils beanUtils = new BeanUtils();


		beanUtils.setAuthorName("cyz");//作者
		beanUtils.setTableName("section");//表名
		beanUtils.setTableDes("小说章节表");//表信息
		beanUtilCreate.beanTool(beanUtils,Section.class,"id",Integer.class);// 运行生产后，需要刷新工程
		
		
	}

	/**
	 * 根据bean生成相应的文件
	 * 
	 * @param beanUtils
	 * @param c
	 * @throws Exception
	 */
	public void beanTool(BeanUtils beanUtils, Class<Section> c, String idName, Class type) throws Exception {

		beanUtils.createBeanControl(c);
		beanUtils.createBeanService(c);
		beanUtils.createBeanServiceImpl(c);
		beanUtils.createBeanMapper(c);
		beanUtils.createBeanXml(c, idName, type);

	}
}
