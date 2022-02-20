package mainframe;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import constant.Constant;
import enums.TransitionAnimationType;
import scene.*;


public class MainFrame extends Frame implements KeyListener,MenuDelegate,TransitionAnimationDelegate,ActionListener
{
	//����ɫ
	private Color backgroundColor = Color.BLACK;
	//����
	private List<AbstractBaseScene> scenes = new ArrayList<AbstractBaseScene>();
	//˫����ͼƬ
	private Image bufferImage = null;
	private long lastTime = -1;
	//�˵���Ŀ
	private MenuItem pauseItem,audioItem,saveItem,loadItem,mapItem,refreshItem;
	
	public static MainFrame app;
	public static boolean isReady = false;
	private Timer startTimer;
	
	public MainFrame()
	{
		this.setTitle(Constant.MAIN_TITLE);
		int x = 0,y = 0,width=0,height = 0;
		
		width = Constant.MIN_UNIT + Constant.MAP_WIDTH + Constant.MIN_UNIT + 3*28 + Constant.MIN_UNIT;
		height = Constant.MIN_UNIT + Constant.MAP_WIDTH + Constant.MIN_UNIT;
		
		
		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);
		
		Menu menu = new Menu("����");
		menuBar.add(menu);
		
		pauseItem = new MenuItem("��ͣ��Ϸ",new MenuShortcut(KeyEvent.VK_P));
		pauseItem.addActionListener(this);
		pauseItem.setEnabled(false);
		pauseItem.setActionCommand("pauseAction");
		menu.add(pauseItem);
		
		audioItem = new MenuItem("�ر���Ч", new MenuShortcut(KeyEvent.VK_A));
		audioItem.addActionListener(this);
		audioItem.setEnabled(false);
		audioItem.setActionCommand("audioAction");
		menu.add(audioItem);
		
		refreshItem = new MenuItem("�µ�һ��", new MenuShortcut(KeyEvent.VK_R));
		refreshItem.addActionListener(this);
		refreshItem.setEnabled(false);
		refreshItem.setActionCommand("refreshAction");
		menu.add(refreshItem);
		
		//��ӷָ��
		menu.addSeparator();
		
		saveItem = new MenuItem("�浵", new MenuShortcut(KeyEvent.VK_S));
		saveItem.addActionListener(this);
		saveItem.setActionCommand("saveAction");
		menu.add(saveItem);
		
		
		loadItem = new MenuItem("����", new MenuShortcut(KeyEvent.VK_L));
		loadItem.addActionListener(this);
		loadItem.setActionCommand("loadAction");
		menu.add(loadItem);
		
		
		Menu menu2 = new Menu("��ͼ");
		menuBar.add(menu2);
		
		
		mapItem = new MenuItem("������ͼ", new MenuShortcut(KeyEvent.VK_M));
		mapItem.addActionListener(this);
		mapItem.setActionCommand("mapAction");
		menu2.add(mapItem);
		
		
		
		//����Ϊ�̶���С
		this.setResizable(false);
		//���ÿɼ�
		this.setVisible(true);
		
		Insets insets = this.getInsets();
		Constant.CLIENT_TOP = insets.top;
		width = width + insets.left + insets.right;
		height = height +insets.top + insets.bottom;
		Constant.WIDTH = width;
		Constant.HEIGHT = height;
		
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		x =  w / 2 - width /2;
		y = h / 2 - height /2;
		
		
		this.setBounds(x, y, width, height);
		
		bufferImage = this.createImage(width, height);
		
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		this.addKeyListener(this);
		
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			
			@Override
			public void run()
			{
				dispatcher();
				
			}
		}, 0, 20);
		
		
		showScene(new MenuScene());
		
	}
	/**
	 * ��ʾ����
	 *scene�� Ҫ��ʾ�ĳ���
	 */
	public void showScene(AbstractBaseScene scene)
	{
		pauseItem.setEnabled(false);
		saveItem.setEnabled(false);
		refreshItem.setEnabled(false);
		
		if(scene instanceof MenuScene)
		{
			mapItem.setEnabled(true);
			loadItem.setEnabled(true);
			MenuScene menuScene = (MenuScene) scene;
			menuScene.addDelegate(this);
	
			scene.setVisible(true);
			scenes.add(scene);
		}
		else if(scene instanceof StageScene)
		{
			pauseItem.setEnabled(true);
			audioItem.setEnabled(true);
			refreshItem.setEnabled(true);
			saveItem.setEnabled(true);
			mapItem.setEnabled(false);
			loadItem.setEnabled(false);
			scenes.add(scene);
			
			TransitionAnimationScene animationScene = new TransitionAnimationScene();
			animationScene.setAnimationType(TransitionAnimationType.ENTER_ANIMATION);
			animationScene.setVisible(true);
			animationScene.addDelegate(this);
			scenes.add(animationScene);
		}
		else if(scene instanceof ConstructionMapScene)
		{
			refreshItem.setEnabled(true);
			mapItem.setEnabled(false);
			loadItem.setEnabled(false);
			scenes.add(scene);
			
			TransitionAnimationScene animationScene = new TransitionAnimationScene();
			animationScene.setConstruction(true);
			animationScene.setAnimationType(TransitionAnimationType.ENTER_ANIMATION);
			animationScene.setVisible(true);
			animationScene.addDelegate(this);
			scenes.add(animationScene);
		}
		
		
	}
	public synchronized  void dispatcher()
	{
		if(Constant.isPause)
		{
			repaint();
			return;
		}
		for(int i=0;i<scenes.size();i++)
		{
			if(scenes.get(i).isVisible())
			{
				scenes.get(i).dispatcher(null);
			}
		}
		repaint();
	}

	@Override
	public void update(Graphics g)
	{
		long time = System.currentTimeMillis();
		if(lastTime != -1)
		{
			long offsetTime =  time - lastTime ;
			if(offsetTime > 0)
			{
				Constant.currentFPS =  1000 / offsetTime;
			}
			
		}
		lastTime = time;
		
		//super.update(g);
		Graphics g2 = bufferImage.getGraphics();
		
		Color color = g2.getColor();
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, Constant.WIDTH, Constant.HEIGHT);
		g2.setColor(color);
		
		
		
		for(int i=0;i<scenes.size();i++)
		{
			if(scenes.get(i).isVisible())
			{
				scenes.get(i).draw(g2);
			}
		}
		
		g.drawImage(bufferImage, 0, 0, null);
		
		
	}
	
	public static void main(String[] args)
	{
		MainFrame.app = new MainFrame();

	}
	
	
// �����¼�	
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(Constant.isPause)
		{
			return;
		}
		for(int i=0;i<scenes.size();i++)
		{
			if(scenes.get(i).isAcceptEvent())
			{
				scenes.get(i).keyPressed(e);
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		if(Constant.isPause)
		{
			return;
		}
		for(int i=0;i<scenes.size();i++)
		{
			if(scenes.get(i).isAcceptEvent())
			{
				scenes.get(i).keyReleased(e);
			}
		}
		
	}
	/**
	  *    �ͻ��˷����Ĳ����¼�
	 */
	public void remoteKeyEvent(KeyEvent e,boolean isPressed)
	{
		for(int i=0;i<scenes.size();i++)
		{
			if(scenes.get(i).isAcceptEvent())
			{
				scenes.get(i).remoteKeyEvent(e, isPressed);
			}
		}
	}
	/**
	 * �ͻ��˵���gameover
	 */
	public void showGameOver()
	{
		for(int i=0;i<scenes.size();i++)
		{
			AbstractBaseScene scene = scenes.get(i);
			if(scene instanceof StageScene)
			{
				StageScene stageScene = (StageScene) scene;
				stageScene.showGameOver();
			}
		}
	}
	
	
// �˵������¼�
	
	@Override
	public void selectCompleted()
	{
		this.showScene(new StageScene());
	}
	@Override
	public void showCompleted()
	{
		
		int count = scenes.size() -1;
		for(int i = count ;i>=0;i--)
		{
			AbstractBaseScene scene = scenes.get(i);
			if(scene instanceof StageScene)
			{
				scenes.remove(scene);
			}
			else if(scene instanceof MenuScene)
			{
				backgroundColor = scene.getBackgroudColor();
			}
		}
	}
	
	
// ת�������¼�
	
	@Override
	public void enterCompleted()
	{
		int count = scenes.size() -1;
		for(int i = count ;i>=0;i--)
		{
			AbstractBaseScene scene = scenes.get(i);
			if(scene instanceof MenuScene)
			{
				scenes.remove(scene);
			}
			else if(scene instanceof TransitionAnimationScene)
			{
				TransitionAnimationScene animationScene = (TransitionAnimationScene) scene;
				animationScene.setAnimationType(TransitionAnimationType.EXIT_ANIMATION);
		    }
			else if(scene.isVisible())
			{
				scenes.remove(scene);
			}
			else if(scene instanceof StageScene)
			{
				isReady = false;
				scene.setVisible(true);
				this.backgroundColor = scene.getBackgroudColor();
			}
			else if(scene instanceof ConstructionMapScene)
			{
				scene.setVisible(true);
				this.backgroundColor = scene.getBackgroudColor();
			}
		}
	}
	@Override
	public void exitCompleted()
	{
		int count = scenes.size() -1;
		for(int i = count ;i>=0;i--)
		{
			AbstractBaseScene scene = scenes.get(i);
			if(scene instanceof TransitionAnimationScene)
			{
				this.scenes.remove(scene); 
			}
			else if(scene instanceof StageScene)
			{
				scene.start();
			}
			else if(scene instanceof ConstructionMapScene)
			{
				
				scene.start();
			}
		}
	}
	/**
	 * ��ʼ��Ϸ
	 */
	public void startGame()
	{
		if(isReady)
		{
			startTimer.cancel();
			startTimer = null;
			isReady = false;
			for(AbstractBaseScene scene:scenes)
			{
				if(scene instanceof StageScene)
				{
					scene.start();
				}
			}
		}
		else
		{
			long t = System.currentTimeMillis();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();
		if("pauseAction".equals(action))
		{
			Constant.isPause = !Constant.isPause;
			if(Constant.isPause)
			{
				pauseItem.setLabel("��ʼ��Ϸ");
			}
			else
			{
				pauseItem.setLabel("��ͣ��Ϸ");
			}
		}
		else if("refreshAction".equals(action)) {
			refreshItem.setLabel("�µ�һ��");
			MainFrame.app.showScene(new MenuScene());
		}
		else if("audioAction".equals(action))
		{
			Constant.isPlayAudio = !Constant.isPlayAudio;
			if(Constant.isPlayAudio)
			{
				audioItem.setLabel("�ر���Ч");
			}
			else
			{
				audioItem.setLabel("������Ч");
			}
		}
		else if("saveAction".equals(action))
		{
			if(!Constant.isPause)
			{
				Constant.isPause = !Constant.isPause;
				if(Constant.isPause)
				{
					pauseItem.setLabel("��ʼ��Ϸ");
				}
			}
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("BattleCity(*.bc)", "bc");
			fc.setFileFilter(filter);
			fc.setDialogTitle("���浽");
			if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				
				String path = fc.getSelectedFile().getAbsolutePath();
				if(!path.endsWith(".bc"))
				{
					path = path+".bc";
				}
				StageScene stageScene = null;
				for(AbstractBaseScene scene :scenes)
				{
					if(scene instanceof StageScene)
					{
						stageScene = (StageScene) scene;
					}
				}
				if(stageScene != null && stageScene.saveStageToFile(path))
				{
					JOptionPane.showMessageDialog(null, "�浵�ɹ���");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "�浵ʧ�ܣ�");
				}
			}
		}
		else if("loadAction".equals(action))
		{
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("BattleCity(*.bc)", "bc");
			fc.setFileFilter(filter);
			fc.setDialogTitle("ѡ���ļ�");
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				
				String path = fc.getSelectedFile().getAbsolutePath();
				StageScene scene = StageScene.loadStageFromFile(path);
				if(scene != null)
				{
					scene.setVisible(false);
					scene.setAcceptEvent(false);
					showScene(scene);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "�ļ����𻵣�");
				}
			}
		}
		else if("mapAction".equals(action))
		{
			showScene(new ConstructionMapScene());
		}
	}
	public void showTitle(String title)
	{
		this.setTitle(title);
		if(title.equals(Constant.MAIN_TITLE))
		{
			mapItem.setEnabled(true);
			loadItem.setEnabled(true);
		}
		else
		{
			mapItem.setEnabled(false);
			loadItem.setEnabled(false);
		}
	}
}

