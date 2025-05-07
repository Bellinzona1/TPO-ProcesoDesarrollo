package restaurante.model;

public class Empleado {
    private int id;
    private String nombre;
    private String rol;

    public void cambiarEstadoPedido(Pedido pedido, EstadoPedido estado) {
        pedido.cambiarEstado(estado);
    }
}