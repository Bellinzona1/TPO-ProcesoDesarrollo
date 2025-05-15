package restaurante.service;

import restaurante.model.Factura;
import restaurante.model.Pedido;

public class FacturaService {
    public void generarFactura(Pedido pedido) {
        Factura factura = new Factura(pedido); // Crear una nueva factura
        factura.setPedido(pedido); // Asignar el pedido
        factura.generarPDF();
        factura.enviarPorEmail();
    }
}