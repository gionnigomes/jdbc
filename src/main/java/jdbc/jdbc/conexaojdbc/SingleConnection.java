package jdbc.jdbc.conexaojdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	
	private static String url = "jdbc:mysql://localhost:3306/projectjava?serverTimeZone=UTC";
	private static String user = "root";
	private static String password = "root";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnection () {
		conectar();
	}

	private static Connection conectar() {
		try {
			if (connection == null) {
				connection = DriverManager.getConnection(url,user,password);
				connection.setAutoCommit(false);
				System.out.println("Banco de dados conectado com sucesso.");
				return connection;
			}
		} catch (Exception e) {
			System.out.println("Erro ao tentar conectar ao banco de dados.");
			return null;
		}
		return connection;
		
	}
	
	public static Connection getConnection() {
		return connection;
	}
	

}
