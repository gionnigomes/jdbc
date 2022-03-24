package jdbc.jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import jdbc.jdbc.dao.UserDAO;
import jdbc.jdbc.model.BeanUserPhone;
import jdbc.jdbc.model.Phone;
import jdbc.jdbc.model.User;

public class UserPhoneTest {
	
	
	@Test
	public void initBanco() {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setEmail("gasd");
		user.setId(222);
		userDAO.save(user);
	}

	@Test
	public void delete() {
		try {
			UserDAO userDAO = new UserDAO();
			userDAO.delete(11);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void update() {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		try {
			user = userDAO.selectById(7);
			user.setName("Gael Teste UPDATE");
			userDAO.update(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void savePhone() {
		Phone phone = new Phone();
		phone.setNumberPhone("(81)2012-2122");
		phone.setTypePhone("Mobile");
		phone.setUserId(9);
		UserDAO userDAO = new UserDAO();
		userDAO.savePhone(phone);
	}
	
	@Test
	public void testLoadPhoneUser() {
		UserDAO userDAO = new UserDAO();
		List<BeanUserPhone> beanUserPhones = userDAO.listUserPhone(5);
		for (BeanUserPhone beanUserPhone: beanUserPhones) {
			System.out.println(beanUserPhone);
		}
		
	}
	
	@Test
	public void deletePhoneByUser() {
		UserDAO userDAO = new UserDAO();
		userDAO.deletePhoneByUser(5);
	}

}
