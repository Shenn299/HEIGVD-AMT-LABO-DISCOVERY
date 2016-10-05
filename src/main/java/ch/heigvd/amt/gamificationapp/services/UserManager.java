package ch.heigvd.amt.gamificationapp.services;

import ch.heigvd.amt.gamificationapp.model.User;
import java.util.LinkedList;
import java.util.List;

public class UserManager {

   private static List<User> listUsers = new LinkedList<>();

   public void addUser(User user) {
      if (user != null) {
         if (!listUsers.contains(user)) {
            listUsers.add(user);
         }
      }
   }

   public void delUser(User user) {
      if (user != null) {
         if (listUsers.contains(user)) {
            listUsers.remove(user);
         }
      }
   }

   public boolean usernameIsAvailable(String username) {
      for (User user : listUsers) {
         if (user.getUsername().equals(username)) {
            return false;
         }
      }
      return true;
   }
   
   public boolean areCredentialsCorrect(String username, String password) {
      for (User user : listUsers) {
         String s = "";
         if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return true;
         }
      }
      return false;
   }
}
