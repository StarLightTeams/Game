package gameType.chuachua.entity;

public class Ball {

	/**
	 * 球
	 */
	public int d;//半径
	public double bx;//球的x坐标
	public double by;//球的y坐标
	public double ySpeed;//球的y轴速度
	public double xSpeed;//球的x轴速度
	public double xA;//球的x轴加速度
	public double yA;//球的y轴加速的
	public double degree;//球的角度
	
	public Ball() {
	}
	public Ball(int d, double bx, double by, double ySpeed, double xSpeed,
			double xA, double yA, double degree) {
		super();
		this.d = d;
		this.bx = bx;
		this.by = by;
		this.ySpeed = ySpeed;
		this.xSpeed = xSpeed;
		this.xA = xA;
		this.yA = yA;
		this.degree = degree;
	}
	
	public void move(double t) {
		bx = bx+t*xSpeed;
		by = by+t*ySpeed;
	}
	
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	public double getBx() {
		return bx;
	}
	public void setBx(double bx) {
		this.bx = bx;
	}
	public double getBy() {
		return by;
	}
	public void setBy(double by) {
		this.by = by;
	}
	public double getySpeed() {
		return ySpeed;
	}
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	public double getxSpeed() {
		return xSpeed;
	}
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	public double getxA() {
		return xA;
	}
	public void setxA(double xA) {
		this.xA = xA;
	}
	public double getyA() {
		return yA;
	}
	public void setyA(double yA) {
		this.yA = yA;
	}
	public double getDegree() {
		return degree;
	}
	public void setDegree(double degree) {
		this.degree = degree;
	}
	@Override
	public String toString() {
		return "Ball [d=" + d + ", bx=" + bx + ", by=" + by + ", ySpeed=" + ySpeed + ", xSpeed=" + xSpeed + ", xA=" + xA
				+ ", yA=" + yA + ", degree=" + degree + "]";
	}	
	
	
}
