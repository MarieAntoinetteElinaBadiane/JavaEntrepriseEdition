package model;

import java.util.*;

public class Service {
 private int id;
 private String libelle;

    public List<Employe> getEmployers() {
        return Employers;
    }

    public void setEmployers(Employe e) {
        List<Employe> employers =new ArrayList<Employe>();
        employers.add(e);
        Employers = employers;
    }

    private List<Employe> Employers=new ArrayList<Employe>() ;

 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }
 public String getLibelle() {
  return libelle;
 }

 public void setLibelle(String libelle) {
  this.libelle = libelle;
 }

}
