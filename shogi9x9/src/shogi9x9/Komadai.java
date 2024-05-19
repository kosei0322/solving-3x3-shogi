package shogi9x9;

import java.util.ArrayList;

public class Komadai {
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
	
	ArrayList<String> SENTE_MOTIGOMA = new ArrayList<String>();
	ArrayList<String> GOTE_MOTIGOMA = new ArrayList<String>();
	
	public void addtoSenteKomadai(String type) {
		SENTE_MOTIGOMA.add(type);
	}
	
	public void addtoGoteKomadai(String type) {
		GOTE_MOTIGOMA.add(type);
	}
	
	public void removeFromSenteKomadai(String type) {
		for(int i = 0; i < SENTE_MOTIGOMA.size(); i++) {
			if(SENTE_MOTIGOMA.get(i).equals(type)) {
				SENTE_MOTIGOMA.remove(i);
				return;
			}
		}
	}
	
	public void removeFromGoteMotigoma(String type) {
		for(int i = 0; i < GOTE_MOTIGOMA.size(); i++) {
			if(GOTE_MOTIGOMA.get(i).equals(type)) {
				GOTE_MOTIGOMA.remove(i);
				return;
			}
		}
	}
	
	public String getListOfSenteMotigoma() {
		String ret = "";
		for(int i = 0; i < SENTE_MOTIGOMA.size(); i++) {
			ret = ret + SENTE_MOTIGOMA.get(i) + ", ";
		}
		return ret;
	}
	
	public String getListOfGoteMotigoma() {
		String ret = "";
		for(int i = 0; i < GOTE_MOTIGOMA.size(); i++) {
			ret = ret + GOTE_MOTIGOMA.get(i) + ", ";
		}
		return ret;
	}
	
}
