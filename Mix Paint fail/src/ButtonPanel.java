import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{

	public ButtonPanel(){
		super();
		setLayout(new GridLayout(0,1));
		addButtons();
	}
	
	public void addButtons(){
		//declaring buttons here
		JButton clear = new JButton("Clear");
		JButton drawLine = new JButton();
		JButton filledOval = new JButton();
		JButton filledRectangle = new JButton();
		JButton emptyRectangle = new JButton();
		JButton emptyOval = new JButton();
		
		BufferedImage bi = new BufferedImage(180,100,2);
		Graphics2D gc = bi.createGraphics();
		gc.setColor(Color.BLACK);
		gc.setStroke(new BasicStroke(10));
		gc.drawLine(bi.getWidth()*(1/3), bi.getHeight()*(2/5), bi.getWidth()-(bi.getWidth()*(1/3)), bi.getHeight()-(bi.getHeight()*(2/5)));
		ImageIcon ii = new ImageIcon(bi);
		drawLine.setIcon(ii);
		//System.out.println(drawLine.getWidth()+ " "+ drawLine.getHeight());
		
		bi = new BufferedImage(180,100,2);
		gc = bi.createGraphics();
		gc.setColor(Color.BLACK);
		gc.fillOval(bi.getWidth()*(1/3), bi.getHeight()*(2/5), bi.getWidth()-(bi.getWidth()*(1/3)), bi.getHeight()-(bi.getWidth()*(2/5)));
		ii = new ImageIcon(bi);
		filledOval.setIcon(ii);
		
		bi = new BufferedImage(180,100,2);
		gc = bi.createGraphics();
		gc.setColor(Color.BLACK);
		gc.fillRect(bi.getWidth()*(1/3), bi.getHeight()*(2/5), bi.getWidth()-(bi.getWidth()*(1/3)), bi.getHeight()-(bi.getHeight()*(2/5)));
		ii = new ImageIcon(bi);
		filledRectangle.setIcon(ii);
		
		bi = new BufferedImage(180,100,2);
		gc = bi.createGraphics();
		gc.setColor(Color.BLACK);
		gc.drawOval(bi.getWidth()*(1/3), bi.getHeight()*(2/5), bi.getWidth()-(bi.getWidth()*(1/3)), bi.getHeight()-(bi.getHeight()*(2/5)));
		ii = new ImageIcon(bi);
		emptyOval.setIcon(ii);
		
		bi = new BufferedImage(180,100,2);
		gc = bi.createGraphics();
		gc.setColor(Color.BLACK);
		gc.drawRect(bi.getWidth()*(1/3), bi.getHeight()*(2/5), bi.getWidth()-(bi.getWidth()*(2/3)), bi.getHeight()-(bi.getHeight()*(2/5)));
		ii = new ImageIcon(bi);
		emptyRectangle.setIcon(ii);
		
		//add action listeners to Button's here.
		clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PaintPanel.getInst().empty();
			}
			
		});
		
		drawLine.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PaintPanel.getInst().setShape(GC.LINE);
			}
			
		});
		
		filledOval.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PaintPanel.getInst().setShape(GC.FILLOVAL);
			}
			
		});
		
		filledRectangle.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PaintPanel.getInst().setShape(GC.FILLRECTANGLE);
			}
			
		});
		
		emptyOval.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PaintPanel.getInst().setShape(GC.EMPTYOVAL);
			}
			
		});
		
		emptyRectangle.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PaintPanel.getInst().setShape(GC.EMPTYRECTANGLE);
			}
			
		});
		
		//adding buttons to panel here
		add(clear);
		add(drawLine);
		add(filledOval);
		add(filledRectangle);
		add(emptyRectangle);
		add(emptyOval);
	}	
	
	public int setPaint(){
		
		
	}
	
}
