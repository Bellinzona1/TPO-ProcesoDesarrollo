package restaurante.controller;

import restaurante.model.Cliente;
import restaurante.model.Pedido;

public class ClienteController {
    public void realizarPedido(Cliente cliente, Pedido pedido) {
        cliente.realizarPedido(pedido);
    }

    public void recibirNotificacion(Cliente cliente) {
        // Lógica para que el cliente reciba notificaciones
    }
}