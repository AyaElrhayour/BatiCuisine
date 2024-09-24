package org.youcode.baticuisine.repositories.implementation;

import org.youcode.baticuisine.db.DBConnection;
import org.youcode.baticuisine.entities.Material;
import org.youcode.baticuisine.entities.Project;
import org.youcode.baticuisine.enums.ComponentType;
import org.youcode.baticuisine.repositories.interfaces.MaterialInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialImplementation implements MaterialInterface {

    private final Connection conn;

    public MaterialImplementation() {
        conn = DBConnection.getInstance().establishConnection();
    }

    private Material materialResultSet(ResultSet resultSet) throws SQLException {
        Material material = new Material();
        material.setId(UUID.fromString(resultSet.getString("id")));
        material.setName(resultSet.getString("name"));
        material.setComponentType(ComponentType.MATERIALS);
        material.setTvaRate(resultSet.getDouble("tvaRate"));
        material.setUnitaryPay(resultSet.getDouble("unitaryPay"));
        material.setQuantity(resultSet.getDouble("quantity"));
        material.setOutputFactor(resultSet.getDouble("outputFactor"));
        material.setTransportCost(resultSet.getDouble("transportCost"));

        Project project = new Project();
        project.setId(UUID.fromString(resultSet.getString("projectId")));
        material.setProject(project);

        return material;
    }



    @Override
    public Optional<Material> addMaterial(Material material) {
        String insertSQL = "INSERT INTO materials (id, name, componentType, tvaRate, unitaryPay, quantity, " +
                "outputFactor, projectId, transportCost) VALUES (?, ?, ?::componentType, ?, ?, ?, ?, ?::uuid, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)){
            preparedStatement.setObject(1, material.getId());
            preparedStatement.setString(2, material.getName());
            preparedStatement.setString(3, material.getComponentType().toString());
            preparedStatement.setDouble(4, material.getTvaRate());
            preparedStatement.setDouble(5, material.getUnitaryPay());
            preparedStatement.setDouble(6, material.getQuantity());
            preparedStatement.setDouble(7, material.getOutputFactor());
            preparedStatement.setObject(8, material.getProject().getId());
            preparedStatement.setDouble(9, material.getTransportCost());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0){
                return Optional.empty();
            }
            return  Optional.of(material);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Material> getMaterialByProject(UUID projectId) {
        List<Material> materials = new ArrayList<>();
        String selectSQL = "SELECT * FROM materials WHERE projectId = ?::uuid";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)){
            preparedStatement.setObject(1, projectId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    materials.add(materialResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}


