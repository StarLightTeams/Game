package gameType.chuachua.entity;

public class BoardProps {
	public String propsId; 	//道具id
	public String propsName;//道具名字
	public String propsInfo;//道具信息
	
	public BoardProps() {
		super();
	}

	public BoardProps(String propsId, String propsName, String propsInfo) {
		super();
		this.propsId = propsId;
		this.propsName = propsName;
		this.propsInfo = propsInfo;
	}

	public String getPropsId() {
		return propsId;
	}

	public void setPropsId(String propsId) {
		this.propsId = propsId;
	}

	public String getPropsName() {
		return propsName;
	}

	public void setPropsName(String propsName) {
		this.propsName = propsName;
	}

	public String getPropsInfo() {
		return propsInfo;
	}

	public void setPropsInfo(String propsInfo) {
		this.propsInfo = propsInfo;
	}

	@Override
	public String toString() {
		return "BoardProps [propsId=" + propsId + ", propsName=" + propsName + ", propsInfo=" + propsInfo + "]";
	}
	
}
