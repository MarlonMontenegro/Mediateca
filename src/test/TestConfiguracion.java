package test;

import controllers.ConfiguracionController;
import model.ConfiguracionSistema;

public class TestConfiguracion {

    public static void main(String[] args) {
        ConfiguracionController controller = new ConfiguracionController();

        System.out.println("----- Probando obtenerConfiguracion -----");
        ConfiguracionSistema config = controller.obtenerConfiguracion();
        if (config != null) {
            System.out.println("Configuración actual:");
            System.out.println("ID: " + config.getId());
            System.out.println("Ejemplares máximos: " + config.getEjemplaresMaximos());
            System.out.println("Mora diaria: " + config.getMoraDiaria());
        } else {
            System.out.println("No se encontró configuración.");
        }

        System.out.println("\n----- Probando actualizarConfiguracion -----");
        if (config != null) {
            // Cambiamos algunos valores para probar actualización
            config.setEjemplaresMaximos(config.getEjemplaresMaximos() + 1);
            config.setMoraDiaria(config.getMoraDiaria() + 0.1);

            boolean resultado = controller.actualizarConfiguracion(config);
            System.out.println("Actualización exitosa: " + resultado);

            // Volver a obtener para verificar
            ConfiguracionSistema configActualizada = controller.obtenerConfiguracion();
            System.out.println("Configuración después de actualización:");
            System.out.println("Ejemplares máximos: " + configActualizada.getEjemplaresMaximos());
            System.out.println("Mora diaria: " + configActualizada.getMoraDiaria());
        } else {
            System.out.println("No se puede actualizar porque no existe configuración.");
        }
    }
}