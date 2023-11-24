import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Q5 extends JFrame {
    private JLabel imageLabel;
    private JButton previousButton, nextButton;
    private File[] imageFiles;
    private int currentImageIndex;

    public Q5() {
        setTitle("Image Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        currentImageIndex = 0;
        imageFiles = loadImagesFromDirectory("images");

        displayImage(currentImageIndex);

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImageIndex > 0) {
                    currentImageIndex--;
                    displayImage(currentImageIndex);
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImageIndex < imageFiles.length - 1) {
                    currentImageIndex++;
                    displayImage(currentImageIndex);
                }
            }
        });

        setVisible(true);
    }

    private void displayImage(int index) {
        if (index >= 0 && index < imageFiles.length) {
            try {
                BufferedImage img = ImageIO.read(imageFiles[index]);
                imageLabel.setIcon(new ImageIcon(img));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File[] loadImagesFromDirectory(String directoryPath) {
        File folder = new File(directoryPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg")
                || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".png"));

        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(this, "No images found in the specified directory.");
            System.exit(0);
        }

        return files;
    }

    public static void main(String[] args) {
        new Q5();
    }
}
