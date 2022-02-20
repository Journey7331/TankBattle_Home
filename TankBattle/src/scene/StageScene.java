package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import constant.Constant;
import constant.StageMap;
import enums.AudioType;
import enums.GameModel;
import enums.TankType;
import mainframe.MainFrame;
import layer.GameOverLayer;
import layer.GameOverLayerDelegate;
import layer.MapLayer;
import msg.MsgInfo;
import msg.NewHeroMsg;
import msg.NewSpriteMsg;
import sprite.*;
//import sprite.AbstractBaseSprite;
//import sprite.BombSprite;
//import sprite.BuffSprite;
//import sprite.BulletSprite;
//import sprite.EnemyTankSprite;
//import sprite.HeroTankSprite;
import util.AudioUtil;
import util.RandomUtil;
/**
 * 舞台场情
 */

public class StageScene extends AbstractBaseScene implements GameOverLayerDelegate
{
	private static final long serialVersionUID = -1270142030480761234L;
	private MapLayer mapLayer ;
	/**
	 * 每个关卡要显示的坦克数
	 */
	private final static int enemyCount = 30;
	/**
	 * 当前已显示的坦克数
	 */
	private int showEnemyCount = 0;
	/**
	 * gameOver图层
	 */
	private GameOverLayer gameOverLayer = null;
	
	private List<HeroTankSprite> list  = null;
	private boolean isFromFile = false;

	public StageScene()
	{
		mapLayer = new MapLayer(this);
		
		this.setBackgroudColor(Constant.stage_bg_Color);
		
		mapLayer.initMap();
		
	}
	public StageScene(List<HeroTankSprite> list)
	{
		this.list = list;
		
		mapLayer = new MapLayer(this);
		
		this.setBackgroudColor(Constant.stage_bg_Color);
		
		mapLayer.initMap();
	}
	@Override
	public void start()
	{
		AbstractBaseSprite.resetAutoIncrement();
		if(this.isFromFile())
		{
			this.setAcceptEvent(true);
			return;
		}
		showEnemyCount = 0;
		if(list == null)
		{
			/**
			 * 初始化玩家
			 */
			if(Constant.selectModel == GameModel.PLAYER)
			{
				mapLayer.addSprite(new HeroTankSprite(mapLayer,TankType.LEFT_HERO));
			}
			else if(Constant.selectModel == GameModel.PLAYERS )
			{
				mapLayer.addSprite(new HeroTankSprite(mapLayer,TankType.LEFT_HERO));
				mapLayer.addSprite(new HeroTankSprite(mapLayer,TankType.RIGHT_HERO));
			}
		}
		else
		{
			for(HeroTankSprite sprite :list)
			{
				mapLayer.addSprite(new HeroTankSprite(mapLayer, sprite));
			}
		}
		this.setAcceptEvent(true);
		
	}
	/**
	 * 制造敌方坦克
	 */
	public void makeEnemy()
	{
		if(enemyCount > showEnemyCount  && this.mapLayer.getEnemyTankSprites().size() < 6)
		{
			if(this.mapLayer.getEnemyTankSprites().size() ==0)
			{
				mapLayer.addSprite(new EnemyTankSprite(mapLayer));
				showEnemyCount++;
			}
			else
			{
				int index = RandomUtil.nextInt(60 - Constant.currentStage);
				if(index ==0)
				{
					mapLayer.addSprite(new EnemyTankSprite(mapLayer));
					showEnemyCount++;
				}
			}
		}
		
	}
	
	
	@Override
	public void draw(Graphics g)
	{
		mapLayer.draw(g);
		
		drawEnemyCount(g);
		drawIP(g);
		drawGrade(g);
		this.drawFPS(g);
		
		if(gameOverLayer != null)
		{
			gameOverLayer.draw(g);
		}
		
	}

	@Override
	public void dispatcher(MsgInfo info)
	{
		if(this.isAcceptEvent())
		{
			if(gameOverLayer != null)
			{
				gameOverLayer.dispatcher(info);
			}
			else
			{
				makeSprite(info);
				
				mapLayer.dispatcher(info);
				makeEnemy();
				
				if(enemyCount == showEnemyCount && mapLayer.getEnemyTankSprites().size() ==0)
				{
					this.setAcceptEvent(false);
					nextStage();
				}
			}
		}
	}

	/**
	 * 根据消息创建精灵
	 */
	public void makeSprite(MsgInfo info)
	{
		if(info==null)
		{
			return;
		}
		if(info.getNewHeros().size() > 0)
		{
			for(NewHeroMsg msg :info.getNewHeros())
			{
				mapLayer.addSprite(new HeroTankSprite(mapLayer, msg));
			}
		}
		if(info.getNewSprites().size() > 0)
		{
			for(NewSpriteMsg msg :info.getNewSprites())
			{
				if(msg.getType()==0 || msg.getType()==1 || msg.getType() == 2)
				{
					showEnemyCount++;
					mapLayer.addSprite(new EnemyTankSprite(mapLayer,msg));
					
				}
				else if(msg.getType() == 3)
				{
					mapLayer.addSprite(new BulletSprite(mapLayer, msg));
				}
				else if(msg.getType() == 4)
				{
					mapLayer.addSprite(new BombSprite(msg));
				}
				else if(msg.getType() == 5)
				{
					mapLayer.addSprite(new BuffSprite(msg));
				}
			}
		}
		
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(!interceptKeyEvent(e, true))
		{
			mapLayer.keyPressed(e);
		}
		

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(!interceptKeyEvent(e, false))
		{
			mapLayer.keyReleased(e);
		}
	}
	/**
	 * 执行客户端的操作事件
	 */
	@Override
	public void remoteKeyEvent(KeyEvent e, boolean isPressed)
	{
		if(isPressed)
		{
			mapLayer.keyPressed(e);
		}
		else
		{
			mapLayer.keyReleased(e);
		}
	}
	/**
	 * 拦截操作事件并修改
	 */
	public boolean interceptKeyEvent(KeyEvent e,boolean isPressed)
	{
		return false;
	}
	
	/**
	 * 画未显示敌方坦克的数量
	 */
	public void drawEnemyCount(Graphics g)
	{
		int count = enemyCount - showEnemyCount;
		for(int i=0;i<count;i++)
		{
			int dx1 = 2 * Constant.MIN_UNIT + Constant.MAP_WIDTH + (i % 3) * 28;
			int dy1 = MapLayer.top +  (i / 3) *28;
			int dx2 = dx1 + 28;
			int dy2 = dy1 + 28;
			
			int sx1 = 184;
			int sy1 = 224;
			int sx2 = sx1 + 28;
			int sy2 = sy1 + 28;
			
			
			g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
	}
	/**
	 * 画英雄的生命值
	 */
	public void drawIP(Graphics g)
	{
		
		Color color = g.getColor();
		Font font = g.getFont();
		
		g.setColor(Color.BLACK);
		g.setFont(Constant.font_bold_40);
		
		
		int leftCount = 0;
		int rightCount = 0;
		for(HeroTankSprite sprite:mapLayer.getHeroTanks())
		{
			if(sprite.getTankType() == TankType.LEFT_HERO)
			{
				leftCount = sprite.getLife();
			}
			else
			{
				rightCount = sprite.getLife();
			}
		}
		int dx1 = 2 * Constant.MIN_UNIT + Constant.MAP_WIDTH + 7;
		int dy1 = Constant.HEIGHT / 2 + Constant.MIN_UNIT;
		int dx2 = dx1 + 60;
		int dy2 = dy1 + 64;
		int sx1 = 0;
		int sy1 = 226;
		int sx2 = sx1 + 60;
		int sy2 = sy1 + 64;
		
		g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		g.drawString(leftCount+"", dx1 + 38, dy1 + 60);
		
		if(Constant.selectModel != GameModel.PLAYER)
		{
			
			dy1 = dy2 +  Constant.MIN_UNIT;
			dx2 = dx1 + 60;
			dy2 = dy1 + 64;
			
			sx1 = 60;
			sy1 = 226;
			sx2 = sx1 + 60;
			sy2 = sy1 + 64;
			
			g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
			g.drawString(rightCount+"", dx1 + 38, dy1 + 60);
		}
		
		g.setColor(color);
		g.setFont(font);
	}

	/**
	 * 画等级
	 */
	public void drawGrade(Graphics g)
	{
		Color color = g.getColor();
		Font font = g.getFont();
		g.setColor(Color.BLACK);
		g.setFont(Constant.font_bold_26);
		
		int dx1 = 2 * Constant.MIN_UNIT + Constant.MAP_WIDTH + 7;
		int dy1 = Constant.HEIGHT / 2 + Constant.MIN_UNIT * 2 + 64;
		
		if(Constant.selectModel != GameModel.PLAYER)
		{
			dy1 = dy1 + 64 + Constant.MIN_UNIT;
		}
		
		int dx2 = dx1 + 64;
		int dy2 = dy1 + 60;
		int sx1 = 120;
		int sy1 = 226;
		int sx2 = sx1 + 64;
		int sy2 = sy1 + 60;
		
		g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		if(Constant.currentStage ==0)
		{
			g.setFont(Constant.font_bold_16);
			g.drawString("自定义", dx1 + 20, dy1 + 58);
		}
		else
		{
			 g.drawString(Constant.currentStage+"", dx1 + 20, dy1 + 58);
		}
	   
		g.setColor(color);
		g.setFont(font);
		
	}
	/**
	 * 显示gameOver代码
	 */
	public void showGameOver()
	{
		if(gameOverLayer == null)
		{
			gameOverLayer = new GameOverLayer();
			this.mapLayer.showDie();
			gameOverLayer.addDelegate(this);
			AudioUtil.playAudio(AudioType.GMAE_OVER);
		}
	}
	/**
	  *  通关进入下一个关卡
	 */
	public void nextStage()
	{
		Constant.currentStage = (Constant.currentStage + 1 ) % StageMap.list.size();
		MainFrame.app.showScene(new StageScene(mapLayer.getHeroTanks()));
	}
	
	/**
	 * gameover动画完成后回调
	 */
	@Override
	public void animationCompleted()
	{	
		//关卡变为 1
		Constant.currentStage = 1;
		MainFrame.app.showScene(new MenuScene());
	}
	public boolean isFromFile()
	{
		return isFromFile;
	}
	public void setFromFile(boolean isFromFile)
	{
		this.isFromFile = isFromFile;
	}
	/**
	 * 把当前的对象保存到文件
	 * path： 保存的路径
	 */
	public boolean saveStageToFile(String path)
	{
		try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File(path))))
		{
			
			this.setFromFile(true);
			os.writeObject(this);
			os.writeInt(Constant.currentStage);
			os.writeInt(Constant.selectModel.ordinal());
			os.flush();
			
			return true;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	   *    从文件中加载舞台场情
	 */
	public static StageScene loadStageFromFile(String path)
	{
		StageScene scene = null;
		try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File(path))))
		{
			scene = (StageScene) is.readObject();
			Constant.currentStage = is.readInt();
			int value = is.readInt();
			Constant.selectModel = GameModel.valueOf(value);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return scene;
	}
	
}
