package samsung.ejemplos;

public class RateInfo {
	private String currency;
	private double rate;
	private int flag;
	
	public RateInfo() {
		this.currency = "";
		this.rate = (double) 0;
		this.flag = 0;
	}
	
	public RateInfo(String currency, double rate, int flag) {
		this.currency = currency;
		this.rate = rate;
		this.flag = flag;
	}
	
	public RateInfo(String currency, String rate, String flag) {
		this.currency = currency;
		this.rate = Double.parseDouble(rate);
		this.flag = Integer.parseInt(flag);
	}
	
	public String getCurrency(){
		return currency;
	}
	
	public double getRate(){
		return rate;
	}
	
	public int getFlag(){
		return flag;
	}
	
	public void setCurrency(String currency){
		this.currency = currency;
	}
	
	public void setRate(double rate){
		this.rate = rate;
	}
	
	public void setFlag(int flag){
		this.flag = flag;
	}
}
