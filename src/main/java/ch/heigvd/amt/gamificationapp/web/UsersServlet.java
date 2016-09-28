
package ch.heigvd.amt.gamificationapp.web;

import ch.heigvd.amt.gamificationapp.model.User;
import ch.heigvd.amt.gamificationapp.services.UserManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author seb
 */
@WebServlet(name = "UsersServlet", urlPatterns = {"/Users"})
public class UsersServlet extends HttpServlet {
   
   // Va etre appelé une fois, attention pas thread-safe
   UserManager userManager = new UserManager();

   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      
      User toto = userManager.getRandomUser();
      
      // Accrocher l'objet à l'objet request
      request.setAttribute("theUser", toto);
      
      // Le contrôleur à appelé le service et a obtenu un model, va passer le contrôle à la vue
      request.getRequestDispatcher("/WEB-INF/pages/User.jsp").forward(request, response);
      
      response.setContentType("text/html;charset=UTF-8");
      try (PrintWriter out = response.getWriter()) {
         /* TODO output your page here. You may use following sample code. */
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet UsersServlet</title>");         
         out.println("</head>");
         out.println("<body>");
         out.println("<h1>Servlet UsersServlet at " + request.getContextPath() + "</h1>");
         out.println("<h1> User is " + toto.getUsername() + "</h1>");
         out.println("</body>");
         out.println("</html>");
      }
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo() {
      return "Short description";
   }// </editor-fold>

}
