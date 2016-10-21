
package ch.heigvd.amt.gamificationapp.services;

import ch.heigvd.amt.gamificationapp.model.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import ch.heigvd.amt.gamificationapp.services.dao.UsersManagerDAOLocal;
import java.nio.charset.Charset;

/**
 * Service tier which implements the business logic.
 *
 * @author F. Franchini, S. Henneberger
 */
@Stateless
public class UsersManagerService implements UsersManagerServiceLocal {

    /**
     *
     * Dependence injection for <code>UsersManagerDAOLocal</code>.
     */
    @EJB
    private UsersManagerDAOLocal usersManagerDAO;

    @Override
    public String normalize(String stringToNormalize) {
        return new String(stringToNormalize.getBytes(), Charset.forName("UTF-8"));
    }

    @Override
    public boolean isSyntacticallyValid(String stringToTest) {
        return !(stringToTest == null || stringToTest.trim().equals(""));
    }

    @Override
    public boolean userIdExists(long id) {
        for (User user : usersManagerDAO.findAllUsers()) {
            if (user.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean usernameIsAvailable(String username) {
        for (User user : usersManagerDAO.findAllUsers()) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean areCredentialsCorrect(String username, String password) {
        for (User user : usersManagerDAO.findAllUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
