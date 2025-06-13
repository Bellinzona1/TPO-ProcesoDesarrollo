package restaurante.gui;

import restaurante.model.*;
import restaurante.service.*;
import restaurante.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {
    private PedidoService pedidoService = new PedidoService();
    private FacturaService facturaService = new FacturaService();
    private NotificacionService notificacionService = new NotificacionService();
    private Cliente clienteActual = new Cliente(1, "Juan", "juan@email.com");
    private Cupon cupon = new Cupon("DESC500", 500.0);
    private List<Pedido> pedidos = new ArrayList<>();
    private List<Producto> productosDisponibles = new ArrayList<>();

    private DefaultListModel<String> modeloListaPedidos = new DefaultListModel<>();
    private JList<String> listaPedidos = new JList<>(modeloListaPedidos);
    private JTextArea areaInfo = new JTextArea(8, 30);

    public VentanaPrincipal() {
        setTitle("Gestión de Pedidos - Restaurante");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cargarProductos();
        areaInfo.setEditable(false);

        JPanel panelBotones = new JPanel(new GridLayout(2, 5, 5, 5));
        JButton btnNuevo = new JButton("Nuevo Pedido");
        JButton btnVer = new JButton("Ver Pedidos");
        JButton btnEstado = new JButton("Cambiar Estado");
        JButton btnCupon = new JButton("Aplicar Cupón");
        JButton btnFactura = new JButton("Generar Factura");
        JButton btnCancelar = new JButton("Cancelar Pedido");
        JButton btnAgregar = new JButton("Agregar Productos");
        JButton btnTiempo = new JButton("Calcular Tiempo");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnNuevo);
        panelBotones.add(btnVer);
        panelBotones.add(btnEstado);
        panelBotones.add(btnCupon);
        panelBotones.add(btnFactura);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnTiempo);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(listaPedidos), BorderLayout.CENTER);
        add(new JScrollPane(areaInfo), BorderLayout.SOUTH);

        btnNuevo.addActionListener(e -> crearPedido());
        btnVer.addActionListener(e -> mostrarPedidos());
        btnEstado.addActionListener(e -> cambiarEstadoPedido());
        btnCupon.addActionListener(e -> aplicarCupon());
        btnFactura.addActionListener(e -> generarFactura());
        btnCancelar.addActionListener(e -> cancelarPedido());
        btnAgregar.addActionListener(e -> agregarProductosAEnEspera());
        btnTiempo.addActionListener(e -> calcularTiempoEstimadoPedido());
        btnSalir.addActionListener(e -> System.exit(0));

        mostrarPedidos();
    }

    private void cargarProductos() {
        productosDisponibles.add(new Producto("P1", "Hamburguesa", "Carne con pan", 1500.0, List.of("Gluten"), 10));
        productosDisponibles.add(new Producto("P2", "Pizza", "Mozzarella", 2000.0, List.of("Lácteos"), 16));
        productosDisponibles.add(new Producto("P3", "Ensalada", "Verduras mixtas", 1000.0, List.of(), 5));
    }

    private void crearPedido() {
        String[] plataformas = {"TOTEM", "APP"};
        String plataforma = (String) JOptionPane.showInputDialog(this, "Seleccione la plataforma:", "Plataforma", JOptionPane.QUESTION_MESSAGE, null, plataformas, plataformas[0]);
        if (plataforma == null) return;

        boolean esDelivery = false;
        if ("APP".equalsIgnoreCase(plataforma)) {
            int delivery = JOptionPane.showConfirmDialog(this, "¿El pedido es delivery?", "Delivery", JOptionPane.YES_NO_OPTION);
            esDelivery = delivery == JOptionPane.YES_OPTION;
        }
        Pedido pedido = new Pedido(pedidos.size() + 1, clienteActual, plataforma, esDelivery);

        if ("APP".equalsIgnoreCase(plataforma)) {
            int programar = JOptionPane.showConfirmDialog(this, "¿Desea programar el pedido para más tarde?", "Programar", JOptionPane.YES_NO_OPTION);
            if (programar == JOptionPane.YES_OPTION) {
                String hora = JOptionPane.showInputDialog(this, "Ingrese la hora programada (HH:mm):");
                try {
                    java.time.LocalTime horaProgramada = java.time.LocalTime.parse(hora);
                    java.time.LocalDateTime fechaProgramada = java.time.LocalDateTime.now().withHour(horaProgramada.getHour()).withMinute(horaProgramada.getMinute());
                    if (fechaProgramada.isBefore(java.time.LocalDateTime.now())) {
                        fechaProgramada = fechaProgramada.plusDays(1);
                    }
                    pedido.programarPara(fechaProgramada);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Hora inválida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // Selección de productos
        boolean seguir = true;
        while (seguir) {
            String[] nombres = productosDisponibles.stream().map(Producto::getNombre).toArray(String[]::new);
            String prod = (String) JOptionPane.showInputDialog(this, "Seleccione producto:", "Productos", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);
            if (prod == null) break;
            for (Producto p : productosDisponibles) {
                if (p.getNombre().equals(prod)) {
                    pedido.agregarProducto(p);
                    break;
                }
            }
            int mas = JOptionPane.showConfirmDialog(this, "¿Agregar otro producto?", "Productos", JOptionPane.YES_NO_OPTION);
            if (mas != JOptionPane.YES_OPTION) seguir = false;
        }
        pedido.calcularTotal();

        if (!"TOTEM".equalsIgnoreCase(plataforma)) {
            int aplicar = JOptionPane.showConfirmDialog(this, "¿Desea aplicar un cupón?", "Cupón", JOptionPane.YES_NO_OPTION);
            if (aplicar == JOptionPane.YES_OPTION) {
                if (ValidarCupon.validarCupon(pedido, cupon)) {
                    JOptionPane.showMessageDialog(this, "Cupón aplicado. Nuevo total: $" + pedido.getTotal());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo aplicar el cupón.");
                }
            }
        }

        String[] medios = {"MercadoPago", "Google Pay", "Efectivo (10% descuento)"};
        String medio = (String) JOptionPane.showInputDialog(this, "Seleccione medio de pago:", "Pago", JOptionPane.QUESTION_MESSAGE, null, medios, medios[0]);
        if ("Efectivo (10% descuento)".equals(medio)) {
            pedido.aplicarDescuentoEfectivo();
            JOptionPane.showMessageDialog(this, "Descuento aplicado por pago en efectivo.");
        }

        pedidoService.realizarPedido(clienteActual, pedido);
        pedidos.add(pedido);
        mostrarPedidos();
        areaInfo.setText("Pedido creado con éxito. Total: $" + pedido.getTotal() + (pedido.isEsProgramado() ? "\nProgramado para: " + pedido.getFechaProgramada().toLocalTime() : ""));
    }

    private void mostrarPedidos() {
        modeloListaPedidos.clear();
        for (Pedido pedido : pedidos) {
            modeloListaPedidos.addElement("#" + pedido.getNumeroOrden() + " - " + pedido.getEstado() + " - $" + pedido.getTotal());
        }
    }

    private Pedido seleccionarPedido() {
        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pedidos registrados.");
            return null;
        }
        String[] opciones = pedidos.stream().map(p -> "#" + p.getNumeroOrden() + " - " + p.getEstado()).toArray(String[]::new);
        String sel = (String) JOptionPane.showInputDialog(this, "Seleccione un pedido:", "Pedidos", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (sel == null) return null;
        int num = Integer.parseInt(sel.split(" ")[0].replace("#", ""));
        return pedidos.stream().filter(p -> p.getNumeroOrden() == num).findFirst().orElse(null);
    }

    private void cambiarEstadoPedido() {
        Pedido pedido = seleccionarPedido();
        if (pedido == null) return;
        EstadoPedido[] estados = EstadoPedido.values();
        EstadoPedido estado = (EstadoPedido) JOptionPane.showInputDialog(this, "Nuevo estado:", "Estado", JOptionPane.QUESTION_MESSAGE, null, estados, pedido.getEstado());
        if (estado == null) return;
        pedidoService.cambiarEstadoPedido(pedido, estado);
        String mensaje = "El estado de su pedido #" + pedido.getNumeroOrden() + " ha cambiado a: " + estado;
        notificacionService.notificarCambioEstado(pedido.getCliente(), mensaje);
        mostrarPedidos();
        areaInfo.setText("Estado actualizado y notificación enviada.");
    }

    private void aplicarCupon() {
        Pedido pedido = seleccionarPedido();
        if (pedido == null) return;
        if ("TOTEM".equalsIgnoreCase(pedido.getPlataforma())) {
            JOptionPane.showMessageDialog(this, "No se pueden aplicar cupones a pedidos realizados en TOTEM.");
            return;
        }
        if (ValidarCupon.validarCupon(pedido, cupon)) {
            JOptionPane.showMessageDialog(this, "Cupón aplicado. Nuevo total: $" + pedido.getTotal());
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo aplicar el cupón.");
        }
        mostrarPedidos();
    }

    private void generarFactura() {
        Pedido pedido = seleccionarPedido();
        if (pedido == null) return;
        facturaService.generarFactura(pedido);
        areaInfo.setText("Factura generada para el pedido #" + pedido.getNumeroOrden());
    }

    private void cancelarPedido() {
        Pedido pedido = seleccionarPedido();
        if (pedido == null) return;
        pedidoService.cancelarPedido(pedido, notificacionService);
        mostrarPedidos();
    }

    private void agregarProductosAEnEspera() {
        Pedido pedido = seleccionarPedido();
        if (pedido == null) return;
        boolean seguir = true;
        while (seguir) {
            String[] nombres = productosDisponibles.stream().map(Producto::getNombre).toArray(String[]::new);
            String prod = (String) JOptionPane.showInputDialog(this, "Seleccione producto:", "Productos", JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);
            if (prod == null) break;
            for (Producto p : productosDisponibles) {
                if (p.getNombre().equals(prod)) {
                    pedido.agregarProducto(p);
                    break;
                }
            }
            int mas = JOptionPane.showConfirmDialog(this, "¿Agregar otro producto?", "Productos", JOptionPane.YES_NO_OPTION);
            if (mas != JOptionPane.YES_OPTION) seguir = false;
        }
        pedido.calcularTotal();
        mostrarPedidos();
        areaInfo.setText("Productos agregados al pedido en espera. Total: $" + pedido.getTotal());
    }

    private void calcularTiempoEstimadoPedido() {
        Pedido pedido = seleccionarPedido();
        if (pedido == null) return;
        String activosStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad de pedidos activos en el local:");
        String rappiStr = JOptionPane.showInputDialog(this, "Ingrese el tiempo de delivery de Rappi (en minutos, 0 si no aplica):");
        try {
            int pedidosActivosEnLocal = Integer.parseInt(activosStr);
            int tiempoRappi = Integer.parseInt(rappiStr);
            int tiempoEstimado = pedidoService.calcularTiempoEstimado(pedido, pedidosActivosEnLocal, tiempoRappi);
            areaInfo.setText("Tiempo estimado para el pedido: " + tiempoEstimado + " minutos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
