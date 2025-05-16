package test;

import controllers.PrestamoController;
import model.Prestamo;

import java.util.Date;
import java.util.List;

public class TestPrestamoController {

    public static void main(String[] args) {

        PrestamoController controller = new PrestamoController();

        // Crear un nuevo préstamo
        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setIdUsuario(1);
        nuevoPrestamo.setIdMaterial("LIB00001");
        nuevoPrestamo.setFechaPrestamo(new Date());
        // Fecha esperada: 5 días después (puedes cambiar)
        nuevoPrestamo.setFechaDevolucionEsperada(new Date(System.currentTimeMillis() + 5L * 24 * 3600 * 1000));
        nuevoPrestamo.setDiasMora(0);
        nuevoPrestamo.setMoraCalculada(java.math.BigDecimal.ZERO);
        nuevoPrestamo.setDevuelto(false);

        boolean creado = controller.crearPrestamo(nuevoPrestamo);
        System.out.println("Crear préstamo: " + (creado ? "Éxito" : "Falló"));

        // Listar préstamos
        List<Prestamo> prestamos = controller.listarPrestamos();
        System.out.println("\nLista de préstamos:");
        for (Prestamo p : prestamos) {
            System.out.println("ID Prestamo: " + p.getIdPrestamo() +
                    ", Usuario: " + p.getIdUsuario() +
                    ", Material: " + p.getIdMaterial() +
                    ", Devuelto: " + p.isDevuelto());
        }

        // Devolver un préstamo (ajusta el ID para probar)
        int idDevolver = 1;
        boolean devuelto = controller.devolverPrestamo(idDevolver, new Date());
        System.out.println("\nDevolver préstamo ID " + idDevolver + ": " + (devuelto ? "Éxito" : "Falló"));
    }
}