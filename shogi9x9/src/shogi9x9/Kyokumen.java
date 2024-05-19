package shogi9x9;

import java.util.ArrayList;

public class Kyokumen {
	//全局面を格納する可変長配列
	ArrayList<String> al = new ArrayList<String>();
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
		for(int i = 0; i < 10500; i++) {
			board = new Board(al.get(i));
			System.out.println("index:" + i);
			board.inputBoardInfo();
			System.out.println("------------------------------------"); 
		}
		
		System.out.println(al.size());
		board = new Board(al.get(10000));
		
		/*
		 * for(int i = 0; i < 100; i++) { board = new Board(al.get(i + 10000));
		 * System.out.println("先手玉は王手か:" + board.isChecked(SENTE));
		 * System.out.println("後手玉は王手か:" + board.isChecked(GOTE));
		 * board.inputBoardInfo();
		 * System.out.println("------------------------------------"); }
		 */
		/*
		 * for(int i = 0; i < 100; i++) { board = new Board(al.get(i));
		 * board.inputBoardInfo(); System.out.println("----------------"); }
		 */
		
		/*
		 * board.inputBoardInfo(); board.isChecked(0);
		 */
		
	}
	
	
	//同時に二つの駒から王手を受けている、双方の玉が王手を受けているなどの違法な局面を配列から削除する
	public void removeIllegalBoard() {
		
	}
	/*
	 * public Board madeBoardFromIndex(String index) { Board ret; return ret;
	 * 
	 * }
	 */
	
	
}
