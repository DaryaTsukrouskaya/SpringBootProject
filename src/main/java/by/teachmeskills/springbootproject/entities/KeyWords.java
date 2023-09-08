package by.teachmeskills.springbootproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class KeyWords {
    private String keyWords;
    private int currentPageNumber;

    public KeyWords() {
        this.currentPageNumber = 1;
    }

}
