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
}