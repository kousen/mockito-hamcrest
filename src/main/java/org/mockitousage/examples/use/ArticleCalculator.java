package org.mockitousage.examples.use;

public interface ArticleCalculator {
    int countArticles(String newspaper);
    int countArticlesInPolish(String newspaper);
    int countNumberOfRelatedArticles(Article article);
    int countAllArticles(String... publications);
}
