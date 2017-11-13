package gameType.chuachua.entity.bfbData;

/**
 * 百分比板
 */
public class BfbBoard {

	public double bf_width; //宽
	public double bf_height;//高
	public double bf_locX;  //位置x
	public double bf_locY;  //位置y
	public double ySpeed;//移动速度
	public double yA;	  //移动加速度
	
	public BfbBoard() {
		super();
	}

	public BfbBoard(double bf_width, double bf_height, double bf_locX,
			double bf_locY, double bf_ySpeed, double bf_yA) {
		super();
		this.bf_width = bf_width;
		this.bf_height = bf_height;
		this.bf_locX = bf_locX;
		this.bf_locY = bf_locY;
		this.ySpeed = bf_ySpeed;
		this.yA = bf_yA;
	}
	
	
}
