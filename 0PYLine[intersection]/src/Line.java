import java.awt.Color;
import java.awt.Graphics;

public class Line {

	private float[] ends;
	private double angle;
	private Color color;
	
	public Line(float points,float points2,float points3,float points4,Color color) {
		this.ends=new float[] {points,points2,points3,points4};
		this.angle=Plan.findAngle(points, points2, points3, points4);
		this.color=color;
	}
	public void update() {
		this.angle=Plan.findAngle(ends[0], ends[1], ends[2], ends[3]);
	}
	public void render(Graphics g) {
		g.setColor(color);
		g.drawLine((int)ends[0], (int)ends[1], (int)ends[2], (int)ends[3]);
	}
	public void render(Graphics g,Color color) {
		g.setColor(color);
		g.drawLine((int)ends[0], (int)ends[1], (int)ends[2], (int)ends[3]);
	}
	public float[] getends() {
		return ends;
	}
	public void setends(float[] to) {
		this.ends=to;
	}
	public void setends(int where,float to) {
		ends[where]=to;
	}
	public Color getColor() {return color;}
	public void setColor(Color to) {this.color=to;}
}
