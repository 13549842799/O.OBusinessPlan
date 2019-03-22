package create;

import com.oo.businessplan.admin.pojo.entity.AcceptMessage;
import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.pojo.entity.SendMessage;
import com.oo.businessplan.authority.pojo.Authority;
import com.oo.businessplan.authority.pojo.Resource;

public class BeanUtilCreate {

	public static void main(String[] args) throws Exception {
		BeanUtilCreate beanUtilCreate = new BeanUtilCreate();
		BeanUtils beanUtils = new BeanUtils();


		beanUtils.setAuthorName("cyz");//作者
		beanUtils.setTableName("acceptmessage");//表名
		beanUtils.setTableDes("消息接收表");//表信息
		beanUtilCreate.beanTool(beanUtils,AcceptMessage.class);// 运行生产后，需要刷新工程
		
		
	}

	/**
	 * 根据bean生成相应的文件
	 * 
	 * @param beanUtils
	 * @param c
	 * @throws Exception
	 */
	public void beanTool(BeanUtils beanUtils, Class c) throws Exception {

		//beanUtils.createBeanControl(c);
		//beanUtils.createBeanService(c);
		//beanUtils.createBeanServiceImpl(c);
		//beanUtils.createBeanMapper(c);
		beanUtils.createBeanXml(c);

	}
}
