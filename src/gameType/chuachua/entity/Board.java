package gameType.chuachua.entity;

public class Board {

	public int width; //宽
	public int height;//高
	public double locX;  //位置x
	public double locY;  //位置y
	public int ySpeed;//移动速度
	public int yA;	  //移动加速度
	
	public Board() {
		super();
	}

	public Board(int width, int height, double locX, double locY, int ySpeed,
			int yA) {
		super();
		this.width = width;
		this.height = height;
		this.locX = locX;
		this.locY = locY;
		this.ySpeed = ySpeed;
		this.yA = yA;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getLocX() {
		return locX;
	}

	public void setLocX(double locX) {
		this.locX = locX;
	}

	public double getLocY() {
		return locY;
	}

	public void setLocY(double locY) {
		this.locY = locY;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public int getyA() {
		return yA;
	}

	public void setyA(int yA) {
		this.yA = yA;
	}
}
