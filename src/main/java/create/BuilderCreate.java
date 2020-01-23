package create;

import com.oo.businessplan.upload.pojo.UploadFile;

public class BuilderCreate {

	public static void main(String[] args) {

		BeanUtils beanUtils = new BeanUtils();
		try {
			beanUtils.createBeanBuilder(UploadFile.class, "id", Long.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
