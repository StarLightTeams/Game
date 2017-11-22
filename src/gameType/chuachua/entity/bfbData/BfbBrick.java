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

	public double getBg_height() {
		return bg_height;
	}

	public void setBg_height(double bg_height) {
		this.bg_height = bg_height;
	}

	public double getBg_width() {
		return bg_width;
	}

	public void setBg_width(double bg_width) {
		this.bg_width = bg_width;
	}

	public double getBg_locX() {
		return bg_locX;
	}

	public void setBg_locX(double bg_locX) {
		this.bg_locX = bg_locX;
	}

	public double getBg_locY() {
		return bg_locY;
	}

	public void setBg_locY(double bg_locY) {
		this.bg_locY = bg_locY;
	}

	public String getbPropsId() {
		return bPropsId;
	}

	public void setbPropsId(String bPropsId) {
		this.bPropsId = bPropsId;
	}

	public int getHardness() {
		return hardness;
	}

	public void setHardness(int hardness) {
		this.hardness = hardness;
	}

	@Override
	public String toString() {
		return "BfbBrick [bg_height=" + bg_height + ", bg_width=" + bg_width + ", bg_locX=" + bg_locX + ", bg_locY="
				+ bg_locY + ", bPropsId=" + bPropsId + ", hardness=" + hardness + "]";
	}
	
}
