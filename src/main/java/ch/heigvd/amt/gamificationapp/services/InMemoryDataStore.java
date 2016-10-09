package ch.heigvd.amt.gamificationapp.services;

import ch.heigvd.amt.gamificationapp.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;

/**
 *
 * @author Sébastien Henneberger - Fabien Franchini
 */
@Singleton
public class InMemoryDataStore implements InMemoryDataStoreLocal {

  private long userIdCounter;

  private final Map<Long, User> users = new HashMap<>();

  @Override
  public long saveUser(User user) {
    userIdCounter++;
    users.put(userIdCounter, user);
    return userIdCounter;
  }

  @Override
  public User loadUser(long id) {
    return users.get(id);
  }
  
  @Override
  public void deleteUser(long id) {
     users.remove(id);
  }

  @Override
  public List<User> findAllUsers() {
    return new ArrayList(users.values());
  }
  
}
