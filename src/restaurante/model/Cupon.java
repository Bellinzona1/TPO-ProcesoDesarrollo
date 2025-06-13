package restaurante.model;

public class Cupon {
    private String codigo;
    private double descuento;

    public Cupon(String codigo, double descuento) {
        this.codigo = codigo;
        this.descuento = descuento;
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
