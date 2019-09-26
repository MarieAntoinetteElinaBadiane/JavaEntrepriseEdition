package controller;

import model.Employe;
import model.Service;
import model.User;
import services.EmployeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name="employe", urlPatterns="/employe")

public class EmployeServlet extends HttpServlet {
    EmployeDao employeDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Charge une page de redirection
        String action = req.getParameter("action");
        EmployeDao employeDao = new EmployeDao();
        switch (action) {
            case "add":
                req.setAttribute("employes", employeDao.findEmployes());
                req.setAttribute("services", employeDao.findServices());
                getServletContext().getRequestDispatcher("/WEB-INF/employe.jsp").forward(req, resp);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        EmployeDao employeDao = new EmployeDao();
        switch (action) {
            case "add":

                try {
                    String matricule = req.getParameter("matricule");
                    String nom = req.getParameter("nom");
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate datenais = LocalDate.parse(req.getParameter("datenais"), df);
                    String tel = req.getParameter("tel");
                    String s=req.getParameter("salaire");
                    int salaire = Integer.parseInt(req.getParameter("salaire"));
                    int idser = Integer.parseInt(req.getParameter("service"));
                    Service service = new Service();
                    service.setId(idser);
                    Employe employe = new Employe();
                    employe.setDatenais(datenais);
                    employe.setMatricule(matricule);
                    employe.setNom(nom);
                    employe.setSalaire(salaire);
                    employe.setTel(tel);
                    employe.setService(service);

                    employeDao.addEmploye(employe);
                    req.setAttribute("employes", employeDao.findEmployes());

                } catch (Exception e) {
                    req.setAttribute("error","Il y a erreur dans l'insertion. Veuillez contacter l'administrateur");

                    e.printStackTrace();
                }
                req.setAttribute("services", employeDao.findServices());
                getServletContext().getRequestDispatcher("/WEB-INF/employe.jsp").forward(req, resp);
                break;
        }
    }
}