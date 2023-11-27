package dnd.vention.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rate {
    private Integer id;
    private Integer userId;
    private Integer playlist_Id;
    private boolean liked;
}
