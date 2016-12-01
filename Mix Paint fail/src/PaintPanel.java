import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PaintPanel extends JPanel{

	BufferedImage grid;
	Graphics2D gc;
	Graphics2D g2;
	private int shape;
	private int locationX;
	private int locationY;
	private static PaintPanel inst;
	private int sliderValue = 50;
	private Color newColor = Color.BLACK;
	
	public static PaintPanel getInst(){
		if(inst==null)
			inst = new PaintPanel();
		return inst;
	}
	
	private PaintPanel(){
		super();	
	}
	
	public void paintComponent(Graphics g)
    { 
         super.paintComponent(g);  
         Graphics2D g2 = (Graphics2D)g;
         if(grid == null){
            int w = this.getWidth();
            int h = this.getHeight();
            grid = (BufferedImage)(this.createImage(w,h));
            gc = grid.createGraphics();
            
         }
         
         g2.drawImage(grid, null, 0, 0);
         g.setColor(new Color(255, 255, 255));
         g.fillRect(0, 0, getWidth(), getHeight());
         
   
         //g.drawImage(takeSnapShot(this), 0, 0, this.getWidth(), this.getHeight(), null);
    }
	
	public void empty(){
		getGraphics().fillRect(0, 0, getWidth(), getHeight());
		getGraphics().setColor(new Color(255, 255, 255));
		repaint();
	}
	
	public void setShape(int a){
		shape = a;
		System.out.println(a);
	}

	
	int x1;
	int x2;
	int y1;
	int y2;
	
	public void DetermineShape(int a1, int a2, int b1, int b2){
		x1 = a1;
		x2 = a2;
		y1 = b1;
		y2 = b2;
		fml();
	}
	
	public void fml(){
		g2 =((Graphics2D) getGraphics());
		g2.setStroke(new BasicStroke(sliderValue,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		g2.setColor(newColor);
		switch (shape) {
		case GC.LINE:
			g2.drawLine(x1, y1, x2, y2);
			break;
		case GC.FILLOVAL:
			
			if((x1 < x2)&&y1<y2){
				g2.fillOval(x1, y1, x2-x1, y2-y1);
			} else if((x1 > x2)&&y1<y2){
				g2.fillOval(x2, y1, x1-x2, y2-y1);
			} else if((x1< x2)&&y1>y2){
				g2.fillOval(x1, y2, x2-x1, y1-y2);
			} else if((x1 > x2)&&y1>y2){
				g2.fillOval(x2, y2, x1-x2, y1-y2);
			} 
			
			break;
		case GC.FILLRECTANGLE:
			
			if((x1 < x2)&&y1<y2){
				g2.fillRect(x1, y1, x2-x1, y2-y1);
			} else if((x1 > x2)&&y1<y2){
				g2.fillRect(x2, y1, x1-x2, y2-y1);
			} else if((x1< x2)&&y1>y2){
				g2.fillRect(x1, y2, x2-x1, y1-y2);
			} else if((x1 > x2)&&y1>y2){
				g2.fillRect(x2, y2, x1-x2, y1-y2);
			} 
			
			//getGraphics().drawRect(x, y, width, height);
			break;
		case GC.EMPTYOVAL:
			if((x1 < x2)&&y1<y2){
				g2.drawOval(x1, y1, x2-x1, y2-y1);
			} else if((x1 > x2)&&y1<y2){
				g2.drawOval(x2, y1, x1-x2, y2-y1);
			} else if((x1< x2)&&y1>y2){
				g2.drawOval(x1, y2, x2-x1, y1-y2);
			} else if((x1 > x2)&&y1>y2){
				g2.drawOval(x2, y2, x1-x2, y1-y2);
			} 
			break;
		case GC.EMPTYRECTANGLE:
			if((x1 < x2)&&y1<y2){
				g2.drawRect(x1, y1, x2-x1, y2-y1);
			} else if((x1 > x2)&&y1<y2){
				g2.drawRect(x2, y1, x1-x2, y2-y1);
			} else if((x1< x2)&&y1>y2){
				g2.drawRect(x1, y2, x2-x1, y1-y2);
			} else if((x1 > x2)&&y1>y2){
				g2.drawRect(x2, y2, x1-x2, y1-y2);
			} 
			break;
		case GC.TEXTDRAW:
			g2.setFont(g2.getFont().deriveFont((float)sliderValue));
			String concentration = JOptionPane.showInputDialog("Enter Text Here: ");
			g2.drawString(concentration, x1, y1);
			break;
		default:
			break;
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		//g.drawImage(grid, 0, 0, null);
	}
	
	public void updateGC(int a){
		((Graphics2D) getGraphics()).setStroke(new BasicStroke(a));
		gc.setStroke(new BasicStroke(500));
		sliderValue = a;
		System.out.println("afsbjbsa");
	}
	
	//public void drawLine(int x1, int x2, int y1, int y2){
		//getGraphics().setColor(new Color(50, 100, 255));
		
		//grid.getGraphics().repaint();
	//}
	public void drawRectangleFill(){
		
	}
	public void drawRectangleEmpty(){
		
	}
	public void drawOvalFill(){
		
	}
	public void drawOvalEmpty(){
		
	}
	public void free(int x, int y)
	{
		if(shape==GC.FREEDRAW)
		{
			g2 =((Graphics2D) getGraphics());
			g2.setStroke(new BasicStroke(sliderValue,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
			g2.setColor(newColor);
			//do the smoothing thing, and have it toggleable for abrasive
			g2.drawLine(locationX, locationY, x, y);
			locationX=x;
			locationY=y;
			System.out.println("ififif");
		}
		System.out.println("help");
	}
	
	public void setColor(Color a)
	{
		newColor = a;
	}
	
	public void setLocation(int x, int y)
	{
		locationX=x;
		locationY=y;
	}
	
	public BufferedImage takeSnapShot(JPanel panel ){
	       BufferedImage bufImage = new BufferedImage(panel.getSize().width, panel.getSize().height,BufferedImage.TYPE_INT_RGB);
	       //panel.paint(bufImage.createGraphics());
	       return bufImage;
}
}
