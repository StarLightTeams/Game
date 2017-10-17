package entity.agrement;

public enum Agrement implements Behaviour{

	Unknown("δָ֪��",0x0000),
	Heart("����ָ��", 0x0001),
	Connect("����Э��", 0x0002), 
	GuestLogin("�ο͵�¼", 0x0003),
	Login ("�û���¼", 0x0004);
	
	private String name;
	private int id;
	
	// ���췽��
    private Agrement(String name, int index) {
        this.name = name;
        this.id = id;
    }
    // �ӿڷ���
    
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
