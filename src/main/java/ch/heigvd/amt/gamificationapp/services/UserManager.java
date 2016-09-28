package ch.heigvd.amt.gamificationapp.services;

import ch.heigvd.amt.gamificationapp.model.User;
import java.util.List;

public class UserManager {

   private List<User> listUsers;
   
   public final User getRandomUser() {
      return new User("Toto","password");      
   }

   public final void addUser(User user) {
      if (user != null) {
         if (!listUsers.contains(user)) {
            listUsers.add(user);
         }
      }
   }
   
   public final void delUser(User user) {
      if (user != null) {
         if (listUsers.contains(user)) {
            listUsers.remove(user);
         }
      }
   }
}
