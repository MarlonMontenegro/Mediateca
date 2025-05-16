package test;

import controllers.LibroController;
import model.Libro;

import java.util.List;

public class TestLibroController {

    public static void main(String[] args) {

        LibroController controller = new LibroController();

        // Crear y agregar 3 libros
        Libro libro1 = new Libro(
                generarCodigo(),
                "El Señor de los Anillos",
                5,
                "Minotauro",
                "J.R.R. Tolkien",
                1200,
                "978-84-420-7254-9",
                "1954"
        );

        Libro libro2 = new Libro(
                generarCodigo(),
                "Cien años de soledad",
                3,
                "Sudamericana",
                "Gabriel García Márquez",
                417,
                "978-84-376-04920",
                "1967"
        );

        Libro libro3 = new Libro(
                generarCodigo(),
                "1984",
                4,
                "Secker & Warburg",
                "George Orwell",
                328,
                "978-84-376-0424-6",
                "1949"
        );

        controller.agregarLibro(libro1);
        controller.agregarLibro(libro2);
        controller.agregarLibro(libro3);

        // Mostrar libros guardados
        List<Libro> libros = controller.listarTodosLoslibros();
        for (Libro libro : libros) {
            mostrarLibro(libro);
        }
    }

    private static String generarCodigo() {
        return "LIB" + String.format("%05d", (int) (Math.random() * 100000));
    }

    private static void mostrarLibro(Libro libro) {
        System.out.println("Código: " + libro.getCodigoIdentificacion());
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor: " + libro.getAutor());
        System.out.println("Editorial: " + libro.getEditorial());
        System.out.println("ISBN: " + libro.getIsbn());
        System.out.println("Año de publicación: " + libro.getAnioPublicacion());
        System.out.println("Páginas: " + libro.getNumeroPaginas());
        System.out.println("Unidades disponibles: " + libro.getUnidadesDisponibles());
        System.out.println("---------------------------------------------");
    }
}
