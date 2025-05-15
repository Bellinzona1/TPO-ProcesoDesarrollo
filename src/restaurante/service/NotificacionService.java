package restaurante.service;

import restaurante.model.Cliente;
import restaurante.model.Notificacion;

public class NotificacionService {
    public void enviarNotificacion(Cliente cliente, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setDestinatario(cliente);
        notificacion.enviar();
    }
}