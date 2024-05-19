package shogi9x9;

//次の一手を表現するクラス 持ち駒を使う場合はnextFile, nextRankを-1にする
public class NextMove {
	int nextFile, nextRank, fromFile, fromRank;
	String teban, type;
	public NextMove(String type, int nextFile, int nextRank, String teban, int fromFile, int fromRank) {
		this.teban = teban;
		this.nextFile = nextFile;
		this.nextRank = nextRank;
		this.type = type;
		this.fromFile = fromFile;
		this.fromRank = fromRank;
		
	}
	
	public void inputInfo() {
		System.out.println("teban:" + teban);
		System.out.println("nextrank:" + nextRank);
		System.out.println("nextfile:" + nextFile);
		System.out.println("type:" + type);
	}
}
