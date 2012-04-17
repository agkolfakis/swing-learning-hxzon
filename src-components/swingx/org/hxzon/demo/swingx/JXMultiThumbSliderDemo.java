package org.hxzon.demo.swingx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.hxzon.swing.layout.simple.SimpleLayout;
import org.hxzon.swing.layout.simple.SimpleLayoutData;
import org.jdesktop.swingx.JXMultiThumbSlider;
import org.jdesktop.swingx.multislider.MultiThumbModel;
import org.jdesktop.swingx.multislider.ThumbRenderer;
import org.jdesktop.swingx.multislider.TrackRenderer;

public class JXMultiThumbSliderDemo extends JFrame {
    private static final long serialVersionUID = 1L;

    public JXMultiThumbSliderDemo() {
        super("JXMultiThumbSlider Demo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new SimpleLayout());
        this.add(demo(), SimpleLayoutData.fillPercent(100));
        this.setLocation(300, 300);
        this.pack();
    }

    public JPanel demo() {
        JPanel panel = new JPanel(new SimpleLayout());
        JXMultiThumbSlider slider = new JXMultiThumbSlider();
        slider.setThumbRenderer(new MyThumbRenderer());
        slider.setTrackRenderer(new MyTrackRenderer());
        slider.setMinimumSize(new Dimension(300, 100));
        MultiThumbModel model = slider.getModel();
        model.setMaximumValue(100);
        model.setMinimumValue(0);
        model.addThumb(10, "1");
        model.addThumb(20, "2");
        model.addThumb(30, "3");
        model.addThumb(40, "4");
        panel.add(new JScrollPane(slider), SimpleLayoutData.fillPercent(100));
        return panel;

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JXMultiThumbSliderDemo().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static class MyThumbRenderer extends JComponent implements ThumbRenderer {
        public MyThumbRenderer() {
            setPreferredSize(new Dimension(14, 14));
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.green);
            Polygon poly = new Polygon();
            JComponent thumb = this;
            poly.addPoint(thumb.getWidth() / 2, 0);
            poly.addPoint(0, thumb.getHeight() / 2);
            poly.addPoint(thumb.getWidth() / 2, thumb.getHeight());
            poly.addPoint(thumb.getWidth(), thumb.getHeight() / 2);
            g.fillPolygon(poly);
        }

        public JComponent getThumbRendererComponent(JXMultiThumbSlider slider, int index, boolean selected) {
            return this;
        }

    }

    private class MyTrackRenderer extends JComponent implements TrackRenderer {
        private JXMultiThumbSlider<?> slider;

        @Override
        public void paintComponent(Graphics g) {
            g.setColor(slider.getBackground());
            g.fillRect(0, 0, slider.getWidth(), slider.getHeight());
            g.setColor(Color.black);
            g.drawLine(0, slider.getHeight() / 2, slider.getWidth(), slider.getHeight() / 2);
            g.drawLine(0, slider.getHeight() / 2 + 1, slider.getWidth(), slider.getHeight() / 2 + 1);
        }

        public JComponent getRendererComponent(JXMultiThumbSlider slider) {
            this.slider = slider;
            return this;
        }
    }

    public static Person[] personData() {
        return new Person[] { new Person("1", "hello@163.com"), new Person("2", "hi@126.com"), new Person("3", "hxzon@163.org"), new Person("4", "hd@126.com"), new Person("5", "xzon@qq.org"),
                new Person("6", "xuezh@mail.com"), new Person("7", "baidu@mail.com"), new Person("8", "hi@mail.org"), new Person("9", "hello@mail.org"), };
    }

    public static class Person {
        private String id;
        private String email;

        public Person(String id, String email) {
            this.id = id;
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

}
