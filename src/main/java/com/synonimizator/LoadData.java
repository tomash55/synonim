package com.synonimizator;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public class LoadData
{
	private static List<Projekt> listaProjektow;

	public static void main(String[] args)
	{
		wczytajdane();
		for (Projekt p : listaProjektow)
			for (Artykul a : p.getListaArtykulow())
				System.out.println(a.getTytulBazowy());
	}

	public static List<Projekt> getListaProjektow()
	{
		return listaProjektow;
	}

	public static void wczytajdane()
	{
		Session sesja = SessionFactoryUtil.getSessionFactory()
				.getCurrentSession();
		sesja.beginTransaction();
		Query zapytanie = sesja.createQuery("from Projekt");
		listaProjektow = zapytanie.list();

		for (Projekt a : listaProjektow)
		{
			// System.out.println(a.getListaArtykulow().size());
			Hibernate.initialize(a.getListaArtykulow());
		}
		sesja.getTransaction().commit();
	}

	public static void zapiszDane(List<Projekt> data)
	{
		
		System.out.println(listaProjektow.retainAll(data));
		Session sesja = SessionFactoryUtil.getSessionFactory()
				.getCurrentSession();
		sesja.beginTransaction();
		for (Projekt p : data)
		{
			sesja.saveOrUpdate(p);
			for (Artykul a : p.getListaArtykulow())
			{
				sesja.saveOrUpdate(a);
			}
		}
//		sesja.delete(data.get(1));

		sesja.getTransaction().commit();
	}

}
