package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArtistObject {
    public ArtistObject(String mbid, Integer listeners, String streamable, Integer playcount, String name, String url) {
        this.mbid = mbid;
        this.listeners = listeners;
        this.streamable = streamable;
        this.playcount = playcount;
        this.name = name;
        this.url = url;
    }

    private Integer id;
//    private List<ImageItem> image;
    private String mbid;
    private Integer listeners;
    private String streamable;
    private Integer playcount;
    private String name;
    private String url;
}