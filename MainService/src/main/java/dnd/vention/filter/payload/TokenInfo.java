package dnd.vention.filter.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenInfo {
        private String username;
        private String password;
        private Integer role_id;
        private long expirationTimestamp;

    public TokenInfo(String username, String password, long expirationTimestamp) {
        this.username = username;
        this.password = password;
        this.expirationTimestamp = expirationTimestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public TokenInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
