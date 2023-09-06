package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Reservacion;

public class ReservasDAO {

	final private Connection con;

	public ReservasDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Reservacion reservacion) {
		try {
			final PreparedStatement statement = con.prepareStatement(""
					+ "INSERT INTO RESERVACION(fecha_entrada, fecha_salida, valor, forma_pago) "
					+ "VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				ejecutarRegistro(reservacion, statement);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void ejecutarRegistro(Reservacion reservacion, PreparedStatement statement) throws SQLException {
		statement.setDate(1, (Date) reservacion.getFechaEntrada());
		statement.setDate(2, (Date) reservacion.getFechaSalida());
		statement.setDouble(3, reservacion.getValor());
		statement.setString(4, reservacion.getFormaPago());

		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();
		try (resultSet) {
			while (resultSet.next()) {
				reservacion.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado la resevacion %s", reservacion));
			}
		}
	}
	
	
	public int modificar (Date fechaEntrada, Date fechaSalida, Double valor, String formaPago, Integer idReservacion) {
		
		try {
			
			final PreparedStatement statement = con.prepareStatement("UPDATE RESERVACION SET "
					+ "FECHA_ENTRADA = ?, FECHA_SALIDA = ?, VALOR = ?, FORMA_PAGO = ? "
					+ "WHERE ID = ?");
			
			try(statement){
				
				statement.setDate(1, fechaEntrada);
				statement.setDate(2, fechaSalida);
				statement.setDouble(3, valor);
				statement.setString(4, formaPago);
				
				
				statement.setInt(5, idReservacion);
				
				statement.execute();
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public int eliminar(Integer id) {

		try {

			final PreparedStatement statement = con.prepareStatement("DELETE FROM RESERVACION WHERE ID = ?");

			try (statement) {

				statement.setInt(1, id);
				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	
	public List<Reservacion> listar() {
		
		List<Reservacion> resultado = new ArrayList<>();
		
		try {
			final PreparedStatement statement = con.prepareStatement(
"SELECT ID, FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO FROM RESERVACION");
			
			try (statement) {
				
				listaReservaciones(resultado, statement);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return resultado;
	}
	
	
public List<Reservacion> listarPorIdReservacion(Integer idReservacion) {
		
		List<Reservacion> resultado = new ArrayList<>();
		
		try {
			final PreparedStatement statement = con.prepareStatement(
"SELECT ID, FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO FROM RESERVACION WHERE ID = ?");
			
			try (statement) {
				
				statement.setInt(1, idReservacion);
				
				listaReservaciones(resultado, statement);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return resultado;
	}

private void listaReservaciones(List<Reservacion> resultado, final PreparedStatement statement) throws SQLException {
	statement.execute();
	
	final ResultSet resultSet = statement.getResultSet();
	
	try (resultSet){
		while(resultSet.next()) {
			
			Reservacion fila = new Reservacion(resultSet.getInt("ID"), resultSet.getDate("FECHA_ENTRADA"), 
					resultSet.getDate("FECHA_SALIDA"), resultSet.getDouble("VALOR"), 
					resultSet.getString("FORMA_PAGO"));
			
			resultado.add(fila);
		}
	}
}

}
