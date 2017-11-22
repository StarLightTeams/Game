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

	public double getBf_d() {
		return bf_d;
	}

	public void setBf_d(double bf_d) {
		this.bf_d = bf_d;
	}

	public double getBf_bx() {
		return bf_bx;
	}

	public void setBf_bx(double bf_bx) {
		this.bf_bx = bf_bx;
	}

	public double getBf_by() {
		return bf_by;
	}

	public void setBf_by(double bf_by) {
		this.bf_by = bf_by;
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
		return "BfbBall [bf_d=" + bf_d + ", bf_bx=" + bf_bx + ", bf_by=" + bf_by + ", ySpeed=" + ySpeed + ", xSpeed="
				+ xSpeed + ", xA=" + xA + ", yA=" + yA + ", degree=" + degree + "]";
	}
}
