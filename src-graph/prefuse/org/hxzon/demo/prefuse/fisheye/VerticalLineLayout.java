package org.hxzon.demo.prefuse.fisheye;

import java.awt.Insets;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import prefuse.Display;
import prefuse.action.layout.Layout;
import prefuse.visual.VisualItem;

/**
 * Lines up all VisualItems vertically. Also scales the size such that
 * all items fit within the maximum layout size, and updates the
 * Display to the final computed size.
 */
public class VerticalLineLayout extends Layout {
    private double m_maxHeight = 600;
    private double m_scale = 7;

    public VerticalLineLayout(double maxHeight, double scale) {
        m_maxHeight = maxHeight;
        m_scale = scale;
    }

    public void run(double frac) {
        // first pass
        double w = 0, h = 0;
        Iterator iter = m_vis.items();
        while (iter.hasNext()) {
            VisualItem item = (VisualItem) iter.next();
            item.setSize(1.0);
            h += item.getBounds().getHeight();
        }
        double scale = h > m_maxHeight ? m_maxHeight / h : 1.0;

        Display d = m_vis.getDisplay(0);
        Insets ins = d.getInsets();

        // second pass
        h = ins.top;
        double ih, y = 0, x = ins.left;
        iter = m_vis.items();
        while (iter.hasNext()) {
            VisualItem item = (VisualItem) iter.next();
            item.setSize(scale);
            item.setEndSize(scale);
            Rectangle2D b = item.getBounds();

            w = Math.max(w, b.getWidth());
            ih = b.getHeight();
            y = h + (ih / 2);
            setX(item, null, x);
            setY(item, null, y);
            h += ih;
        }

        // set the display size to fit text
        setSize(d, (int) Math.round(2 * m_scale * w + ins.left + ins.right), (int) Math.round(h + ins.bottom));
    }

    private void setSize(final Display d, final int width, final int height) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                d.setSize(width, height);
            }
        });
    }
} // end of inner class VerticalLineLayout
