import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphPanel extends JComponent{

    private static class Line{
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    private final LinkedList<Line> lines = new LinkedList<Line>();

    public void addLine(int x1, int x2, int x3, int x4) {
        addLine(x1, x2, x3, x4, Color.black);
    }

    public void addLine(int x1, int x2, int x3, int x4, Color color) {
        lines.add(new Line(x1,x2,x3,x4, color));
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Line line : lines) {
            g.setColor(line.color);
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
    }

    static int x1, x2, y1, y2;

    public void displayGraph(ArrayList<Point> points) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GraphPanel comp = new GraphPanel();
        comp.setPreferredSize(new Dimension(640, 400));
        comp.setMaximumSize(comp.getPreferredSize());
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);

        for(int i=1; i < points.size(); i++){
            x1 = (Integer) points.get(i-1).getxValue();
            x2 = (Integer) points.get(i).getxValue();
            y1 = (Integer) points.get(i-1).getyValue();
            y2 = (Integer) points.get(i).getyValue();
            Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
            comp.addLine(x1, y1, x2, y2, randomColor);
        }

        testFrame.pack();
        testFrame.setVisible(true);
    }

}