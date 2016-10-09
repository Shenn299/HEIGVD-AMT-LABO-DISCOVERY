package ch.heigvd.amt.gamificationapp.rest;

import ch.heigvd.amt.gamificationapp.model.User;
import ch.heigvd.amt.gamificationapp.rest.dto.UserDTO;
import ch.heigvd.amt.gamificationapp.services.InMemoryDataStoreLocal;
import java.net.URI;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author seb
 */
@Stateless
@Path("/users")
public class UserResource {

  @EJB
  private InMemoryDataStoreLocal inMemoryDataStore;

  @Context
  UriInfo uriInfo;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<UserDTO> getUsers(@QueryParam(value = "byName" ) String byName) {
    List<User> user = inMemoryDataStore.findAllUsers();
    return user.stream()
      .filter(p -> byName == null || p.getUsername().equalsIgnoreCase(byName))
      .map(p -> toDTO(p))
      .collect(toList());
      
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(UserDTO userDTO) {
    User user  = fromDTO(userDTO);
    long userId = inMemoryDataStore.saveUser(user);

    URI href = uriInfo
      .getBaseUriBuilder()
      .path(UserResource.class)
      .path(UserResource.class, "getUser")
      .build(userId);

    return Response
      .created(href)
      .build();
  }

  @Path("{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO getUser(@PathParam(value = "id") long id) {
    User user = inMemoryDataStore.loadUser(id);
    return toDTO(user);
  }
  
  @Path("{id}")
  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  public void deleteUser(@PathParam(value = "id") long id) {
    inMemoryDataStore.deleteUser(id);
  }
  
  public User fromDTO(UserDTO dto) {
    return new User(dto.getUsername());
  }
  
  public UserDTO toDTO(User user) {
    UserDTO dto = new UserDTO(user.getUsername());
    return dto;
  }

}
