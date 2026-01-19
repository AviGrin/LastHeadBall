import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class StartPanel extends JPanel {
    private GameFrame frame;
    private BufferedImage backgroundImage;
    private AudioPlayer audioPlayer;

    public StartPanel(GameFrame frame) {
        setLayout(new GridBagLayout());
        this.frame=frame;

        setButtons();
        frame.pack();

        frame.add(this);
        frame.setTitle("Game Start Screen");
        try {
            this.backgroundImage = ImageIO.read(new File("images/TheBackround.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        frame.setSize(800, 600);
        this.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);

        this.audioPlayer = new AudioPlayer("Audio/אייל גולן מלך המגרש Eyal Golan (1).wav");
        this.audioPlayer.play();





        frame.setVisible(true);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (this.backgroundImage != null) {
            g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }


    }
    private void setButtons(){
        GridBagConstraints gbc = new GridBagConstraints();

        // הגדרות בסיס שחלות על כל הכפתורים
        gbc.gridx = 0; // תמיד בעמודה 0 (האמצעית)
        gbc.fill = GridBagConstraints.HORIZONTAL; // (אופציונלי) למתוח כפתורים לרוחב אחיד

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setSize(100, 50);
        startGameButton.setBackground(Color.BLACK);
        startGameButton.setForeground(Color.WHITE);

        gbc.gridy = 0; // שים אותו בשורה הראשונה
        gbc.insets = new Insets(0, 0, 10, 0); // (Top, Left, Bottom, Right) -> הוספנו 10 פיקסלים רווח למטה
        add(startGameButton, gbc); // הוספה לפאנל עם ההוראות




        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               audioPlayer.stop();
                GamePanel gamePanel = new GamePanel(frame);
                AudioPlayer audioPlayer = new AudioPlayer("Audio/Audio_Voicy_Whistle sound effect.wav");
                audioPlayer.play();
            };
        });

        JButton buttonInstructions = new JButton("Instructions");
        buttonInstructions.setSize(100+50, 50);
        gbc.gridy = 1; // שים אותו בשורה השנייה (מתחת לראשון)
        gbc.insets = new Insets(10, 0, 0, 0); // הוספנו 10 פיקסלים רווח מלמעלה
        add(buttonInstructions, gbc);        // יצירת חלון ההוראות
        JDialog instructionsDialog = new JDialog(frame, "Instructions", true);
        instructionsDialog.setSize(400, 400);
        buttonInstructions.setBackground(Color.BLACK);
        buttonInstructions.setForeground(Color.WHITE);
        instructionsDialog.setLayout(new BorderLayout());
        JLabel instructionsPic = new JLabel(new ImageIcon("images/istructions.png"));
        instructionsPic.setVisible(true);
        // יצירת התווית עם טקסט ההוראות

        instructionsDialog.add(instructionsPic, BorderLayout.CENTER);

        // יצירת כפתור לסגירת חלון ההוראות
        JButton closeInstructionsButton = new JButton("סגור");
        instructionsDialog.add(closeInstructionsButton, BorderLayout.SOUTH);

        // הוספת מאזין אירועים לכפתור הסגירה
        closeInstructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instructionsDialog.setVisible(false);
            }
        });

        // הוספת מאזין אירועים לכפתור הפתיחה
        buttonInstructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instructionsDialog.setLocationRelativeTo(frame); // מיקום החלון באמצע הפריים הראשי
                instructionsDialog.setVisible(true);
            }
        });


    }
}