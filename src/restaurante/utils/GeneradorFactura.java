package restaurante.utils;

import restaurante.model.Factura;
import restaurante.model.Pedido;

public class GeneradorFactura {
    public static Factura generarFactura(Pedido pedido) {
        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.generarPDF();
        return factura;
    }
}