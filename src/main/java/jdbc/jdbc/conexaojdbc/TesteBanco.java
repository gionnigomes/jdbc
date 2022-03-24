package jdbc.jdbc.conexaojdbc;

import java.sql.SQLException;
import java.util.List;

import jdbc.jdbc.dao.UserDAO;
import jdbc.jdbc.model.User;

public class TesteBanco {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		SingleConnection connection = new SingleConnection();
		connection.getConnection();
	
		UserDAO userDAO = new UserDAO();
		try {
			List<User> users = userDAO.select();
			System.out.println("Usuarios:");
			for (int i=0; i < users.size(); i++) {
				System.out.println("ID: " + users.get(i).getId() + "\n" 
							+ "Name:" + users.get(i).getName() + "\t" 
							+ "Email:" + users.get(i).getEmail());
			}
		} catch (SQLException e) {
			System.err.println("Unexpected error trying to list users.");
			e.printStackTrace();
		}
		
	}

}
