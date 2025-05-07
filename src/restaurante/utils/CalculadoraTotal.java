package restaurante.utils;

import restaurante.model.Pedido;
import restaurante.model.Producto;

public class CalculadoraTotal {
    public static double calcularTotal(Pedido pedido) {
        double total = 0;
        for (Producto producto : pedido.getProductos()) {
            total += producto.obtenerPrecio();
        }
        return total;
    }
}