package restaurante.controller;

import restaurante.model.Cliente;
import restaurante.model.EstadoPedido;
import restaurante.model.Pedido;
import restaurante.service.PedidoService;

public class PedidoController {
    private PedidoService pedidoService;

    public PedidoController() {
        this.pedidoService = new PedidoService();
    }

    public void realizarPedido(Cliente cliente, Pedido pedido) {
        pedidoService.realizarPedido(cliente, pedido);
    }

    public void cambiarEstadoPedido(Pedido pedido, EstadoPedido estado) {
        pedidoService.cambiarEstadoPedido(pedido, estado);
    }

    public void programarPedido(Pedido pedido, java.time.LocalDateTime fecha) {
        pedido.programarPara(fecha);
    }

    public boolean cancelarPedido(Pedido pedido) {
        return pedido.cancelar();
    }

    public void agregarProductoAPedido(Pedido pedido, restaurante.model.Producto producto) {
        pedido.agregarProducto(producto);
    }

    public int calcularTiempoEstimado(Pedido pedido, int pedidosActivosEnLocal, int tiempoRappi) {
        return pedido.calcularTiempoEstimado(pedidosActivosEnLocal, tiempoRappi);
    }

    public void aplicarDescuentoEfectivo(Pedido pedido) {
        pedido.aplicarDescuentoEfectivo();
    }
}