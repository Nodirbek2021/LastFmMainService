package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Track {
    private Integer id;
    private String name;
    private Integer duration;
    private Integer playcount;
    private Integer listeners;
    private String mbid;
    private String url;
    private Rank rank;
    private Artist artist;
    private boolean isBlocked;
}
