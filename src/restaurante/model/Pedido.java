package restaurante.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Pedido {
    private static final AtomicInteger contadorOrdenes = new AtomicInteger(1);
    private int numeroOrden;
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
        this.numeroOrden = contadorOrdenes.getAndIncrement();
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
        if (cupon != null) {
            double descuento = cupon.getDescuento();
            this.total = Math.max(0, this.total - descuento); // Evitar totales negativos
        }
    }

    // Método para generar la factura
    public void generarFactura() {
        // Asegurarse de que el total esté actualizado antes de generar la factura
        calcularTotal();

        // Crear una factura a partir del pedido
        factura = new Factura(this);

        // Generar el PDF y enviarlo por correo
        factura.generarPDF();
        factura.enviarPorEmail();
    }

    // Método para agregar productos
    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
        this.total += producto.obtenerPrecio(); // Actualizar el total al agregar un producto
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

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setTotal(double nuevoTotal) {
        this.total = nuevoTotal;
    }
}