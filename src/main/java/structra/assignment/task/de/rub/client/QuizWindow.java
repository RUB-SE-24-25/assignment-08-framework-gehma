package structra.assignment.task.de.rub.client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuizWindow {

    private static JButton newQuestion;

    public QuizWindow() {
        QuizWindowGenerator();
    }
    int frameHeight;
    static ArrayList<String> currentAnswer;
    static String explanation;


    public void QuizWindowGenerator() {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    int frameWidth = (int) (width);
    frameHeight = (int) (height);


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
        JPanel answerPanel = getAnswerPanel();

        panel.add(answerPanel,BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        //buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        newQuestion = new JButton(" First Question");

        GridBagConstraints buttonGbc = new GridBagConstraints();

        newQuestion.addActionListener(e ->{
                String newQuest= generateQuestion();
                answerArea.setText(newQuest);
                newQuestion.setEnabled(false);
                newQuestion.setBackground(Color.LIGHT_GRAY);
                answerField.setText("enter your answer here");
                answerButton.setBackground(Color.decode("#40629f"));
                answerButton.setText("submit");


       });
        newQuestion.setFont(new Font("Arial", Font.PLAIN, 40));
        newQuestion.setEnabled(true);
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
    private JTextArea answerArea;
    private static JButton answerButton;
    private static JTextField answerField;

    public JPanel getAnswerPanel() {
        JPanel answerPanel = new JPanel();

        answerPanel.setBackground(Color.LIGHT_GRAY);

        // create grid Bag layout so i can set up the styling

        answerPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

        // the text area where the questions are getting into after Button click toDo
        answerArea = new JTextArea("Ready for the First Question?");

            answerArea.setFont(new Font("Arial", Font.PLAIN,frameHeight/50 ));
            answerArea.setEditable(false);
            answerArea.setLineWrap(true);
            answerArea.setWrapStyleWord(true);
            answerArea.setFocusable(false);



            // setting up the constraints for the Layout
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span across both columns
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 0.90;

        answerPanel.add(answerArea, gbc);
        // creating the answerfield fpr user input
        answerField = new JTextField("If you're ready Press First Question");
            answerField.setEditable(false);
            answerField.setFont(new Font("Arial", Font.PLAIN, 20));
            answerField.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    answerField.setText("");
                }
            });
            // add the listener fot the submitting
            answerField.addActionListener(e -> {

                checkAnswer();
            });

            gbc.gridx = 0;
            gbc.gridy = 1; // Same row as the submit button
            gbc.gridwidth = 1; // Span only 1 column for answer field
            gbc.weightx = 0.8;
            gbc.weighty=0.1;
            gbc.fill = GridBagConstraints.BOTH;

        answerPanel.add(answerField, gbc);
        // create the button for submitting the answers with the respective grid layout
        answerButton = new JButton("Submit");
            answerButton.setEnabled(false);
            answerButton.addActionListener(e -> checkAnswer());
            answerButton.setFont(new Font("Arial", Font.PLAIN, 20));
            answerButton.setBackground(Color.decode("#40629f")); // blue
            answerButton.setForeground(Color.WHITE);


            gbc.gridx = 1;  // Place the submit button in the next column
            gbc.weightx = 0.2;
            gbc.fill = GridBagConstraints.BOTH;

        answerPanel.add(answerButton, gbc);
        return answerPanel;
    }
        // Todo create AnswerHandeling

    public void checkAnswer(){
        //System.out.println("Test");
        answerField.setEditable(true);
        if ( answerArea.getText().contentEquals("Model Failed Try Again")) {
            newQuestion.setEnabled(true);
            newQuestion.setBackground(Color.decode("#40629f"));
        }
        else
        {
            // check user input for correct answer and return the fittin button
            for (String s : currentAnswer) {
                //System.out.println(s);
                if (answerField.getText().contentEquals(s)) {
                    setAnswerTrue();
                    return;
                }
                answerButton.setBackground(Color.RED);
                answerButton.setText("Wrong answer\n Try again");
                answerField.setRequestFocusEnabled(true);

            }


        }

    }
    // helper class to change button to True and allow new answer
    private  void setAnswerTrue(){
        answerField.setRequestFocusEnabled(true);
        answerButton.setBackground(Color.GREEN);
        answerButton.setText("Well DONE!!");
        answerButton.setEnabled(false);
        answerArea.append("\n"+explanation);
        answerArea.append("\n");
        answerArea.append("Answer: " +currentAnswer);
        newQuestion.setBackground(Color.decode("#40629f"));
        newQuestion.setEnabled(true);
    }

    public static String generateQuestion(){
       // Submit button enable
        answerButton.setEnabled(true);
        //enable anserfield
        answerField.setRequestFocusEnabled(true);
        answerField.setEditable(true);
        // update the question and set the new Button to unsolved

        newQuestion.setEnabled(false);
        newQuestion.setBackground(Color.LIGHT_GRAY);
        newQuestion.setText("Next Question");
        ArrayList<Object> result = QuestGen.questionGen();

        currentAnswer= (ArrayList<String>) result.get(2);
        explanation= (String) result.get(1);
        System.out.println(currentAnswer);
        return (String) result.get(0);

    }

}
