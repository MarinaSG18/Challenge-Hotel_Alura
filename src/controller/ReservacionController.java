package controller;

import java.sql.Date;
import java.util.List;

import DAO.ReservasDAO;
import factory.ConnectionFactory;
import modelo.Reservacion;

public class ReservacionController {
	
	private ReservasDAO reservacionDAO;
	
	public ReservacionController() {
		this.reservacionDAO = new  ReservasDAO(
				new ConnectionFactory().recuperaConexion()
				);
	}

	public void guardar(Reservacion reservacion) {
		reservacionDAO.guardar(reservacion);
		
	}
	
	public int modificar(java.util.Date fechaEntrada, java.util.Date fechaSalida, Double valor, String formaPago, Integer idReservacion) {
		
		return reservacionDAO.modificar(new Date(fechaEntrada.getTime()), new Date(fechaSalida.getTime()), valor, formaPago, idReservacion);
	}
	
	public int eliminar(Integer id) {
		return reservacionDAO.eliminar(id);
	}
	
	public List<Reservacion> listar() {
		return reservacionDAO.listar();
	}

	public List<Reservacion> listar(Integer idReservacion) {
		
		 return reservacionDAO.listarPorIdReservacion(idReservacion);
	}

}
