package test1;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.Test;

import com.oo.businessplan.article.pojo.entity.Classify;

public class Test2 {

	
	@Test
	public void testType(){
		long t = System.currentTimeMillis();
		Timestamp tt = new Timestamp(t);
		System.out.println(tt);
	}
	
	@Test
	public void testMacth() {
		String str ="哈哈abc123哈哈哈";
		System.out.println(str.replaceAll("[\\d\\w]", ""));
	}
	
	@Test
	public void testDate() {
		LocalDate start = LocalDate.of(2019, 10, 15);
		LocalDate end = LocalDate.of(2020, 3, 13);
		long daysDiff = ChronoUnit.MONTHS.between(start, end);
		
		System.out.println(daysDiff);
	}
	
	@Test
	public void testMat() {
		System.out.println((3/1));
	}
}
