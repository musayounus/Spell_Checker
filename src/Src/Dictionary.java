package Src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Dictionary implements Iterable<String> {
    private HashSet<String> words;

    public Dictionary(String filepath) throws FileNotFoundException {
        words = new HashSet<>();
        Scanner scanner = new Scanner(new File(filepath));
        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();
            words.add(word);
        }
        scanner.close();
    }
    
    public void add(String word) {
        words.add(word.toLowerCase());
    }
    
    public void remove(String word) {
        words.remove(word.toLowerCase());
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public List<String> suggest(String misspelled) {
        List<String> suggestions = new ArrayList<>();
        for (String word : words) {
            if (isSuggestion(misspelled, word)) {
                suggestions.add(word);
            }
        }
        return suggestions;
    }

    private boolean isSuggestion(String misspelled, String candidate) {
        if (misspelled.length() != candidate.length()) {
            return false;
        }
        int diffCount = 0;
        for (int i = 0; i < misspelled.length(); i++) {
            if (misspelled.charAt(i) != candidate.charAt(i)) {
                diffCount++;
                if (diffCount > 1) {
                    return false;
                }
            }
        }
        return diffCount == 1;
    }

    @Override
    public Iterator<String> iterator() {
        return words.iterator();
    }
}

