package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayListTrack {
    private Integer id;
    private Integer trackId;
    private Integer playlistId;

}
