package gameType.chuachua.entity.bfbData;

/**
 * 百分比砖
 */
public class BfbBrick {

	public double bg_height;//高
	public double bg_width;//宽
	public double bg_locX;//x
	public double bg_locY;//y
	public String bPropsId;//砖块道具id
	public int hardness;//硬度
	
	public BfbBrick() {
		super();
	}

	public BfbBrick(double bg_height, double bg_width, double bg_locX,
			double bg_locY, String bPropsId, int hardness) {
		super();
		this.bg_height = bg_height;
		this.bg_width = bg_width;
		this.bg_locX = bg_locX;
		this.bg_locY = bg_locY;
		this.bPropsId = bPropsId;
		this.hardness = hardness;
	}
	
}
