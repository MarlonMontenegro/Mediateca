package dao;

import model.MaterialResumen;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaterialResumenDAO {

    private static final Logger logger = LogManager.getLogger(MaterialResumenDAO.class);

    public List<MaterialResumen> listarTodosLosMateriales() {
        List<MaterialResumen> lista = new ArrayList<>();
        String sql = """
            SELECT 
                m.id_material, 
                m.titulo, 
                m.unidades_disponibles, 
                tm.nombre AS tipo
            FROM Material m
            JOIN TipoMaterial tm ON m.id_tipo = tm.id_tipo
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MaterialResumen mat = new MaterialResumen(
                        rs.getString("id_material"),
                        rs.getString("titulo"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("tipo")
                );
                lista.add(mat);
            }

            logger.info("Se listaron {} materiales de forma general.", lista.size());

        } catch (SQLException e) {
            logger.error("Error al listar materiales: {}", e.getMessage(), e);
        }

        return lista;
    }
}