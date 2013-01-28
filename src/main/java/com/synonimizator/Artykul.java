package com.synonimizator;
import java.util.Arrays;


public class Artykul {
	private String tytulSynonim;
	private String tytulBazowy;
	private String tekstSynonim;
	private String tekstBazowy;
	private long artykulId;
	private Projekt projekt;
	
	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}

	public long getArtykulId() {
		return artykulId;
	}

	public void setArtykulId(long artykulId) {
		this.artykulId = artykulId;
	}

	public Artykul(){
		
	}
	
	public Artykul(String tytul,String tekst){
		this.tytulBazowy = tytul;
		this.tekstBazowy = tekst;
	}
	
	public String getTytulSynonim() {
		return tytulSynonim;
	}

	public void setTytulSynonim(String tytulSynonim) {
		this.tytulSynonim = tytulSynonim;
	}

	public String getTytulBazowy() {
		return tytulBazowy;
	}

	public void setTytulBazowy(String tytulBazowy) {
		this.tytulBazowy = tytulBazowy;
	}

	public String getTekstSynonim() {
		return tekstSynonim;
	}

	public void setTekstSynonim(String tekstSynonim) {
		this.tekstSynonim = tekstSynonim;
	}

	public String getTekstBazowy() {
		return tekstBazowy;
	}

	public void setTekstBazowy(String tekstBazowy) {
		this.tekstBazowy = tekstBazowy;
	}

	public void synonimizuj(){
		String[] tekstPodzielony = tekstBazowy.split("[{}]");
//		for(String s: tekstPodzielony)
//			System.out.println(s);
		
		for(int i=0;i<tekstPodzielony.length;i++){
			if(tekstPodzielony[i].contains("|")){
				String[] wyrazySynonimy = tekstPodzielony[i].split("[|]");
				tekstPodzielony[i] = wyrazySynonimy[(int)(Math.random()*wyrazySynonimy.length)];
			}
		}
		tekstSynonim = "";
		for(String s: tekstPodzielony)
			tekstSynonim+=s;
	}
	
	public String toString(){
		return getTytulBazowy();
	}
}
