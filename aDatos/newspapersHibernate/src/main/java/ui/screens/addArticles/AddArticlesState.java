package ui.screens.addArticles;

import lombok.Data;
import modelo.Article;

import java.util.List;

@Data
public class AddArticlesState {
    private final List<Article> articles;
    private final String error;
}
