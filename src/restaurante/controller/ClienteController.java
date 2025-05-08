package restaurante.controller;

import restaurante.model.Cliente;
import restaurante.model.Pedido;
import restaurante.model.Notificacion;

public class ClienteController {
    public void realizarPedido(Cliente cliente, Pedido pedido) {
        cliente.realizarPedido(pedido);
    }

    public void recibirNotificacion(Cliente cliente, Notificacion notificacion) {
        cliente.recibirNotificacion(notificacion);
    }
}