package restaurante.service;

import restaurante.model.Cliente;
import restaurante.model.Notificacion;

public class NotificacionService {
    public void notificarCambioEstado(Cliente cliente, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setDestinatario(cliente);
        notificacion.enviar();
    }
}