package de.coding.kata.nim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.springframework.util.StringUtils.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    private String name;

    public boolean isValid() {
        return !isEmpty(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
