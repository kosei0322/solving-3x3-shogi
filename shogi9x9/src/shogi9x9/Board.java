package shogi9x9;

import java.util.ArrayList;

public class Board {
	
	// 持ち駒　飛
	// 王 
	//   
	//　　　　　　玉
	//　持ち駒　金
	
	//                     手番/一段目/二段目/三段目/先手持ち駒/後手持ち駒
	//局面を表す文字列　初期局面 = 0/Kee/eee/eek/G/s
	
	int teban;
	
	String[][] board = {
			{EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY}
	};
	
	Komadai komadai = new Komadai();
	
	//玉
	Piece gyoku, gin, kin;

	//それぞれの駒を表す文字列
	final static String FU = "P";
	final static String E_FU = "p";
	final static String KYOU = "L";
	final static String E_KYOU = "l";
	final static String GIN = "S";
	final static String E_GIN = "s";
	final static String KIN = "G";
	final static String E_KIN = "g";
	final static String KAKU = "B";
	final static String E_KAKU = "b";
	final static String HISYA = "R";
	final static String E_HISYA = "r";
	final static String GYOKU = "K";
	final static String E_GYOKU = "k";
	final static String EMPTY = "e";
	final static String SENTE = "0";
	final static String GOTE = "1";
	
	
	//局面を表す文字列をもとに作成
	public Board(String index) {
		char[] charIndex = index.toCharArray();
		int count = 0;
		
		//盤を表す部分だけ抜き出す
		String boardString = "";
		for(int i = 0; i< index.length(); i++) {
			if(charIndex[i] == '/') {
				count++;
				continue;
			}
			
			switch(count) {
			case 0:
				//手番情報を取得
				
				teban = Character.getNumericValue(charIndex[i]);
				break;
			case 1, 2, 3:
				//盤情報を取得
				boardString = boardString + charIndex[i];
				break;
			case 4:
				komadai.addtoSenteKomadai(String.valueOf(charIndex[i]));
				break;
			case 5:
				komadai.addtoGoteKomadai(String.valueOf(charIndex[i]));
				break;
			}
		}
		
		int countv1 = 0;
		int countv2 = 0;
		for(int i = 0; i < boardString.length(); i++) {
			board[countv2][countv1] = String.valueOf(boardString.charAt(i));
			
			//テスト用
			if(String.valueOf(boardString.charAt(i)).equals(E_GYOKU)) {
				gyoku = new Piece(E_GYOKU, countv2, countv1);
			}
			if(String.valueOf(boardString.charAt(i)).equals(KIN)) {
				kin = new Piece(KIN, countv2, countv1);
			}
			
			
			countv1++;
			if(countv1 > 2) {
				countv1 = 0;
				countv2++;
			}
			
		}
		System.out.println(boardString);
		//テスト用
		/*
		 * System.out.println(boardString); ArrayList<NextMove> ar =
		 * kin.getMovableList(this); System.out.println(ar.size()); for(int l = 0; l <
		 * ar.size(); l++) { ar.get(l).inputInfo();
		 * System.out.println("------------------"); }
		 */
		 
		
	}
	
	//コンソールに盤の情報を出力する
	public void inputBoardInfo() {
		System.out.println("teban:" + teban);
		System.out.println("board:");
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println(" ");
		}
		
		System.out.println("sente motigoma:" + komadai.getListOfSenteMotigoma());
		System.out.println("gote motigoma:" + komadai.getListOfGoteMotigoma());
	}
	
	//その手番の移動可能な駒とその座標のリストを返す
	public void getMovableList(String teban) {
		ArrayList<NextMove> movableList = new ArrayList<NextMove>();
		Piece p;
		//先手の場合
		if(teban.equals(SENTE)) {
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board[i].length; j++) {
					if(getTebanFromType(board[i][j]).equals(SENTE)) {
						p = new Piece(board[i][j], i, j);
						//その駒の移動可能な座標を格納する変数
						ArrayList<NextMove> movableList_;
						movableList_ = p.getMovableList(this);
						for(int l = 0; l < movableList_.size(); l++) {
							movableList.add(movableList_.get(l));
						}
					}
				}
			}
			for(int i = 0; i < movableList.size(); i++) {
				movableList.get(i).inputInfo();
				System.out.println("------------------");
			}
		}else if(teban.equals(GOTE)) {
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board[i].length; j++) {
					if(getTebanFromType(board[i][j]).equals(GOTE)) {
						p = new Piece(board[i][j], i, j);
						//その駒の移動可能な座標を格納する変数
						ArrayList<NextMove> movableList_;
						movableList_ = p.getMovableList(this);
						for(int l = 0; l < movableList_.size(); l++) {
							movableList.add(movableList_.get(l));
						}
					}
				}
			}
			for(int i = 0; i < movableList.size(); i++) {
				movableList.get(i).inputInfo();
				System.out.println("------------------");
			}
		}
		
	}
	
	//王手チェック
	public boolean isChecked(String teban) {
		ArrayList<NextMove> enemyMovable = new ArrayList<NextMove>();
		Piece gyoku_ = new Piece(GYOKU, -1, -1);
		//先手玉を調べる場合
		if(teban.equals(SENTE)) {
			//敵の駒動きの一覧を取得する
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board.length; j++) {
					if(getTebanFromType(board[i][j]).equals(GOTE)) {
						ArrayList<NextMove> enemyMovable_ = new Piece(board[i][j], i, j).getMovableList(this);
						for(int l = 0; l < enemyMovable_.size(); l++) {
							enemyMovable.add(enemyMovable_.get(l));
						}
					}
					if(board[i][j].equals(GYOKU)) {
						gyoku_ = new Piece(board[i][j], i, j);
					}
				}
			}
		//後手玉を調べる場合	
		}else if(teban.equals(GOTE)) {
			//敵の駒動きの一覧を取得する
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board.length; j++) {
					if(getTebanFromType(board[i][j]).equals(SENTE)) {
						ArrayList<NextMove> enemyMovable_ = new Piece(board[i][j], i, j).getMovableList(this);
						for(int l = 0; l < enemyMovable_.size(); l++) {
							enemyMovable.add(enemyMovable_.get(l));
						}
					}
					if(board[i][j].equals(E_GYOKU)) {
						gyoku_ = new Piece(board[i][j], i, j);
					}
				}
			}
		}
		
		//nextmoveの中に玉の座標に移動するものがあるかを調べる
		for(int i = 0; i < enemyMovable.size(); i++) {
			if(gyoku_.x == enemyMovable.get(i).nextFile && gyoku_.y == enemyMovable.get(i).nextRank) {
				return true;
			}
		}
		return false;
		
	}
	
	//詰みチェック
	public void isMate(String teban) {
		
	}
	
	//タイプから先手番か後手番を返す
		public String getTebanFromType(String type) {
			if(Character.isUpperCase(type.toCharArray()[0])) {
				return SENTE;
			}else if(!type.equals("e")){
				return GOTE;
			}else {
				return "e";
			}
		}
		
	//駒を動かす	
	public void moveKoma(NextMove nextMove) {
		
		//boardの手番とnextmoveの手番が一致しない場合はreturnする
		if(!nextMove.teban.equals(this.teban + "")) {
			return;
		}
		
		//持ち駒の場合fromRank, fromFileは-1
		if(isRange(nextMove.fromFile) && isRange(nextMove.fromRank)) {
			board[nextMove.fromRank][nextMove.fromFile] = EMPTY;
			//駒を動かす 移動先にコマがあった場合はその駒を駒台に移動する
			if(!board[nextMove.nextRank][nextMove.nextFile].equals(EMPTY)) {
				System.out.println("ちんこ");
				//先手の場合
				if(nextMove.teban.equals(SENTE)) {
					this.komadai.addtoSenteKomadai(board[nextMove.nextRank][nextMove.nextFile]);
				//後手の場合	
				}else if(nextMove.teban.equals(GOTE)) {
					this.komadai.addtoGoteKomadai(board[nextMove.nextRank][nextMove.nextFile]);
				}
			}
			
			board[nextMove.nextRank][nextMove.nextFile] = nextMove.type;
		//持ち駒を使う場合	
		}else {
			
		}
		
		//手番を相手に渡す
		if((this.teban + "").equals(SENTE)) {
			this.teban = 1;
		}else {
			this.teban = 0;
		}
	}
	
	//与えられた整数値xが0<= x < 3であるかどうかを確認する
		public boolean isRange(int x) {
			if(x >= 0 && x < 3) {
				return true;
			}else {
				return false;
			}
		}
	
}
