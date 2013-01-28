package com.synonimizator;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ArtykulTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		// try {
		// SessionFactory sessionFactory = new Configuration().configure()
		// .buildSessionFactory();
		// Session sesja = sessionFactory.getCurrentSession();
		// sesja.beginTransaction();
		// Artykul art = new Artykul("Tytul test", "Dawno temu w trawie");
		// Projekt proj = new Projekt("taki projekt");
		// art.setProjekt(proj);
		// proj.dodajArtykul(art);
		// sesja.save(proj);
		// sesja.save(art);
		// sesja.getTransaction().commit();
		// } catch (Throwable e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
		Map<Integer, String> mapa = new java.util.HashMap<Integer, String>();
		mapa.put(1, "zero");
		mapa.put(2, "zero");
		mapa.put(1, "zerocdcd");
		mapa.put(1, "jeden");
		System.out.println(mapa.size());
	}
}
