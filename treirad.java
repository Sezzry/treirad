package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class treirad extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private char currentPlayer;
    private JLabel statusLabel;
    private boolean gameWon;

    public treirad() {
        setTitle("Tre i rad vs AI");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buttons = new JButton[3][3];
        currentPlayer = 'X';
        gameWon = false;

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);
                buttonPanel.add(buttons[row][col]);
            }
        }

        statusLabel = new JLabel("Current Player: X");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(statusLabel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameWon) {
            return; // Spelet är slut
        }

        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton.getText().equals("")) {
            clickedButton.setText(String.valueOf(currentPlayer));
            clickedButton.setEnabled(false);

            if (checkGameResult(currentPlayer)) {
                statusLabel.setText("Player " + currentPlayer + " wins!");
                gameWon = true;
            } else if (isBoardFull()) {
                statusLabel.setText("It's a draw!");
                gameWon = true;
            } else {
                togglePlayer();
                makeRandomMove();
            }
        }
    }

    private void togglePlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusLabel.setText("Current Player: " + currentPlayer);
    }

    private boolean checkGameResult(char player) {
        // Kollar rader, colummer och diagonalt om nån vunnit
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(player)) &&
                    buttons[i][1].getText().equals(String.valueOf(player)) &&
                    buttons[i][2].getText().equals(String.valueOf(player))) {
                return true; // Rad vinst
            }

            if (buttons[0][i].getText().equals(String.valueOf(player)) &&
                    buttons[1][i].getText().equals(String.valueOf(player)) &&
                    buttons[2][i].getText().equals(String.valueOf(player))) {
                return true; // Column vinst
            }
        }

        if (buttons[0][0].getText().equals(String.valueOf(player)) &&
                buttons[1][1].getText().equals(String.valueOf(player)) &&
                buttons[2][2].getText().equals(String.valueOf(player))) {
            return true; // Kollar diagonal vinst längst upp åt vänster till längst ner till höger
        }

        if (buttons[0][2].getText().equals(String.valueOf(player)) &&
                buttons[1][1].getText().equals(String.valueOf(player)) &&
                buttons[2][0].getText().equals(String.valueOf(player))) {
            return true; // Kollar diagonal vinst längst upp åt höger till längst ner till vänster
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("")) {
                    return false; // Finns en tom ruta
                }
            }
        }
        return true; // Bordet är fullt
    }

    private void makeRandomMove() {
        if (gameWon) {
            return; // Spelet är slut
        }

        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().equals(""));

        buttons[row][col].setText(String.valueOf(currentPlayer));
        buttons[row][col].setEnabled(false);

        if (checkGameResult(currentPlayer)) {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            gameWon = true;
        } else if (isBoardFull()) {
            statusLabel.setText("It's a draw!");
            gameWon = true;
        } else {
            togglePlayer();
        }
    }
