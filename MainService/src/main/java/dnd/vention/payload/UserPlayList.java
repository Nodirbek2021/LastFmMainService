package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPlayList {
    private Integer userId;
    private Integer playlistId;

}
