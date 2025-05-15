package restaurante.service;

import restaurante.model.Cliente;
import restaurante.model.EstadoPedido;
import restaurante.model.Pedido;

public class PedidoService {
    public void realizarPedido(Cliente cliente, Pedido pedido) {
        // Lógica de negocio para crear un pedido
        cliente.realizarPedido(pedido);
    }

    public void cambiarEstadoPedido(Pedido pedido, EstadoPedido estado) {
        // Lógica de negocio para cambiar el estado del pedido
        pedido.cambiarEstado(estado);
    }
}