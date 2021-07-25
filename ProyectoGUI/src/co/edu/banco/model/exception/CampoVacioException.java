
package co.edu.banco.model.exception;

public class CampoVacioException extends Exception {
	private static final long serialVersionUID = 1L;

	public CampoVacioException(String mensaje) {
		super(mensaje);
	}
}
