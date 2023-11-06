package Src;

import java.util.LinkedList;
import java.util.List;

public class SpellCheckerResult {
    private String word;
    private List<String> suggestions;

    public SpellCheckerResult(String word, List<String> suggestions) {
        this.word = word;
        this.suggestions = new LinkedList<>(suggestions);
    }

    public String getWord() {
        return word;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
