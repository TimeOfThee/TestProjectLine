import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Plan {
	
	private KeyManager kM;
	private MouseManager mM;

	public static ArrayList<int[]> pivot=new ArrayList<int[]>();
	
	//put variables here
	ArrayList<Line> lines;
	ArrayList<Poligon> polis,polis2;
	static ArrayList<double[]> test;
	int mode=0;
	
	boolean tog=false,tog2=true;
	static boolean debug=false;
	int temone=0;
	Line testL=new Line(0,0,0,0,Color.green.darker());
	Color clorG=new Color(150,255,200);
	
	public Plan(KeyManager km,MouseManager mm) {
		this.kM=km;
		this.mM=mm;
		lines=new ArrayList<Line>();
		lines.add(new Line(200,100,500,100,Color.gray));
		lines.add(new Line(700,300,100,200,Color.gray));
		polis=new ArrayList<Poligon>();
		polis2=new ArrayList<Poligon>();
		
		float[][] points=new float[8][2];
		points[0]=new float[] {100,100};
		points[1]=new float[] {499.9f,100};
		points[2]=new float[] {550,200};
		points[3]=new float[] {300,200};
		points[4]=new float[] {350,300};
		points[5]=new float[] {449.9f,300};
		points[6]=new float[] {600,500};
		points[7]=new float[] {200.1f,500};
		polis.add(new Poligon( points ));

		points=new float[4][2];
		points[0]=new float[] {100,600};
		points[1]=new float[] {300,600};
		points[2]=new float[] {800,300};
		points[3]=new float[] {800,200};
		polis.add(new Poligon( points ));
		
		points=new float[10][2];
		points[0]=new float[] {560,30};
		points[1]=new float[] {700,30};
		points[2]=new float[] {700,120};
		points[3]=new float[] {670,120};
		points[4]=new float[] {670,60};
		points[5]=new float[] {590,60};
		points[6]=new float[] {590,90};
		points[7]=new float[] {671,90};
		points[8]=new float[] {670,120};
		points[9]=new float[] {560,120};
		polis.add(new Poligon( points ));
	}
	
	public void update() {
		//update variables here
		
		if(kM.kW) {
			mode=0;
		}else if(kM.kD) {
			mode=1;
		}else if(kM.kS) {
			mode=2;
		}
		if(kM.kA) {
			if(tog2) {
				mode=3;
				polis2=new ArrayList<Poligon>();
				for(int b=0;b<2;b++) {
					float[][] points=new float[3+(int)(Math.random()*2)][2];
					for(int a=0;a<points.length;a++) {
						points[a]=new float[] {(float)(Math.random()*600)+100,(float)(Math.random()*400)+100};
					}
					polis2.add(new Poligon(points));
				}
			}
			tog2=false;
		}else {
			tog2=true;
		}
		
		if(kM.kSP)debug=true;else debug=false;
		
		testL.setends(0, mM.getMX());
		testL.setends(1, mM.getMY());
		testL.setends(2, mM.getMX());
		
		test=new ArrayList<double[]>();
		
		switch(mode) {
		case 0:
			if(kM.kSP) {
				if(tog) {
					temone++;
					if(temone>=lines.size())temone=0;
				}
				tog=false;
			}
			else {
				tog=true;
			}
			
			lines.get(temone).setends(2, mM.getMX());
			lines.get(temone).setends(3, mM.getMY());
			for(Line l:lines) {
				for(Line m:lines) {
					if(l==m)continue;
					intersects(l.getends(),m.getends());
				}
			}
			break;
		case 1:
			for(Poligon p:polis) {
				p.setFilled(false);
				boolean is=false;
				for(Line l:p.getLines()) {
					if(intersects(l.getends(),testL.getends())) {
						is=!is;
					}
				}
				if(is)p.setFilled(true);
			}
			break;
		case 2:
			if(kM.kSP) {
				if(tog) {
					temone++;
					if(temone>=lines.size())temone=0;
				}
				tog=false;
			}
			else {
				tog=true;
			}
			
			for(Line l:lines) {
				intersects(l.getends(),new float[] {mM.getMX(),mM.getMY(),mM.getMX(),0});
			}
			break;
		case 3:
			for(Poligon p:polis2) {
				p.setFilled(false);
				boolean is=false;
				for(Line l:p.getLines()) {
					if(intersects(l.getends(),testL.getends())) {
						is=!is;
					}
				}
				if(is)p.setFilled(true);
			}
			break;
		default:
			mode=0;
		}
		
		for(int a=pivot.size()-1;a>=0;a--) {
			if(pivot.get(a)[4]<=0) {
				pivot.remove(pivot.get(a));
			}else {pivot.get(a)[4]--;}
		}
	}
	public void render(Graphics g) {
		//draw here
		
		g.setColor(new Color(50,200,100,60));
		g.fillRect(10, 10, 45, 30);
		g.setColor(Color.black);
		g.fillRect(10, 10, 15, 15);
		g.fillRect(40, 10, 15, 15);
		g.setColor(new Color(50,200,50).darker());
		g.drawRect(10, 10, 45, 30);
		g.setColor(new Color(50,200,50));
		g.drawRect(10, 25, 45, 15);
		g.drawRect(25, 10, 15, 30);
		g.drawString("W", 13+(15*1), 22+(15*0));
		g.drawString("A", 14+(15*0), 22+(15*1));
		g.drawString("S", 14+(15*1), 22+(15*1));
		g.drawString("D", 14+(15*2), 22+(15*1));
		
		
		switch(mode) {
		case 0:
			g.setColor(new Color(50,200,100,60));
			g.fillRect(10+(15*1), 10+(15*0), 15, 15);
			for(Line l:lines) {
				l.render(g);
			}
			for(double[] a:test){
				g.setColor(new Color(100,200,100));
				g.drawOval((int)(a[0]-5), (int)(a[1]-5), 10, 10);
			}
			break;
		case 1:
			g.setColor(new Color(50,200,100,60));
			g.fillRect(10+(15*2), 10+(15*1), 15, 15);
			
			g.setColor(Color.white);
			g.drawOval(mM.getMX()-2, mM.getMY()-2, 4, 4);
			testL.render(g);
			for(Poligon p:polis) {
				
				p.render(g, clorG,false);
				if(p.isFilled()) {
					p.render(g, new Color(50,200,100,60), true);
				}
			}
			for(double[] a:test) {
				g.setColor(new Color(100,200,100));
				g.drawOval((int)(a[0]-5), (int)(a[1]-5), 10, 10);
			}
			g.setColor(Color.green);
			g.drawString(mM.getMX()+" "+mM.getMY(), mM.getMX()-22, mM.getMY()-4);
			break;
		case 2:
			g.setColor(new Color(50,200,100,60));
			g.fillRect(10+(15*1), 10+(15*1), 15, 15);
			
			for(Line l:lines) {
				l.render(g);
			}
			for(double[] a:test){
				g.setColor(new Color(100,200,100));
				g.drawOval((int)(a[0]-5), (int)(a[1]-5), 10, 10);
			}
			g.drawLine(mM.getMX(), mM.getMY(), mM.getMX(), 0);
			break;
		case 3:
			g.setColor(new Color(50,200,100,60));
			g.fillRect(10+(15*0), 10+(15*1), 15, 15);
			
			g.setColor(Color.white);
			g.drawOval(mM.getMX()-2, mM.getMY()-2, 4, 4);
			testL.render(g);
			for(Poligon p:polis2) {
				
				p.render(g, clorG,false);
				if(p.isFilled()) {
					p.render(g, new Color(50,200,100,60), true);
				}
			}
			for(double[] a:test) {
				g.setColor(new Color(100,200,100));
				g.drawOval((int)(a[0]-5), (int)(a[1]-5), 10, 10);
			}
			g.setColor(Color.green);
			g.drawString(mM.getMX()+" "+mM.getMY(), mM.getMX()-22, mM.getMY()-4);
			break;
		default:
			
		}
		
		for(int[] l:pivot) {
			int tran=25*l[4]; 
			if(tran>255)tran=255;
			g.setColor(new Color(150,150,150,tran));
			if(l.length==5) {
				g.drawLine(l[0],l[1], l[2], l[3]);
			}
			else if(l.length==6) {
				if(l[5]==0) {
					g.drawOval(l[0], l[1], l[2], l[3]);
				}else {
					g.fillOval(l[0], l[1], l[2], l[3]);
				}
			}
			else if(l.length==7) {
				g.drawArc(l[0], l[1], l[2], l[3], l[5], l[6]);
			}
		}
		
	}
	public static boolean intersects(float[] l1,float[] l2) {
		if(debug) {
			System.out.println("="+l1[0]+" "+l1[1]+" "+l1[2]+" "+l1[3]);
			System.out.println(" "+l2[0]+" "+l2[1]+" "+l2[2]+" "+l2[3]);
		}
		if((l1[0] > l2[0] && 
			l1[0] > l2[2] && 
			l1[2] > l2[0] && 
			l1[2] > l2[2])
				||
		   (l1[0] < l2[0] && 
			l1[0] < l2[2] && 
			l1[2] < l2[0] && 
			l1[2] < l2[2])) {
			return false;
		}
		if((l1[1] > l2[1] && 
			l1[1] > l2[3] && 
			l1[3] > l2[1] && 
			l1[3] > l2[3])
				||
		   (l1[1] < l2[1] && 
			l1[1] < l2[3] && 
			l1[3] < l2[1] && 
			l1[3] < l2[3])) {
			return false;	
		}
		
		/*
			y= (m x)+b
			y= (sin/cos)x+b
		  >[b]= yp-(sin/cos)xp
			
			f(r)=(sin/cos)r + (yp -(sin/cos)xp)
			`
			(sin/cos1)r + (yp1 -(sin/cos1)xp1) = (sin/cos2)r + (yp2 -(sin/cos2)xp2)
			(sin/cos1)r - (sin/cos2)r = (yp2 -(sin/cos2)xp2) - (yp1 -(sin/cos1)xp1)
			
			 		(yp2 -(sin/cos2)xp2) - (yp1 -(sin/cos1)xp1)
		   >[r] =	-------------------------------------------
							(sin/cos1) - (sin/cos2)	
			
			y= (sin/cos)[r]+[b]
		*/
		
		double sl1=(findCosSin(findAngle(l1[0],l1[1],l1[2],l1[3]),1)[1])
				/(findCosSin(findAngle(l1[0],l1[1],l1[2],l1[3]),1)[0])
			,sl2=(findCosSin(findAngle(l2[0],l2[1],l2[2],l2[3]),1)[1])
				/(findCosSin(findAngle(l2[0],l2[1],l2[2],l2[3]),1)[0]);
		
		double ix=( (l2[1] -sl2*l2[0]) - (l1[1] -sl1*l1[0]) ) / ( sl1-sl2 );
		double iy=sl1*ix+l1[1]-(sl1*l1[0]);
		
		//if(isBetween(ix,l2[0],l2[2]) && isBetween(iy,l2[1],l2[3]) && isBetween(ix,l1[0],l1[2]) && isBetween(iy,l1[1],l1[3])) {
		if((isBetween(ix,l2[0],l2[2]) && isBetween(ix,l1[0],l1[2])) && (isBetween(iy,l2[1],l2[3]) && isBetween(iy,l1[1],l1[3]))) {
			test.add(new double[] {ix,iy,1});
			//pivotLine((int)(ix-3),(int)(iy-3),(int)(ix+3),(int)(iy+3),10);
			//pivotLine((int)(ix-3),(int)(iy+3),(int)(ix+3),(int)(iy-3),10);
			return true;
		}else {
			return false;
		}
	}
	public static boolean isBetween(double look,double start,double end) {
		if(start<=end) {
			if(look>=start-0.1 && look<=end+0.1)return true;
			return false;
		}
		else {
			if(look>=end-0.1 && look<=start+0.1)return true;
			return false;
		}
	}
	//------------------------------------------------------------------------------------------------------
	public static double findAngle(float xs,float ys,float xe,float ye) {
		double ang;
		if(xe-xs!=0) {
			ang= Math.toDegrees( Math.atan((ye-ys)/(xe-xs)) );
			if(xe<xs) {ang+=180;}
		}
		else {
			if(ye-ys<0)ang=270;else ang=90;
		}
		return ang;
	}
	public static double findDif(double angle,double angt) {
		double dif1=0,dif2=0;
		
		dif1=angt-angle;
		dif2=dif1-(360*(dif1/Math.abs(dif1)));
		
		if(dif1>360) dif1-=360;
		else if(dif1<-360)dif1+=360;
		
		if(dif2>360) dif2-=360;
		else if(dif2<-360)dif2+=360;
		
		if(Math.abs(dif2)<Math.abs(dif1)) {
			return dif2;
		}else {
			return dif1;
		}
	}
	public static double[] findCosSin(double ang,double dis) {
		double cos=Math.cos( Math.toRadians(ang) )*dis;
		double sin=Math.sin( Math.toRadians(ang) )*dis;
		return new double[] {cos,sin};
	}
	public static void pivotLine(int x,int y,int ex, int wy,int health) {
		pivot.add(new int[] {x,y,ex,wy,health});
	}
}
