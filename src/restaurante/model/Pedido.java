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
    private String plataforma; // "TOTEM" o "APP"
    private boolean cuponValidado;
    private boolean notificacionEmpleado;
    private java.time.LocalDateTime fechaProgramada; // Para pedidos programados
    private boolean esProgramado;
    private boolean esDelivery;
    private int tiempoPreparacionEstimado; // en minutos
    private boolean cancelado;
    private double montoReembolsado;

    // Constructor
    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.estado = EstadoPedido.EN_ESPERA; // Estado inicial del pedido
        this.total = 0;
        this.numeroOrden = contadorOrdenes.getAndIncrement();
    }

    // Constructor extendido
    public Pedido(int id, Cliente cliente, String plataforma, boolean esDelivery) {
        this(id, cliente);
        this.plataforma = plataforma;
        this.esDelivery = esDelivery;
        this.cuponValidado = !"TOTEM".equalsIgnoreCase(plataforma); // No valida cupones en tótem
        this.notificacionEmpleado = !"TOTEM".equalsIgnoreCase(plataforma); // No notifica en tótem
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


    // Método para agregar productos (con restricción de estado)
    public void agregarProducto(Producto producto) {
        if (!puedeAgregarProducto()) {
            throw new IllegalStateException("Solo se pueden agregar productos en estado EN_ESPERA");
        }
        this.productos.add(producto);
        this.total += producto.obtenerPrecio();
    }

    // Programar pedido
    public void programarPara(java.time.LocalDateTime fecha) {
        this.fechaProgramada = fecha;
        this.esProgramado = true;
    }


    // Cálculo de tiempo estimado
    public int calcularTiempoEstimado(int pedidosActivosEnLocal, int tiempoRappi) {
        if (estado == EstadoPedido.EN_ESPERA) {
            if (pedidosActivosEnLocal >= 10) {
                int extra = ((pedidosActivosEnLocal - 10) / 10) * 5;
                tiempoPreparacionEstimado = 20 + extra;
            } else {
                tiempoPreparacionEstimado = 5;
            }
        } else if (estado == EstadoPedido.EN_PREPARACION) {
            int suma = 0;
            for (Producto p : productos) {
                suma += p.obtenerTiempoPreparacion(); // Debe agregarse este método a Producto
            }
            tiempoPreparacionEstimado = suma;
        }
        if (esDelivery) {
            tiempoPreparacionEstimado += Math.max(0, tiempoRappi); // Si la API no responde, tiempoRappi=0
        }
        return tiempoPreparacionEstimado;
    }

    // Cancelar pedido
    public boolean cancelar() {
        if (estado == EstadoPedido.EN_ESPERA || estado == EstadoPedido.EN_PREPARACION) {
            estado = EstadoPedido.CANCELADO;
            cancelado = true;
            montoReembolsado = total * 0.75;
            return true;
        }
        return false;
    }

    public boolean puedeAgregarProducto() {
        return estado == EstadoPedido.EN_ESPERA;
    }

    // Pago con descuento efectivo
    public void aplicarDescuentoEfectivo() {
        this.total = this.total * 0.9;
    }

    // Getters y setters extendidos
    public String getPlataforma() { return plataforma; }
    public java.time.LocalDateTime getFechaProgramada() { return fechaProgramada; }
    public boolean isEsProgramado() { return esProgramado; }
    public double getMontoReembolsado() { return montoReembolsado; }


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