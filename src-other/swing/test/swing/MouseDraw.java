package test.swing;

//MouseDraw.java，鼠标拖动图像-Java源代码
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MouseDraw {
    public static void main(String[] args) {
        MouseFrame frame = new MouseDraw.MouseFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static class MouseFrame extends JFrame {
        public MouseFrame() {
            setTitle("利用鼠标画图");
            setSize(WIDTH, HEIGHT);
            // 将panel加入到frame
            MousePanel panel = new MousePanel();
            Container contentPane = getContentPane();
            contentPane.add(panel);
        }

        public static final int WIDTH = 300;
        public static final int HEIGHT = 200;
    }

    private static class MousePanel extends JPanel {
        public MousePanel() {
            faces = new ArrayList(); //初始化链表
            facetypes = new ArrayList();
            current = null; //初始化当前图像位置
            String imageName = "smile.gif"; //加载图像
            smileimg = Toolkit.getDefaultToolkit().getImage(imageName);
            imageName = "cry.gif";
            cryimg = Toolkit.getDefaultToolkit().getImage(imageName);
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(smileimg, 0);
            tracker.addImage(cryimg, 1);
            try {
                tracker.waitForID(0);
                tracker.waitForID(1);
            } catch (InterruptedException exception) {
            }
            //得到图像的高度和宽度
            imageWidth = smileimg.getWidth(this);
            imageHeight = smileimg.getWidth(this);
            //注册监听器
            addMouseListener(new MouseHandler());
            addMouseMotionListener(new MouseMotionHandler());
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //显示当前光标位置
            String text = "鼠标指针位置：" + mousex + "：" + mousey;
            g.drawString(text, 10, 10);
            //显示图像
            for (int i = 0; i < faces.size(); i++) { //得到图像位置
                int x = (int) ((Rectangle) faces.get(i)).getX();
                int y = (int) ((Rectangle) faces.get(i)).getY();
                //得到图像类型
                text = (String) (facetypes.get(i));
                if (text.equals("smile"))
                    g.drawImage(smileimg, x, y, null);
                else
                    g.drawImage(cryimg, x, y, null);
            }
        }

//查找某个点是否在链表faces中的某个矩形内部
        public Rectangle search(Point pt) {
            for (int i = 0; i < faces.size(); i++) {
                Rectangle rect = (Rectangle) faces.get(i);
                if (rect.contains(pt))
                    return rect;
            }
            return null;
        }

        //将以点pt为中心，以图像大小为高度和宽度的矩形加入链表faces中
        public void add(Point pt) {
            //取得点pt的坐标
            int x = (int) pt.getX();
            int y = (int) pt.getY();
            //生成矩形
            current = new Rectangle(x - imageWidth / 2, y - imageHeight / 2, imageWidth, imageHeight);
            //将生成的矩形加入链表faces中
            faces.add(current);
            //将图像类型加入链表facetypes中
            facetypes.add(facetype);
            repaint();
        }

        //修改图像的类型
        public void modify(Rectangle rect) {
            if (rect == null)
                return;
            int index = faces.indexOf(rect);
            String text = (String) (facetypes.get(index));
            if (text.equals("smile"))
                facetypes.set(index, "cry");
            else
                facetypes.set(index, "smile");
            repaint();
        }

        private ArrayList faces;
        private ArrayList facetypes;
        private Rectangle current;
        private Image smileimg;
        private Image cryimg;
        private int imageWidth;
        private int imageHeight;
        private String facetype;
        private int mousex, mousey;

        private class MouseHandler extends MouseAdapter {
            public void mousePressed(MouseEvent event) {
                //得到鼠标光标的当前位置
                mousex = event.getX();
                mousey = event.getY();
                //查找当前位置是否已有图像
                current = search(event.getPoint());
                if (current == null) {
                    //添加一个“笑脸”图像
                    facetype = "smile";
                    add(event.getPoint());
                } else
                    //对图像进行切换
                    modify(current);
            }
        }

        private class MouseMotionHandler implements MouseMotionListener {
            public void mouseMoved(MouseEvent event) {
                //得到鼠标光标的当前位置
                mousex = event.getX();
                mousey = event.getY();
                repaint();
            }

            public void mouseDragged(MouseEvent event) {
                //得到鼠标光标的当前位置
                mousex = event.getX();
                mousey = event.getY();
                // 拖动图像
                current.setFrame(mousex - imageWidth / 2, mousey - imageHeight / 2, imageWidth, imageHeight);
                repaint();
            }
        }
    }
}
