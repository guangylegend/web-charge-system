package Common;

public class IP {
	String address;
	public IP(String _ip) {
		address = new String(_ip);
	}
	@Override
	public String toString() {
		return address;
	}
}
