package restaurante.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private List<Producto> productos;
    private Cliente cliente;
    private EstadoPedido estado;
    private double total;
    private Cupon cupon;
    private Pago pago;
    private Factura factura;

    // Constructor
    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.estado = EstadoPedido.EN_ESPERA; // Estado inicial del pedido
        this.total = 0;
    }

    // Método para calcular el total
    public double calcularTotal() {
        double total = 0;
        for (Producto producto : productos) {
            total += producto.obtenerPrecio();
        }
        this.total = total;

        return total;
    }

    // Método para cambiar el estado
    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Método para aplicar un cupón
    public void aplicarCupon(Cupon cupon) {
        // Lógica para aplicar el cupón y ajustar el total
        if (cupon != null) {
            this.total -= cupon.getDescuento();
        }
    }

    // Método para generar la factura
    public void generarFactura() {
        // Crear una factura a partir del pedido
        factura = new Factura();
    }

    // Método para agregar productos
    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    // Getters
    public List<Producto> getProductos() {
        return productos;
    }

    public double getTotal() {
        return total;
    }

    public EstadoPedido getEstado() {
        return estado;
    }
}
