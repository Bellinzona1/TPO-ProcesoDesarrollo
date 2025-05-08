package restaurante.model;

public class PagoCredito implements Pago {
    private String numTarjeta;
    private String vencimiento;

    public PagoCredito(String numTarjeta, String vencimiento) {
        this.numTarjeta = numTarjeta;
        this.vencimiento = vencimiento;
    }

    @Override
    public void procesarPago() {
        if (validarTarjeta()) {
            System.out.println("Procesando pago con tarjeta de crédito...");
            System.out.println("Número de tarjeta: **** **** **** " + numTarjeta.substring(numTarjeta.length() - 4));
            System.out.println("Pago procesado exitosamente.");
        } else {
            System.out.println("Error: Datos de la tarjeta de crédito no válidos.");
        }
    }

    private boolean validarTarjeta() {
        // Validar que el número de tarjeta tenga 16 dígitos y que el vencimiento no esté vacío
        return numTarjeta != null && numTarjeta.matches("\\d{16}") && vencimiento != null && !vencimiento.isEmpty();
    }

    // Getters y setters
    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }
}