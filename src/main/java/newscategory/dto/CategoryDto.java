package newscategory.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String title;
    @JsonIgnore// при парсинге в json не будет учитываться
    private List<NewsDto> newsList;
}
