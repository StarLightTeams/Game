package entity.agrement;

public enum Agrement implements Behaviour{

	Unknown("未知指令",0x0000),
	Heart("心跳指令", 0x0001),
	Connect("连接协议", 0x0002), 
	GuestLogin("游客登录", 0x0003),
	Login ("用户登录", 0x0004);
	
	private String name;
	private int id;
	
	// 构造方法
    private Agrement(String name, int index) {
        this.name = name;
        this.id = id;
    }
    // 接口方法
    
	public void print() {
		// TODO Auto-generated method stub
		System.out.println(this.id + ":" + this.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfo() {
		// TODO Auto-generated method stub
		 return this.name;
	}

}
