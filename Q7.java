import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class Q7 extends JFrame {
    private List<ColorShape> shapes = new ArrayList<>();
    private ColorShape currentShape;
    private Color currentColor = Color.BLACK;
    private String currentTool = "Line";

    public Q7() {
        setTitle("Drawing Program");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);

        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new FlowLayout());

        JButton lineButton = new JButton("Line");
        JButton rectangleButton = new JButton("Rectangle");
        JButton ellipseButton = new JButton("Ellipse");

        lineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTool = "Line";
            }
        });

        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTool = "Rectangle";
            }
        });

        ellipseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTool = "Ellipse";
            }
        });

        toolsPanel.add(lineButton);
        toolsPanel.add(rectangleButton);
        toolsPanel.add(ellipseButton);

        JButton colorButton = new JButton("Choose Color");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = JColorChooser.showDialog(null, "Choose Color", currentColor);
            }
        });

        toolsPanel.add(colorButton);

        add(toolsPanel, BorderLayout.NORTH);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentTool != null) {
                    currentShape = createShape(currentTool, e.getX(), e.getY());
                    currentShape.setColor(currentColor);
                    shapes.add(currentShape);
                    repaint();
                }
            }
        });

        setVisible(true);
    }

    private class Canvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            for (ColorShape shape : shapes) {
                g2.setColor(shape.getColor());
                g2.draw(shape.getShape());
            }
        }
    }

    private ColorShape createShape(String tool, int x, int y) {
        if ("Line".equals(tool)) {
            return new ColorShape(new Line2D.Double(x, y, x, y));
        } else if ("Rectangle".equals(tool)) {
            return new ColorShape(new Rectangle2D.Double(x, y, 0, 0));
        } else if ("Ellipse".equals(tool)) {
            return new ColorShape(new Ellipse2D.Double(x, y, 0, 0));
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        new Q7();
    }
}

class ColorShape {
    private Shape shape;
    private Color color;

    public ColorShape(Shape shape) {
        this.shape = shape;
        this.color = Color.BLACK;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
