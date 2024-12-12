package structra.assignment.task.impl;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuizWindow {

    public QuizWindow() {
        QuizWindowGenerator();
    }

    public void QuizWindowGenerator() {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    int frameWidth = (int) (width);
    int frameHeight = (int) (height);


    JFrame quizFrame= new JFrame("The Quiz will be HARRRD");
        quizFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel= new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

    quizFrame.add(panel);

    JLabel questLabel = new JLabel(" AREEE YOUEEE REAAADYY");
        questLabel.setFont(new Font("Arial", Font.BOLD, 20));


    panel.add(questLabel,BorderLayout.NORTH);


    // create new panel for the answers
    JPanel answerPanel = new JPanel();

        answerPanel.setBackground(Color.LIGHT_GRAY);

    // create grid Bag layout so i cas set up the styling

    answerPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

    // the text area where the questions are getting into after Button click toDo
    JTextArea answerArea = new JTextArea("new Ansewers Herer");
        answerArea.setFont(new Font("Arial", Font.PLAIN, 15));
        answerArea.setEditable(false);
        answerArea.setLineWrap(true);
        answerArea.setWrapStyleWord(true);
        answerArea.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(answerArea);

        // setting up the constraints for the Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across both columns
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.90;

    answerPanel.add(scrollPane, gbc);

    JTextField answerField = new JTextField("enter your answer here");

        answerField.setFont(new Font("Arial", Font.PLAIN, 20));

        gbc.gridx = 0;
        gbc.gridy = 1; // Same row as the submit button
        gbc.gridwidth = 1; // Span only 1 column for answer field
        gbc.weightx = 0.8; gbc.weighty=0.1;
        gbc.fill = GridBagConstraints.BOTH;

    answerPanel.add(answerField, gbc);

    JButton answerButton = new JButton("submit");
        answerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        answerButton.setBackground(Color.decode("#40629f")); // blue
        answerButton.setForeground(Color.WHITE);


        gbc.gridx = 1;  // Place the submit button in the next column
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;

    answerPanel.add(answerButton, gbc);

    panel.add(answerPanel,BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        //buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

    JButton newQuestion = new JButton(" nexxxxt QUEEEEESTIOOON");
        GridBagConstraints buttonGbc = new GridBagConstraints();
        newQuestion.setFont(new Font("Arial", Font.PLAIN, 40));

        newQuestion.setBackground(Color.decode("#40629f"));
        newQuestion.setForeground(Color.WHITE);

        buttonGbc.gridx = 0; // Place button in the first column
        buttonGbc.gridy = 0; // Place button in the first row
        buttonGbc.weightx = 0.2; // Make button occupy 50% of the width
        buttonGbc.fill = GridBagConstraints.HORIZONTAL; // Make button stretch horizontally

        buttonPanel.add(newQuestion, buttonGbc); // Add button to the panel

        panel.add(buttonPanel, BorderLayout.SOUTH);



    quizFrame.setSize(frameWidth,frameHeight);
    quizFrame.setVisible(true);

    }
    
    public void Test(){

    }


}
