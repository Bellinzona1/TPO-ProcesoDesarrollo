package restaurante.model;

import java.util.List;

public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private List<String> alergenos;

    // Constructor
    public Producto(String id, String nombre, String descripcion, double precio, List<String> alergenos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.alergenos = alergenos;
    }

    // Obtener el precio
    public double obtenerPrecio() {
        return precio;
    }

    // Obtener alergenos
    public List<String> getAlergenos() {
        return alergenos;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }
}