import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main {
    JFrame frame;
    Puzzle puzzle;
    JLabel label;






    public void setLayout() {

        frame = new JFrame("Puzzlegame ©FreakySheldon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 485);
        label = new JLabel("0");
        label.setFont(new Font("Serif", Font.PLAIN, 80));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        frame.add(label, BorderLayout.PAGE_END);
        puzzle = new Puzzle(new ImageIcon(Main.class.getResource("/middlesex-university.png")).getImage(),label);

        frame.add(puzzle, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);

        frame.setResizable(false);
        frame.setVisible(true);

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!puzzle.started) {
                    puzzle.start();
                }else {
                    if (!puzzle.mixing) {
                        puzzle.onClick(e);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setLayout();
    }

}
