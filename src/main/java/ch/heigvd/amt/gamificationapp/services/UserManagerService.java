package ch.heigvd.amt.gamificationapp.services;

import ch.heigvd.amt.gamificationapp.model.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sébastien Henneberger - Fabien Franchini
 */
@Stateless
public class UserManagerService implements UserManagerServiceLocal {

   @EJB
   private InMemoryDataStoreLocal inMemoryDataStore;

   @Override
   public void addUser(User user) {
      
      List<User> users = inMemoryDataStore.findAllUsers();
      
      if (user != null) {
         if (!users.contains(user)) {
            inMemoryDataStore.saveUser(user);
         }
      }
   }

   @Override
   public boolean usernameIsAvailable(String username) {
      for (User user : inMemoryDataStore.findAllUsers()) {
         if (user.getUsername().equals(username)) {
            return false;
         }
      }
      return true;
   }

   @Override
   public boolean areCredentialsCorrect(String username, String password) {
      for (User user : inMemoryDataStore.findAllUsers()) {
         if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return true;
         }
      }
      return false;
   }

}
