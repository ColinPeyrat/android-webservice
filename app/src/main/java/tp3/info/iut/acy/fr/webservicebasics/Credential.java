package tp3.info.iut.acy.fr.webservicebasics;

/**
 * Created by peyratc on 27/01/2016.
 */
public class Credential {

    public String username;
    public String password;

    public Credential() {
    }

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
