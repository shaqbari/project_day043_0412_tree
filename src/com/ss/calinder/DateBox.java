/*각 날짜를 표현하는 커스터마이징 컴포넌트*/

package com.ss.calinder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DateBox extends JPanel {
	JLabel la;
	MainFrame frame;
	
	public DateBox(MainFrame frame) {
		this.frame=frame;
		
		setLayout(new BorderLayout());;
		la=new JLabel();
		
		add(la, BorderLayout.NORTH);
		
		//나와 마우스 리스너 연결
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				pop();
			}
		});		
		
		setPreferredSize(new Dimension(120, 120));
		setBackground(Color.YELLOW);
		setVisible(true);		
	}

	
	public void pop(){		
		int yyyy=frame.yyyy;
		int mm=frame.mm;
		int dd=Integer.parseInt(la.getText());
		JOptionPane.showMessageDialog(this, yyyy+"년 "+(mm+1)+"월 "+dd+"일");
		
		for (int i = 0; i < frame.box.length; i++) {
			if(frame.box[i]!=this){//내가 아니라면
				frame.box[i].setBackground(Color.YELLOW);
			}else{
				frame.box[i].setBackground(Color.PINK);
			}
		}		
		//setBackground(Color.PINK);	위에서 모두 yellow로 바꾸고 자신을 pink로바꿔도 되긴한다.	
	}


	
	
	
}
