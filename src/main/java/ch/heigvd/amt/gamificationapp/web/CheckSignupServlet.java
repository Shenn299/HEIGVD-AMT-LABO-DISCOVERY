/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificationapp.web;

import ch.heigvd.amt.gamificationapp.model.User;
import ch.heigvd.amt.gamificationapp.services.Notification;
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
@WebServlet(name = "CheckSignupServlet", urlPatterns = {"/CheckSignup"})
public class CheckSignupServlet extends HttpServlet {

   Notification notification = new Notification();

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
      
      // If the user session exists
      if (request.getSession(false) != null) {
         // An user is connected
         request.getRequestDispatcher("/WEB-INF/pages/Home.jsp").forward(request, response);
      }
      else {
         // Nobody is connected
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

      boolean error = false;

      if (username == null || username.trim().equals("")) {
         notification.setMessage("Username entered is not valid !");
         error = true;
      } else if (password == null || password.trim().equals("")) {
         notification.setMessage("Password entered is not valid !");
         error = true;
      }

      // if username and password format are correct
      if (!error) {

         UserManager userManager = new UserManager();

         // Check if username is available
         if (userManager.usernameIsAvailable(username)) {
            userManager.addUser(new User(username, password));
            //request.getServletContext().setAttribute("userManager", userManager);
            
            // User session creation
            request.getSession(true).setAttribute("identity", new String(username));

            request.getRequestDispatcher("/WEB-INF/pages/Home.jsp").forward(request, response);
            
         } else {
            notification.setMessage("Sorry, this username is already taken !");
         }

      }

      request.setAttribute("signupMessage", notification);

      request.getRequestDispatcher("/WEB-INF/pages/Signup.jsp").forward(request, response);

      response.setContentType("text/html;charset=UTF-8");
      try (PrintWriter out = response.getWriter()) {
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
