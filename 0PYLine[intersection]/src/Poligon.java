import java.awt.Color;
import java.awt.Graphics;

public class Poligon {

	private Line[] lines;
	private boolean filled=false;
	Color clor=new Color(200,255,200);
	
	public Poligon(float[][] points) {
		lines=new Line[points.length];
		for(int a=0;a<points.length-1;a++) {
			lines[a]=new Line(points[a][0], points[a][1], points[a+1][0], points[a+1][1],clor);
		}
		lines[points.length-1]=new Line(points[points.length-1][0], points[points.length-1][1], points[0][0], points[0][1],clor);
	}
	
	public void render(Graphics g, Color c, boolean fill) {
		int[] xs=new int[lines.length];
		int[] ys=new int[lines.length];
		for(int a=0;a<lines.length;a++) {
			xs[a]=(int)(lines[a].getends()[0]);
			ys[a]=(int)(lines[a].getends()[1]);
		}

		g.setColor(c);
		if(fill) {
			g.fillPolygon(xs, ys, lines.length);
		}else {
			g.drawPolygon(xs, ys, lines.length);
		}
	}
	public boolean pointIsIn(float x,float y) {
		boolean is=false;
		
		Line l1=new Line(x,y,x,0,Color.white);
		Plan.pivotLine((int)l1.getends()[0], (int)l1.getends()[1], (int)l1.getends()[2], (int)l1.getends()[3], 10);
		for(Line l:lines) {
			Plan.pivotLine((int)l.getends()[0], (int)l.getends()[1], (int)l.getends()[2], (int)l.getends()[3], 10);
			if(Plan.intersects(l1.getends(), l.getends())) {
				is=!is;
			}
		}
		
		return is;
		
	}
	
	public Line[] getLines() {return lines;}
	public Line getLine(int where) {return lines[where];}
	
	public boolean isFilled() {return filled;}
	public void setFilled(boolean to) {this.filled=to;}
	
}
