package dnd.vention.payload.payloadForUrl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaylistUpdateDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("playlist_type_id")
    private Integer playlistTypeId;
}
