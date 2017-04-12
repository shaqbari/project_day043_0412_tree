package com.ss.calinder;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener{
	JPanel p_north, p_center;
	JButton bt_prev, bt_next;
	JLabel la_title;
	DateBox[] box=new DateBox[7*6];
	Calendar cal=Calendar.getInstance();
	
	//���糯¥�� ����ϱ� ���� ����
	int yyyy;
	int mm;
	int dd;
	
	public MainFrame() {
		p_north=new JPanel();
		p_center=new JPanel();
		bt_prev=new JButton("��");
		bt_next=new JButton("��");
		la_title=new JLabel("2017�� 4��");
		la_title.setFont(new Font("����", Font.BOLD|Font.ITALIC, 28));
		
		p_north.add(bt_prev);
		p_north.add(la_title);
		p_north.add(bt_next);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);		
		
		//System.out.println(cal);
		yyyy=cal.get(Calendar.YEAR);//int field�� �μ��� �䱸�Ǹ� ����� �����ϰ� ��������
		mm=cal.get(Calendar.MONTH); //0���� �����ϱ� ������ ����Ҷ��� +1�ؾ��Ѵ�.
		dd=cal.get(Calendar.DATE);
		
		System.out.println(yyyy+"-"+mm+"-"+dd);
		
			
		
		printDate();
		
		bt_prev.addActionListener(this);
		bt_next.addActionListener(this);
		
		setSize((120*7)+100, (120*6)+150);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	
	//��¥��� �޼ҵ� ����
	public void printDate(){
		//���� ��¥�� �󺧿� ���
		la_title.setText(yyyy+"-"+(mm+1));		
		
		//�簢����� ������!
		p_center.removeAll();
		
		//�� ���� ���� ���Ϻ��� �����ϳ�?
		//�ش���� 1�Ϸ� ���߰�, �� ������ ���� �������� ����� �ȴ�.
		cal.set(yyyy, mm, 1);
		int firstDay=cal.get(Calendar.DAY_OF_WEEK);
		//System.out.println(mm+"�� ���ۿ�����"+firstDay); //�Ͽ��Ϻ��� 1���ͽ����ؼ� �����7�� ������.
		int num=0;//���� ���� ��¥�� ����
		
		//�� ���� ������ ��¥ �˾Ƹ��߱�
		//��? �ݺ����� ���� �˱� ���ؼ�,
		cal.set(yyyy, mm+1, 0);//�״������� 1���� 1����	
		int lastDay=cal.get(Calendar.DATE);
		
		//�󺧸� �ٲٰų� �����ϰ� �ٽ� �ٿ��� �Ѵ�.			
		for (int i = 0; i < box.length; i++) {
			box[i]=new DateBox(this);						
			p_center.add(box[i]);						
			
			/*if(i<firstDay-1){
				box[i].la.setText("");
			}else{
				num++;
				box[i].la.setText(Integer.toString(num));
			}*/
			
			if(i>=firstDay-1){
				num++;	
			}else {
				num=0;
			}
			
			if (num!=0) {
				if (num<=lastDay) {
					box[i].la.setText(Integer.toString(num));
				}
			}else{
				box[i].la.setText("");					
			}
		}		
	}

	public void actionPerformed(ActionEvent e) {
		Object obj=(Object)e.getSource();
		
		if (obj==bt_prev) {//������
			mm--;
			if(mm<0){
				yyyy--;
				mm=11;
			}			
			printDate();
		}else if (obj==bt_next) {//������
			mm++;
			if(mm>11) {
				yyyy++;
				mm=0;
			}			
			printDate();
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}

}
