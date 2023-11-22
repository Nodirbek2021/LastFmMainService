package dnd.vention.payload.payloadForUrl;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaylistTracksDto {
    @JsonProperty("list")
    private Integer listId;
    @JsonProperty("tracks")
    private List<Integer> trackIdList;

}
