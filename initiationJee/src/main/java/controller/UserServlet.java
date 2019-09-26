package controller;
import model.User;
import services.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "user",urlPatterns = "/user")

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // resp.getWriter().println("Salut tout le monde");
        getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.logon(login, password);
        if(user!= null) {
            HttpSession session = req.getSession();
            req.getSession().setAttribute("connectedUser",user);
            //on le redirige dans la page suivante

            if(user.getProfil().equalsIgnoreCase("user")){
                getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(req, resp);
            }else{
                getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(req,resp);
            }
        } else {
            // on le redirige sur la même page avec un message d'erreur au cas ou cela ne marche pas
            req.setAttribute("error", "login ou password incorrect");
            getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);

        }
    }
}
