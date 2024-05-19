package shogi9x9;

public class NextMove {
	int nextFile, nextRank;
	String teban, type;
	public NextMove(String type, int nextFile, int nextRank, String teban) {
		this.teban = teban;
		this.nextFile = nextFile;
		this.nextRank = nextRank;
		this.type = type;
		
	}
	
	public void inputInfo() {
		System.out.println("teban:" + teban);
		System.out.println("nextrank:" + nextRank);
		System.out.println("nextfile:" + nextFile);
		System.out.println("type:" + type);
	}
}
