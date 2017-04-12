package com.ss.tree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class AppMain extends JFrame implements TreeSelectionListener, Runnable{
	JPanel p_west, p_center;
	DefaultMutableTreeNode root = null;
	JTree tree;
	JScrollPane scroll;
	JTextArea area;
	String path = "E:/git/java_workspace3/project_day043_0412_tree/data/";
	String fileName;
	
	Thread thread;

	public AppMain() {
		p_west = new JPanel();
		p_center = new JPanel();

		// createNode();//�̸޼ҵ带 ���� root�� �ڽ��� �� �ٿ��ִ´�.
		// createDir(); //���丮 ��� ����
		createMusicDir();

		tree = new JTree(root);
		scroll = new JScrollPane(tree);
		area = new JTextArea(50, 40);

		p_west.setLayout(new BorderLayout());
		p_west.setPreferredSize(new Dimension(200, 500));
		p_west.add(scroll);
		// p_center.add(area);

		add(p_west, BorderLayout.WEST);
		// add(p_center);
		add(area);

		tree.addTreeSelectionListener(this);
		

		setSize(700, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void createNode() {
		root = new DefaultMutableTreeNode("����"); // �ֻ��� ��� �����ϱ�

		DefaultMutableTreeNode node1 = null;
		DefaultMutableTreeNode node2 = null;
		DefaultMutableTreeNode node3 = null;
		DefaultMutableTreeNode node4 = null;

		node1 = new DefaultMutableTreeNode("��纣��");
		node2 = new DefaultMutableTreeNode("����");
		node3 = new DefaultMutableTreeNode("����");
		node4 = new DefaultMutableTreeNode("���Ի� ����");

		root.add(node1);
		root.add(node2);
		root.add(node3);
		root.add(node4);

		DefaultMutableTreeNode node41 = new DefaultMutableTreeNode("�ƺ�ī��");
		DefaultMutableTreeNode node42 = new DefaultMutableTreeNode("���Ű��");
		DefaultMutableTreeNode node43 = new DefaultMutableTreeNode("������");
		DefaultMutableTreeNode node44 = new DefaultMutableTreeNode("����");

		node4.add(node41);
		node4.add(node42);
		node4.add(node43);
		node4.add(node44);

	}

	// �������� ������ �����ֱ�(����Ž����)
	public void createDir() {
		root = new DefaultMutableTreeNode("����ǻ��");

		File[] drive = File.listRoots();
		FileSystemView fsv = FileSystemView.getFileSystemView();// ��ũ ���� ������
																// ǥ�����ش�.
		// api���� ��ȯ���� ��������. �߻�Ŭ������ method�� �ǽ��غ���.

		for (int i = 0; i < drive.length; i++) {
			String volume = fsv.getSystemDisplayName(drive[i]);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(volume);
			root.add(node);
		}

	}

	public void createMusicDir() {
		root = new DefaultMutableTreeNode("��ũ�ڽ�");
		File file = new File(path);
		File[] child = file.listFiles();
		for (int i = 0; i < child.length; i++) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(child[i].getName());
			root.add(node);
		}
	}

	// ������ ����� ���Ͽ� ���� ���� �����ϱ�
	public void extract(String filename) {
		System.out.println(filename);
		// apache�� tica lib�� �̿��ؾ� mp3������ ��� �� �м� �� �� �ִ�.
		String fileLocation = filename;

		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(new File(fileLocation));
		}  catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		}
		ParseContext pcontext = new ParseContext();

		// Mp3 parser
		Mp3Parser Mp3Parser = new Mp3Parser();

		LyricsHandler lyrics;
		try {
			
			
			
			
			Mp3Parser.parse(inputstream, handler, metadata, pcontext);
			lyrics = new LyricsHandler(inputstream, handler);
			while (lyrics.hasLyrics()) {
				System.out.println(lyrics.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}

		System.out.println("Contents of the document:" + handler.toString());
		System.out.println("Metadata of the document:");
		String[] metadataNames = metadata.names();

		for (String name : metadataNames) {
			System.out.println(name + ": " + metadata.get(name));
			area.append(name + ": " + metadata.get(name)+"\n");
		}

		
	}
	
	//������ mp3���� ���, jlayer
	public void play(String filename){	
		thread=new Thread(this);
		thread.start();
	
	}

	public void valueChanged(TreeSelectionEvent e) {
		area.setText("");		
		Object obj = (Object) e.getSource();
		JTree tree = (JTree) obj;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		// System.out.println(node.getUserObject());
		fileName=path+node.getUserObject().toString();
		extract(fileName);
		play(fileName);
	}
	
	public void run() {
		FileInputStream fis=null;
		String fileLocation=fileName;
				
		try {			
			fis=new FileInputStream(new File(fileLocation));		
			AdvancedPlayer player=new AdvancedPlayer(fis);
			player.play();
			
			//���� ������� ������ �����찡 �ȸ����.
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AppMain();
	}



}
