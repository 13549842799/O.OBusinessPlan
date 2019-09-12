package create;

import com.oo.businessplan.article.pojo.entity.Portion;
import com.oo.businessplan.article.pojo.entity.Section;
import com.oo.businessplan.upload.pojo.UploadFile;

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
	@SuppressWarnings("rawtypes")
	public void beanTool(BeanUtils beanUtils, Class<Section> c, String idName, Class type) throws Exception {

		beanUtils.createBeanControl(c);
		beanUtils.createBeanService(c);
		beanUtils.createBeanServiceImpl(c);
		beanUtils.createBeanMapper(c);
		beanUtils.createBeanXml(c, idName, type);

	}
}
