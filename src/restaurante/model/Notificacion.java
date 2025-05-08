package restaurante.model;


public class Notificacion {
    private String mensaje;
    private Cliente destinatario;

    public void enviar() {
        System.out.println("Enviando notificación a " + destinatario.getNombre() + ": " + mensaje);
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}