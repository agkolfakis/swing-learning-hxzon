/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ------------------
 * BarChartDemo1.java
 * ------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   ;
 *
 * Changes
 * -------
 * 09-Mar-2005 : Version 1 (DG);
 *
 */

package org.hxzon.demo.jfreechart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;

import org.hxzon.swing.components.easy.HEasyJCheckBox;
import org.hxzon.swing.layout.simple.SimpleLayout;
import org.hxzon.swing.model.HEasyJModelValue;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

public class DatasetVisibleDemo3 extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
    private static final String series1Name = "series 1";
    private static final String series2Name = "series 2";
    private static final String series3Name = "series 3";
    private static final Paint plotBackgroundPaint = Color.lightGray;
    private static JFreeChart timeSeriesChart = createTimeSeriesChart();

    public DatasetVisibleDemo3(String title) {
        super(title);
        ChartPanel chartPanel = new ChartPanel(timeSeriesChart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        getContentPane().add(chartPanel);
        JPanel buttonPanel = new JPanel(new SimpleLayout(true));
        buttonPanel.add(new ChartCheckBox(series1Name, chartPanel));
        buttonPanel.add(new ChartCheckBox(series2Name, chartPanel));
        buttonPanel.add(new ChartCheckBox(series3Name, chartPanel));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private static XYDataset createDataset1() {
        boolean notify = false;

        TimeSeries s1 = new TimeSeries(series1Name);
        s1.add(new Month(1, 2001), 181.8, notify);
        s1.add(new Month(2, 2001), 167.3, notify);
        s1.add(new Month(4, 2001), 153.8, notify);
        s1.add(new Month(5, 2001), 167.6, notify);
        s1.add(new Month(6, 2001), 158.8, notify);
        s1.add(new Month(9, 2001), 148.3, notify);
        s1.add(new Month(10, 2001), 153.9, notify);
        s1.add(new Month(11, 2001), 142.7, notify);
        s1.add(new Month(12, 2001), 123.2, notify);
        s1.add(new Month(1, 2002), 131.8, notify);
        s1.add(new Month(2, 2002), 139.6, notify);
        s1.add(new Month(3, 2002), 142.9, notify);
        s1.add(new Month(4, 2002), 138.7, notify);
        s1.add(new Month(6, 2002), 137.3, notify);
        s1.add(new Month(7, 2002), 143.9, notify);
        s1.add(new Month(8, 2002), 139.8, notify);
        s1.add(new Month(9, 2002), 137.0, notify);
        s1.add(new Month(10, 2002), 132.8, notify);

        TimeSeries s2 = new TimeSeries(series2Name);
        s2.add(new Month(2, 2001), 129.6, notify);
        s2.add(new Month(3, 2001), 123.2, notify);
        s2.add(new Month(4, 2001), 117.2, notify);
        s2.add(new Month(5, 2001), 124.1, notify);
        s2.add(new Month(6, 2001), 122.6, notify);
        s2.add(new Month(7, 2001), 119.2, notify);
        s2.add(new Month(8, 2001), 116.5, notify);
        s2.add(new Month(9, 2001), 112.7, notify);
        s2.add(new Month(10, 2001), 101.5, notify);
        s2.add(new Month(11, 2001), 106.1, notify);
        s2.add(new Month(12, 2001), 110.3, notify);
        s2.add(new Month(1, 2002), 111.7, notify);
        s2.add(new Month(2, 2002), 111.0, notify);
        s2.add(new Month(3, 2002), 109.6, notify);
        s2.add(new Month(4, 2002), 113.2, notify);
        s2.add(new Month(5, 2002), 111.6, notify);
        s2.add(new Month(6, 2002), 108.8, notify);
        s2.add(new Month(7, 2002), 101.6, notify);

        TimeSeries s3 = new TimeSeries(series3Name);
        s3.add(new Month(2, 2001), 1129.6, notify);
        s3.add(new Month(3, 2001), 1123.2, notify);
        s3.add(new Month(4, 2001), 1117.2, notify);
        s3.add(new Month(5, 2001), 1124.1, notify);
        s3.add(new Month(6, 2001), 1122.6, notify);
        s3.add(new Month(7, 2001), 1119.2, notify);
        s3.add(new Month(8, 2001), 1116.5, notify);
        s3.add(new Month(9, 2001), 1112.7, notify);
        s3.add(new Month(10, 2001), 1101.5, notify);
        s3.add(new Month(11, 2001), 1106.1, notify);
        s3.add(new Month(12, 2001), 1110.3, notify);
        s3.add(new Month(1, 2002), 1111.7, notify);
        s3.add(new Month(2, 2002), 1111.0, notify);
        s3.add(new Month(3, 2002), 1109.6, notify);
        s3.add(new Month(4, 2002), 1113.2, notify);
        s3.add(new Month(5, 2002), 1111.6, notify);
        s3.add(new Month(6, 2002), 1108.8, notify);
        s3.add(new Month(7, 2002), 1101.6, notify);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s3);
        dataset.addSeries(s2);
        dataset.addSeries(s1);

        return dataset;
    }

    private static XYDataset createDataset2() {
        boolean notify = false;

        TimeSeries s1 = new TimeSeries(series1Name);
        s1.add(new Month(1, 2001), 181.8, notify);
        s1.add(new Month(2, 2001), 167.3, notify);
        s1.add(new Month(4, 2001), 153.8, notify);
        s1.add(new Month(5, 2001), 167.6, notify);
        s1.add(new Month(6, 2001), 158.8, notify);
        s1.add(new Month(9, 2001), 148.3, notify);
        s1.add(new Month(10, 2001), 153.9, notify);
        s1.add(new Month(11, 2001), 142.7, notify);
        s1.add(new Month(12, 2001), 123.2, notify);
        s1.add(new Month(1, 2002), 131.8, notify);
        s1.add(new Month(2, 2002), 139.6, notify);
        s1.add(new Month(3, 2002), 142.9, notify);
        s1.add(new Month(4, 2002), 138.7, notify);
        s1.add(new Month(6, 2002), 137.3, notify);
        s1.add(new Month(7, 2002), 143.9, notify);
        s1.add(new Month(8, 2002), 139.8, notify);
        s1.add(new Month(9, 2002), 137.0, notify);
        s1.add(new Month(10, 2002), 132.8, notify);

        TimeSeries s2 = new TimeSeries(series2Name);
        s2.add(new Month(2, 2001), 129.6, notify);
        s2.add(new Month(3, 2001), 123.2, notify);
        s2.add(new Month(4, 2001), 117.2, notify);
        s2.add(new Month(5, 2001), 124.1, notify);
        s2.add(new Month(6, 2001), 122.6, notify);
        s2.add(new Month(7, 2001), 119.2, notify);
        s2.add(new Month(8, 2001), 116.5, notify);
        s2.add(new Month(9, 2001), 112.7, notify);
        s2.add(new Month(10, 2001), 101.5, notify);
        s2.add(new Month(11, 2001), 106.1, notify);
        s2.add(new Month(12, 2001), 110.3, notify);
        s2.add(new Month(1, 2002), 111.7, notify);
        s2.add(new Month(2, 2002), 111.0, notify);
        s2.add(new Month(3, 2002), 109.6, notify);
        s2.add(new Month(4, 2002), 113.2, notify);
        s2.add(new Month(5, 2002), 111.6, notify);
        s2.add(new Month(6, 2002), 108.8, notify);
        s2.add(new Month(7, 2002), 101.6, notify);

        TimeSeries s3 = new TimeSeries(series3Name);
        s3.add(new Month(2, 2001), 2129.6, notify);
        s3.add(new Month(3, 2001), 2123.2, notify);
        s3.add(new Month(4, 2001), 2117.2, notify);
        s3.add(new Month(5, 2001), 2124.1, notify);
        s3.add(new Month(6, 2001), 2122.6, notify);
        s3.add(new Month(7, 2001), 2119.2, notify);
        s3.add(new Month(8, 2001), 2116.5, notify);
        s3.add(new Month(9, 2001), 2112.7, notify);
        s3.add(new Month(10, 2001), 2101.5, notify);
        s3.add(new Month(11, 2001), 2106.1, notify);
        s3.add(new Month(12, 2001), 2110.3, notify);
        s3.add(new Month(1, 2002), 2111.7, notify);
        s3.add(new Month(2, 2002), 2111.0, notify);
        s3.add(new Month(3, 2002), 2109.6, notify);
        s3.add(new Month(4, 2002), 2113.2, notify);
        s3.add(new Month(5, 2002), 2111.6, notify);
        s3.add(new Month(6, 2002), 2108.8, notify);
        s3.add(new Month(7, 2002), 2101.6, notify);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s3);
        dataset.addSeries(s2);
        dataset.addSeries(s1);

        return dataset;
    }

    private static JFreeChart createTimeSeriesChart() {
        XYDataset dataset1 = createDataset1();
        //DomainAxis
        DateAxis timeAxis = new DateAxis("");
        timeAxis.setLowerMargin(0.02); // reduce the default margins
        timeAxis.setUpperMargin(0.02);
        timeAxis.setDateFormatOverride(new SimpleDateFormat("MM-yyyy"));
        //RangeAxis
        NumberAxis valueAxis = new NumberAxis("");
        valueAxis.setAutoRangeIncludesZero(false); // override default
        NumberAxis valueAxis2 = new NumberAxis("");
        valueAxis2.setAutoRangeIncludesZero(false); // override default
//        valueAxis.setDefaultAutoRange(new Range(100, 1150));
        //Renderer
        XYToolTipGenerator toolTipGenerator = StandardXYToolTipGenerator.getTimeSeriesInstance();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setBaseToolTipGenerator(toolTipGenerator);
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        renderer.setDrawSeriesLineAsPath(true);
        XYDataset dataset2 = createDataset2();
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
        //Plot
        XYPlot plot = new XYPlot(dataset1, timeAxis, valueAxis, null);
        plot.setRenderer(renderer);
        plot.setDataset(1, dataset2);
        plot.setRenderer(1, renderer2);
        plot.setRangeAxis(1, valueAxis2);
        plot.mapDatasetToRangeAxis(1, 1);
        plot.setBackgroundPaint(plotBackgroundPaint);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
//        plot.setRangePannable(true);
        //chart
        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        chart.setBackgroundPaint(Color.white);
        valueAxis.setAutoRange(false);
        timeAxis.setAutoRange(false);
        return chart;
    }

    public static class ChartCheckBox extends HEasyJCheckBox<String> implements ItemListener {
        private static final long serialVersionUID = 1L;
        private String name;
        @SuppressWarnings("unused")
        private ChartPanel chartPanel;

        public ChartCheckBox(String name, ChartPanel chartPanel) {
            super(new HEasyJModelValue<String>(name, name, false));
            this.name = name;
            this.chartPanel = chartPanel;
            this.setSelected(true);
            this.addItemListener(this);
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            boolean visible = e.getStateChange() == ItemEvent.SELECTED;
            XYPlot plot = (XYPlot) timeSeriesChart.getPlot();
            XYLineAndShapeRenderer renderer1 = (XYLineAndShapeRenderer) plot.getRenderer();
            TimeSeriesCollection dataset1 = (TimeSeriesCollection) plot.getDataset();
            int index1 = dataset1.getSeries().indexOf(dataset1.getSeries(name));
            //            renderer1.setSeriesVisible(index, visible);
            renderer1.setSeriesLinesVisible(index1, visible);
            renderer1.setSeriesShapesVisible(index1, visible);
            XYLineAndShapeRenderer renderer2 = (XYLineAndShapeRenderer) plot.getRenderer(1);
            TimeSeriesCollection dataset2 = (TimeSeriesCollection) plot.getDataset(1);
            int index2 = dataset2.getSeries().indexOf(dataset2.getSeries(name));
            renderer2.setSeriesVisible(index2, visible);
        }
    }

    public static void main(String[] args) {
        DatasetVisibleDemo3 demo = new DatasetVisibleDemo3("Dataset Visible Demo 3");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
