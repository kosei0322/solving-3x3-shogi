package shogi9x9;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Board {
	
	//                     手番/一段目/二段目/三段目/先手持ち駒/後手持ち駒
	//局面を表す文字列　初期局面 = 0/Kee/eee/eek/G/s
	
	int teban;
	
	String[][] board = {
			{EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY}
	};
	
	Komadai komadai = new Komadai();

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
			countv1++;
			if(countv1 > 2) {
				countv1 = 0;
				countv2++;
			}
			
		}
		
	}
	
	//局面を表す文字列を返す
	public String getIndex() {
		String ret = this.teban + "/" + board[0][0] + board[0][1] + board[0][2] + "/" + board[1][0] + board[1][1] + board[1][2] + "/" + board[2][0] + board[2][1] + board[2][2] + "/" ;
		if(komadai.SENTE_MOTIGOMA.size() >= 2) {
			if(komadai.SENTE_MOTIGOMA.get(0).equals(KIN)) {
				komadai.SENTE_MOTIGOMA.set(0, GIN);
				komadai.SENTE_MOTIGOMA.set(1, KIN);
			}
		}
		if(komadai.GOTE_MOTIGOMA.size() >=2) {
			if(komadai.GOTE_MOTIGOMA.get(0).equals(E_KIN)) {
				komadai.GOTE_MOTIGOMA.set(0, E_GIN);
				komadai.GOTE_MOTIGOMA.set(1, E_KIN);
			}
		}
		for(int i = 0; i < komadai.SENTE_MOTIGOMA.size(); i++) {
			ret = ret + komadai.SENTE_MOTIGOMA.get(i);
		}
		ret = ret + "/";
		for(int i = 0; i < komadai.GOTE_MOTIGOMA.size(); i++) {
			ret = ret + komadai.GOTE_MOTIGOMA.get(i);
		}
		
		return ret;
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
	public ArrayList<NextMove> getMovableList(String teban) {
		ArrayList<NextMove> movableList = new ArrayList<NextMove>();
		Piece p;
		//先手の場合
		if(teban.equals(SENTE)) {
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board[i].length; j++) {
					//盤上の駒を動かす手
					if(getTebanFromType(board[i][j]).equals(SENTE)) {
						p = new Piece(board[i][j], i, j);
						//その駒の移動可能な座標を格納する変数
						ArrayList<NextMove> movableList_;
						movableList_ = p.getMovableList(this);
						for(int l = 0; l < movableList_.size(); l++) {
							movableList.add(movableList_.get(l));
						}
					//持ち駒にある駒を打つ場合	
					}else if(board[i][j].equals(EMPTY)) {
						for(int s = 0; s < komadai.SENTE_MOTIGOMA.size(); s++) {
							movableList.add(new NextMove(komadai.SENTE_MOTIGOMA.get(s), j, i, SENTE, -1, -1));
						}
					}
					
				}
			}
		}else if(teban.equals(GOTE)) {
			
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board[i].length; j++) {
					//盤上の駒を動かす手
					if(getTebanFromType(board[i][j]).equals(GOTE)) {
						p = new Piece(board[i][j], i, j);
						//その駒の移動可能な座標を格納する変数
						ArrayList<NextMove> movableList_;
						movableList_ = p.getMovableList(this);
						for(int l = 0; l < movableList_.size(); l++) {
							movableList.add(movableList_.get(l));
						}
					//持ち駒を使う場合	
					}else if(board[i][j].equals(EMPTY)) {
						for(int s = 0; s < komadai.GOTE_MOTIGOMA.size(); s++) {
							movableList.add(new NextMove(komadai.GOTE_MOTIGOMA.get(s), j, i, GOTE, -1, -1));
						}
					}
				}
			}
			
			/*
			 * for(int i = 0; i < movableList.size(); i++) { movableList.get(i).inputInfo();
			 * System.out.println("------------------"); }
			 */
		}
		
		//その手を指したあとにその手番の玉の王手が解除されないものを排除する
		Board nextBoard;
		for(Iterator<NextMove> iterator = movableList.iterator(); iterator.hasNext();) {
			NextMove nextMove = iterator.next();
			/*
			 * System.out.println("---------------");
			 * System.out.println("cheking nextBoard #");
			 */
			nextBoard = new Board(this.getIndex());
			nextBoard.moveKoma(nextMove);
			/* nextBoard.inputBoardInfo(); */
			if(nextBoard.isChecked(this.teban + "")) {
				iterator.remove();
				/* System.out.println("王手, removed"); */
			}else {
				/* System.out.println("not 王手"); */
				
			}
		}
		
		
		  
		 
		
		return movableList;
		
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
					}else if(board[i][j].equals(E_GYOKU)) {
						Piece test = new Piece(E_GYOKU, i, j);
						for(int s = 0; s < test.movableFileVector.length; s++) {
							//玉が玉に王手されている場合trueを返す
							if(isRange(i + test.movableRankVector[s]) && isRange(j + test.movableFileVector[s]) &&board[i + test.movableRankVector[s]][j + test.movableFileVector[s]].equals(GYOKU)) {
								return true;
							}
						}
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
					}else if(board[i][j].equals(GYOKU)) {
						Piece test = new Piece(GYOKU, i, j);
						for(int s = 0; s < test.movableFileVector.length; s++) {
							//玉が玉に王手されている場合trueを返す
							if(isRange(i + test.movableRankVector[s]) && isRange(j + test.movableFileVector[s]) && board[i + test.movableRankVector[s]][j + test.movableFileVector[s]].equals(E_GYOKU)) {
								return true;
							}
						}
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
	public boolean isMate(String teban) {
		if(getMovableList(teban).size() == 0) return true;
		else return false;
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
			if(nextMove.teban.equals(SENTE)) {
				board[nextMove.nextRank][nextMove.nextFile] = nextMove.type;
				this.komadai.removeFromSenteKomadai(nextMove.type);
			}else if(nextMove.teban.equals(GOTE)) {
				board[nextMove.nextRank][nextMove.nextFile] = nextMove.type;
				this.komadai.removeFromGoteMotigoma(nextMove.type);
			}
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
