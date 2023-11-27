package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Playlist {
    private Integer id;
    private String name;
    private Integer playcount;
    private Integer listeners;
    private List<Track> trackList;
    private RankPlaylist rank;
    private PlaylistType playlistType;
}
