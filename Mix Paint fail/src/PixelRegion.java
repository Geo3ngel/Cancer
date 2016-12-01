

public class PixelRegion {
	
	private int[] rgb;
	
	private int xCord;
	private int yCord;
	
	private int checked;
	private int threshold;
	
	public PixelRegion(int x, int y, int[] a){
		checked = 0;
		
		xCord = x;
		yCord = y;
		rgb = a;
		
		//CAN CHANGE TOLERANCE HERE
		threshold = 200;
		changeColor();
	}
	
	public PixelRegion(int x, int y, int[] a, int thres){

		xCord = x;
		yCord = y;
		rgb = a;
		
		System.out.println("New Pixel Region at x: "+ xCord + " y: " + yCord);
		
		threshold = thres;
	}
	
	public void changeColor(){
		if(rgb[3]>threshold){
			rgb[1] = 0;
			rgb[2] = 0;
			rgb[3] = 0;
		}
	}
	
	public int[] getColor(){
		return rgb;
	}
	
	public void nextChecked(){
		if(checked < 8){
			checked++;
		}
	}
	
	public int getChecked(){
		return checked;
	}
	
	public boolean meetsThreshold(){
		return (rgb[3]>threshold);
	}
	
	//These will return the last checked pixel's cords
	public int[] getCordsOfNext(){
		int[] temp = new int[2];
		switch (checked) {
		case 0:
			temp[0] = xCord - 1;
			temp[1] = yCord - 1;
			
			return temp;
		case 1:
			temp[0] = xCord;
			temp[1] = yCord - 1;
			
			return temp;
		case 2:
			temp[0] = xCord + 1;
			temp[1] = yCord - 1;
			
			return temp;
		case 3:
			temp[0] = xCord - 1;
			temp[1] = yCord;
			
			return temp;
		case 4:
			temp[0] = xCord + 1;
			temp[1] = yCord;
			
			return temp;
		case 5:
			temp[0] = xCord + 1;
			temp[1] = yCord + 1;
			
			return temp;
		case 6:
			temp[0] = xCord;
			temp[1] = yCord + 1;
			
			return temp;

		case 7:
			temp[0] = xCord - 1;
			temp[1] = yCord + 1;
			
			return temp;
		default:
			System.out.println("Why would it be going to default? "+xCord+" "+yCord);
			temp[0] = xCord - 1;
			temp[1] = yCord + 1;
			return temp;
		}
		
	}
	public int getXcord(){
		return xCord;
	}
	
	public int getYCord(){
		return yCord;
	}
}
