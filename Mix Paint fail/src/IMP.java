/*
 *Hunter Lloyd
 * Copyrite.......I wrote, ask permission if you want to use it outside of class. 
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.awt.image.PixelGrabber;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;


class IMP implements MouseListener, MouseMotionListener, ChangeListener{
   JFrame frame;
   PaintPanel mp;
   ButtonPanel buttonPane;
   JButton start;
   JScrollPane scroll;
   JMenuItem openItem, exitItem, resetItem, about;
   Toolkit toolkit;
   File pic;
   ImageIcon img;
   int colorX, colorY;
   int [] pixels;
   int [] results;
   //Instance Fields you will be using below
   
   //This will be your height and width of your 2d array
   int height=0, width=0;
   
   //your 2D array of pixels
    int picture[][];

    /* 
     * In the Constructor I set up the GUI, the frame the menus. The open pulldown 
     * menu is how you will open an image to manipulate. 
     */
   IMP()
   {
      toolkit = Toolkit.getDefaultToolkit();
      frame = new JFrame("Paint n' Stuff");
      JMenuBar bar = new JMenuBar();
      JMenu file = new JMenu("File");
      JMenu edit = new JMenu("Edit");
      JMenu choices = new JMenu("Choices");
      JMenu help = new JMenu("Help");
      JMenu functions = getFunctions();
      frame.addWindowListener(new WindowAdapter(){
    	  @Override
    	  public void windowClosing(WindowEvent ev){quit();}
      });
      
      JSlider stroke = new JSlider(0,500); 
      stroke.setValue(50);
      stroke.setMajorTickSpacing(10);
      stroke.setPaintTicks(true);
      stroke.addChangeListener(new ChangeListener(){
   
		@Override
		public void stateChanged(ChangeEvent evt) {
			// TODO Auto-generated method stub
			JSlider source = (JSlider)evt.getSource();
			mp.updateGC(source.getValue());
			System.out.println("Slider State: " + source.getValue());
		}
      }); 
      
      openItem = new JMenuItem("Open");
      openItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ handleOpen(); }
           });
      resetItem = new JMenuItem("Reset");
      resetItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ reset(); }
           });     
      exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ quit(); }
           });
      about = new JMenuItem("About");
      about.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ aboutToast(); }
           });
      JButton cancer = new JButton("TEXT");
      cancer.addActionListener(new ActionListener(){
    	  @Override
    	  public void actionPerformed(ActionEvent evt){
    		  gasChamber();
    	  }
      });
      file.add(openItem);
      file.add(resetItem);
      file.add(exitItem);
      help.add(about);
      bar.add(file);
      bar.add(edit);
      bar.add(choices);
      bar.add(functions);
      bar.add(help);
      frame.setSize(1200, 1200);
      mp = PaintPanel.getInst();
      mp.setBackground(new Color(255, 255, 255));
      mp.addMouseMotionListener(this);
      scroll = new JScrollPane(mp);
      frame.getContentPane().add(scroll, BorderLayout.CENTER);
      JPanel butPanel = new JPanel();
      //Add buttons onto a JPanel and then put that to the west of the BorderLayout.
 
      butPanel.setBackground(new Color(255, 255, 255));
      butPanel.setSize(frame.getWidth(), 500);
      start = new JButton("Free Draw");
      start.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ 
            	freeDraw();
            	}
           });
      butPanel.add(start);
      butPanel.add(stroke);
      butPanel.add(cancer);
      
      JButton bagett = new JButton("color");
      bagett.addActionListener(new ActionListener(){
          @Override
        public void actionPerformed(ActionEvent evt){ invadePoland(); }
         });     
      butPanel.add(bagett);      
  
      frame.getContentPane().add(butPanel, BorderLayout.SOUTH);
      frame.setJMenuBar(bar);
      
      buttonPane = new ButtonPanel(); 
      frame.getContentPane().add(buttonPane, BorderLayout.WEST);
      frame.setVisible(true);  
      
      
      
   }
   
   /* 
    * This method creates the pulldown menu and sets up listeners to selection of the menu choices. If the listeners are activated they call the methods 
    * for handling the choice, fun1, fun2, fun3, fun4, etc. etc. 
    */
   
   private JMenu getFunctions()
   {
      JMenu fun = new JMenu("Functions");
      JMenuItem firstItem = new JMenuItem("MyExample - fun1 method");
      JMenuItem secondItem = new JMenuItem("Stacks Lab");
     
      firstItem.addActionListener(new ActionListener(){
             @Override
           public void actionPerformed(ActionEvent evt){fun1();}
      });
      secondItem.addActionListener(new ActionListener (){
     	 @Override
     	 public void actionPerformed(ActionEvent evt){ fun2(); }
      });
      
      
      //fun.add(firstItem); 


       fun.add(firstItem);
       fun.add(secondItem);
       
       return fun;   

   }
  
  /*
   * This method handles opening an image file, breaking down the picture to a one-dimensional array and then drawing the image on the frame. 
   * You don't need to worry about this method. 
   */
    private void handleOpen()
  {  
     img = new ImageIcon();
     JFileChooser chooser = new JFileChooser();
     int option = chooser.showOpenDialog(frame);
     if(option == JFileChooser.APPROVE_OPTION) {
       pic = chooser.getSelectedFile();
       img = new ImageIcon(pic.getPath());
      }
     width = img.getIconWidth();
     height = img.getIconHeight(); 
     
     JLabel label = new JLabel(img);
     label.addMouseListener(this);
     label.addMouseMotionListener(this);
     pixels = new int[width*height];
     
     results = new int[width*height];
  
          
     Image image = img.getImage();
        
     PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width );
     try{
         pg.grabPixels();
     }catch(InterruptedException e)
       {
          System.err.println("Interrupted waiting for pixels");
          return;
       }
     for(int i = 0; i<width*height; i++)
        results[i] = pixels[i];  
     turnTwoDimensional();
     mp.removeAll();
     mp.add(label);
     
     mp.revalidate();
  }
  
  /*
   * The libraries in Java give a one dimensional array of RGB values for an image, I thought a 2-Dimensional array would be more usefull to you
   * So this method changes the one dimensional array to a two-dimensional. 
   */
  private void turnTwoDimensional()
  {
     picture = new int[height][width];
     for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          picture[i][j] = pixels[i*width+j];
      
     
  }
  /*
   *  This method takes the picture back to the original picture
   */
  private void reset()
  {
        for(int i = 0; i<width*height; i++)
             pixels[i] = results[i]; 
       Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width)); 

      JLabel label2 = new JLabel(new ImageIcon(img2));    
       mp.removeAll();
       mp.add(label2);
     
       mp.revalidate(); 
    }
  /*
   * This method is called to redraw the screen with the new image. 
   */
  private void resetPicture()
  {
	  for(int i=0; i<height; i++)
		  for(int j=0; j<width; j++)
			  pixels[i*width+j] = picture[i][j];
	  Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width)); 

	  JLabel label2 = new JLabel(new ImageIcon(img2));    
	  mp.removeAll();
	  mp.add(label2);

	  mp.revalidate();
	  MouseMotionListener mm = new MouseMotionListener()
	  {

		  @Override
		  public void mouseDragged(MouseEvent e) {
			  // TODO Auto-generated method stub
			  mp.free(e.getX(),e.getY());
		  }

		  @Override
		  public void mouseMoved(MouseEvent e) {
			  // TODO Auto-generated method stub

		  }

	  };
	  if(mp.getMouseMotionListeners().length < 1){
		  mp.addMouseMotionListener(mm);
		  System.out.println("HAI");
	  }
	  MouseListener m = new MouseListener() {

		  @Override
		  public void mouseClicked(MouseEvent m){
			  colorX = m.getX();
			  colorY = m.getY();
			  System.out.println(colorX + "  " + colorY);
			  getValue();
			  start.setEnabled(true);
		  }

		  @Override
		  public void mouseEntered(MouseEvent e) {
			  // TODO Auto-generated method stub

		  }

		  @Override
		  public void mouseExited(MouseEvent e) {
			  // TODO Auto-generated method stub

		  }

		  @Override
		  public void mousePressed(MouseEvent e) {
			  // TODO Auto-generated method stub

		  }

		  @Override
		  public void mouseReleased(MouseEvent e) {
			  // TODO Auto-generated method stub

		  }

	  };
	  //mouseClicked();
	  if(mp.getMouseListeners().length < 1){
		  mp.addMouseListener(m);
	  }
	  /*try {
				mp.getMouseListeners()[0];
			} catch (ArrayIndexOutOfBoundsException e) {
				// TODO: handle exception
				mp.addMouseListener(m);
			}*/

  }


    /*
     * This method takes a single integer value and breaks it down doing bit manipulation to 4 individual int values for A, R, G, and B values
     */
  private int [] getPixelArray(int pixel)
  {
      int temp[] = new int[4];
      temp[0] = (pixel >> 24) & 0xff;
      temp[1]   = (pixel >> 16) & 0xff;
      temp[2] = (pixel >>  8) & 0xff;
      temp[3]  = (pixel      ) & 0xff;
      return temp;
      
    }
    /*
     * This method takes an array of size 4 and combines the first 8 bits of each to create one integer. 
     */
  private int getPixels(int rgb[])
  {
         int alpha = 0;
         int rgba = (rgb[0] << 24) | (rgb[1] <<16) | (rgb[2] << 8) | rgb[3];
        return rgba;
  }
  
  public void getValue()
  {
      int pix = picture[colorY][colorX];
      int temp[] = getPixelArray(pix);
      System.out.println("Color value " + temp[0] + " " + temp[1] + " "+ temp[2] + " " + temp[3]);
    }
  
  /**************************************************************************************************
   * This is where you will put your methods. Every method below is called when the corresponding pulldown menu is 
   * used. As long as you have a picture open first the when your fun1, fun2, fun....etc method is called you will 
   * have a 2D array called picture that is holding each pixel from your picture. 
   *************************************************************************************************/
   /*
    * Example function that just removes all red values from the picture. 
    * Each pixel value in picture[i][j] holds an integer value. You need to send that pixel to getPixelArray the method which will return a 4 element array 
    * that holds A,R,G,B values. Ignore [0], that's the Alpha channel which is transparency, we won't be using that, but you can on your own.
    * getPixelArray will breaks down your single int to 4 ints so you can manipulate the values for each level of R, G, B. 
    * After you make changes and do your calculations to your pixel values the getPixels method will put the 4 values in your ARGB array back into a single
    * integer value so you can give it back to the program and display the new picture. 
    */
  private void fun1()
  {
     
    for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
       {   
          int rgbArray[] = new int[4];
         
          //get three ints for R, G and B
          rgbArray = getPixelArray(picture[i][j]);
         
        
           rgbArray[1] = 0;
           //take three ints for R, G, B and put them back into a single int
           picture[i][j] = getPixels(rgbArray);
        } 
     resetPicture();
  }

  /*private void Clear(){
	  
	  for(int i=0; i<height; i++)
	       for(int j=0; j<width; j++){
	    	   
	    	   int rgbArray[] = new int[4];
	    	   
	    	   rgbArray = getPixelArray(picture[i][j]);
	    	   
	    	   rgbArray[1] = 0;
	    	   rgbArray[2] = 0;
	    	   rgbArray[3] = 0;
	    	   
	    	   picture[i][j] = getPixels(rgbArray);
	    	   
	       }
  }
  */
  /*
   * fun2
   * This is where you will write your STACK
   * All the pixels are in picture[][]
   * Look at above fun1() to see how to get the RGB out of the int (getPixelArray)
   * and then put the RGB back to an int (getPixels)
   */  
  private void fun2()
  {
	  
	  Stack<PixelRegion> pixelStack = new Stack<PixelRegion>();
	  int x = colorX;
	  int y = colorY;
	  int[] cords = new int[2];
	  
	  int rgbArray[] = getPixelArray(picture[colorY][colorX]);
	  
	  PixelRegion pixel = new PixelRegion(x,y,rgbArray);
	  
	  pixelStack.add(pixel);
	  
	  //Go through everything here
	  while(!pixelStack.isEmpty()){
		  //System.out.println("Still Alive 1 X:"+ x+", Y:"+y+" Stack Size:"+pixelStack.size());
		  picture[pixelStack.peek().getYCord()][pixelStack.peek().getXcord()] = getPixels(pixelStack.peek().getColor());
		  if(pixelStack.peek().getChecked() == 7){
			  pixelStack.pop();
			  if(pixelStack.isEmpty()){
				  break;
			  }
		 }
		  
		//Updates cords to current top of Stack
		  cords = pixelStack.peek().getCordsOfNext();
		  x = cords[0];
		  y = cords[1];
		  
		  //Generates new PixelRegion based off current top pixel's info
		  if((x<0)||(y<0)||(width <= x)||(height <= y)){
			  pixelStack.peek().nextChecked();
		  }else if(getPixelArray(picture[y][x])[3]>50){
			  
			  pixel = new PixelRegion(x,y,getPixelArray(picture[y][x]));
			  pixelStack.add(pixel);
			  
		  }else{
			  //System.out.println("Still Alive 2, Skipping non compatible pixel");
			  pixelStack.peek().nextChecked();
		  }
	  }
	  resetPicture();
	  System.out.println("Done!");

  }
  
  public void aboutToast(){
	  
	  BufferedImage logo = null;
	  try {
		logo = ImageIO.read(new File("Logo.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  //grid = (BufferedImage)(this.createImage(150,150));
	  
	  ImageIcon icon = new ImageIcon(logo);
	  //JOptionPane.showInternalMessageDialog(frame,logo, "About",JOptionPane.INFORMATION_MESSAGE, logo);
	  JOptionPane.showMessageDialog(frame, new JLabel("MS-Paint#\n\nCreated by: George Engel & CJ Cassidy\n\nYeah, we Awesome.", icon, JLabel.LEFT),"Information you totally need to know.", JOptionPane.INFORMATION_MESSAGE);
  }
  
  public void freeDraw(){
	  PaintPanel.getInst().setShape(GC.FREEDRAW);
  }
	  private void quit()
  {  
		    /*   BufferedImage bufImage = new BufferedImage(mp.getSize().width, mp.getSize().height,BufferedImage.TYPE_INT_RGB);
		       mp.paint(bufImage.createGraphics());
		       File imageFile = new File("."+File.separator);
		    try{
		        imageFile.createNewFile();
		        ImageIO.write(bufImage, "jpeg", imageFile);
		    }catch(Exception ex){
		    }*/
		    System.exit(0);
		}



    @Override
   public void mouseEntered(MouseEvent m){}
    @Override
   public void mouseExited(MouseEvent m){}
    @Override
   public void mouseClicked(MouseEvent m){
        colorX = m.getX();
        colorY = m.getY();
        System.out.println(colorX + "  " + colorY);
        getValue();
        start.setEnabled(true);
    }
    
    int x;
    int y;
    @Override
   public void mousePressed(MouseEvent m){
    	x = m.getX();
    	y = m.getY();
    	PaintPanel.getInst().setLocation(x,y);
    	System.out.println("Mouse Pressed");
    }
    @Override
   public void mouseReleased(MouseEvent m){
    	PaintPanel.getInst().DetermineShape(x, m.getX(), y, m.getY());
    }
   
   public static void main(String [] args)
   {
      IMP imp = new IMP();
   }

@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	mp.free(e.getX(),e.getY());
	System.out.println("Mouse Dragged");
}

@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub

}

public void invadePoland(){
	/*JLabel banner = new JLabel("Welcome to the concentration campz! Nien.",JLabel.CENTER);
	banner.setForeground(Color.YELLOW);
	JColorChooser stallen = new JColorChooser(banner.getForeground());
	JDialog france = new JDialog();
	stallen.getSelectionModel().addChangeListener(new ChangeListener()){
		public void stateChanged(ChangeEvent e)
		{
			Color newColor = stallen.getColor();
			PaintPanel.getInst().setColor(newColor);
		}
	}
	
	france.add(stallen);
	france.setSize(500,500);
	france.setVisible(true);*/
	Color newColor = JColorChooser.showDialog(frame, "Hello", Color.BLUE);
	PaintPanel.getInst().setColor(newColor);
}

public void gasChamber(){

	PaintPanel.getInst().setShape(GC.TEXTDRAW);
	
}
}