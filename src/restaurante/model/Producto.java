package restaurante.model;

import java.util.List;

import java.util.List;

public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;

    // Constructor
    public Producto(String id, String nombre, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Obtener el precio
    public double obtenerPrecio() {
        return precio;
    }

    // Verificar si contiene alergenos

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