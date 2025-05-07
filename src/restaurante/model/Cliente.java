package restaurante.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int id;
    private String nombre;
    private String email;
    private List<Pedido> pedidos; // Lista de pedidos del cliente

    // Constructor
    public Cliente(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.pedidos = new ArrayList<Pedido>();
    }

    // Método para realizar un pedido
    public void realizarPedido(Pedido pedido) {
        // Agregar el pedido a la lista de pedidos del cliente
        this.pedidos.add(pedido);
    }

    // Método para recibir una notificación
    public void recibirNotificacion(Notificacion notificacion) {
        // Lógica para recibir notificación (puedes agregar algo aquí, como imprimirla)
        System.out.println("Notificación para " + this.nombre + ": " + notificacion.getMensaje());
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}
