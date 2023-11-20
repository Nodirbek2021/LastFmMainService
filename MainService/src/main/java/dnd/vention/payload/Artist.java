package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Artist {
    private Integer id;
    private String mbid;
    private Integer listeners;
    private String streamable;
    private Integer playcount;
    private String name;
    private String url;

}
