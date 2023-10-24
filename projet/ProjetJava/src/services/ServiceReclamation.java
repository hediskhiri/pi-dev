package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import entities.Reclamation;
import entities.User;
import entities.TypeRec;
import entities.Etat;
import java.sql.Statement;

public class ServiceReclamation implements IServiceReclamation<Reclamation> {
    private static ServiceReclamation instance;
    private Connection con;
    private PreparedStatement preparedstatement;
    private Statement ste;

    public ServiceReclamation() {
        con = tools.DataSource.getinstance().getCon();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }

    public void ajouter(Reclamation r) {
        try {
            String req = "INSERT INTO reclamation (id, titre, description, date, iduser, etat, typerec) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, r.getId());
            pre.setString(2, r.getTitre());
            pre.setString(3, r.getDescription());
            pre.setDate(4, r.getDate());
            pre.setInt(5, r.getIduser());
            pre.setString(6, r.getEtat().name());
            pre.setString(7, r.getTypeRec().name());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void modifier(Reclamation r) {
        try {
            String req = "UPDATE reclamation SET titre=?, description=?, date=?, iduser=?, etat=?, typerec=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1, r.getTitre());
            ps.setString(2, r.getDescription());
            ps.setDate(3, r.getDate());
            ps.setInt(4, r.getIduser());
            ps.setString(5, r.getEtat().name());
            ps.setString(6, r.getTypeRec().name());
            ps.setInt(7, r.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void supprimer(int id) {
        try {
            String req = "DELETE FROM reclamation WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public Reclamation getOne(Reclamation r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Reclamation> getAll() {
        List<Reclamation> listReclamation = new ArrayList<>();
        String req = "SELECT rec.id AS reclamation_id, rec.description, rec.iduser, rec.titre, rec.date, rec.etat, rec.typerec FROM reclamation rec JOIN user u ON rec.iduser = u.iduser";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("iduser"));
                Reclamation reclamation = new Reclamation(
                        rs.getInt("reclamation_id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getDate("date"),
                        user,
                       
                        TypeRec.valueOf(rs.getString("typerec")),
                        Etat.valueOf(rs.getString("etat"))
                );
                listReclamation.add(reclamation);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return listReclamation;
    }
}


/*List<Reclamation> reclamation = new ArrayList<>();
        String req = "select * from reclamation";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
        Reclamation rec = new Reclamation();
        rec.setId(rs.getInt("id"));
        rec.setTitre(rs.getString("titre"));
        rec.setDescription(rs.getString("description"));
        rec.setDate(rs.getDate("date"));
        rec.setIduser(rs.getInt("iduser"));
        

            reclamation.add(rec);
        }
        return reclamation;
    } */
