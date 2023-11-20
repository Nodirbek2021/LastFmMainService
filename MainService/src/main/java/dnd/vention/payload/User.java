package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private boolean isEnabled;
    private boolean isBlocked;
    private List<Playlist> playlists;
    private List<Album> albumList;
    private List<Track> trackList;
    private Role role;

    public User(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public User(String firstname, String lastname, String username, String password, boolean isEnabled, boolean isBlocked, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.isBlocked = isBlocked;
        this.role = role;
    }

    public User(Integer id, String firstname, String lastname, String username, String password, boolean isEnabled, boolean isBlocked, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.isBlocked = isBlocked;
        this.role = role;
    }
}
