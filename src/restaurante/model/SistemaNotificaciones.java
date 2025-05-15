package restaurante.model;

import java.util.ArrayList;
import java.util.List;

public class SistemaNotificaciones {
    private List<Notificacion> notificaciones;

    public SistemaNotificaciones() {
        this.notificaciones = new ArrayList<>();
    }

    public void notificarCambioEstado(Pedido pedido) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje("El estado de su pedido #" + pedido.getNumeroOrden() + " ha cambiado a: " + pedido.getEstado());
        notificacion.setDestinatario(pedido.getCliente());
        notificacion.enviar();
        notificaciones.add(notificacion);
    }
}