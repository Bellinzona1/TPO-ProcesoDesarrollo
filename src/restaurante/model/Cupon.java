package restaurante.model;

public class Cupon {
    private String codigo;
    private double descuento;

    public Cupon(String codigo, double descuento) {
        this.codigo = codigo;
        this.descuento = descuento;
    }

    public void aplicarDescuento(Pedido pedido) {
        if (pedido != null && descuento > 0) {
            double nuevoTotal = Math.max(0, pedido.getTotal() - descuento); // Evitar totales negativos
            pedido.setTotal(nuevoTotal);
            System.out.println("Cupón aplicado. Descuento: $" + descuento + ". Nuevo total: $" + nuevoTotal);
        } else {
            System.out.println("No se pudo aplicar el cupón. Verifique los datos.");
        }
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    // Agregar validar

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
