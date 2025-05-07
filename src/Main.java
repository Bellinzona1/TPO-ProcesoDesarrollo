import restaurante.controller.ClienteController;
import restaurante.model.Cliente;
import restaurante.model.Pedido;
import restaurante.model.Producto;
import restaurante.service.FacturaService;
import restaurante.service.NotificacionService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Cliente cliente = new Cliente(1, "Juan", "juan@example.com");
        Pedido pedido = new Pedido(1, cliente);
        Producto producto1 = new Producto("P001", "Pizza", "Pizza de jamón", 5000.0);
        Producto producto2 = new Producto("P002", "Coca", "Coca Cola", 2000.0);
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);

        // Simular la creación de un pedido
        ClienteController clienteController = new ClienteController();
        clienteController.realizarPedido(cliente, pedido);

        // Simular la notificación
        NotificacionService notificacionService = new NotificacionService();
        notificacionService.enviarNotificacion(cliente, "Su pedido ha sido realizado con éxito.");

        // Simular la factura
        FacturaService facturaService = new FacturaService();
        facturaService.generarFactura(pedido);

        // Mostrar el total del pedido
        double total = pedido.calcularTotal();
        System.out.println("El total del pedido es: " + total);

    }

}