package controllers;

import dao.MaterialResumenDAO;
import model.MaterialResumen;

import java.util.List;

public class MaterialResumenController {

    private final MaterialResumenDAO materialGeneralDAO = new MaterialResumenDAO();

    /**
     * Retorna una lista con todos los materiales disponibles,
     * incluyendo libros, revistas, CDs y DVDs.
     */
    public List<MaterialResumen> listarTodosLosMateriales() {
        return materialGeneralDAO.listarTodosLosMateriales();
    }
}
