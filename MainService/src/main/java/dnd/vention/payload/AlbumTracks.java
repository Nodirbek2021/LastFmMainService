package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlbumTracks{
    private Integer id;
    private Integer trackId;
    private Integer albumId;
}
