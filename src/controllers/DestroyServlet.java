package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.tasklist;
import utils.DBUtil;

/**
 * Servlet implementation class DestroyServlet
 */
@WebServlet("/destroy")
public class DestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public DestroyServlet() {
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            tasklist m = em.find(tasklist.class, (Integer)(request.getSession().getAttribute("tasklist_id")));

            em.getTransaction().begin();
            em.remove(m);
            em.getTransaction().commit();
            em.close();

            request.getSession().removeAttribute("tasklist_id");

            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

}
