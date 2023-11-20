package dnd.vention.payload;

import dnd.vention.payload.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    private Integer id;
    private RoleType roleType;
}
