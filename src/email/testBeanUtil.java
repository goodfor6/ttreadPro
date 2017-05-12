
package email;

import org.springframework.beans.BeanUtils;

import entity.TestBO;
import entity.TestDTO;
import util.BeanUtil;

/**
 * @author luolianhuan
 *
 */
public class testBeanUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestBO testBO=new TestBO();
		testBO.setGid("123");
//		testBO.setName("jack");
		TestDTO testDTO=new TestDTO();
		BeanUtil.copyProperties(testBO, testDTO);
		BeanUtils.copyProperties(testBO, testDTO);
		System.out.println("gid:"+testDTO.getGid()+" name:"+testDTO.getName());

	}

}
