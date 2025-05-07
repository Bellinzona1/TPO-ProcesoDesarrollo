package restaurante.model;

import java.util.ArrayList;
import java.util.Date;

public class Factura {
    private int numero;
    private Date fecha;
    private Pedido pedido;

    public Factura() {

    }


    public void generarPDF() {
        // Lógica para generar el PDF
    }

    public void enviarPorEmail() {
        // Lógica para enviar la factura por email
    }

    public void setPedido(Pedido pedido) {

    }
}