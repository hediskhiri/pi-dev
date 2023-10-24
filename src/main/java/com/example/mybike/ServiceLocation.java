package com.example.mybike;

import entity.Location;
import entity.Velo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceLocation implements IserviceLocation<Location>{
    private Connection con;
    private Statement ste;
    private PreparedStatement pre;
    private ResultSet result;
    public ServiceLocation() {
        con = MyDB.getinstance().getCon();    }

    @Override
    public void ajouter(Location t) {
        try {
            String req = "INSERT INTO location (id_u, start_date, end_date,prix) VALUES (?, ?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pre.setInt(1, t.getClientId());
            pre.setString(2, t.getStartDate());
            pre.setString(3, t.getEndDate());

            pre.setInt(4, t.getPrix());


            int affectedRows = pre.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating location failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pre.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    t.setId(generatedKeys.getInt(1)); // Set the generated ID for the Location
                } else {
                    throw new SQLException("Creating location failed, no ID obtained.");
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }




    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM location WHERE location_id = ?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, id);

            int affectedRows = pre.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting location failed, no rows affected.");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public void modifier(Location t) {
        try {
            String req = "UPDATE location SET id_u = ?, id = ?, start_date = ?, end_date = ? WHERE location_id = ?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, t.getClientId());
            pre.setInt(2, t.getVeloId());
            pre.setString(3, t.getStartDate());
            pre.setString(4, t.getEndDate());
            pre.setInt(5, t.getId());

            int affectedRows = pre.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating location failed, no rows affected.");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public List<Location> afficher() {
        List<Location> locations = new ArrayList<>();

        try {
            String req = "SELECT * FROM location";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(req);

            while (resultSet.next()) {
                int id = resultSet.getInt("location_id");
                int clientId = resultSet.getInt("id_u");
                int veloId = resultSet.getInt("id");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");

                Location location = new Location(clientId, veloId, startDate, endDate);
                location.setId(id);

                locations.add(location);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return locations;
    }}


