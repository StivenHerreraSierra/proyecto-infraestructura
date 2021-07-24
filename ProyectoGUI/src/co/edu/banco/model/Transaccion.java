package co.edu.banco.model;

import java.time.format.DateTimeFormatter;

public class Transaccion {
	public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	private TipoTransaccion tipo;
	private String fechaHora;
	
	public Transaccion(TipoTransaccion tipo, String fechaHora) {
		this.tipo = tipo;
		this.fechaHora = fechaHora;
	}
	
	public Transaccion() {
		
	}

	public TipoTransaccion getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransaccion tipo) {
		this.tipo = tipo;
	}

	public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	@Override
	public String toString() {
		return "[" + tipo + "," + fechaHora + "]";
	}
}
