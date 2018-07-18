package test;

public class Pojo {
	
	String desc;
	
	String asd;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAsd() {
		return asd;
	}

	public void setAsd(String asd) {
		this.asd = asd;
	}
	
	public static void main(String[] args){
		try {
			Class c;
			c = Class.forName("Pojo");
			System.out.println(c.toString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	

}
