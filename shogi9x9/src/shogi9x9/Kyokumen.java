package shogi9x9;

import java.util.ArrayList;
import java.util.Iterator;

public class Kyokumen {
	//全局面を格納する可変長配列
	ArrayList<String> al = new ArrayList<String>();
	int[] state;
	int[] tesuu;
	Board bo;
	//1 2 3
	//4 5 6
	  
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
	
	final static int SENTE_WIN = 0;
	final static int GOTE_WIN = 1;
	final static int DRAW = 2;
	final static int NOT_FINALLIZED = 3;
	
	int goteWinCount = 0;
	int senteWinCount = 0;
	
	public void generateKyokumen() {
		
		String[] board_  = new String[9];
		int gyokunum;
		int e_gyokunum;
		int kinnum;
		int ginnum;
		
		//盤面上の駒が玉二枚のみの場合
		for(int i = 0; i < board_.length; i++) {
			gyokunum = i;
			for(int j = 0; j < 9; j++) {
				if(i != j) {
					e_gyokunum = j;
					//局面の要素をEMPTYで埋める
					for(int l = 0; l < board_.length; l++) {
						board_[l] = EMPTY;
					}
					board_[gyokunum] = GYOKU;
					board_[e_gyokunum] = E_GYOKU;
					
					//先手の持ち駒が金、後手の持ち駒が銀
					al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/G/s");
					al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/G/s");
					
					//先手の持ち駒が銀、後手の持ち駒が金
					al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/S/g");
					al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/S/g");
					
					//先手の持ち駒が金銀
					al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/SG/");
					al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/SG/");
					
					//後手の持ち駒が金銀
					al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//sg");
					al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//sg");
				}
			}
		}
		
		
		//盤面上の駒が玉二枚、金のとき
		for(int i = 0; i < board_.length; i++) {
			gyokunum = i;
			for(int j = 0; j < board_.length; j++) {
				if(i != j) {
					e_gyokunum = j;
					for(int k = 0; k < board_.length; k++) {
						if(k != j && k !=i) {
							kinnum = k;
							//局面の要素をEMPTYで埋める
							for(int l = 0; l < board_.length; l++) {
								board_[l] = EMPTY;
							}
							
							board_[gyokunum] = GYOKU;
							board_[e_gyokunum] = E_GYOKU;
							
							//金が先手の駒の場合
							board_[kinnum] = KIN;
							
							//先手の持ち駒が銀
							al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/S/");
							al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/S/");
							
							//後手の持ち駒が銀
							al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//s");
							al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//s");
							
							//金が後手の駒の場合
							board_[kinnum] = E_KIN;
							
							//先手の持ち駒が銀
							al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/S/");
							al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/S/");
							
							//後手の持ち駒が銀
							al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//s");
							al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//s");
							
						}
					}
				}
			}
			
			
		}
		
		
		//盤面上の駒が玉二枚、銀のとき
				for(int i = 0; i < board_.length; i++) {
					gyokunum = i;
					for(int j = 0; j < board_.length; j++) {
						if(i != j) {
							e_gyokunum = j;
							for(int k = 0; k < board_.length; k++) {
								if(k != j && k !=i) {
									ginnum = k;
									//局面の要素をEMPTYで埋める
									for(int l = 0; l < board_.length; l++) {
										board_[l] = EMPTY;
									}
									
									board_[gyokunum] = GYOKU;
									board_[e_gyokunum] = E_GYOKU;
									
									//銀が先手の駒の場合
									board_[ginnum] = GIN;
									
									//先手の持ち駒が金
									al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/G/");
									al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/G/");
									
									//後手の持ち駒が金
									al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//g");
									al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//g");
									
									//金が後手の駒の場合
									board_[ginnum] = E_GIN;
									
									//先手の持ち駒が銀
									al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/G/");
									al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "/G/");
									
									//後手の持ち駒が銀
									al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//g");
									al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//g");
									
								}
							}
						}
					}
					
					
				}
				
		//盤面上の駒が玉二枚、金銀のとき
		for(int i = 0; i < board_.length; i++) {
			gyokunum = i;
			for(int j = 0; j < board_.length; j++) {
				if(i != j) {
					e_gyokunum = j;
					for(int k = 0; k < board_.length; k++) {
						if(k != i && k != j) {
							ginnum = k;
							for(int l = 0; l < board_.length; l++) {
								if(l != i && l != j && l != k) {
									kinnum = l;
									
									//局面の要素をEMPTYで埋める
									for(int m = 0; m < board_.length; m++) {
										board_[m] = EMPTY;
									}
									
									board_[gyokunum] = GYOKU;
									board_[e_gyokunum] = E_GYOKU;
									
									//金銀が先手の駒の場合
									board_[ginnum] = GIN;
									board_[kinnum] = KIN;
									
									al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//");
									al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//");
									
									//金銀が後手の駒の場合
									board_[ginnum] = E_GIN;
									board_[kinnum] = E_KIN;
									
									al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//");
									al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//");
									
									//金が先手、銀が後手の場合
									board_[kinnum] = KIN;
									board_[ginnum] = E_GIN;
									
									al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//");
									al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//");
									
									//金が後手、銀が先手の場合
									board_[kinnum] = E_KIN;
									board_[ginnum] = GIN;
									
									al.add("0/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//");
									al.add("1/" + board_[0] + board_[1] + board_[2] + "/" +  board_[3] + board_[4] + board_[5] + "/" + board_[6] + board_[7] + board_[8] + "//");
								}
							}
						}
					}
				}
			}
		}
		
		
		
		
		
		//コンソールに出力
		Board board ;
		for(int i = 0; i < 100; i++) {
			board = new Board(al.get(i + 30000));
			if(board.isMate(board.teban + "")) {
				System.out.println("index:" + (i + 20000));
				board.inputBoardInfo();
				System.out.println("詰み");
				System.out.println("------------------------------------"); 
			}
		}
		
	}
	
	
	//同時に二つの駒から王手を受けている、双方の玉が王手を受けているなどの違法な局面を配列から削除する
	public void removeIllegalBoard() {
		int count = 0;
		for(Iterator<String> iterator = al.iterator(); iterator.hasNext();) {
			
			String boardIndex = iterator.next();
			Board board = new Board(boardIndex);
			String notTeban;
			if(board.teban == 0) {
				notTeban = GOTE;
			}else{
				notTeban = SENTE;
			}
			
			//手番じゃない側の玉が王手を受けている　同時に二つの駒から王手を受けているなどの違法な局面を排除する
			if(board.isChecked(SENTE) && board.isChecked(GOTE) || board.isChecked(notTeban)) {
				count++;
				board.inputBoardInfo();
				System.out.println("state: removed");
				System.out.println("-----------------------");
				iterator.remove();
			}
		}
		System.out.println("removed " + count + " illegal boards");
	}
	
	//詰んでいる局面を探し、記録する
	public void findMateBoard() {
		state = new int[al.size()];
		tesuu = new int[al.size()];
		for(int i = 0; i < al.size(); i++) {
			Board board = new Board(al.get(i));
			if(board.isMate(board.teban + "")) {
				board.inputBoardInfo();
				System.out.println("index: " + i);
				if(board.teban == 0) {
					System.out.println("state: 後手勝ち");
					goteWinCount++;
					state[i] = GOTE_WIN;
					tesuu[i] = 0;
				}
				else if(board.teban == 1) {
					System.out.println("state: 先手勝ち");
					senteWinCount++;
					state[i] = SENTE_WIN;
					tesuu[i] = 0;
				}
				System.out.println("--------------");
			}else {
				state[i] = NOT_FINALLIZED;
				tesuu[i] = -1;
			}
		}
		
		System.out.println("総局面数: " + al.size());
		System.out.println("founded " + senteWinCount + " boards which sente win");
		System.out.println("founded " + goteWinCount + " boards wich gote win");
		 
		
	}
	
	//後退解析
	public void kaiseki() {
	boolean flag = false;
		while(!flag) {
			int newSenteWinCount = 0;
			int newGoteWinCount = 0;
			for(int i = 0; i < al.size(); i++) {
			//未確定の局面
			if(state[i] != GOTE_WIN && state[i] != SENTE_WIN) {
				Board board = new Board(al.get(i));
				int teban = board.teban;
				ArrayList<NextMove> movableList = board.getMovableList(board.teban + "");
				//遷移先の負け局面の数
				int oppomentWinCount = 0;
				
				//遷移可能な局面をすべて調べる
				for(int j = 0; j < movableList.size(); j++) {
					board = new Board(al.get(i));
					board.moveKoma(movableList.get(j));
					String boardString = board.getIndex();
					int index = al.indexOf(boardString);
					
					//先手番の場合
					if(teban == 0) {
						//遷移先に先手勝ち局面が一つでもあればその局面も先手勝ちとする
						try {
						if(state[index] == SENTE_WIN) {
							state[i] = SENTE_WIN;
							newSenteWinCount++;
						}else if(state[index] == GOTE_WIN) {
							oppomentWinCount++;
						}
						}catch(IndexOutOfBoundsException e) {
							System.out.println(boardString);
						}
					}else if(teban == 1) {
						//遷移先に後手勝ち局面が一つでもあればその局面も後手勝ちとする
						try {
						if(state[index] == GOTE_WIN) {
							state[i] = GOTE_WIN;
							newGoteWinCount++;
						}else if(state[index] == SENTE_WIN) {
							oppomentWinCount++;
						}
						}catch(IndexOutOfBoundsException e) {
							System.out.println(boardString);
						}
					}
				}
				
				//oppomentWinCountとmovableList.size()が等しい場合、つまり遷移先がすべて負け局面だった場合、その局面を相手の勝ちとする
				if(oppomentWinCount == movableList.size()) {
					if(teban == 0) {
						state[i] = GOTE_WIN;
						newGoteWinCount++;
					}else if(teban == 1) {
						state[i] = SENTE_WIN;
						newSenteWinCount++;
					}
				}
				
				int min = Integer.MAX_VALUE;
				if(state[i] == GOTE_WIN || state[i] == SENTE_WIN) {
					for(int j = 0; j < movableList.size(); j++) {
						board = new Board(al.get(i));
						board.moveKoma(movableList.get(j));
						String boardString = board.getIndex();
						int index = al.indexOf(boardString);
						if(tesuu[index] != -1) {
							min = Math.min(tesuu[index], min);
						}
					}
					tesuu[i] = min + 1;
				}
			}
		}
			if(newSenteWinCount == 0 && newGoteWinCount == 0) {
				flag = true;
			}
		}
		
		
		Board firstBoard = new Board("0/Kee/eee/eek/G/s");
		ArrayList<NextMove> br = firstBoard.getMovableList(firstBoard.teban + "");
		for(int i = 0; i < br.size(); i++) {
			br.get(i).inputInfo();
			firstBoard = new Board("0/Kee/eee/eek/G/s");
			firstBoard.moveKoma(br.get(i));
			System.out.println(state[al.indexOf(firstBoard.getIndex())]);
			
			System.out.println("----------------");
			
		}
		System.out.println(state[al.indexOf("0/Kee/eee/eek/S/g")]);
		System.out.println(state[al.indexOf("1/kee/eee/eeK/SG/")]);
		System.out.println(state[al.indexOf("1/kee/eee/SGK//")]);
		
	}
	/*
	 * public Board madeBoardFromIndex(String index) { Board ret; return ret;
	 * 
	 * }
	 */
	
	
}
