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
            System.out.println("6. Cancelar un pedido");
            System.out.println("7. Agregar productos a un pedido en espera");
            System.out.println("8. Calcular tiempo estimado de un pedido");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> crearPedido();
                case 2 -> mostrarPedidos();
                case 3 -> cambiarEstadoPedido();
                case 4 -> aplicarCupon();
                case 5 -> generarFactura();
                case 6 -> {
                    System.out.print("Ingrese el número de orden del pedido a cancelar: ");
                    int numOrdenCancelar = scanner.nextInt();
                    scanner.nextLine();
                    Pedido pedidoCancelar = null;
                    for (Pedido p : pedidos) {
                        if (p.getNumeroOrden() == numOrdenCancelar) {
                            pedidoCancelar = p;
                            break;
                        }
                    }
                    if (pedidoCancelar == null) {
                        System.out.println("Pedido no encontrado.");
                    } else {
                        boolean cancelado = pedidoCancelar.cancelar();
                        if (cancelado) {
                            System.out.println("Pedido cancelado. Monto a reembolsar: $" + pedidoCancelar.getMontoReembolsado());
                        } else {
                            System.out.println("No se puede cancelar el pedido. Solo se pueden cancelar pedidos en espera o en preparación.");
                        }
                    }
                }
                case 7 -> agregarProductosAEnEspera();
                case 8 -> calcularTiempoEstimadoPedido();
                case 9 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 9);
    }

    private static void cargarProductos() {
        productosDisponibles.add(new Producto("P1", "Hamburguesa", "Carne con pan", 1500.0, List.of("Gluten")));
        productosDisponibles.add(new Producto("P2", "Pizza", "Mozzarella", 2000.0, List.of("Lácteos")));
        productosDisponibles.add(new Producto("P3", "Ensalada", "Verduras mixtas", 1000.0, List.of()));
    }

    private static void crearPedido() {
        System.out.println("Seleccione la plataforma:");
        System.out.println("1. TOTEM");
        System.out.println("2. APP");
        int plataformaSel = scanner.nextInt();
        scanner.nextLine();
        String plataforma = plataformaSel == 1 ? "TOTEM" : "APP";

        System.out.print("¿El pedido es delivery? (s/n): ");
        boolean esDelivery = scanner.nextLine().trim().equalsIgnoreCase("s");

        Pedido pedido = new Pedido(pedidos.size() + 1, clienteActual, plataforma, esDelivery);

        System.out.println("¿Desea programar el pedido para más tarde? (s/n): ");
        String programar = scanner.nextLine().trim();
        if (programar.equalsIgnoreCase("s")) {
            System.out.print("Ingrese la hora programada (HH:mm): ");
            String hora = scanner.nextLine();
            java.time.LocalTime horaProgramada = java.time.LocalTime.parse(hora);
            java.time.LocalDateTime fechaProgramada = java.time.LocalDateTime.now().withHour(horaProgramada.getHour()).withMinute(horaProgramada.getMinute());
            if (fechaProgramada.isBefore(java.time.LocalDateTime.now())) {
                fechaProgramada = fechaProgramada.plusDays(1); // Si la hora ya pasó, es para el día siguiente
            }
            pedido.programarPara(fechaProgramada);
        }

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
        scanner.nextLine();

        pedido.calcularTotal();

        // Preguntar si desea aplicar cupón (solo si no es TOTEM)
        if (!"TOTEM".equalsIgnoreCase(plataforma)) {
            System.out.print("¿Desea aplicar un cupón? (s/n): ");
            String aplicarCupon = scanner.nextLine().trim();
            if (aplicarCupon.equalsIgnoreCase("s")) {
                if (ValidarCupon.validarCupon(pedido, cupon)) {
                    System.out.println("Cupón aplicado. Nuevo total: $" + pedido.getTotal());
                } else {
                    System.out.println("No se pudo aplicar el cupón.");
                }
            }
        }

        System.out.println("Seleccione medio de pago:");
        System.out.println("1. MercadoPago");
        System.out.println("2. Google Pay");
        System.out.println("3. Efectivo (10% descuento)");
        int medioPago = scanner.nextInt();
        scanner.nextLine();
        if (medioPago == 3) {
            pedido.aplicarDescuentoEfectivo();
            System.out.println("Descuento aplicado por pago en efectivo.");
        }
        // Aquí podrías instanciar y asignar el objeto Pago correspondiente si lo deseas

        pedidoService.realizarPedido(clienteActual, pedido);
        pedidos.add(pedido);
        System.out.println("Pedido creado con éxito. Total: $" + pedido.getTotal());
        if (pedido.isEsProgramado()) {
            System.out.println("Este pedido está programado para las: " + pedido.getFechaProgramada().toLocalTime());
        }
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

        if ("TOTEM".equalsIgnoreCase(pedido.getPlataforma())) {
            System.out.println("No se pueden aplicar cupones a pedidos realizados en TOTEM.");
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

    private static void cancelarPedido() {
        mostrarPedidos();
        System.out.print("Ingrese número del pedido a cancelar: ");
        int numero = scanner.nextInt();

        Pedido pedido = buscarPedidoPorNumero(numero);
        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        pedidoService.cancelarPedido(pedido);
        System.out.println("Pedido cancelado.");
    }

    private static void agregarProductosAEnEspera() {
        mostrarPedidos();
        System.out.print("Ingrese número del pedido en espera: ");
        int numero = scanner.nextInt();

        Pedido pedido = buscarPedidoPorNumero(numero);
        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        System.out.println("Selecciona productos para agregar al pedido en espera:");
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
        System.out.println("Productos agregados al pedido en espera. Total: $" + pedido.getTotal());
    }

    private static void calcularTiempoEstimadoPedido() {
        mostrarPedidos();
        System.out.print("Ingrese número del pedido para calcular tiempo estimado: ");
        int numero = scanner.nextInt();

        Pedido pedido = buscarPedidoPorNumero(numero);
        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        System.out.print("Ingrese la cantidad de pedidos activos en el local: ");
        int pedidosActivosEnLocal = scanner.nextInt();
        System.out.print("Ingrese el tiempo de delivery de Rappi (en minutos, 0 si no aplica o no responde): ");
        int tiempoRappi = scanner.nextInt();

        int tiempoEstimado = pedidoService.calcularTiempoEstimado(pedido, pedidosActivosEnLocal, tiempoRappi);
        System.out.println("Tiempo estimado para el pedido: " + tiempoEstimado + " minutos.");
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