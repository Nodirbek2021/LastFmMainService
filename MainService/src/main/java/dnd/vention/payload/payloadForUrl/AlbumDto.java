package dnd.vention.payload.payloadForUrl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlbumDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("artistId")
    private Integer artist_id;
}
