package shogi9x9;
import java.util.ArrayList;

public class Piece {
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
	final static String SENTE = "0";
	final static String GOTE = "1";
	boolean isOnBoard;
	boolean isUsed;       //使われているか
	int x, y;
	String type;
	
	int movableFileVector[];
	int movableRankVector[]; //移動可能方向 rank = 段, file = 筋
	
	// 駒が移動可能な X 座標と Y 座標の方向
	// 王{左, 左上, 上, 右上, 右, 右下, 下, 左下}
	final static int[] GYOKU_X = {-1, -1, 0, 1, 1, 1, 0, -1};
	final static int[] GYOKU_Y = {0, -1, -1, -1, 0, 1, 1, 1};
	// 金　{左、左上、上、右上、右、下}
	final static int[] KIN_X = {-1, -1, 0, 1, 1, 0};
	final static int[] KIN_Y = {0, -1, -1, -1, 0, 1};
	// 銀 {左上、上、右上、右下、左下}
	final static int[] GIN_X = {-1, 0, 1, 1, -1};
	final static int[] GIN_Y = {-1, -1, -1, 1, 1};
	
	// 敵駒
	final static int[] E_GYOKU_X = {-1, -1, 0, 1, 1, 1, 0, -1};
	final static int[] E_GYOKU_Y = {0, -1, -1, -1, 0, 1, 1, 1};
	final static int[] E_KIN_X = {1, 1, 0, -1, -1, 0};
	final static int[] E_KIN_Y = {0, 1, 1, 1, 0, -1};
	final static int[] E_GIN_X = {1, 0, -1, -1, 1};
	final static int[] E_GIN_Y = {1, 1, 1, -1, -1};
	
	
	//type: 種類 rank: 段 file: 筋 
	public Piece(String type, int rank, int file) {
		this.type = type;
		this.x = file;
		this.y = rank;
		this.isOnBoard = true;
		setMovableVector();
	}
	
	
	//駒の移動方向をセットする
	public void setMovableVector() {
		switch(type) {
		case GYOKU:
			movableFileVector = GYOKU_X;
			movableRankVector = GYOKU_Y;
			break;
		case KIN:
			movableFileVector = KIN_X;
			movableRankVector = KIN_Y;
			break;
		case GIN:
			movableFileVector = GIN_X;
			movableRankVector = GIN_Y;
			break;
		case E_GYOKU:
			movableFileVector = E_GYOKU_X;
			movableRankVector = E_GYOKU_Y;
			break;
		case E_KIN:
			movableFileVector = E_KIN_X;
			movableRankVector = E_KIN_Y;
			break;
		case E_GIN:
			movableFileVector = E_GIN_X;
			movableRankVector = E_GIN_Y;
			break;
		default:
			System.out.println("駒を認識できません");
			System.exit(-1);
		}
	}
	
	//駒を初期位置にセットする 左上が(1, 1)
	public void setInitialPosition() {
		switch(type) {
		case GYOKU:
			x = 1;
			y = 1;
			isOnBoard = true;
			break;
		case E_GYOKU:
			x = 3;
			y = 3;
			isOnBoard = true;
			break;
		default:
			isOnBoard = false;
			break;
		}
		
	}
	
	//駒の移動可能な位置を返す
	public ArrayList<NextMove> getMovableList(Board board) {
		ArrayList<NextMove> movableList = new ArrayList<NextMove>();
		String[][] board_ = board.board;
		boolean[][] isMovable = {
				
				{true, true, true},
				{true, true, true},
				{true, true, true}
				
		};
		
		//先手玉の場合
		if(type.equals(GYOKU)) {
			for(int i = 0; i < board_.length; i++) {
				for(int j = 0; j < board_[i].length; j++) {
					//自分の駒がいる位置には動けない
					if(getTebanFromType(board_[i][j]).equals(getTebanFromType(this.type))) {
						isMovable[i][j] = false;
					}else {
						//敵の駒の場合
						switch(board_[i][j]) {
						case E_GYOKU:
							if(isRange(i+1) && isRange(j+1)) isMovable[i+1][j+1] = false;
							if(isRange(i+1) && isRange(j)) isMovable[i+1][j] = false;
							if(isRange(i+1) && isRange(j-1)) isMovable[i+1][j-1] = false;
							if(isRange(i) && isRange(j+1)) isMovable[i][j+1] = false;
							if(isRange(i) && isRange(j-1)) isMovable[i][j-1] = false;
							if(isRange(i-1) && isRange(j+1)) isMovable[i-1][j+1] = false;
							if(isRange(i-1) && isRange(j)) isMovable[i-1][j] = false;
							if(isRange(i-1) && isRange(j-1)) isMovable[i-1][j-1] = false;
							break;
						case E_GIN:
							for(int k = 0; k < E_GIN_Y.length; k++) {
								if(isRange(E_GIN_Y[k] + i) && isRange(E_GIN_X[k] + j)) {
									isMovable[E_GIN_Y[k] + i][E_GIN_X[k] + j] = false;
								}
							}
							break;
						case E_KIN:
							for(int k = 0; k < E_KIN_Y.length; k++) {
								if(isRange(E_KIN_Y[k] + i) && isRange(E_KIN_X[k] + j)) {
									
									isMovable[E_KIN_Y[k] + i][E_KIN_X[k] + j] = false;
								}
							}
							break;
						}
					}
				}
			}
		//金銀の場合	
		}else if(type.equals(GIN) || type.equals(KIN) || type.equals(E_GIN) || type.equals(E_KIN)) {
			for(int i = 0; i < board_.length; i++) {
				for(int j = 0; j < board_[i].length; j++) {
					//味方の駒がいる座標を移動可能な座標のリストから排除する
					if(getTebanFromType(board_[i][j]).equals(getTebanFromType(this.type))) {
						isMovable[i][j] = false;
					}
				}
			}
		//後手玉の場合	
		}else if(type.equals(E_GYOKU)) {
			for(int i = 0; i < board_.length; i++) {
				for(int j = 0; j < board_.length; j++) {
					//自分の駒がいる位置には動けない
					if(getTebanFromType(board_[i][j]).equals(getTebanFromType(this.type))) {
						isMovable[i][j] = false;
					}else {
						//敵の駒の場合
						switch(board_[i][j]) {
						case GYOKU:
							if(isRange(i+1) && isRange(j+1)) isMovable[i+1][j+1] = false;
							if(isRange(i+1) && isRange(j)) isMovable[i+1][j] = false;
							if(isRange(i+1) && isRange(j-1)) isMovable[i+1][j-1] = false;
							if(isRange(i) && isRange(j+1)) isMovable[i][j+1] = false;
							if(isRange(i) && isRange(j-1)) isMovable[i][j-1] = false;
							if(isRange(i-1) && isRange(j+1)) isMovable[i-1][j+1] = false;
							if(isRange(i-1) && isRange(j)) isMovable[i-1][j] = false;
							if(isRange(i-1) && isRange(j-1)) isMovable[i-1][j-1] = false;
							break;
						case GIN:
							for(int k = 0; k < GIN_Y.length; k++) {
								if(isRange(GIN_Y[k] + i) && isRange(GIN_X[k] + j)) {
									isMovable[GIN_Y[k] + i][GIN_X[k] + j] = false;
								}
							}
							break;
						case KIN:
							for(int k = 0; k < KIN_Y.length; k++) {
								if(isRange(KIN_Y[k] + i) && isRange(KIN_X[k] + j)) {
									
									isMovable[KIN_Y[k] + i][KIN_X[k] + j] = false;
								}
							}
							break;
						}
					}
				}
			}
		}
		
		//実際に動けるかどうか
		for(int i = 0; i < movableFileVector.length; i++) {
			int nextFile = x + movableFileVector[i];
			int nextRank = y + movableRankVector[i];
			
			if(isRange(nextFile) && isRange(nextRank)) {
				if(isMovable[nextRank][nextFile]) {
					NextMove nextMove = new NextMove(type, nextFile, nextRank, getTebanFromType(this.type));
					movableList.add(nextMove);
				}
			}
		}
		
		return movableList;
	}
	
	//与えられた整数値xが0<= x < 3であるかどうかを確認する
	public boolean isRange(int x) {
		if(x >= 0 && x < 3) {
			return true;
		}else {
			return false;
		}
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
}
