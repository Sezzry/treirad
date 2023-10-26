import javax.swing.SwingUtilities;

public class treiradmain { 
// Spelet startas i main med set visible på true, om false dyker inte popup
    // skärmen upp för spelet
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new treirad().setVisible(true);
            }
        });
    }
}