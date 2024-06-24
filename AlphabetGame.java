import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Collections;

public class AlphabetGame extends Frame implements ActionListener, WindowListener {
    Button[][] buttons;
    final int rows = 3;
    final int cols = 3;
    final int buttonSize = 100; // Increased button size
    Button emptyButton;

    AlphabetGame() {
        super("Puzzle - SimpliGame"); // Increase title size

        // Create a list of button labels
        ArrayList<String> labels = new ArrayList<>();
        labels.add("A");
        labels.add("B");
        labels.add("C");
        labels.add("D");
        labels.add("E");
        labels.add("F");
        labels.add("G");
        labels.add("H");
        labels.add("");

        // Shuffle the list to randomize button labels
        Collections.shuffle(labels);

        // Initialize buttons with shuffled labels
        buttons = new Button[rows][cols];
        int labelIndex = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                buttons[r][c] = new Button(labels.get(labelIndex));
                buttons[r][c].setBounds(150 + c * buttonSize, 150 + r * buttonSize, buttonSize, buttonSize); // Centering buttons with padding
                buttons[r][c].setFont(new Font("Arial", Font.BOLD, 36)); // Setting font size for button labels
                buttons[r][c].addActionListener(this);
                add(buttons[r][c]);
                if (labels.get(labelIndex).equals("")) {
                    emptyButton = buttons[r][c];
                }
                labelIndex++;
            }
        }

        // Frame settings
        int frameWidth = cols * buttonSize + 300; // Adjusted frame width
        int frameHeight = rows * buttonSize + 300; // Adjusted frame height
        setSize(frameWidth, frameHeight); // Set frame size
        setLayout(null);

        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        // Add window listener to handle window events
        addWindowListener(this);

        setVisible(true);

        // Bring the window to the front and request focus
        toFront();
        requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button) e.getSource();

        // Check if the clicked button is adjacent to the empty button
        if (isAdjacent(clickedButton, emptyButton)) {
            // Swap labels between clickedButton and emptyButton
            String clickedLabel = clickedButton.getLabel();
            clickedButton.setLabel(emptyButton.getLabel());
            emptyButton.setLabel(clickedLabel);

            // Update emptyButton reference
            emptyButton = clickedButton;
        }

        // Check if the player has won
        if (isGameWon()) {
            JOptionPane.showMessageDialog(this, "Congratulations! You won.");
        }
    }

    private boolean isAdjacent(Button btn1, Button btn2) {
        // Get positions of btn1 and btn2
        Point pos1 = findButtonPosition(btn1);
        Point pos2 = findButtonPosition(btn2);

        // Check if btn1 is adjacent to btn2
        return Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y) == 1;
    }

    private Point findButtonPosition(Button btn) {
        // Find the position of btn in the buttons array
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (buttons[r][c] == btn) {
                    return new Point(r, c);
                }
            }
        }
        return null;
    }

    private boolean isGameWon() {
        // Check if all buttons are in correct order
        int label = 1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (r == rows - 1 && c == cols - 1) {
                    // Last button should be empty
                    if (!buttons[r][c].getLabel().equals("")) {
                        return false;
                    }
                } else {
                    // Check labels are in order
                    if (!buttons[r][c].getLabel().equals(String.valueOf((char)('A' + label - 1)))) {
                        return false;
                    }
                }
                label++;
            }
        }
        return true;
    }

    // WindowListener methods
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {
        dispose(); // Dispose the frame when window is closing
        System.exit(0); // Exit the application
    }
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    public static void main(String[] args) {
        new AlphabetGame();
    }
}
