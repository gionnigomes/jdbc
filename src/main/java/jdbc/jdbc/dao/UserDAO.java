package jdbc.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import jdbc.jdbc.conexaojdbc.SingleConnection;
import jdbc.jdbc.model.BeanUserPhone;
import jdbc.jdbc.model.Phone;
import jdbc.jdbc.model.User;

public class UserDAO {

	private Connection connection;

	private final static String USER_TABLE = "USER";
	private final static String PHONEUSER_TABLE = "PHONEUSER";

	public UserDAO() {
		connection = SingleConnection.getConnection();
	}

	public List<User> select() throws SQLException {
		String sql = "SELECT * FROM " + USER_TABLE;
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);

		List<User> users = new ArrayList<User>();

		while (result.next()) {
			User user = new User();
			user.setId(result.getInt("id"));
			user.setName(result.getString("name"));
			user.setEmail(result.getString("email"));
			users.add(user);
		}

		return users;
	}

	public User selectById(int id) throws SQLException {
		String sql = "SELECT * FROM " + USER_TABLE + " WHERE ID  = " + id;
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		User user = new User();
		while (result.next()) {
			user.setId(result.getInt("id"));
			user.setName(result.getString("name"));
			user.setEmail(result.getString("email"));
		}

		return user;
	}

	public boolean validId(ResultSet result, int id) {
		boolean existId = false;
		try {
			while (result.next()) {
				int idExist = result.getInt("id");
				if (id == idExist) {
					existId = true;
					return existId;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public boolean checkExistingId(int id) throws SQLException {
		String sql = "SELECT * FROM " + USER_TABLE + " WHERE ID = " + id;
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		boolean existId = false;
		existId = validId(result, id);
		return existId;
	}

	public void save(User user) {
		String sql = "INSERT INTO " + USER_TABLE + " (NAME, EMAIL) VALUES (?,?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, user.getName());
			insert.setString(2, user.getEmail());
			insert.execute();
			connection.commit();
			int rowInserted = insert.executeUpdate();
			if (rowInserted > 0) {
				System.out.println("Usuário " + user.getName() + " cadastrado com sucesso.");
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public void savePhone(Phone phone) {
		try {

			String sql = "INSERT INTO PHONEUSER (NUMBERPHONE, TYPEPHONE, USERPERSON) VALUES (?,?,?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, phone.getNumberPhone());
			statement.setString(2, phone.getTypePhone());
			statement.setInt(3, phone.getUserId());
			statement.execute();
			connection.commit();
			System.out.println("Phone add.");

		} catch (Exception e) {
			try {
				e.printStackTrace();
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
	}

	public void delete(Integer id) throws SQLException {
		try {
			String sql = "DELETE FROM " + USER_TABLE + " WHERE ID = " + id;
			PreparedStatement delete = connection.prepareStatement(sql);
			boolean verifyId = checkExistingId(id);
			if (verifyId) {
				delete.execute();
				connection.commit();
				System.out.println("ID " + id + "  successfully deleted");
			} else {
				System.out.println("ID " + id + " has not been deleted.");
			}

		} catch (Exception e) {
			try {
				System.out.println("Unexpected error trying to delete id. Rollback apply.");
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void update(User user) {
		String sql = "UPDATE " + USER_TABLE + " SET NAME = ?, EMAIL = ? WHERE ID = " + user.getId();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			int rowUpdate = statement.executeUpdate();
			if (rowUpdate > 0) {
				connection.commit();
				System.err.println("User successfully updated.");
			} else {
				System.err.println("User has not been updated.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<BeanUserPhone> listUserPhone(Integer idUser) {
		List<BeanUserPhone> listUserP = new ArrayList<BeanUserPhone>();
		String sql = "SELECT NAME, NUMBERPHONE, EMAIL FROM " + PHONEUSER_TABLE + " AS PHONE";
		sql += " INNER JOIN USER ";
		sql += " ON PHONE.USERPERSON = USER.ID ";
		sql += " WHERE USER.ID = " + idUser;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				BeanUserPhone beanUserPhone = new BeanUserPhone();
				beanUserPhone.setName(resultSet.getString("name"));
				beanUserPhone.setEmail(resultSet.getString("email"));
				beanUserPhone.setNumberPhone(resultSet.getString("numberphone"));
				listUserP.add(beanUserPhone);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listUserP;

	}

	public void deletePhoneByUser(Integer idUser) {
		String sqlPhone = "DELETE FROM " + PHONEUSER_TABLE + " WHERE USERPERSON = " + idUser;
		String sqlUser = "DELETE FROM " + USER_TABLE + " WHERE ID = " + idUser;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlPhone);
			if(checkExistingId(idUser)) {
				preparedStatement.executeUpdate();
				connection.commit();
				System.out.println("User ID " + idUser + " successfully deleted");
				
				preparedStatement = connection.prepareStatement(sqlUser);
				preparedStatement.executeUpdate();
				connection.commit();
				System.out.println("UserPhone ID " + idUser + " successfully deleted");
			} else {
				System.out.println("User ID " + idUser + " not found. Has not been deleted.");
			}
			
	
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				System.out.println("User ID " + idUser + " has not been deleted.");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

}
