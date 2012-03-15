package org.hxzon.swing.components.ext;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSliderUI;

import com.sun.java.swing.plaf.windows.WindowsSliderUI;

public class WindowsHMultiSplitSliderUI extends WindowsSliderUI {
    private HMultiSplitSlider _slider;
    private Point origThumbLocation = new Point();

    public WindowsHMultiSplitSliderUI(HMultiSplitSlider slider) {
        super(slider);
        _slider = slider;
    }

    public static ComponentUI createUI(JComponent slider) {
        return new WindowsHMultiSplitSliderUI((HMultiSplitSlider) slider);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        myPaint(g);
    }

    private void myPaint(Graphics g) {
        Rectangle clip = g.getClipBounds();
        backupThumb();

        int[] values = _slider.getValues();
        for (int i = values.length - 1; i >= 0; i--) {
            this.adjustThumbForValue(values[i]);
            if (clip.intersects(thumbRect)) {
                paintThumb(g);
            }
        }

        restoreThumb();
    }

    protected void restoreThumb() {
        thumbRect.x = origThumbLocation.x;
        thumbRect.y = origThumbLocation.y;
    }

    protected void backupThumb() {
        origThumbLocation.x = thumbRect.x;
        origThumbLocation.y = thumbRect.y;
    }

    protected void adjustThumbForValue(int value) {
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            int valuePosition = xPositionForValue(value);
            thumbRect.x = valuePosition - (thumbRect.width / 2);
        } else {
            int valuePosition = yPositionForValue(value);
            thumbRect.y = valuePosition - (thumbRect.height / 2);
        }
    }

    protected void calculateThumbLocation() {
        if (_slider.getOrientation() == JSlider.HORIZONTAL) {
            thumbRect.x = -1000;//valuePosition - (thumbRect.width / 2);
            thumbRect.y = trackRect.y;
        } else {
            thumbRect.x = trackRect.x;
            thumbRect.y = -1000;//valuePosition - (thumbRect.height / 2);
        }
    }

    @Override
    protected BasicSliderUI.TrackListener createTrackListener(JSlider slider) {
        return new RangeTrackListener(super.createTrackListener(slider));
    }

    protected class RangeTrackListener extends BasicSliderUI.TrackListener {
        int _thumbIndex;
        int _mouseStartLocation;
        BasicSliderUI.TrackListener _listener;

        public RangeTrackListener(BasicSliderUI.TrackListener listener) {
            _listener = listener;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (!_slider.isEnabled()) {
                return;
            }

            if (_slider.isRequestFocusEnabled()) {
                _slider.requestFocus();
            }

            _thumbIndex = getThumbIndex(e.getX(), e.getY());
            myRepaint();

            int[] _values = _slider.getValues();
            if (_thumbIndex >= 0 && _thumbIndex < _values.length) {

                _mouseStartLocation = (_slider.getOrientation() == JSlider.VERTICAL) ? e.getY() : e.getX();

                _slider.getModel().setValueIsAdjusting(true);
            }
            _listener.mousePressed(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!_slider.isEnabled()) {
                return;
            }

            int newLocation = (_slider.getOrientation() == JSlider.VERTICAL) ? e.getY() : e.getX();

            int newValue = (_slider.getOrientation() == JSlider.VERTICAL) ? valueForYPosition(newLocation) : valueForXPosition(newLocation);

            if (newValue < _slider.getModel().getMinimum()) {
                newValue = _slider.getModel().getMinimum();
            }

            if (newValue > _slider.getModel().getMaximum()) {
                newValue = _slider.getModel().getMaximum();
            }

            int[] _values = _slider.getValues();
            if (_thumbIndex >= 0 && _thumbIndex <= _values.length - 1) {
                if (_thumbIndex != 0) {
                    newValue = Math.max(newValue, _values[_thumbIndex - 1]);
                }
                if (_thumbIndex != _values.length - 1) {
                    newValue = Math.min(newValue, _values[_thumbIndex + 1]);
                }
                _values[_thumbIndex] = newValue;
            }
            _slider.setValues(_values);
            _slider.repaint();
//            myRepaint();//first and last thumb paint wrong
            _listener.mouseDragged(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            slider.getModel().setValueIsAdjusting(false);
            myRepaint();
            _listener.mouseReleased(e);
        }

        private void setCursor(int c) {
            Cursor cursor = Cursor.getPredefinedCursor(c);

            if (slider.getCursor() != cursor) {
                slider.setCursor(cursor);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            _listener.mouseMoved(e);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            slider.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            slider.repaint();
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }

    protected int getThumbIndex(int x, int y) {
        slider.putClientProperty(HMultiSplitSlider.CLIENT_PROPERTY_MOUSE_POSITION, null);

        backupThumb();
        int[] values = _slider.getValues();
        for (int i = values.length - 1; i >= 0; i--) {
            adjustThumbForValue(values[i]);
            if (thumbRect.contains(x, y)) {
                if (i == values.length - 1) {
                    int max = _slider.getModel().getMaximum();
                    if (values[i] >= max) {
                        int j = i - 1;
                        while (j >= 0 && values[j] == values[i]) {
                            j--;
                        }
                        return j + 1;
                    }
                }
                return i;
            }

        }
        restoreThumb();
        return -1;
    }

    public void paintThumb(Graphics g) {
        try {
            boolean b = (thumbRect.x == origThumbLocation.x) && (thumbRect.y == origThumbLocation.y);
            Field field;
            field = getClass().getSuperclass().getDeclaredField("rollover");
            field.setAccessible(true);
            field.set(this, b);

            field = getClass().getSuperclass().getDeclaredField("pressed");
            field.setAccessible(true);
            field.set(this, b);
        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        }

        super.paintThumb(g);
    }

    private void myRepaint() {
        backupThumb();
        int[] values = _slider.getValues();
        for (int i = values.length - 1; i >= 0; i--) {
            adjustThumbForValue(values[i]);
            slider.repaint(thumbRect);
        }
        restoreThumb();
    }

    @Override
    public void scrollByBlock(int direction) {

    }

    @Override
    public void scrollByUnit(int direction) {

    }
}
