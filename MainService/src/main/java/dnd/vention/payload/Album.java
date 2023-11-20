package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Album {
    private Integer id;
    private  String name;
    private Integer playcount;
    private Integer listeners;
    private Artist artist;
    private List<Track> trackList;
    private boolean isBlocked;

}
