package other.demo;

/*
 * %W% %E%
 * 
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduce the above copyright notice, 
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 
 * Neither the name of Oracle or the names of contributors may 
 * be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL 
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST 
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, 
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY 
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, 
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

/*
 * %W% %E%
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Time!
 *
 * @author Rachel Gollub
 * @modified Daniel Peek replaced circle drawing calculation, few more changes
 */
public class Clock extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    private volatile Thread timer; // The thread that displays clock
    private int lastxs, lastys, lastxm, lastym, lastxh, lastyh; // Dimensions used to draw hands 
    private SimpleDateFormat formatter; // Formats the date displayed
    private String lastdate; // String to hold date displayed
    private Font clockFaceFont; // Font for number display on clock
    private Date currentDate; // Used to get date to display
    private Color handColor; // Color of main hands and dial
    private Color numberColor; // Color of second hand and numbers
    private int xcenter = 80, ycenter = 55; // Center position

    public void init() {
        lastxs = lastys = lastxm = lastym = lastxh = lastyh = 0;
        formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
        currentDate = new Date();
        lastdate = formatter.format(currentDate);
        clockFaceFont = new Font("Serif", Font.PLAIN, 14);
        handColor = Color.blue;
        numberColor = Color.darkGray;

        try {
            setBackground(Color.WHITE);
        } catch (NullPointerException e) {
        } catch (NumberFormatException e) {
        }
        try {
            handColor = Color.BLACK;
        } catch (NullPointerException e) {
        } catch (NumberFormatException e) {
        }
        try {
            numberColor = Color.BLACK;
        } catch (NullPointerException e) {
        } catch (NumberFormatException e) {
        }
        setSize(300, 300); // Set clock window size
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //update(Graphics)
        int xh, yh, xm, ym, xs, ys;
        int s = 0, m = 10, h = 10;
        String today;

        currentDate = new Date();

        formatter.applyPattern("s");
        try {
            s = Integer.parseInt(formatter.format(currentDate));
        } catch (NumberFormatException n) {
            s = 0;
        }
        formatter.applyPattern("m");
        try {
            m = Integer.parseInt(formatter.format(currentDate));
        } catch (NumberFormatException n) {
            m = 10;
        }
        formatter.applyPattern("h");
        try {
            h = Integer.parseInt(formatter.format(currentDate));
        } catch (NumberFormatException n) {
            h = 10;
        }

        // Set position of the ends of the hands
        xs = (int) (Math.cos(s * Math.PI / 30 - Math.PI / 2) * 45 + xcenter);
        ys = (int) (Math.sin(s * Math.PI / 30 - Math.PI / 2) * 45 + ycenter);
        xm = (int) (Math.cos(m * Math.PI / 30 - Math.PI / 2) * 40 + xcenter);
        ym = (int) (Math.sin(m * Math.PI / 30 - Math.PI / 2) * 40 + ycenter);
        xh = (int) (Math.cos((h * 30 + m / 2) * Math.PI / 180 - Math.PI / 2) * 30 + xcenter);
        yh = (int) (Math.sin((h * 30 + m / 2) * Math.PI / 180 - Math.PI / 2) * 30 + ycenter);

        // Get the date to print at the bottom
        formatter.applyPattern("EEE MMM dd HH:mm:ss yyyy");
        today = formatter.format(currentDate);

        g.setFont(clockFaceFont);
        // Erase if necessary
        g.setColor(getBackground());
        if (xs != lastxs || ys != lastys) {
            g.drawLine(xcenter, ycenter, lastxs, lastys);
            g.drawString(lastdate, 5, 125);
        }
        if (xm != lastxm || ym != lastym) {
            g.drawLine(xcenter, ycenter - 1, lastxm, lastym);
            g.drawLine(xcenter - 1, ycenter, lastxm, lastym);
        }
        if (xh != lastxh || yh != lastyh) {
            g.drawLine(xcenter, ycenter - 1, lastxh, lastyh);
            g.drawLine(xcenter - 1, ycenter, lastxh, lastyh);
        }

        // Draw date and hands
        g.setColor(numberColor);
        g.drawString(today, 5, 125);
        g.drawLine(xcenter, ycenter, xs, ys);
        g.setColor(handColor);
        g.drawLine(xcenter, ycenter - 1, xm, ym);
        g.drawLine(xcenter - 1, ycenter, xm, ym);
        g.drawLine(xcenter, ycenter - 1, xh, yh);
        g.drawLine(xcenter - 1, ycenter, xh, yh);
        lastxs = xs;
        lastys = ys;
        lastxm = xm;
        lastym = ym;
        lastxh = xh;
        lastyh = yh;
        lastdate = today;
        currentDate = null;
        //paint(Graphics)
        g.setFont(clockFaceFont);
        // Draw the circle and numbers
        g.setColor(handColor);
        g.drawArc(xcenter - 50, ycenter - 50, 100, 100, 0, 360);
        g.setColor(numberColor);
        g.drawString("9", xcenter - 45, ycenter + 3);
        g.drawString("3", xcenter + 40, ycenter + 3);
        g.drawString("12", xcenter - 5, ycenter - 37);
        g.drawString("6", xcenter - 3, ycenter + 45);

        // Draw date and hands
        g.setColor(numberColor);
        g.drawString(lastdate, 5, 125);
        g.drawLine(xcenter, ycenter, lastxs, lastys);
        g.setColor(handColor);
        g.drawLine(xcenter, ycenter - 1, lastxm, lastym);
        g.drawLine(xcenter - 1, ycenter, lastxm, lastym);
        g.drawLine(xcenter, ycenter - 1, lastxh, lastyh);
        g.drawLine(xcenter - 1, ycenter, lastxh, lastyh);
    }

    public void start() {
        timer = new Thread(this);
        timer.start();
    }

    public void stop() {
        timer = null;
    }

    public void run() {
        Thread me = Thread.currentThread();
        while (timer == me) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Clock clock = new Clock();
                clock.init();
                clock.start();
                JFrame f = new JFrame("clock demo");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.add(clock);
                f.pack();
                f.setVisible(true);
            }
        });
    }
}
