package factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource dataSorce;
	
	public ConnectionFactory() {
		ComboPooledDataSource pooleanDataSourse = new ComboPooledDataSource();
		pooleanDataSourse.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimeZone=true&serverTimeZone=UTC");
		pooleanDataSourse.setUser("root");
		pooleanDataSourse.setPassword("Mata2005J");
		pooleanDataSourse.setMaxPoolSize(10);
		
		this.dataSorce = pooleanDataSourse;
	}

	public Connection recuperaConexion()  {
	 
		try {
			return this.dataSorce.getConnection();
		} catch (SQLException e) {
			
			throw new RuntimeException(e);
			
		}
		
	}

}
