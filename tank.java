package tank;

import java.io.*; 
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*; 
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;


public class Tank extends JFrame implements Runnable {
    static final int WINDOW_WIDTH = 1920;
    static final int WINDOW_HEIGHT = 1038;
    final int XBORDER = 20;
    final int YBORDER = 30;
    final int YTITLE = 20;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;
    
    
    static Tank frame;
    public static void main(String[] args) {
        frame = new Tank();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Tank() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {

                }
                if (e.BUTTON3 == e.getButton()) 
                    reset();
                
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
  
      }});

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
          
        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) 
                {
                } 
                else if (e.VK_DOWN == e.getKeyCode()) 
                {

                } 
                else if (e.VK_LEFT == e.getKeyCode()) 
                {
                } 
                else if (e.VK_RIGHT == e.getKeyCode()) 
                { 
                }
                else if (e.VK_R == e.getKeyCode()) 
                    reset();
                
                repaint();
                
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        }
        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
        
        //fill background
        g.setColor(Color.gray);
        g.fillRect(0, 0, xsize, ysize);
        
        //fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
        
        // draw border
        g.setColor(Color.gray);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
       

        gOld.drawImage(image, 0, 0, null);
    }
/////////////////////////////////////////////////////////////////////////
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.04; //25fps   //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {    
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {
    if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
            reset();
        
        }
    }
//////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) 
        {return (x + XBORDER);}

    public int getY(int y) 
        {return (y + YBORDER + YTITLE);}

    public int getYNormal(int y) 
        {return (-y + YBORDER + YTITLE + getHeight2());}
      
    public int getWidth2()
        {return (xsize - getX(0) - XBORDER);}

    public int getHeight2() 
        {return (ysize - getY(0) - YBORDER);}
}
