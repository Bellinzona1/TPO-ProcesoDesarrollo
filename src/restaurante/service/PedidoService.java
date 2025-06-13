package restaurante.service;

import restaurante.model.Cliente;
import restaurante.model.EstadoPedido;
import restaurante.model.Pedido;
import restaurante.model.Producto;

import java.util.List;

public class PedidoService {
    public void realizarPedido(Cliente cliente, Pedido pedido) {
        // Lógica de negocio para crear un pedido
        cliente.realizarPedido(pedido);
    }

    public void cambiarEstadoPedido(Pedido pedido, EstadoPedido estado) {
        // Lógica de negocio para cambiar el estado del pedido
        pedido.cambiarEstado(estado);
    }

    public boolean cancelarPedido(Pedido pedido, NotificacionService notificacionService) {
        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return false;
        }
        if (pedido.getEstado() == EstadoPedido.EN_ESPERA || pedido.getEstado() == EstadoPedido.EN_PREPARACION) {
            boolean cancelado = pedido.cancelar();
            if (cancelado) {
                System.out.println("Pedido cancelado. Monto a reembolsar: $" + pedido.getMontoReembolsado());
                String mensaje = "Su pedido #" + pedido.getNumeroOrden() + " ha sido cancelado. Monto a reembolsar: $" + pedido.getMontoReembolsado();
                notificacionService.notificarCambioEstado(pedido.getCliente(), mensaje);
            }
            return cancelado;
        } else {
            System.out.println("No se puede cancelar el pedido. Solo se pueden cancelar pedidos en espera o en preparación.");
            return false;
        }
    }

    public int calcularTiempoEstimado(Pedido pedido, int pedidosActivosEnLocal, int tiempoRappi) {
        return pedido.calcularTiempoEstimado(pedidosActivosEnLocal, tiempoRappi);
    }

}