package restaurante.model;

import java.util.List;

public class Categoria {
    private String nombre;
    private List<Producto> productos;

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }
}