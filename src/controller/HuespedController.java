package controller;

import java.sql.Date;
import java.util.List;

import DAO.HuespedDAO;
import factory.ConnectionFactory;
import modelo.Huesped;

public class HuespedController {
	
	private HuespedDAO huespedDAO;
	
	public HuespedController() {
		this.huespedDAO = new HuespedDAO(new ConnectionFactory().recuperaConexion());

	}

	public void guardar(Huesped huesped) {
		
		huespedDAO.guardar(huesped);
	}
	
	
	public int modificar (String nombre, String apellido, java.util.Date fechaNacimiento, String nacionalidad, String telefono, Integer idHuesped) {
		return huespedDAO.modificar(nombre, apellido, new Date(fechaNacimiento.getTime()), nacionalidad, telefono, idHuesped);
	}
	
	public int eliminar (Integer id) {
		return huespedDAO.eliminar(id);
	}
	
	public List<Huesped> listar(){
		
		return huespedDAO.listar();
	}
	
public List<Huesped> listarPorApellido(String apellido){
		
		return huespedDAO.listaPorApellido(apellido);

}

}
