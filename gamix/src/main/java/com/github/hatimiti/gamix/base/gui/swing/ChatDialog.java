package com.github.hatimiti.gamix.base.gui.swing;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.github.hatimiti.gamix.base.gui.swing.support.WindowAdapter;
import com.github.hatimiti.gamix.base.gui.swing.support.WindowMouseDraggableListener;
import com.github.hatimiti.gamix.base.network.chat.ChatClient;
import com.github.hatimiti.gamix.base.network.chat.ChatMessageSender;
import com.github.hatimiti.gamix.base.util._Util;

public class ChatDialog extends JDialog
		implements ChatMessageSender {

	protected static final Color BACK_GROUND_COLOR = new Color(0x00000000, true);//Color.WHITE;

	private ChatPanel panel;
	private ChatClient client;
	private InetSocketAddress serverAddress;

	private BufferedImage image;

	public ChatDialog(InetSocketAddress serverAddress) {
		
		initDialog();
		this.panel = new ChatPanel();
		this.serverAddress = serverAddress;

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(final WindowEvent e) {
				ChatDialog.this.panel.textArea.requestFocus(false);
				ChatDialog.this.panel.textField.requestFocusInWindow();
			}
		});
		
		try {
			this.image = ImageIO.read(getClass().getResource("/syber1.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		getContentPane().add(this.panel);
		setVisible(false);
	}
	
	@Override
	public void paint(final Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		double imageWidth = this.image.getWidth();
		double imageHeight = this.image.getHeight();
		double panelWidth = this.getWidth();
		double panelHeight = this.getHeight();

		// 画像がコンポーネントの何倍の大きさか計算
		double sx = (panelWidth / imageWidth);
		double sy = (panelHeight / imageHeight);

		// スケーリング
		AffineTransform af = AffineTransform.getScaleInstance(sx, sy);
		g2D.drawImage(this.image, af, this);
	}

	protected void initDialog() {
		setBounds(300, 300, 800, 200);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		setBackground(BACK_GROUND_COLOR);
		setForeground(BACK_GROUND_COLOR);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		new WindowMouseDraggableListener(this);
	}

	@Override
	public void setVisible(final boolean isVisible) {
		if (!this.isVisible() && isVisible) {
			this.client = new ChatClient(serverAddress, this);
			client.start();
		}
		super.setVisible(isVisible);
	}

	/**
	 * チャットダイアログでエンターキーで確定した文字列を取得する。
	 */
	@Override
	public String notifyMessage() {
		return this.panel.textField.establish();
	}

	/**
	 * チャットPanel
	 * @author hatimiti
	 *
	 */
	protected class ChatPanel extends JPanel {

		private final ChatTextArea textArea;
		private final ChatTextField textField;
		
		public ChatPanel() {
			this.textArea = new ChatTextArea();
			this.textField = new ChatTextField();
			init();
		}
		
		protected void init() {

			setBackground(BACK_GROUND_COLOR);
			setForeground(BACK_GROUND_COLOR);

			// TODO 背景に画像を使う？

			GroupLayout layout = new GroupLayout(this);
			setLayout(layout);

			// 自動的にコンポーネント間のすき間をあけるようにする
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);

			// 水平方向のグループ
			GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

			hGroup.addGroup(layout.createParallelGroup()
					.addComponent(textArea).addComponent(textField));
			// レイアウト・マネージャに登録
			layout.setHorizontalGroup(hGroup);

			// 垂直方向のグループ
			GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
			vGroup.addGroup(layout.createParallelGroup().addComponent(textArea));
			vGroup.addGroup(layout.createParallelGroup().addComponent(textField));

			// レイアウト・マネージャに登録
			layout.setVerticalGroup(vGroup);
		}
		
	}

	/**
	 * チャット表示エリア
	 * @author hatimiti
	 *
	 */
	protected class ChatTextArea extends JScrollPane {

		protected JTextPane textPane;

		public ChatTextArea() {
			super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			this.textPane = new JTextPane();
//			this.textPane.setBounds(50, 50, 300, 100);
			this.textPane.setPreferredSize(new Dimension(300, 100));
			this.textPane.setMinimumSize(new Dimension(300, 100));
			this.textPane.setMaximumSize(new Dimension(300, 100));
			this.textPane.setEditable(false);

			setViewportView(this.textPane);
		}

		public void setText(final String text) {
			this.textPane.setText(text);
			this.textPane.setCaretPosition(
					this.textPane.getDocument().getLength());
		}
	}

	/**
	 * 入力フィールド
	 * @author hatimiti
	 *
	 */
	protected class ChatTextField extends JTextField {

		public ChatTextField() {
			init();
			
			/*
			 * エンターキー押下時の文字列確定用リスナ
			 */
			this.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (!hasPusshedEnterKey(e)) {
						return;
					}
					
					ChatDialog.this.client.resume();
					
					if (_Util.isNullOrEmpty(getText())) {
						ChatDialog.this.setVisible(false);
						return;
					}
				}
				
				private boolean hasPusshedEnterKey(KeyEvent e) {
					return KeyEvent.VK_ENTER == e.getKeyCode();
				}
				
				@Override
				public void keyTyped(KeyEvent e) {
				}
				@Override
				public void keyReleased(KeyEvent e) {
				}
			});
		}

		private void init() {
			setColumns(50);
			setPreferredSize(new Dimension(500, 20));
			setMinimumSize(new Dimension(500, 20));
			setMaximumSize(new Dimension(500, 20));
		}

		public String establish() {
			String establishedText = this.getText();
			this.setText("");
			return establishedText;
		}
		
	}

}