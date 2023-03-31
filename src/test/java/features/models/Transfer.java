package features.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transfer {
    public String uuid;
    public double number;
    public int size;
    public Instant date;
    public String name;
}
