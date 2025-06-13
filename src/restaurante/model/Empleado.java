package restaurante.model;

public class Empleado {
    private int id;
    private String nombre;
    private String rol;

    public Empleado(int id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public void atenderPedido(Pedido pedido) {
        System.out.println("El empleado " + nombre + " está atendiendo el pedido #" + pedido.getNumeroOrden());
    }

    public void cambiarEstadoPedido(Pedido pedido, EstadoPedido estado) {
        pedido.cambiarEstado(estado);
    }
}