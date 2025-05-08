package restaurante.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Factura {
    private static int contadorFacturas = 1; // Contador para generar números únicos
    private int numero;
    private Date fecha;
    private Pedido pedido;

    public Factura(Pedido pedido) {
        this.numero = contadorFacturas++;
        this.fecha = new Date(); // Fecha actual
        this.pedido = pedido;
    }

    public void generarPDF() {
        // Simulación de generación de PDF
        System.out.println("Generando PDF de la factura...");
        System.out.println("Factura #" + numero);
        System.out.println("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(fecha));
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Total: $" + pedido.getTotal());
        System.out.println("Productos:");
        pedido.getProductos().forEach(producto -> 
            System.out.println(" - " + producto.getNombre() + ": $" + producto.getPrecio())
        );
        System.out.println("PDF generado correctamente.");
    }

    public void enviarPorEmail() {
        // Simulación de envío por email
        System.out.println("Enviando factura #" + numero + " al correo: " + pedido.getCliente().getEmail());
        System.out.println("Factura enviada correctamente.");
    }

    public int getNumero() {
        return numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}