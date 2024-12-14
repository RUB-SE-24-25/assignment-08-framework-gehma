package structra.assignment.task.de.rub.client;


import java.awt.*;

import javax.swing.*;

public class OurGUI {




    public OurGUI() {
        // create the gui window
        JFrame frame = new JFrame("CoolstesQuizEver");
        JPanel panel = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panel.setBackground(Color.decode("#bae1f8"));
        panel.setLayout(new GridBagLayout());

        // get the screen dimensions and calc the desired Frame size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        int frameWidth = (int) (width );
        int frameHeight = (int) (height );


        frame.pack();
        frame.setBackground(Color.BLACK);
        frame.setForeground(Color.RED);





        // create The Game Button
        JButton homeButton = new JButton("Start your QUIIZZZ!!!");

        homeButton.setFont(new Font("Arial", Font.BOLD, 40));
        homeButton.setBackground(Color.decode("#40629f"));
        homeButton.setForeground(Color.WHITE);
        homeButton.setPreferredSize(new Dimension(frameWidth/5,frameHeight/5));



        homeButton.addActionListener(e -> {
            QuizWindow quizWindow = new QuizWindow();
            frame.dispose();


        });

        panel.add(homeButton);
        frame.add(panel);



        frame.setVisible(true);
    }


    // Starts Application

}
