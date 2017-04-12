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

		// createNode();//이메소드를 통해 root에 자식을 막 붙여넣는다.
		// createDir(); //디렉토리 경로 생성
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
		root = new DefaultMutableTreeNode("과일"); // 최상위 노드 생성하기

		DefaultMutableTreeNode node1 = null;
		DefaultMutableTreeNode node2 = null;
		DefaultMutableTreeNode node3 = null;
		DefaultMutableTreeNode node4 = null;

		node1 = new DefaultMutableTreeNode("블루베리");
		node2 = new DefaultMutableTreeNode("딸기");
		node3 = new DefaultMutableTreeNode("레몬");
		node4 = new DefaultMutableTreeNode("수입산 과일");

		root.add(node1);
		root.add(node2);
		root.add(node3);
		root.add(node4);

		DefaultMutableTreeNode node41 = new DefaultMutableTreeNode("아보카도");
		DefaultMutableTreeNode node42 = new DefaultMutableTreeNode("골드키위");
		DefaultMutableTreeNode node43 = new DefaultMutableTreeNode("오렌지");
		DefaultMutableTreeNode node44 = new DefaultMutableTreeNode("망고");

		node4.add(node41);
		node4.add(node42);
		node4.add(node43);
		node4.add(node44);

	}

	// 윈도우의 구조를 보여주기(파일탐색기)
	public void createDir() {
		root = new DefaultMutableTreeNode("내컴퓨터");

		File[] drive = File.listRoots();
		FileSystemView fsv = FileSystemView.getFileSystemView();// 디스크 볼륨 정보를
																// 표현해준다.
		// api볼때 반환형을 먼저보자. 추사클래스면 method를 의심해보자.

		for (int i = 0; i < drive.length; i++) {
			String volume = fsv.getSystemDisplayName(drive[i]);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(volume);
			root.add(node);
		}

	}

	public void createMusicDir() {
		root = new DefaultMutableTreeNode("쥬크박스");
		File file = new File(path);
		File[] child = file.listFiles();
		for (int i = 0; i < child.length; i++) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(child[i].getName());
			root.add(node);
		}
	}

	// 선택한 노드의 파일에 대한 정보 추출하기
	public void extract(String filename) {
		System.out.println(filename);
		// apache의 tica lib를 이용해야 mp3파일을 재생 및 분석 할 수 있다.
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
	
	//선택한 mp3파일 재생, jlayer
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
			
			//따로 쓰레드로 빼야지 윈도우가 안멈춘다.
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
