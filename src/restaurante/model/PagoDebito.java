package restaurante.model;

public class PagoDebito implements Pago {
    private String numTarjeta;
    private String banco;

    @Override
    public void procesarPago() {
        // Lógica para procesar el pago con tarjeta de débito
    }
}