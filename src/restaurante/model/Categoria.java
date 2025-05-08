package restaurante.model;

import java.util.List;
import java.util.ArrayList;

public class Categoria {
    private String nombre;
    private List<Producto> productos = new ArrayList<>(); // Inicialización

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }

    public String getNombre() {
        return nombre;
    }
}