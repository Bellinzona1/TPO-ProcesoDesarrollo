package restaurante.model;

import java.util.List;

public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private List<String> alergenos;
    private int tiempoPreparacion; // en minutos

    // Constructor
    public Producto(String id, String nombre, String descripcion, double precio, List<String> alergenos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.alergenos = alergenos;
    }

    // Constructor extendido
    public Producto(String id, String nombre, String descripcion, double precio, List<String> alergenos, int tiempoPreparacion) {
        this(id, nombre, descripcion, precio, alergenos);
        this.tiempoPreparacion = tiempoPreparacion;
    }

    // Obtener el precio
    public double obtenerPrecio() {
        return precio;
    }

    // Obtener alergenos
    public List<String> getAlergenos() {
        return alergenos;
    }

    public int obtenerTiempoPreparacion() {
        return tiempoPreparacion;
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