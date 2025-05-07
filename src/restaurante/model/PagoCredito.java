package restaurante.model;

public class PagoCredito implements Pago {
    private String numTarjeta;
    private String vencimiento;

    @Override
    public void procesarPago() {
        // Lógica para procesar el pago con tarjeta de crédito
    }
}