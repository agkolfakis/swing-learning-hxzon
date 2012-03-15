package org.hxzon.swing.components.ext;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.hxzon.swing.layout.simple.SimpleLayout;
import org.hxzon.swing.layout.simple.SimpleLayoutData;

public class HMultiSplitSlider extends JSlider {

    private static final long serialVersionUID = 1L;

    private static final String uiClassID = "HMultiSplitSliderUI";

    private boolean _rangeDraggable = true;
    public static final String CLIENT_PROPERTY_MOUSE_POSITION = "mousePosition";
    public static final String PROPERTY_VALUES = "values";
    private int[] _values;

    public HMultiSplitSlider() {
        this(HORIZONTAL, 0, 100, new int[] { 50 });
    }

    public HMultiSplitSlider(int orientation) {
        this(orientation, 0, 100, new int[] { 50 });
    }

    public HMultiSplitSlider(int min, int max) {
        this(HORIZONTAL, min, max, new int[] { (min + max) / 2 });
    }

    public HMultiSplitSlider(int min, int max, int[] values) {
        this(HORIZONTAL, min, max, values);
    }

    public HMultiSplitSlider(int orientation, int min, int max, int[] values) {
        super(orientation, min, max, values[0]);
        _values = values;
    }

    public String getUIClassID() {
        return uiClassID;
    }

    public int[] getValues() {
        return _values;
    }

    @Override
    public void setValue(int value) {
        _values[0] = value;
    }

    public int getValue() {
        if (_values == null) {
            return 0;
        }
        return _values[0];
    }

    public void setValues(int[] values) {
        _values = values;
        firePropertyChange(HMultiSplitSlider.PROPERTY_VALUES, null, _values);
    }

    public boolean isRangeDraggable() {
        return _rangeDraggable;
    }

    public void setRangeDraggable(boolean rangeDraggable) {
        _rangeDraggable = rangeDraggable;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("HMultiSplitSliderUI", WindowsHMultiSplitSliderUI.class.getName());
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
                    createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void createAndShowGUI() {
        final JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new SimpleLayout());
        final HMultiSplitSlider slider = new HMultiSplitSlider(0, 100, new int[] { 10, 20, 60, 90 });
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(5);
        slider.setPaintLabels(true);
        final JLabel valueLabel = new JLabel();
        final JLabel splitValueLabel = new JLabel();
        slider.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                int[] values = slider.getValues();
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                sb1.append(0).append(":").append(values[0]).append(";");
                sb2.append(0).append(":").append(values[0]).append(";");
                int i = 1;
                for (; i < values.length; i++) {
                    sb1.append(i).append(":").append(values[i]).append(";");
                    sb2.append(i).append(":").append(values[i] - values[i - 1]).append(";");
                }
                sb2.append(i).append(":").append(slider.getMaximum() - values[i - 1]).append(";");
                valueLabel.setText(sb1.toString());
                splitValueLabel.setText(sb2.toString());
            }

        });
        f.add(slider);
        f.add(valueLabel, SimpleLayoutData.fixedSize(25));
        f.add(splitValueLabel, SimpleLayoutData.fixedSize(25));
        f.pack();
        f.setVisible(true);
    }
}
