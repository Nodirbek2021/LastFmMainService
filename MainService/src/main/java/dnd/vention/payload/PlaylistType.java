package dnd.vention.payload;

import dnd.vention.payload.enums.PlaylistTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaylistType {
    private Integer id;
    private PlaylistTypeEnum playlistTypeEnum;
}
