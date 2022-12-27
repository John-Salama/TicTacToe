/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author husse
 */
public class TicTacToe_GUI extends JFrame implements ActionListener, MouseListener {

    JPanel pGame;
    JPanel pControl;
    JPanel plogo;
    JButton btplay, exit;
    JRadioButtonMenuItem human, computer;
    int WHO_FIRST = 0;
    BoardGame boardGame = new BoardGame();
    Turn turn = new Turn();
    GridLayout grid = new GridLayout(3, 3);
    JButton bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    JButton button[];
    ImageIcon iX = new ImageIcon("imgs\\x.png");
    ImageIcon iO = new ImageIcon("imgs\\o.png");

    public TicTacToe_GUI() {
        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        pGame = new JPanel();
        pGame.setBackground(Color.WHITE);
        pGame.setLayout(grid);
        plogo = new JPanel();
        add(pGame, BorderLayout.CENTER);
        add(plogo, BorderLayout.NORTH);
        plogo.add(new JLabel(new ImageIcon("imgs\\logo.jpg")));
        pControl = new JPanel();
        pControl.setLayout(new GridLayout(4, 1));
        JPanel whofirst = new JPanel();
        whofirst.add(human = new JRadioButtonMenuItem("I'm first"));
        human.setBackground(Color.WHITE);
        whofirst.add(computer = new JRadioButtonMenuItem("You first"));
        computer.setBackground(Color.WHITE);
        ButtonGroup a = new ButtonGroup();
        a.add(human);
        a.add(computer);
        human.setSelected(true);

        pGame.setVisible(false);
        pControl.add(whofirst);
        pControl.setBackground(Color.WHITE);
        whofirst.setBackground(Color.WHITE);
        pControl.add(btplay = new JButton());
        pControl.add(exit = new JButton());
        btplay.setIcon(new ImageIcon("imgs\\play.png"));
        exit.setIcon(new ImageIcon("imgs\\edit.png"));
        btplay.setOpaque(false);
        btplay.setContentAreaFilled(false);
        btplay.setBorderPainted(false);

        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        btplay.addActionListener(this);
        exit.addActionListener(this);
        add(pControl, BorderLayout.EAST);
        setButton();

        for (final JButton bt : button) {
            bt.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (bt.getName().equalsIgnoreCase("")) {
                        int indext = -1;
                        for (int i = 0; i < button.length; i++) {
                            if (bt == button[i]) {
                                indext = i;
                                System.out.println("position : " + indext);
                                button[i].setIcon(new ImageIcon("imgs\\x.png"));
                            }
                        }

                        boardGame.placeAMove(new Point(indext / 3, indext % 3), 2);
                        boardGame.displayBoard();
          //              System.out.println("Placed a move at: (" + indext / 3 + ", " + indext % 3 + ")");
                        int next = boardGame.returnNextMove();

                        if (next != -1) {   //If the game isn't finished yet!   
                            int indexCell = next;

                            button[indexCell].setIcon(new ImageIcon("imgs\\o.png"));
                            button[indexCell].setName("1");//used
                            boardGame.placeAMove(new Point(indexCell / 3, indexCell % 3), 1);
                            bt.setName("1");
                        }

                        if (boardGame.isGameOver()) {

                            if (boardGame.isXWon()) {

                                JOptionPane.showMessageDialog(null, "You lost");
                                boardGame.resetBoard();
                                boardGame.displayBoard();
                                setVisible(false);
                                new TicTacToe_GUI();

                            } else {
                                JOptionPane.showMessageDialog(null, "Draw");
                                boardGame.resetBoard();
                                boardGame.displayBoard();
                                setVisible(false);
                                new TicTacToe_GUI();
                            }

                        }

                    }

                }
            });

        }

        int x = this.getToolkit().getScreenSize().width / 2 - 200;
        int y = this.getToolkit().getScreenSize().height / 2 - 200;
        setLocation(x, y);
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void setButton() {
        bt1 = new JButton();
        bt1.setName("");
        bt2 = new JButton();
        bt2.setName("");
        bt3 = new JButton();
        bt3.setName("");
        bt4 = new JButton();
        bt4.setName("");
        bt5 = new JButton();
        bt5.setName("");
        bt6 = new JButton();
        bt6.setName("");
        bt7 = new JButton();
        bt7.setName("");
        bt8 = new JButton();
        bt8.setName("");
        bt9 = new JButton();
        bt9.setName("");

        button = new JButton[9];
        JButton bt[] = {bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9};

        for (int i = 0; i < button.length; i++) {
            button[i] = bt[i];
            button[i].setBackground(Color.WHITE);
            pGame.add(button[i]);
        }

    }

    public static void main(String[] args) {

        new TicTacToe_GUI();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btplay) {
            WHO_FIRST = getFirst();
            if (WHO_FIRST == 1)//human
            {
                turn.next = Turn.NextMove.O;
                pControl.setVisible(false);
                pGame.setVisible(true);

            }
            if (WHO_FIRST == 2)//computer
            {

                pControl.setVisible(false);
                pGame.setVisible(true);
                int index = new Random().nextInt(9);
                button[index].setIcon(new ImageIcon("imgs\\o.png"));
                button[index].setName("1");
                boardGame.placeAMove(new Point(index / 3, index % 3), 1);
                turn.next = Turn.NextMove.X;

            }
        }
        if (e.getSource() == exit) {
            System.exit(0);
        }

    }

    private int getFirst() {
        if (human.isSelected()) {
            return 1;
        } else if (computer.isSelected()) {
            return 2;
        }
        return 0;
    }

}
