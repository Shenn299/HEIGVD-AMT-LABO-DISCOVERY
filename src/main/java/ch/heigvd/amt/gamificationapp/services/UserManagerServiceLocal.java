package ch.heigvd.amt.gamificationapp.services;

import ch.heigvd.amt.gamificationapp.model.User;
import javax.ejb.Local;

/**
 *
 * @author Sébastien Henneberger - Fabien Franchini
 */
@Local
public interface UserManagerServiceLocal {
   
   public void addUser(User user);

   public boolean usernameIsAvailable(String username);

   public boolean areCredentialsCorrect(String username, String password);
  
}
