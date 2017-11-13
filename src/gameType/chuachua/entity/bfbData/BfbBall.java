package gameType.chuachua.entity.bfbData;

/** 
 *百分比球
 */
public class BfbBall {

	public double bf_d;//半径
	public double bf_bx;//球的x坐标
	public double bf_by;//球的y坐标
	public double ySpeed;//球的y轴速度
	public double xSpeed;//球的x轴速度
	public double xA;//球的x轴加速度
	public double yA;//球的y轴加速的
	public double degree;//球的角度
	
	public BfbBall() {
		super();
	}

	public BfbBall(double bf_d, double bf_bx, double bf_by, double ySpeed,
			double xSpeed, double xA, double yA, double degree) {
		super();
		this.bf_d = bf_d;
		this.bf_bx = bf_bx;
		this.bf_by = bf_by;
		this.ySpeed = ySpeed;
		this.xSpeed = xSpeed;
		this.xA = xA;
		this.yA = yA;
		this.degree = degree;
	}

	
}
