package restaurante.model;

import java.util.List;
import java.util.ArrayList;

public class Menu {
    private List<Categoria> categorias = new ArrayList<>(); // Inicialización

    public void agregarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    public List<Categoria> obtenerCategorias() {
        return categorias;
    }

    public void mostrarMenu() {
        for (Categoria categoria : categorias) {
            System.out.println("Categoría: " + categoria.getNombre());
            for (Producto producto : categoria.obtenerProductos()) {
                System.out.println(" - Producto: " + producto.getNombre());
                System.out.println("   Descripción: " + producto.getDescripcion());
                System.out.println("   Precio: $" + producto.getPrecio());
                System.out.println("   Alérgenos: " + String.join(", ", producto.getAlergenos()));
            }
        }
    }
}