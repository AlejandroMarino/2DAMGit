package ui.screens.articles;

import lombok.Data;
import modelo.Article;

import java.util.List;

@Data
public class ArticlesState {
    private final List<Article> articles;
    private final String error;
}
