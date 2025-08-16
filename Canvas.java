import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Canvas implements MouseMotionListener {
    private final JFrame frame = new JFrame();
    private final JPanel canvasPanel = new JPanel();
    private final JTextField col = new JTextField();
    private final JTextField sz = new JTextField();
    private String prev = "#000000";
    private String back = "#FFFFFF";


    Canvas() {
        canvasPanel.setLayout(null);
        canvasPanel.setBackground(Color.WHITE);
        canvasPanel.setBounds(320, 30, 850, 600);
        canvasPanel.addMouseMotionListener(this);
    }

    public void canvasView() {
        frame.setSize(1250, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#001122"));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.add(canvasPanel);

        JPanel toolBar = new JPanel();
        toolBar.setBounds(30, 30, 250, 600);
        toolBar.setBackground(Color.WHITE);
        toolBar.setLayout(null);
        frame.add(toolBar);

        JButton clear = new JButton("CLEAR");
        clear.setBounds(30, 515, 190, 30);
        toolBar.add(clear);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasPanel.removeAll();
                canvasPanel.repaint();
            }
        });

        JLabel cllabel = new JLabel("Custom Colors : ");
        cllabel.setBounds(30, 260, 100, 50);
        toolBar.add(cllabel);

        col.setBounds(30, 310, 190, 30);
        col.setText("#000000");
        toolBar.add(col);

        JButton bc = new JButton("SET BACKGROUND");
        bc.setBounds(30, 350, 190, 30);
        toolBar.add(bc);
        bc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back = col.getText();
                canvasPanel.setBackground(Color.decode(back));
            }
        });

        JLabel szlabel = new JLabel("Size : ");
        szlabel.setBounds(30, 380, 100, 50);
        toolBar.add(szlabel);

        JButton sub = new JButton("-");
        sub.setBounds(30, 415, 50, 30);
        toolBar.add(sub);

        sz.setBounds(100, 415, 50, 30);
        sz.setText("5");
        toolBar.add(sz);

        JButton add = new JButton("+");
        add.setBounds(170, 415, 50, 30);
        toolBar.add(add);

        JButton save = new JButton("SAVE");
        save.setBounds(30, 555, 190, 30);
        toolBar.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tm = java.time.LocalTime.now() + "";
                tm = tm.substring(0, 2) + tm.substring(3, 5) + tm.substring(6, 8) + tm.substring(9, 12) + ".png";
                try {
                    BufferedImage image = getImg(canvasPanel);
                    ImageIO.write(image, "png", new File(tm));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sz.setText(Integer.parseInt(sz.getText()) - 1 + "");
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sz.setText(Integer.parseInt(sz.getText()) + 1 + "");
            }
        });
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        String cl = col.getText();
        int rad = Integer.parseInt(sz.getText());
        Graphics grap = canvasPanel.getGraphics();
        grap.setColor(Color.decode(cl));
        grap.fillRect(e.getX(), e.getY(), rad, rad);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private BufferedImage getImg(Component comp) throws AWTException {
        Point p = comp.getLocationOnScreen();
        Dimension dim = comp.getSize();
        BufferedImage capture = new Robot().createScreenCapture(new Rectangle(p, dim));
        return capture;
    }
    public JFrame getFrame() {
        return frame;
    }
    public JPanel getCanvasPanel() {
        return canvasPanel;
    }



}

class ToolBar implements MouseMotionListener {
    private final JPanel toolBar;
    private final JTextField col;
    private final JTextField sz;
    private final Canvas canvas;
    private String prev = "#000000";
    private String back = "#FFFFFF";

    public ToolBar(Canvas canvas) {
        this.canvas = canvas;
        toolBar = new JPanel();
        toolBar.setBounds(30, 30, 250, 600);
        toolBar.setBackground(Color.WHITE);
        toolBar.setLayout(null);
        col = new JTextField();
        col.setBounds(30, 310, 190, 30);
        col.setText("#000000");
        sz = new JTextField();
        sz.setBounds(100, 415, 50, 30);
        sz.setText("5");
    }

    public void createToolBar() {
        canvas.getFrame().add(toolBar);
        addButton(32, 30, "#000000");
        addButton(32, 30, "#000000");   // Black
        addButton(105, 30, "#FFFFFF");  // White
        addButton(177, 30, "#808080");  // Gray
        addButton(32, 90, "#FF0000");   // Red
        addButton(105, 90, "#00FF00");  // Lime
        addButton(177, 90, "#0000FF");  // Blue
        addButton(32, 150, "#FFFF00");  // Yellow
        addButton(105, 150, "#FFA500"); // Orange
        addButton(177, 150, "#A020F0"); // Purple
        addButton(32, 210, "#FFC0CB");  // Pink
        addButton(105, 210, "#964B00"); // Brown
        addButton(177, 210, "#C32148"); // Crimson



        toolBar.add(col);
        JLabel cllabel = new JLabel("Custom Colors : ");
    cllabel.setBounds(30, 260, 100, 50);
    toolBar.add(cllabel);

    JButton bc = new JButton("SET BACKGROUND");
    bc.setBounds(30, 350, 190, 30);
    toolBar.add(bc);
    bc.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            back = col.getText();
            canvas.getCanvasPanel().setBackground(Color.decode(back));
        }
    });
    }

    public void draw(int x, int y) {
        String cl = col.getText();
        int rad = Integer.parseInt(sz.getText());
        Graphics grap = canvas.getCanvasPanel().getGraphics();
        grap.setColor(Color.decode(cl));
        grap.fillRect(x, y, rad, rad);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        draw(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private void addButton(int x, int y, String clr) {
        JButton btn = new JButton();
        btn.setBounds(x, y, 40, 40);
        btn.setBackground(Color.decode(clr));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                col.setText(clr);
            }
        });
        toolBar.add(btn);
    }
}


