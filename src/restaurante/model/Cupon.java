package restaurante.model;

public class Cupon {
    private String codigo;
    private double descuento;

    public void aplicarDescuento(Pedido pedido) {
        // Lógica para aplicar descuento
    }

    public double getDescuento() {
        return descuento;
    }
}
