package Src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SpellChecker {
    private Dictionary dict;
    private AVLTree avlTree;
    private JButton addButton;
    private JFrame frame;
    private JTextField textField;
    private JLabel spellcheckLabel;
    private JButton checkButton;
    private JButton removeButton;
    private JButton clearButton;
    public SpellChecker(String filepath) throws FileNotFoundException {
        dict = new Dictionary(filepath);
        avlTree = new AVLTree();
        for (String word : dict) {
            avlTree.insert(word);
        }
    }

    private class AddWordButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Let's add a new word to the dictionary
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JTextField textField = new JTextField();
            panel.add(new JLabel("Enter a new word to add:"));
            panel.add(textField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Add Word", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String word = textField.getText();
                if (!word.isEmpty()) {
                    if (!dict.contains(word)) {
                        dict.add(word);
                        avlTree.insert(word);
                        JOptionPane.showMessageDialog(frame, "The word \"" + word + "\" has been added!", "Add Word", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "The word \"" + word + "\" is already in the dictionary.", "Add Word", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private class RemoveWordButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Let's remove a word from the dictionary
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JTextField textField = new JTextField();
            panel.add(new JLabel("Enter a word to remove:"));
            panel.add(textField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Remove Word", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String word = textField.getText();
                if (!word.isEmpty()) {
                    if (dict.contains(word)) {
                        dict.remove(word);
                        avlTree.remove(word);
                        JOptionPane.showMessageDialog(frame, "The word \"" + word + "\" has been removed!", "Remove Word", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "The word \"" + word + "\" is not in the dictionary.", "Remove Word", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    //start
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//PART 2
    
    //MAIN METHOD to implement the GUI
    public void start() {
        frame = new JFrame("Spell Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        textField = new JTextField();
        spellcheckLabel = new JLabel("Enter a sentence to check spelling");
        checkButton = new JButton("Check Spelling");
        clearButton = new JButton("Clear");

        checkButton.addActionListener(new CheckSpellingListener());
        clearButton.addActionListener(new ClearListener());

        panel.add(textField);
        panel.add(spellcheckLabel);
        panel.add(checkButton);
        panel.add(clearButton);
        addButton = new JButton("Add Word");
        removeButton = new JButton("Remove Word");

        addButton.addActionListener(new AddWordButtonListener());
        removeButton.addActionListener(new RemoveWordButtonListener());
        panel.add(addButton);
        panel.add(removeButton);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
// implenting the action listener 
    
    private class CheckSpellingListener implements ActionListener {
        @Override
        //changing words to lower case to keep it uniform 
        public void actionPerformed(ActionEvent e) {
            String sentence = textField.getText().toLowerCase();
            //splits the sentences in spaces
            String[] words = sentence.split("\\s+");
            //storing the corrected words

            List<String> correctedWords = new ArrayList<>();
            //used to track any misspellings

            boolean hasMisspellings = false;
            
            //loop iterating over word the check if present in the avl tree

            for (String word : words) {
                boolean avlTreeContains = avlTree.contains(word);
                if (!avlTreeContains) {
                    hasMisspellings = true;
                    String suggestion = getCorrectedSpelling(word);
                    if (suggestion != null) {
                        correctedWords.add(suggestion);
                    } else {
                        correctedWords.add(word);
                    }

                } else {
                    correctedWords.add(word);
                }
            }
            
            //get corrected word and add it to the list

            if (hasMisspellings) {
                String correctedSentence = String.join(" ", correctedWords);
                textField.setText(correctedSentence);
            } else {
            	// incase no misspellings
                JOptionPane.showMessageDialog(frame, "There are no misspellings.", "Spell Check Result", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    //implementing the listeners
    
    
    //sets the field to an empty text field 

    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textField.setText("");
        }
    }
    
//receives the word as an input and tries to search for a corrected spelling suggestions
    private String getCorrectedSpelling(String word) {
    	//creating the dialog to showcase the options
        JDialog dialog = new JDialog();
        List<String> suggestions = dict.suggest(word);
        //if no suggestions return null
        if (suggestions.isEmpty()) {
            return null;
        }
        
                 //if there are suggestions we create a suggestion box and populate it with the suggestions obtained from the dictionary
        
        JComboBox<String> suggestionBox = new JComboBox<>(suggestions.toArray(new String[0]));
//apply button to apply the change
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            //retrieves the word selected in the combobox and returns it in the text field
            public void actionPerformed(ActionEvent e) {
                String correctedWord = (String) suggestionBox.getSelectedItem();
                if (correctedWord != null && !correctedWord.isEmpty()) {
                    String sentence = textField.getText();
                    int index = sentence.toLowerCase().indexOf(word);
                    String newSentence = sentence.substring(0, index) + correctedWord + sentence.substring(index + word.length());
                    textField.setText(newSentence);
                }
                dialog.dispose();
            }
        })
        ;
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        //PART 3

        JButton manualButton = new JButton("Input Manual Correction");
        manualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String manualWord = JOptionPane.showInputDialog("Enter manual correction:");
                if (manualWord != null && !manualWord.isEmpty()) {
                    String sentence = textField.getText();
                    int index = sentence.toLowerCase().indexOf(word);
                    String newSentence = sentence.substring(0, index) + manualWord + sentence.substring(index + word.length());
                    textField.setText(newSentence);
                }
                dialog.dispose();
            }
        });
        //stop

        JPanel suggestionPanel = new JPanel(new BorderLayout());
        suggestionPanel.add(suggestionBox, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(applyButton);
        buttonPanel.add(manualButton);
        suggestionPanel.add(buttonPanel, BorderLayout.SOUTH);

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("\"").append(word).append("\" is spelled incorrectly.");
        messageBuilder.append("\nDid you mean: ");
        for (int i = 0; i < Math.min(suggestions.size(), 5); i++) {
            messageBuilder.append("\"").append(suggestions.get(i)).append("\"");
            if (i < Math.min(suggestions.size(), 5) - 1) {
                messageBuilder.append(", ");
            }
        }
        messageBuilder.append("\nPlease select a suggestion or input a manual correction, or press Cancel to skip");

        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(new JLabel(messageBuilder.toString()), BorderLayout.NORTH);
        dialog.getContentPane().add(suggestionPanel, BorderLayout.CENTER);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.getContentPane().add(cancelButton, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        return null;
    }

    public static void main(String[] args) {
        String filepath = "C:/Users/Asum9/OneDrive/Documents/words.txt";
        SpellChecker spellChecker;
        try {
            spellChecker = new SpellChecker(filepath);
            spellChecker.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}



