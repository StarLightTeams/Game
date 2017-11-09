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
	
	
}
