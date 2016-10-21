package ch.heigvd.amt.gamificationapp.web;

import ch.heigvd.amt.gamificationapp.model.User;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ch.heigvd.amt.gamificationapp.services.UsersManagerServiceLocal;
import ch.heigvd.amt.gamificationapp.services.dao.UsersManagerDAOLocal;

/**
 *
 * @author seb
 */
public class CheckSignupServlet extends HttpServlet {

   @EJB
   private UsersManagerServiceLocal usersManagerService;

   // If all conditions are met (user_id exits and user_name is available),
   // we use directly DAO service without to pass via the business service,
   // because we don't change data, we simply treat them.
   @EJB
   private UsersManagerDAOLocal usersManagerDAO;

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
         request.getRequestDispatcher("/WEB-INF/pages/Signup.jsp").forward(request, response);
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
      String passwordConfirmed = request.getParameter("passwordConfirmed");
      String notification = "";

      boolean error = false;

      if (!usersManagerService.isSyntacticallyValid(username)) {
         notification = "Username entered is not valid !";
         error = true;
      }
      else if (!usersManagerService.isSyntacticallyValid(password)) {
         notification = "Password entered is not valid !";
         error = true;
      }
      else if (!usersManagerService.isSyntacticallyValid(passwordConfirmed)) {
         notification = "Password confirmation entered is not valid !";
         error = true;
      }

      // if username and password format are correct
      if (!error) {

         username = usersManagerService.normalize(username);
         password = usersManagerService.normalize(password);
         passwordConfirmed = usersManagerService.normalize(passwordConfirmed);

         // Check if username is available
         if (usersManagerService.usernameIsAvailable(username)) {

            // Check if password confirmation = password
            if (passwordConfirmed.equals(password)) {

               long id = usersManagerDAO.saveUser(new User(username, password));

               if (id != 0) {
                  // User session creation
                  request.getSession(true).setAttribute("identity", username);
                  request.getRequestDispatcher("/WEB-INF/pages/Home.jsp").forward(request, response);
               }
               else {
                  notification = "Internal Server Error 500";
               }
            }
            else {
               notification = "Password confirmation doesn't match the password !";
            }
         }
         else {
            notification = "Sorry, this username is already taken !";
         }
      }
      request.setAttribute("signupMessage", notification);
      request.getRequestDispatcher("/WEB-INF/pages/Signup.jsp").forward(request, response);

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
