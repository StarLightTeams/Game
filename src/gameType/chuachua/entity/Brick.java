package gameType.chuachua.entity;

public class Brick {
	public int height;//高
	public int width;//宽
	public double locX;//x
	public double locY;//y
	public String bPropsId ;//砖块道具id
	public int hardness;//硬度
	
	public Brick() {
	}
	public Brick(int height, int width, double locX, double locY, String bPropsId, int hardness) {
		super();
		this.height = height;
		this.width = width;
		this.locX = locX;
		this.locY = locY;
		this.bPropsId = bPropsId;
		this.hardness = hardness;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
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
		return "Brick [height=" + height + ", width=" + width + ", locX=" + locX + ", locY=" + locY + ", bPropsId="
				+ bPropsId + ", hardness=" + hardness + "]";
	}
	
}
