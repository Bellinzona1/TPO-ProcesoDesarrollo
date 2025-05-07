package restaurante.model;

import java.util.List;

public class Menu {
    private List<Categoria> categorias;

    public void agregarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    public List<Categoria> obtenerCategorias() {
        return categorias;
    }
}