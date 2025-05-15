import restaurante.model.*;
import restaurante.service.*;
import restaurante.utils.*;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Pedido> pedidos = new ArrayList<>();
    private static List<Producto> productosDisponibles = new ArrayList<>();
    private static Cliente clienteActual = new Cliente(1,"Juan", "juan@email.com"); // Cliente de prueba
    private static Cupon cupon = new Cupon("DESC500", 500.0); // Cupón de prueba
    private static PedidoService pedidoService = new PedidoService();
    private static FacturaService facturaService = new FacturaService();
    private static NotificacionService notificacionService = new NotificacionService();

    public static void main(String[] args) {
        cargarProductos();

        int opcion;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Crear nuevo pedido");
            System.out.println("2. Ver pedidos");
            System.out.println("3. Cambiar estado de un pedido");
            System.out.println("4. Aplicar cupón a un pedido");
            System.out.println("5. Generar factura de un pedido");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> crearPedido();
                case 2 -> mostrarPedidos();
                case 3 -> cambiarEstadoPedido();
                case 4 -> aplicarCupon();
                case 5 -> generarFactura();
                case 6 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 6);
    }

    private static void cargarProductos() {
        productosDisponibles.add(new Producto("P1", "Hamburguesa", "Carne con pan", 1500.0, List.of("Gluten")));
        productosDisponibles.add(new Producto("P2", "Pizza", "Mozzarella", 2000.0, List.of("Lácteos")));
        productosDisponibles.add(new Producto("P3", "Ensalada", "Verduras mixtas", 1000.0, List.of()));
    }

    private static void crearPedido() {
        Pedido pedido = new Pedido(pedidos.size() + 1, clienteActual);
        System.out.println("Selecciona productos para el pedido:");
        for (int i = 0; i < productosDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + productosDisponibles.get(i).getNombre());
        }
        System.out.println("0. Finalizar selección");

        int seleccion;
        do {
            System.out.print("Ingrese número del producto: ");
            seleccion = scanner.nextInt();
            if (seleccion > 0 && seleccion <= productosDisponibles.size()) {
                pedido.agregarProducto(productosDisponibles.get(seleccion - 1));
                System.out.println("Producto agregado.");
            }
        } while (seleccion != 0);

        pedido.calcularTotal();
        pedidoService.realizarPedido(clienteActual, pedido);
        pedidos.add(pedido);
        System.out.println("Pedido creado con éxito. Total: $" + pedido.getTotal());
    }

    private static void mostrarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        for (Pedido pedido : pedidos) {
            System.out.println("Pedido #" + pedido.getNumeroOrden() + " - Estado: " + pedido.getEstado() + " - Total: $" + pedido.getTotal());
        }
    }

    private static void cambiarEstadoPedido() {
        mostrarPedidos();
        System.out.print("Ingrese número del pedido a cambiar estado: ");
        int numero = scanner.nextInt();

        Pedido pedido = buscarPedidoPorNumero(numero);
        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        System.out.println("Estados disponibles:");
        for (EstadoPedido estado : EstadoPedido.values()) {
            System.out.println("- " + estado);
        }

        System.out.print("Ingrese nuevo estado: ");
        String estadoNuevo = scanner.next().toUpperCase();

        try {
            EstadoPedido estado = EstadoPedido.valueOf(estadoNuevo);
            pedidoService.cambiarEstadoPedido(pedido, estado);
            new SistemaNotificaciones().notificarCambioEstado(pedido);
            System.out.println("Estado actualizado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Estado inválido.");
        }
    }

    private static void aplicarCupon() {
        mostrarPedidos();
        System.out.print("Ingrese número del pedido para aplicar cupón: ");
        int numero = scanner.nextInt();

        Pedido pedido = buscarPedidoPorNumero(numero);
        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        if (ValidarCupon.validarCupon(pedido, cupon)) {
            System.out.println("Cupón aplicado. Nuevo total: $" + pedido.getTotal());
        } else {
            System.out.println("No se pudo aplicar el cupón.");
        }
    }

    private static void generarFactura() {
        mostrarPedidos();
        System.out.print("Ingrese número del pedido para generar factura: ");
        int numero = scanner.nextInt();

        Pedido pedido = buscarPedidoPorNumero(numero);
        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        facturaService.generarFactura(pedido);
    }

    private static Pedido buscarPedidoPorNumero(int numero) {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumeroOrden() == numero) {
                return pedido;
            }
        }
        return null;
    }
}