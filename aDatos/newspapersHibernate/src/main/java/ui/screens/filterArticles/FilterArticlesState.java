package ui.screens.filterArticles;

import lombok.Data;
import modelo.Article;

import java.util.List;
@Data
public class FilterArticlesState {
    private final List<Article> articles;
    private final String error;
}
