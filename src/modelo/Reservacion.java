package modelo;

import java.sql.Date;


public class Reservacion {
	
	private Integer id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private Double valor;
	private String formaPago;
	
	
	public Reservacion(java.util.Date fechaEntrada, java.util.Date fechaSalida, Double valor, String formaPago) {
		this.fechaEntrada = new Date(fechaEntrada.getTime());
		this.fechaSalida = new Date(fechaSalida.getTime());
		this.valor = valor;
		this.formaPago = formaPago;
	}
	
	public Reservacion(Integer id, Date fechaEntrada, Date fechaSalida, Double valor, String formaPago) {
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getFechaEntrada() {
		return fechaEntrada;
	}


	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}


	public Date getFechaSalida() {
		return fechaSalida;
	}


	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}


	public Double getValor() {
		return valor;
	}


	public void setValor(Double valor) {
		this.valor = valor;
	}


	public String getFormaPago() {
		return formaPago;
	}


	
	
	
	
	
	
	

}
