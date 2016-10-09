package ch.heigvd.amt.gamificationapp.rest.dto;

/**
 *
 * @author seb
 */
public class UserDTO {
  
  private String username;
  
  public UserDTO() {
  }

  public UserDTO(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }  

}
