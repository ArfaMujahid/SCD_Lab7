package GUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import BusinessLayer.*;

public class DocumentPage {
    private JFrame mainframe;
    private JTextPane textPane;
    private BusinessLayer.Document document;
    private AttributeSet lastStyle = null;
    private boolean saved = false;

    private DocumentElement docElement = null;

    public void setDocument(BusinessLayer.Document doc) {
        this.document = doc;
    }

    public DocumentPage() {
        mainframe = new JFrame();
        textPane = new JTextPane();
        lastStyle = textPane.getStyledDocument().getCharacterElement(0).getAttributes();

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(toolBar.getPreferredSize().width, 50));
        addFormattingButtons(toolBar);
        addSaveButton(toolBar);

        toolBar.setBackground(new Color(0, 102, 102));
        mainframe.add(toolBar, BorderLayout.NORTH);
        mainframe.add(new JScrollPane(textPane), BorderLayout.CENTER);

        mainframe.setTitle("Document Editor");
        mainframe.setSize(1000, 600);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setLocationRelativeTo(null);
        mainframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(mainframe, "Do you want to save changes before closing?",
                        "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    saveDocument();
                    mainframe.dispose();
                } else if (option == JOptionPane.NO_OPTION) {
                    mainframe.dispose();
                }
            }
        });
        mainframe.setVisible(true);
    }

    private void addFormattingButtons(JToolBar toolBar) {
        JButton setBackgroundColorButton = new JButton("Set Background Color");
        setBackgroundColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(mainframe, "Choose Background Color",
                        textPane.getBackground());
                if (selectedColor != null) {
                    textPane.setBackground(selectedColor);
                    if (document != null) {
                        document.setColor(selectedColor);
                    }
                }
            }
        });
        toolBar.addSeparator(new Dimension(5, 0));
        toolBar.add(setBackgroundColorButton);

        String[] fontSizes = { "12", "14", "16", "18", "20" };
        JComboBox<String> fontSizeComboBox = new JComboBox<>(fontSizes);
        fontSizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFontSize(Integer.parseInt((String) fontSizeComboBox.getSelectedItem()));
            }
        });
        fontSizeComboBox.setMaximumSize(new Dimension(60, 30));
        toolBar.add(new JLabel("Font Size: "));
        toolBar.add(fontSizeComboBox);
        toolBar.addSeparator(new Dimension(5, 0));

        // Font ComboBox
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontComboBox = new JComboBox<>(fontNames);
        fontComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFontName((String) fontComboBox.getSelectedItem());
            }
        });
        fontComboBox.setMaximumSize(new Dimension(150, 30));
        toolBar.add(new JLabel("Font: "));
        toolBar.add(fontComboBox);
        toolBar.addSeparator(new Dimension(5, 0));

        // Bold Button
        JButton boldButton = new JButton("Bold");
        boldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBold();
            }
        });
        boldButton.setMaximumSize(new Dimension(80, 30));
        toolBar.add(boldButton);
        toolBar.addSeparator(new Dimension(5, 0));

        // Italic Button
        JButton italicButton = new JButton("Italic");
        italicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setItalic();
            }
        });
        italicButton.setMaximumSize(new Dimension(80, 30));
        toolBar.add(italicButton);
        toolBar.addSeparator(new Dimension(5, 0));

        // Color Button
        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseColor();
            }
        });
        colorButton.setMaximumSize(new Dimension(80, 30));
        toolBar.add(colorButton);
        toolBar.addSeparator(new Dimension(5, 0));

        String[] headingTypes = { "Heading 1", "Heading 2", "Heading 3" };
        for (String headingType : headingTypes) {
            JButton headingButton = new JButton(headingType);
            headingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addHeading(headingType);
                }
            });
            headingButton.setMaximumSize(new Dimension(120, 30));
            toolBar.add(headingButton);
        }
        toolBar.addSeparator(new Dimension(5, 0));

        JButton bulletPointsButton = new JButton("Bullet Points");
        bulletPointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBulletPoints();
            }
        });
        bulletPointsButton.setMaximumSize(new Dimension(120, 30));
        toolBar.add(bulletPointsButton);
        toolBar.addSeparator(new Dimension(5, 0));

        JButton imageButton = new JButton("Insert Image");
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertImage();
            }
        });
        imageButton.setMaximumSize(new Dimension(120, 30));
        toolBar.add(imageButton);

        JButton tableButton = new JButton("Insert Table");
        tableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTable();
            }
        });
        tableButton.setMaximumSize(new Dimension(120, 30));
        toolBar.add(tableButton);
        toolBar.addSeparator(new Dimension(5, 0));

        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char typedChar = e.getKeyChar();
                int caretPosition = textPane.getCaretPosition();

                javax.swing.text.Document styledDocument = textPane.getStyledDocument();
                Element paragraphElement = ((StyledDocument) styledDocument).getParagraphElement(caretPosition);
                AttributeSet currentStyle = paragraphElement.getAttributes();

                if (docElement == null || !styleEquals(lastStyle, currentStyle)) {
                    docElement = new TextElement();
                    document.insertElement(docElement);
                    lastStyle = currentStyle;
                    docElement.setStyle(currentStyle);
                }
                docElement.setContent(docElement.getContent() + typedChar);
            }
        });
    }

    private boolean styleEquals(AttributeSet style1, AttributeSet style2) {
        return style1.equals(style2);
    }

    private void addSaveButton(JToolBar toolBar) {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDocument();
            }
        });
        toolBar.addSeparator(new Dimension(5, 0));
        toolBar.add(saveButton);
    }

    private void saveDocument() {
        // Implement the logic to save the document elements to the database
        // You'll need to extract content from the textPane and save it in the
        // appropriate document elements.
        // Update the database with the new content.
        // ...
        for (DocumentElement element : documentElements) {
            // Save the element to the database, e.g., element.saveToDatabase();
            element.displayElement(); // For testing, display the element (replace with actual save logic)
        }
        JOptionPane.showMessageDialog(mainframe, "Document saved successfully.", "Save",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void setFontSize(int size) {
        MutableAttributeSet attrs = textPane.getInputAttributes();
        StyleConstants.setFontSize(attrs, size);
        textPane.setCharacterAttributes(attrs, false);
        if (docElement != null) {
            docElement.setStyle(attrs);
        }
    }

    private void setFontName(String fontName) {
        MutableAttributeSet attrs = textPane.getInputAttributes();
        StyleConstants.setFontFamily(attrs, fontName);
        textPane.setCharacterAttributes(attrs, false);
        if (docElement != null) {
            docElement.setStyle(attrs);
        }
    }

    private void setBold() {
        StyledDocument doc = textPane.getStyledDocument();
        int selectionStart = textPane.getSelectionStart();
        int selectionEnd = textPane.getSelectionEnd();

        Style style = textPane.addStyle("Bold", null);
        StyleConstants.setBold(style, true);

        doc.setCharacterAttributes(selectionStart, selectionEnd - selectionStart, style, false);

        if (docElement != null) {
            int charElementIndex = ((Object) doc.getCharacterElement(selectionStart)).getIndex();
            AttributeSet attributes = doc.getCharacterElement(charElementIndex).getAttributes();
            docElement.setStyle(attributes);
        }
    }

    private void setItalic() {
        StyledDocument doc = textPane.getStyledDocument();
        int selectionStart = textPane.getSelectionStart();
        int selectionEnd = textPane.getSelectionEnd();

        Style style = textPane.addStyle("Italic", null);
        StyleConstants.setItalic(style, true);

        doc.setCharacterAttributes(selectionStart, selectionEnd - selectionStart, style, false);
        if (docElement != null) {
            docElement.setStyle(((StyledDocument) textPane).getCharacterElement(selectionStart).getAttributes());
        }
    }

    private void chooseColor() {
        Color selectedColor = JColorChooser.showDialog(mainframe, "Choose Text Color", Color.BLACK);
        if (selectedColor != null) {
            MutableAttributeSet attrs = textPane.getInputAttributes();
            StyleConstants.setForeground(attrs, selectedColor);
            textPane.setCharacterAttributes(attrs, false);

            if (docElement != null) {
                docElement.setStyle(attrs);
            }

        }
    }

    private void addHeading(String headingType) {
        StyledDocument doc = textPane.getStyledDocument();
        int selectionStart = textPane.getSelectionStart();
        int selectionEnd = textPane.getSelectionEnd();

        // Create a style based on the heading type
        Style style = textPane.addStyle(headingType, null);
        StyleConstants.setFontSize(style, 18); // Adjust the size based on your preference
        StyleConstants.setBold(style, true);

        // Apply the style to the selected text
        doc.setCharacterAttributes(selectionStart, selectionEnd - selectionStart, style, false);
    }

    private void addBulletPoints() {
        StyledDocument doc = textPane.getStyledDocument();
        int selectionStart = textPane.getSelectionStart();

        // Insert a bullet point at the beginning of the line
        try {
            doc.insertString(selectionStart, "\u2022 ", null); // Unicode for bullet point
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void insertImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(mainframe);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file.isFile()) {
                // Create a dialog to get the desired size from the user
                String sizeInput = JOptionPane.showInputDialog(this, "Enter image size (e.g., 100x100):");
                if (sizeInput != null && !sizeInput.isEmpty()) {
                    try {
                        String[] sizeArray = sizeInput.split("x");
                        int width = Integer.parseInt(sizeArray[0]);
                        int height = Integer.parseInt(sizeArray[1]);

                        ImageIcon imageIcon = new ImageIcon(file.getPath());
                        Image img = imageIcon.getImage();
                        img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                        // Create an ImageIcon and insert it into the document
                        ImageIcon scaledIcon = new ImageIcon(img);
                        textPane.insertIcon(scaledIcon);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(mainframe,
                                "Invalid size format. Please use the format 'widthxheight'.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void addTable() {
        // Implement table insertion logic here
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DocumentPage::new);
    }
}
