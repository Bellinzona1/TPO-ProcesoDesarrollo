package restaurante.utils;

import restaurante.model.Cupon;
import restaurante.model.Pedido;

public class ValidarCupon {
    public static boolean validarCupon(Pedido pedido, Cupon cupon) {
        // Lógica de validación de cupones
        if (cupon != null && cupon.getDescuento() > 0) {
            // Si el cupon tiene descuento válido
            pedido.aplicarCupon(cupon);
            return true;
        }
        return false;
    }
}
