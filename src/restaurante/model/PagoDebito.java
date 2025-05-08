package restaurante.model;

public class PagoDebito implements Pago {
    private String numTarjeta;
    private String banco;

    public PagoDebito(String numTarjeta, String banco) {
        this.numTarjeta = numTarjeta;
        this.banco = banco;
    }

    @Override
    public void procesarPago() {
        if (validarTarjeta()) {
            System.out.println("Procesando pago con tarjeta de débito...");
            System.out.println("Banco: " + banco);
            System.out.println("Número de tarjeta: **** **** **** " + numTarjeta.substring(numTarjeta.length() - 4));
            System.out.println("Pago procesado exitosamente.");
        } else {
            System.out.println("Error: Datos de la tarjeta de débito no válidos.");
        }
    }

    private boolean validarTarjeta() {
        // Validar que el número de tarjeta tenga 16 dígitos y que el banco no esté vacío
        return numTarjeta != null && numTarjeta.matches("\\d{16}") && banco != null && !banco.isEmpty();
    }

    // Getters y setters
    public String getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
}