
package ch.heigvd.amt.gamificationapp.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ch.heigvd.amt.gamificationapp.services.UsersManagerServiceLocal;

/**
 *
 * @author seb
 */
public class CheckLoginServlet extends HttpServlet {
   
   @EJB
   private UsersManagerServiceLocal usersManagerService;

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
      
      HttpSession session = request.getSession(false);
      
      if (session != null && session.getAttribute("identity") != null) {
         // An user is authenticated
         request.getRequestDispatcher("/WEB-INF/pages/Home.jsp").forward(request, response);
      }
      else {
         // Nobody is authenticated
         request.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(request, response);
      }
   }

   /**
    * Handles the HTTP <code>POST</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

      String username = request.getParameter("username");
      String password = request.getParameter("password");
      String notification = "";

      boolean error = false;

      if (!usersManagerService.isSyntacticallyValid(username)) {
         notification = "Username entered is not valid !";
         error = true;
      } else if (!usersManagerService.isSyntacticallyValid(password)) {
         notification = "Password entered is not valid !";
         error = true;
      }

      username = usersManagerService.normalize(username);
      password = usersManagerService.normalize(password);
      
      // if username and password format are correct
      if (!error) {

         // Check if credentials entered are correct
         //UserManager userManager = (UserManager) request.getServletContext().getAttribute("userManager");
         if (usersManagerService.areCredentialsCorrect(username, password)) {
            // User session creation
            request.getSession();
            request.getSession(false).setAttribute("identity", new String(username));
            request.getRequestDispatcher("/WEB-INF/pages/Home.jsp").forward(request, response);
         }
         else {
            notification = "Username and/or password incorrect !";
         }
      }
      
      request.setAttribute("signupMessage", notification);
 
      request.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(request, response);

      response.setContentType("text/html;charset=UTF-8");

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
