package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import modelo.Huesped;

public class HuespedDAO {
	
	final private Connection con;
	
	public HuespedDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Huesped huesped) {

		try {

			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO HUESPED(nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reservacion)"
					+ " VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			try (statement) {
				ejecutarRegistro(huesped, statement);
			}

		} catch (SQLException e) {

			throw new RuntimeException(e);
		}

	}
	
	private void ejecutarRegistro(Huesped huesped, PreparedStatement statement) throws SQLException {

		statement.setString(1, huesped.getNombre());
		statement.setString(2, huesped.getApellido());
		statement.setDate(3, (Date) huesped.getFechaNacimiento());
		statement.setString(4, huesped.getNacionalidad());
		statement.setString(5, huesped.getTelefono());
		statement.setInt(6, huesped.getIdReserva());

		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();

		try (resultSet) {// Cierra automaticamente los recursos

			while (resultSet.next()) {
				huesped.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado el huesped %s", huesped));
			}

		}

	}
	
	public int modificar (String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, Integer idHuesped) {
		
		try {
			
			final PreparedStatement statement = con.prepareStatement("UPDATE HUESPED SET "
					+ "NOMBRE = ?, APELLIDO = ?, FECHA_NACIMIENTO = ?, NACIONALIDAD = ?, TELEFONO = ? "
					+ "WHERE ID = ?");
			
			try(statement){
				
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setDate(3, fechaNacimiento);
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				
				statement.setInt(6, idHuesped);
				
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

			final PreparedStatement statement = con.prepareStatement("DELETE FROM HUESPED WHERE ID = ?");

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
	

	public List<Huesped> listar() {
		
		List<Huesped> resultado = new ArrayList<>();
		
		try {
			PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, APELLIDO, "
					+ "FECHA_NACIMIENTO, NACIONALIDAD, TELEFONO, ID_RESERVACION FROM HUESPED");
			try(statement){
				listaHuespedes(resultado, statement);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return resultado;
	}
	
	public List<Huesped> listaPorApellido(String apellido) {

		
		List<Huesped> resultado = new ArrayList<>();

		try {
			 String querySelect = "SELECT ID, NOMBRE, APELLIDO, "
						+ "FECHA_NACIMIENTO, NACIONALIDAD, TELEFONO, ID_RESERVACION FROM HUESPED WHERE APELLIDO=?";
			
			final PreparedStatement statement = con.prepareStatement(querySelect);
			try (statement) {
				
				statement.setString(1, apellido);
				
				listaHuespedes(resultado, statement);
			}

		} catch (SQLException e) {
			throw new RuntimeException();
		}

		return resultado;
	}

	private void listaHuespedes(List<Huesped> resultado, final PreparedStatement statement) throws SQLException {
		statement.execute();
		
		final ResultSet resultSet = statement.getResultSet();

		try (resultSet) {
			while (resultSet.next()) {
				 Huesped huesped = new Huesped(resultSet.getInt("ID"), resultSet.getString("NOMBRE"), 
							resultSet.getString("APELLIDO"), resultSet.getDate("FECHA_NACIMIENTO"), 
							resultSet.getString("NACIONALIDAD"), resultSet.getString("TELEFONO"), 
							resultSet.getInt("ID_RESERVACION"));
					
				resultado.add(huesped);
			}
		}
	}

}
