package restaurante.service;

import restaurante.model.Factura;
import restaurante.model.Pedido;

public class FacturaService {
    public void generarFactura(Pedido pedido) {
        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.generarPDF();
        factura.enviarPorEmail();
    }
}