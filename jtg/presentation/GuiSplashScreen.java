package presentation;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.geom.Rectangle2D;

import javax.swing.JWindow;

import service.SerGUIUtils;

public class GuiSplashScreen extends JWindow
{
   /** der aktualisierbare Anzeige-Text */
   protected String text;

   /** der Titel des Splash-Screens */
   protected String title;

   /** das anzuzeigende Bild */
   protected Image myImage;

   /** die Gr  e des Splash-Screens */
   protected Dimension dim;

   /**
    * Erzeugt einen neuen Splash-Screen aus dem Bild
    * <code>imageFile</code>,
    * der  berschrift <code>title</code> und dem Anzeige-Text
    * <code>text</code>
    * @param imageFile die Bild-Datei
    * @param title der Titel
    * @param text der (ver nderliche) Anzeige-Text
    */
   public GuiSplashScreen(String imageFile,String title,String text) {
    super(new Frame());
    this.title=title;
    this.text=text;
    myImage=getToolkit().createImage(imageFile);
    try
    {
      final MediaTracker tracker=new MediaTracker(this);
      tracker.addImage(myImage, 1);
      tracker.waitForID(1);
    }
    catch (InterruptedException e)
    {
    }

    
    
    dim=getToolkit().getScreenSize();
    dim.width=320;
    dim.height=500;
    setSize(dim.width,dim.height);
    SerGUIUtils.center(this);
     this.setVisible(true);
   }

   /**
    * setzt den Anzeige-Text auf <code>text</code>
    * @param text der Anzeige-Text
    */
   public void setBottomText(String text) {
     this.text=text;
     repaint();
   }

   /**
    * gibt den Anzeige-Text zur ck
    * @return String der Anzeige-Text
    */
   public String getBottomText() {
     return this.text;
   }

   /**
    * zeichnet den Titel doppelt so gro  wie den Anzeige-Text
    * @see java.awt.Component#paint(Graphics)
    */
   public void paint(Graphics g) {
     final Font oldFont=g.getFont();
     final Font newFont=new Font(oldFont.getName(), Font.BOLD,oldFont.getSize()*2);
     g.setFont(newFont);

     FontMetrics fm=g.getFontMetrics();
     int t=fm.getAscent();
     Rectangle2D rect=fm.getStringBounds(title, g);
     int w=(int)rect.getWidth();
     int h=(int)rect.getHeight();

     g.drawString(title, (dim.width-w)/2,t);
     g.setFont(oldFont);

     fm=g.getFontMetrics();
     t=fm.getDescent();

     rect=fm.getStringBounds(text, g);
     w=(int)rect.getWidth();
     int b=(int)rect.getHeight();

     g.drawString(text, (dim.width-w)/2, dim.height-t);

     g.drawImage(myImage,0,h,dim.width,dim.height-b,
           0,0,myImage.getWidth(this),myImage.getHeight(this),
           this);
   }
}