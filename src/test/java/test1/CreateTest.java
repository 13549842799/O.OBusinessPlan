package test1;

import com.oo.businessplan.basic.entity.IdEntity;

public class CreateTest {

	public static void main(String[] args) {
		System.out.println(IdEntity.class.getSuperclass() == Object.class);
	}

}
