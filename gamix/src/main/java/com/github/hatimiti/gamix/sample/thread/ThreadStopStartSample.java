package com.github.hatimiti.gamix.sample.thread;  

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreadStopStartSample extends Frame implements ActionListener{  

	private static final long serialVersionUID = 1L;  

	public static void main(String[] args){  
		new ThreadStopStartSample();  
	}  

	Label lbl1;  
	Button btnRun1;  
	Button btnWait1;  
	Button btnEnd1;  

	MyThread3 t1 = null;  

	public ThreadStopStartSample(){   
		this.setTitle("ThreadSample01");  
		this.setSize(300,300);  
		this.setLayout(new GridLayout(2,1));  

		lbl1 = new Label();  
		this.add(lbl1);  
		Panel p1 = new Panel();  
		btnRun1 = new Button("Run");  
		btnRun1.addActionListener(this);  
		btnWait1 = new Button("Wait");  
		btnWait1.addActionListener(this);  
		btnEnd1 = new Button("End");  
		btnEnd1.addActionListener(this);  
		p1.add(btnRun1);  
		p1.add(btnWait1);  
		p1.add(btnEnd1);  
		this.add(p1);  

		this.setVisible(true);  
	}  

	public void actionPerformed(ActionEvent e) {  
		if (e.getSource() == btnRun1){  
			t1 = new MyThread3(lbl1,500);  
			t1.start();  
		}else if (e.getSource() == btnWait1){  
			t1.setStop();  
		}else if (e.getSource() == btnEnd1){  
			t1.stopRun();  
		}  
	}   
}  

class MyThread3 extends Thread {  
	boolean end = false;   
	boolean stop = false;  
	Label lbl;  
	int time;    


	public MyThread3(Label lbl, int time){  
		this.lbl = lbl;  
		this.time = time;     
	}  

	public void run() {  
		int i = 0;  
		while(!end){  
			lbl.setText("Count:" + i);  
			i++;  
			try {  
				Thread.sleep(time);  
				synchronized(this){  
					if (stop) wait();  
				}  
			} catch (InterruptedException e) {  
				e.printStackTrace();  
			}  
		}  
	}  

	public void stopRun(){  
		stop = true;  
	}  

	public synchronized void setStop() {  
		stop = !stop;  
		if (!stop) {  
			notify();  
		}  

	}  


} 