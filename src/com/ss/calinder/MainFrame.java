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
	
	//현재날짜를 계산하기 위한 변수
	int yyyy;
	int mm;
	int dd;
	
	public MainFrame() {
		p_north=new JPanel();
		p_center=new JPanel();
		bt_prev=new JButton("◀");
		bt_next=new JButton("▶");
		la_title=new JLabel("2017년 4월");
		la_title.setFont(new Font("돋움", Font.BOLD|Font.ITALIC, 28));
		
		p_north.add(bt_prev);
		p_north.add(la_title);
		p_north.add(bt_next);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);		
		
		//System.out.println(cal);
		yyyy=cal.get(Calendar.YEAR);//int field가 인수로 요구되면 상수를 강력하게 생각하자
		mm=cal.get(Calendar.MONTH); //0부터 시작하기 때문에 출력할때만 +1해야한다.
		dd=cal.get(Calendar.DATE);
		
		System.out.println(yyyy+"-"+mm+"-"+dd);
		
			
		
		printDate();
		
		bt_prev.addActionListener(this);
		bt_next.addActionListener(this);
		
		setSize((120*7)+100, (120*6)+150);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	
	//날짜출력 메소드 정의
	public void printDate(){
		//현재 날짜를 라벨에 출력
		la_title.setText(yyyy+"-"+(mm+1));		
		
		//사각형모두 날리기!
		p_center.removeAll();
		
		//각 월이 무슨 요일부터 시작하나?
		//해당월을 1일로 맞추고, 그 요일이 무슨 요일인지 물어보면 된다.
		cal.set(yyyy, mm, 1);
		int firstDay=cal.get(Calendar.DAY_OF_WEEK);
		//System.out.println(mm+"의 시작요일은"+firstDay); //일요일부터 1부터시작해서 토요일7로 끝난다.
		int num=0;//실제 찍힐 날짜용 변수
		
		//각 월의 마지막 날짜 알아맞추기
		//왜? 반복문의 끝을 알기 위해서,
		cal.set(yyyy, mm+1, 0);//그다음달의 1일의 1일전	
		int lastDay=cal.get(Calendar.DATE);
		
		//라벨만 바꾸거나 제거하고 다시 붙여야 한다.			
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
		
		if (obj==bt_prev) {//이전달
			mm--;
			if(mm<0){
				yyyy--;
				mm=11;
			}			
			printDate();
		}else if (obj==bt_next) {//다음달
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
