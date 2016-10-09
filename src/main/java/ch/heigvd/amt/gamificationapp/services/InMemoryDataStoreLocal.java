package ch.heigvd.amt.gamificationapp.services;

import ch.heigvd.amt.gamificationapp.model.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sébastien Henneberger - Fabien Fanchini
 */
@Local
public interface InMemoryDataStoreLocal {

  public long saveUser(User user);
  public User loadUser(long id);
  public void deleteUser(long id);

  public List<User> findAllUsers();
  
}
