package com.synonimizator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.hibernate.Session;

//TODO
//czy zapisac po kliknieciu X
//zapisz bez przelaczenia z tektu (zakladka po prawej) --done
//dodanie do precli
//wygenerowanie synonimu
//poprawa pisowni
//automatyczne zamykanie nawiasow
//dodaj/usun nowy projekt
//refactor zapisywanie biezacego art po x lub zakoncz
//zmiana menuitems na array lub set
//zapisywani if nothing selected

public class Aplikacja extends JPanel implements ActionListener,
		TreeSelectionListener, KeyListener
{
	private JTree drzewko;
	private JMenuBar menuBar;
	private JMenu mPlik, mPomoc;
	private JMenuItem miZakoncz, miOtworz, miOProgramie, miFormatuj;
	private JMenuItem miZmienNazwe, miDodajProjekt, miDodajArtykul,
			miUsunProjekt;
	private JPopupMenu pmProjekt, pmDrzewo;
	private JTextPane aTekst;
	// private Artykul art;
	private List<Projekt> listaProjektow;

	public Aplikacja()
	{
		// Artykul art = new Artykul("Tytul test", "Dawno temu w trawie");
		// Projekt proj = new Projekt("taki projekt");
		// art.setProjekt(proj);
		// proj.dodajArtykul(art);
		// sesja.save(proj);
		// sesja.save(art);
		// cascade = CascadeType.ALL

		/*
		 * // dodawanie art Artykul art = new Artykul( "Krotka historja",
		 * "Jest to krotka {historia|bajka} o tym jak {dawno temu|w czasach zamieszchlych|w minionym wieku} sadzono {pszenice|zyto|owies}."
		 * ); art.synonimizuj(); Artykul art1 = new Artykul( "Dluga historja",
		 * "Jest to dluga {historia|bajka} o tym jak {dawno temu|w czasach zamieszchlych|w minionym wieku} sadzono {pszenice|zyto|owies}."
		 * ); art1.synonimizuj(); listaProjektow = new LinkedList<Projekt>();
		 * 
		 * Projekt nowy = new Projekt("Pierwszy projekt"); art.setProjekt(nowy);
		 * art1.setProjekt(nowy); nowy.dodajArtykul(art);
		 * nowy.dodajArtykul(art1); listaProjektow.add(nowy); //pr 2 art = new
		 * Artykul( "Krotka historja",
		 * "Jest to krotka {historia|bajka} o tym jak {dawno temu|w czasach zamieszchlych|w minionym wieku} sadzono {pszenice|zyto|owies}."
		 * ); art.synonimizuj(); art1 = new Artykul( "Dluga historja",
		 * "Jest to dluga {historia|bajka} o tym jak {dawno temu|w czasach zamieszchlych|w minionym wieku} sadzono {pszenice|zyto|owies}."
		 * ); art1.synonimizuj(); listaProjektow = new LinkedList<Projekt>();
		 * 
		 * nowy = new Projekt("Pierwszy projekt"); art.setProjekt(nowy);
		 * art1.setProjekt(nowy); nowy.dodajArtykul(art);
		 * nowy.dodajArtykul(art1); listaProjektow.add(nowy);
		 */

		// createComponents();
		// //------------Hibernate-------------------
		// Session sesja =
		// SessionFactoryUtil.getSessionFactory().getCurrentSession();
		// sesja.beginTransaction();
		// sesja.save(nowy);
		// sesja.save(art);
		// sesja.save(art1);
		//
		// //sesja.save(nowy);
		// sesja.getTransaction().commit();

		LoadData.wczytajdane();
		listaProjektow = LoadData.getListaProjektow(); // do zmiany jak
														// dodawanie nowych
		createComponents();

	}

	public void createComponents()
	{
		aTekst = new JTextPane();
		menuBar = new JMenuBar();

		miZakoncz = new JMenuItem("Zakoncz");
		miOtworz = new JMenuItem("Otworz");
		miOProgramie = new JMenuItem("O programie");
		miFormatuj = new JMenuItem("Formatuj");
		mPlik = new JMenu("Plik");
		mPomoc = new JMenu("Pomoc");
		mPlik.add(miOtworz);
		mPlik.add(miFormatuj);
		mPlik.add(miZakoncz);
		mPomoc.add(miOProgramie);
		menuBar.add(mPlik);
		menuBar.add(mPomoc);
		miZakoncz.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		miZakoncz.addActionListener(this);
		miOtworz.addActionListener(this);
		miFormatuj.addActionListener(this);
		miOProgramie.addActionListener(this);

		DefaultMutableTreeNode top = new DefaultMutableTreeNode(listaProjektow);
		createTree(top);
		drzewko = new JTree(top);
		drzewko.setRootVisible(false);
		drzewko.setPreferredSize(new Dimension(200, 400));
		drzewko.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		drzewko.addTreeSelectionListener(this);
		add(new JScrollPane(drzewko), BorderLayout.LINE_START);

		aTekst.setPreferredSize(new Dimension(500, 400));
		aTekst.addKeyListener(this);
		// aTekst.setWrapStyleWord(true);
		add(new JScrollPane(aTekst), BorderLayout.CENTER);

		pmProjekt = new JPopupMenu();
		pmDrzewo = new JPopupMenu();
		miDodajProjekt = new JMenuItem("Nowy projekt");
		miDodajArtykul = new JMenuItem("Nowy artykul");
		miZmienNazwe = new JMenuItem("Zmien nazwe");
		miUsunProjekt = new JMenuItem("Usun projekt");
		pmProjekt.add(miDodajProjekt);
		pmProjekt.add(miDodajArtykul);
		pmProjekt.add(miZmienNazwe);
		pmProjekt.add(miUsunProjekt);
		miDodajArtykul.addActionListener(this);
		miDodajProjekt.addActionListener(this);
		miZmienNazwe.addActionListener(this);
		miUsunProjekt.addActionListener(this);

		MouseListener drzewkoPopup = new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent evt)
			{

				TreePath selPath = drzewko.getPathForLocation(evt.getX(),
						evt.getY());
				if (selPath == null)
				{
					return;
				} else
				{
					drzewko.setSelectionPath(selPath);
				}
				if (evt.isPopupTrigger())
				{
					pmProjekt.show((Component) evt.getSource(), evt.getX(),
							evt.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent evt)
			{

				TreePath selPath = drzewko.getPathForLocation(evt.getX(),
						evt.getY());
				if (selPath == null)
				{
					return;
				} else
				{
					drzewko.setSelectionPath(selPath);
				}
				if (evt.isPopupTrigger())
				{
					pmProjekt.show((Component) evt.getSource(), evt.getX(),
							evt.getY());
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		};
		drzewko.addMouseListener(drzewkoPopup);

	}

	public static void createAndShowGui()
	{

		try
		{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		JFrame ramka = new JFrame("Aplikacja synonimizator");
		ramka.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		final Aplikacja aplikacja = new Aplikacja();
		ramka.setJMenuBar(aplikacja.menuBar);
		ramka.add(aplikacja);
		ramka.pack();
		ramka.setVisible(true);

		ramka.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				optionSaveOrNot(aplikacja);
			}
		});
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				createAndShowGui();
			}
		});
	}

	public void actionPerformed(ActionEvent e)
	{
		Object zrodlo = e.getSource();

		if (zrodlo == miZakoncz)
		{
			optionSaveOrNot(this);
		} else if (zrodlo == miOtworz)
		{
			JFileChooser fc = new JFileChooser();
			int akcja = fc.showOpenDialog(null);
			if (akcja == JFileChooser.APPROVE_OPTION)
			{
				File plik = fc.getSelectedFile();
				wczytajPlik(plik);
			}
		} else if (zrodlo == miOProgramie)
		{
			JOptionPane.showMessageDialog(null,
					"Witaj w programie Syninimizator!");
		} else if (zrodlo == miFormatuj)
		{
			formatujArtykul(aTekst.getText(), aTekst, true);
		} else if (zrodlo == miDodajProjekt)
		{
			String nazwaProjektu = JOptionPane.showInputDialog(null,
					"Podaj nazwe projektu");
			listaProjektow.add(new Projekt(nazwaProjektu));
			DefaultTreeModel treeModel = (DefaultTreeModel) drzewko.getModel();
			treeModel.insertNodeInto(
					new DefaultMutableTreeNode(listaProjektow
							.get(listaProjektow.size() - 1)),
					(DefaultMutableTreeNode) treeModel.getRoot(),
					listaProjektow.size() - 1);
		} else if (zrodlo == miUsunProjekt)
		{
			int dialog = JOptionPane.showConfirmDialog(null,
					"Czy na pewno usunac projekt?");
			if (dialog == JOptionPane.YES_OPTION)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) drzewko
						.getLastSelectedPathComponent();
				Object infoNode = node.getUserObject();
				Projekt pr = (Projekt) infoNode;
				listaProjektow.remove(pr);
				DefaultTreeModel treeModel = (DefaultTreeModel) drzewko
						.getModel();

				drzewko.setSelectionPath(new TreePath(treeModel.getRoot()));
				treeModel.removeNodeFromParent(node);
				// System.out.println(listaProjektow);
			}
		}
	}

	private void wczytajPlik(File f)
	{
		Scanner czytaj = null;
		String bufor = "";
		try
		{
			czytaj = new Scanner(f);

			while (czytaj.hasNext())
				bufor += czytaj.nextLine();
		} catch (IOException e)
		{

		} finally
		{
			if (czytaj != null)
				czytaj.close();
		}
		aTekst.setText(bufor);
	}

	public void createTree(DefaultMutableTreeNode top)
	{
		DefaultMutableTreeNode projekt = null;
		DefaultMutableTreeNode artykul = null;
		DefaultMutableTreeNode tytul = null;
		DefaultMutableTreeNode tekst = null;
		for (Projekt p : listaProjektow)
		{
			projekt = new DefaultMutableTreeNode(p);
			for (Artykul a : p.getListaArtykulow())
			{
				artykul = new DefaultMutableTreeNode(a);
				tytul = new DefaultMutableTreeNode("Tytul");
				tekst = new DefaultMutableTreeNode("Tekst");
				artykul.add(tytul);
				artykul.add(tekst);
				projekt.add(artykul);
			}
			top.add(projekt);
		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent t)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) drzewko
				.getLastSelectedPathComponent();

		// Zapis zmian wprowadzonych w polu tekstowym
		if (t.getOldLeadSelectionPath() != null)
		{
			TreePath tp = t.getOldLeadSelectionPath();
			DefaultMutableTreeNode oldNode = (DefaultMutableTreeNode) tp
					.getLastPathComponent();
			// System.out.println(oldNode.toString());
			if (oldNode.isLeaf())
			{
				Object oldNodeInfo = oldNode.getUserObject();

				if (oldNodeInfo.toString().equals("Tytul"))
				{
					oldNode = (DefaultMutableTreeNode) oldNode.getParent();
					oldNodeInfo = oldNode.getUserObject();
					Artykul a = (Artykul) oldNodeInfo;
					a.setTytulBazowy(aTekst.getText());
				} else if (oldNodeInfo.toString().equals("Tekst"))
				{
					oldNode = (DefaultMutableTreeNode) oldNode.getParent();
					oldNodeInfo = oldNode.getUserObject();
					Artykul a = (Artykul) oldNodeInfo;
					a.setTekstBazowy(aTekst.getText());
				}
			}
		}

		// Formatowanie
		if (node.isLeaf())
		{
			Object nodeInfo = node.getUserObject();

			if (nodeInfo.toString().equals("Tytul"))
			{
				node = (DefaultMutableTreeNode) node.getParent();
				nodeInfo = node.getUserObject();
				Artykul a = (Artykul) nodeInfo;
				formatujArtykul(a.getTytulBazowy(), aTekst, true);
			} else if (nodeInfo.toString().equals("Tekst"))
			{
				node = (DefaultMutableTreeNode) node.getParent();
				nodeInfo = node.getUserObject();
				Artykul a = (Artykul) nodeInfo;
				formatujArtykul(a.getTekstBazowy(), aTekst, true);
			}
		}

	}

	private void formatujArtykul(String tekstDoFormatowania,
			JTextPane panelDoFormatowania, boolean wyczysc)
	{
		List<Integer> indexSyntax = new LinkedList<Integer>();
		String tekst = tekstDoFormatowania;
		if (wyczysc)
			panelDoFormatowania.setText("");
		String[] syntaxSkladnia = { "{", "}", "|" };

		for (int i = 0; i < syntaxSkladnia.length; i++)
		{
			int tmp = tekst.indexOf(syntaxSkladnia[i]);
			while (tmp >= 0)
			{
				indexSyntax.add(tmp);
				tmp = tekst.indexOf(syntaxSkladnia[i], tmp + 1);
			}
		}
		Collections.sort(indexSyntax);

		// System.out.println(indexSyntax);
		// System.out.println(indexSyntax.size());

		String[] initTekst = new String[(indexSyntax.size() * 2) + 1];
		int inxT = 0, inxStart = 0;
		for (int j : indexSyntax)
		{
			initTekst[inxT] = tekst.substring(inxStart, j);
			initTekst[++inxT] = tekst.substring(j, j + 1);
			inxStart = j + 1;
			inxT++;
		}
		initTekst[inxT] = tekst.substring(inxStart, tekst.length());
		// for (String s : initTekst)
		// System.out.println(s);

		StyledDocument doc = panelDoFormatowania.getStyledDocument();
		Style def = StyleContext.getDefaultStyleContext().getStyle(
				StyleContext.DEFAULT_STYLE);
		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "SansSerif");
		StyleConstants.setFontSize(regular, 16);

		Style syntax = doc.addStyle("syntax", regular);
		StyleConstants.setForeground(syntax, Color.red);
		// StyleConstants.setBold(syntax, true);

		for (int j = 0; j < initTekst.length; j++)
		{
			try
			{
				if (j % 2 == 0)
					doc.insertString(doc.getLength(), initTekst[j], regular);
				else
				{
					doc.insertString(doc.getLength(), initTekst[j], syntax);
				}
			} catch (BadLocationException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		int pozycjaKursora = aTekst.getCaretPosition();
		formatujArtykul(aTekst.getText(), aTekst, true);
		aTekst.setCaretPosition(pozycjaKursora);
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}
	
	public static void optionSaveOrNot(Aplikacja aplikacja){

		int dialog = JOptionPane.showConfirmDialog(null,
				"Zapisac zmiany na serwerze?");
		if (dialog == JOptionPane.YES_OPTION)
		{
			// zapisywanie na serwerze teraz otwartego artykulu - do
			// refaktoryzacji

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) aplikacja.drzewko
					.getLastSelectedPathComponent();
			if (node != null)
			{
				if (node.isLeaf())
				{
					Object infoNode = node.getUserObject();
					if (node.toString().equals("Tytul"))
					{
						node = (DefaultMutableTreeNode) node
								.getParent();
						infoNode = node.getUserObject();
						Artykul a = (Artykul) infoNode;
						a.setTytulBazowy(aplikacja.aTekst.getText());
					} else if (node.toString().equals("Tekst"))
					{
						node = (DefaultMutableTreeNode) node
								.getParent();
						Artykul a = (Artykul) node.getUserObject();
						a.setTekstBazowy(aplikacja.aTekst.getText());
					}
				}
			}
			// zapisywanie calej listy artykulow
			LoadData.zapiszDane(aplikacja.listaProjektow);
			System.exit(0);
		} else if (dialog == JOptionPane.NO_OPTION)
		{
			System.exit(0);
		}
	
	}

}
