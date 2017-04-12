/*�� ��¥�� ǥ���ϴ� Ŀ���͸���¡ ������Ʈ*/

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
		
		//���� ���콺 ������ ����
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
		JOptionPane.showMessageDialog(this, yyyy+"�� "+(mm+1)+"�� "+dd+"��");
		
		for (int i = 0; i < frame.box.length; i++) {
			if(frame.box[i]!=this){//���� �ƴ϶��
				frame.box[i].setBackground(Color.YELLOW);
			}else{
				frame.box[i].setBackground(Color.PINK);
			}
		}		
		//setBackground(Color.PINK);	������ ��� yellow�� �ٲٰ� �ڽ��� pink�ιٲ㵵 �Ǳ��Ѵ�.	
	}


	
	
	
}
