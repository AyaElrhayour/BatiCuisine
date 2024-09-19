package org.youcode.baticuisine.repositories.implementation;

import org.youcode.baticuisine.db.DBConnection;
import org.youcode.baticuisine.entities.Client;
import org.youcode.baticuisine.repositories.inertface.ClientInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientImplementation implements ClientInterface {

    private final Connection conn ;

    public ClientImplementation(){
        conn = DBConnection.getInstance().establishConnection();
    }

    @Override
    public Optional<Client> addClient(Client client) {
        String insertSQL = "INSERT INTO client (id, name, address, telephone, isProfessional) " +
                "VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)){
            preparedStatement.setObject(1,client.getId());
            preparedStatement.setString(2,client.getName());
            preparedStatement.setString(3,client.getAddress());
            preparedStatement.setString(4,client.getTelephone());
            preparedStatement.setBoolean(5,client.getIsProfessional());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0){
                return Optional.empty();
            }
            return Optional.of(client);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Client> getClient(UUID id) {
        Client client = null;
        String selectClientSQL = "SELECT * FROM client WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectClientSQL)) {
            preparedStatement.setString(1, id.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    client = new Client();
                    client.setId(UUID.fromString(resultSet.getString("id")));
                    client.setName(resultSet.getString("name"));
                    client.setAddress(resultSet.getString("address"));
                    client.setTelephone(resultSet.getString("telephone"));
                    client.setIsProfessional(Boolean.valueOf(resultSet.getString("isProfessional")));

                    return Optional.of(client);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM client";
        try(Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllSQL)){
            while (resultSet.next()){
                Client client = new Client();
                client.setId(UUID.fromString(resultSet.getString("id")));
                client.setName(resultSet.getString("name"));
                client.setAddress(resultSet.getString("address"));
                client.setTelephone(resultSet.getString("telephone"));
                client.setIsProfessional(Boolean.valueOf("isProfessional"));
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    @Override
    public boolean deleteClient(UUID id) {
        String deleteSQL = "DELETE FROM client WHERE id =?::uuid";
        try(PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)){
            preparedStatement.setObject(1,String.valueOf(id));

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}