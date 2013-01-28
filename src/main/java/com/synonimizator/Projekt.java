package com.synonimizator;

import java.util.LinkedHashSet;
import java.util.Set;

public class Projekt
{
	private String nazwaProjektu;
	private String email;
	private Set<Artykul> listaArtykulow;
	private long projektId;

	public Projekt(String nazwa)
	{
		nazwaProjektu = nazwa;
		listaArtykulow = new LinkedHashSet<Artykul>();
	}

	public Projekt()
	{

	}

	public long getProjektId()
	{
		return projektId;
	}

	public void setProjektId(long projektId)
	{
		this.projektId = projektId;
	}

	public String getNazwaProjektu()
	{
		return nazwaProjektu;
	}

	public void setNazwaProjektu(String nazwaProjektu)
	{
		this.nazwaProjektu = nazwaProjektu;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Set<Artykul> getListaArtykulow()
	{
		return listaArtykulow;
	}

	public void setListaArtykulow(Set<Artykul> listaArtykulow)
	{
		this.listaArtykulow = listaArtykulow;
	}

	public void dodajArtykul(Artykul art)
	{
		listaArtykulow.add(art);
	}

	public String toString()
	{
		return nazwaProjektu;
	}
}
