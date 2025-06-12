package restaurante.service;

import restaurante.model.Cliente;
import restaurante.model.EstadoPedido;
import restaurante.model.Pedido;
import restaurante.model.Producto;

public class PedidoService {
    public void realizarPedido(Cliente cliente, Pedido pedido) {
        // Lógica de negocio para crear un pedido
        cliente.realizarPedido(pedido);
    }

    public void cambiarEstadoPedido(Pedido pedido, EstadoPedido estado) {
        // Lógica de negocio para cambiar el estado del pedido
        pedido.cambiarEstado(estado);
    }

    public void programarPedido(Pedido pedido, java.time.LocalDateTime fecha) {
        pedido.programarPara(fecha);
    }

    public boolean cancelarPedido(Pedido pedido) {
        return pedido.cancelar();
    }

    public void agregarProductoAPedido(Pedido pedido, Producto producto) {
        pedido.agregarProducto(producto);
    }

    public int calcularTiempoEstimado(Pedido pedido, int pedidosActivosEnLocal, int tiempoRappi) {
        return pedido.calcularTiempoEstimado(pedidosActivosEnLocal, tiempoRappi);
    }

    public void aplicarDescuentoEfectivo(Pedido pedido) {
        pedido.aplicarDescuentoEfectivo();
    }
}