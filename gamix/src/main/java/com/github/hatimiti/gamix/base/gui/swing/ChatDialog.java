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

import com.github.hatimiti.gamix.app.util.ConstProperty;
import com.github.hatimiti.gamix.base.gui.swing.support.WindowAdapter;
import com.github.hatimiti.gamix.base.gui.swing.support.WindowMouseDraggableListener;
import com.github.hatimiti.gamix.base.network.chat.ChatClient;
import com.github.hatimiti.gamix.base.util.Strings;

public class ChatDialog extends JDialog {

	protected static final Color BACK_GROUND_COLOR = new Color(0x00000000, true);//Color.WHITE;

	private final ChatPanel panel;
	private final ChatTextArea textArea;
	private final ChatTextField textField;

	private BufferedImage image;

	public ChatDialog() {
		initDialog();
		this.textArea = new ChatTextArea();
		this.textField = new ChatTextField();
		this.panel = new ChatPanel(this.textArea, this.textField);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(final WindowEvent e) {
				ChatDialog.this.textArea.requestFocus(false);
				ChatDialog.this.textField.requestFocusInWindow();
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
		if (isVisible) {
			new Thread(new ChatClient(new InetSocketAddress(
					ConstProperty.getInstance().getString("network.server.ip"),
					ConstProperty.getInstance().getInt("network.server.port.chat")))).start();
			// false のときCHATクローズ処理が必要
		}
		super.setVisible(isVisible);
	}

	/**
	 * チャットテキストエリアにメッセージを設定する
	 */
	public synchronized void setText(final String message) {
		this.textArea.setText(message);
	}

	/**
	 * チャットダイアログでエンターキーで確定した文字列を取得する。
	 */
	public String getEstablishedText() {
		return this.textField.getEstablishedString();
	}

	/**
	 * チャットダイアログでエンターキーで確定した文字列を取得する。
	 * 抽出後、保持している文字列はクリアする。
	 */
	private synchronized String extractEstablishedText() {
		String text = this.textField.getEstablishedString();
		this.textField.setEstablishedString("");
		return text;
	}

	/**
	 * チャットPanel
	 * @author hatimiti
	 *
	 */
	protected class ChatPanel extends JPanel {

		public ChatPanel(final ChatTextArea ta, final ChatTextField tf) {

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
					.addComponent(ta).addComponent(tf));
			// レイアウト・マネージャに登録
			layout.setHorizontalGroup(hGroup);

			// 垂直方向のグループ
			GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
			vGroup.addGroup(layout.createParallelGroup().addComponent(ta));
			vGroup.addGroup(layout.createParallelGroup().addComponent(tf));

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
//						this.textPane.setBounds(50, 50, 300, 100);
			this.textPane.setPreferredSize(new Dimension(300, 100));
			this.textPane.setMinimumSize(new Dimension(300, 100));
			this.textPane.setMaximumSize(new Dimension(300, 100));
			this.textPane.setEditable(false);

			setViewportView(this.textPane);
		}

		public void setText(final String text) {
			this.textPane.setText(text);
			int position = this.textPane.getDocument().getLength();
			this.textPane.setCaretPosition(position);
		}
	}

	/**
	 * 入力フィールド
	 * @author hatimiti
	 *
	 */
	protected class ChatTextField extends JTextField {

		protected String establishedString = "";

		public ChatTextField() {
			setColumns(50);
			setPreferredSize(new Dimension(500, 20));
			setMinimumSize(new Dimension(500, 20));
			setMaximumSize(new Dimension(500, 20));
			new ToEstablishKeyListener(this);
		}

		public String getEstablishedString() {
			return this.establishedString;
		}

		public void setEstablishedString(final String establishedString) {
			this.establishedString = establishedString;
		}

		public void establishString() {
			this.establishedString = this.getText();
			this.setText("");
		}

	}

	/**
	 * エンターキー押下時の文字列確定用リスナ
	 * @author hatimiti
	 *
	 */
	protected class ToEstablishKeyListener implements KeyListener {

		protected ChatTextField textField;

		public ToEstablishKeyListener(final ChatTextField textField) {
			this.textField = textField;
			this.textField.addKeyListener(this);
		}

		@Override
		public void keyTyped(final KeyEvent e) {
		}

		@Override
		public void keyPressed(final KeyEvent e) {

			if (KeyEvent.VK_ENTER != e.getKeyCode()) {
				return;
			}

			if (Strings.isNullOrEmpty(this.textField.getText())) {
				ChatDialog.this.setVisible(false);
				return;
			}

			this.textField.establishString();
		}

		@Override
		public void keyReleased(final KeyEvent e) {
		}
	}

}