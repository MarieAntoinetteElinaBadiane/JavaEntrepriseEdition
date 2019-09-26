package services;

import model.Employe;
import model.Service;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeDao {
    public <Throw> void addEmploye(Employe employe) throws Exception {
        try {
            String sql = "INSERT INTO Employe VALUES (null,?,?,?,?,?,?)";
            DatabaseHelper db = DatabaseHelper.getInstance();
            db.iniPreparedCmd(sql);
            db.getPstmt().setString(1, employe.getMatricule());
            db.getPstmt().setString(2,employe.getNom());
            db.getPstmt().setString(3,employe.getTel());
            db.getPstmt().setString(4,employe.getDatenais().toString());
            db.getPstmt().setInt(5,employe.getSalaire());
            db.getPstmt().setInt(6,employe.getService().getId());
            db.My_ExecutePrepareUpdate();


        } catch (Exception ex) {
            throw ex ;
        }
    }
    public List<Service> findServices() {
        List<Service> services =new ArrayList<>();
        try {
            String sql = "SELECT * FROM Service";
            DatabaseHelper db = DatabaseHelper.getInstance();
            db.iniPreparedCmd(sql);
            ResultSet rs= db.My_ExecutePrepareQuery();
            while (rs.next()){
                Service service = new Service();
                service.setId(rs.getInt(1));
                service.setLibelle(rs.getString(2));
                services.add(service);
            }

            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return services;
    }
    public List<Employe> findEmployes() {
        List<Employe> employes =new ArrayList<>();
        try {
            String sql = "SELECT * FROM Employe employe, Service service WHERE employe.idservice=service.idservice";
            DatabaseHelper db = DatabaseHelper.getInstance();
            db.iniPreparedCmd(sql);
            ResultSet rs= db.My_ExecutePrepareQuery();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while (rs.next()){
                Employe employe = new Employe();
                employe.setId(rs.getInt(1));
                employe.setMatricule(rs.getString(2));
                employe.setNom(rs.getString(3));
                employe.setTel(rs.getString(4));
                employe.setDatenais(LocalDate.parse(rs.getString(5),df));
                employe.setSalaire(rs.getInt(6));
                Service service= new Service();
                service.setId(rs.getInt(8));
                service.setLibelle(rs.getString(9));
                employe.setService(service);

                employes.add(employe);
            }

            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return employes;
    }
}