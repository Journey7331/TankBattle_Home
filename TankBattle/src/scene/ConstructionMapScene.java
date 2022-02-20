package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import constant.Constant;
import constant.StageMap;
import mainframe.MainFrame;
import layer.MapLayer;
import msg.MsgInfo;

/**
 * 构建地图场情
 */
public class ConstructionMapScene extends AbstractBaseScene
{
	private static final long serialVersionUID = 3428799654674665694L;
	public static int left = Constant.MIN_UNIT;
	public static int top = Constant.MIN_UNIT + Constant.CLIENT_TOP;
	public static int bottom = top + Constant.MAP_WIDTH;
	public static int right = left + Constant.MAP_WIDTH;
	private byte[][] currentMap = null;
	private int currentStage = 0;
	
	private int x = 0;
	private int y = 0;
	private int tankX = 0;
	private int tankY = 0;
	
	private List<Rectangle> rects = new ArrayList<Rectangle>();
	
	public ConstructionMapScene()
	{
		this.setBackgroudColor(Constant.stage_bg_Color);
		this.currentMap = StageMap.copy(StageMap.constructionList.get(currentStage));
	
		
		tankX = x * Constant.MIN_UNIT + Constant.MIN_UNIT / 2;
		tankY = top + (y+1) * Constant.MIN_UNIT;
		
		rects.add(new Rectangle(left, top, Constant.TANK_SIZE, Constant.TANK_SIZE));
		rects.add(new Rectangle(384 + left, top, Constant.TANK_SIZE, Constant.TANK_SIZE));
		rects.add(new Rectangle(768 + left, top, Constant.TANK_SIZE, Constant.TANK_SIZE));
		rects.add(new Rectangle(8 * Constant.MIN_UNIT + MapLayer.left, 24 * Constant.MIN_UNIT + MapLayer.top, Constant.TANK_SIZE, Constant.TANK_SIZE));
		rects.add(new Rectangle(16 * Constant.MIN_UNIT + MapLayer.left, 24 * Constant.MIN_UNIT + MapLayer.top, Constant.TANK_SIZE, Constant.TANK_SIZE));
		rects.add(new Rectangle(11 * Constant.MIN_UNIT + MapLayer.left, 23 * Constant.MIN_UNIT + MapLayer.top, Constant.MIN_UNIT * 4,3 * Constant.MIN_UNIT));
	}
	@Override
	public void start()
	{
		this.setAcceptEvent(true);
	}
	@Override
	public void draw(Graphics g)
	{
		drawMap(g);
		
		
		Color color = g.getColor();
		g.setColor(Color.RED);
		
		for(Rectangle rect:rects)
		{
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
		g.setColor(color);
		
		g.drawImage(Constant.uiImage, tankX, tankY, tankX + Constant.TANK_SIZE, tankY + Constant.TANK_SIZE, 0, 0, Constant.TANK_SIZE, Constant.TANK_SIZE, null);
		
		drawRightNotice(g);
		
		drawFPS(g);
	}
	
	/**
	 * 画右边的提示信息
	 */
	public void drawRightNotice(Graphics g)
	{
		Color color = g.getColor();
		Font font = g.getFont();
		g.setColor(Color.BLACK);
		g.setFont(Constant.font_bold_16);
		
		
		g.drawString("X: "+this.x +" Y: "+this.y, right + Constant.MIN_UNIT, top + Constant.MIN_UNIT / 2);
		
		for(int i=0;i<=5;i++)
		{
			int dx1 = right + Constant.MIN_UNIT;
			int dy1 = top + 2*Constant.MIN_UNIT + (Constant.MIN_UNIT + Constant.MIN_UNIT/2) * i;
			int dx2 = dx1 + Constant.MIN_UNIT;
			int dy2 = dy1 + Constant.MIN_UNIT;
			
			if(i==5)
			{
				g.fillRect(dx1, dy1, Constant.MIN_UNIT, Constant.MIN_UNIT);
			}
			else
			{
				int sx1 = i * Constant.MIN_UNIT;
				int sy1 = 192;
				int sx2 = sx1 + Constant.MIN_UNIT;
				int sy2 = sy1 + Constant.MIN_UNIT;
				
				g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
			}
			String str = "A";
			if(i==1)
			{
				str = "S";
			}
			else if(i == 2)
			{
				str = "D";
			}
			else if(i==3)
			{
				str = "F";
			}
			else if(i==4)
			{
				str = "G";
			}
			else if(i==5)
			{
				str = "空格";
			}
			
			g.drawString(str, dx1 + 50, dy1 + 20);
			
		}
		int dx1 = right + Constant.MIN_UNIT;
		int dy1 = top + 2*Constant.MIN_UNIT + (Constant.MIN_UNIT + Constant.MIN_UNIT/2) * 6;
		g.drawString("下级 : U", dx1 , dy1 + 20);
		
		dx1 = right + Constant.MIN_UNIT;
		dy1 = top + 2*Constant.MIN_UNIT + (Constant.MIN_UNIT + Constant.MIN_UNIT/2) * 7;
		g.drawString("上级 : J", dx1, dy1 + 20);
		
		dx1 = right + Constant.MIN_UNIT;
		dy1 = top + 2*Constant.MIN_UNIT + (Constant.MIN_UNIT + Constant.MIN_UNIT/2) * 8;
		g.drawString("打印 : P", dx1, dy1 + 20);
		
		dx1 = right + Constant.MIN_UNIT;
		dy1 = top + 2*Constant.MIN_UNIT + (Constant.MIN_UNIT + Constant.MIN_UNIT/2) * 9;
		g.drawString("开始 : B", dx1, dy1 + 20);
		
		dx1 = right + Constant.MIN_UNIT;
		dy1 = top + 2*Constant.MIN_UNIT + (Constant.MIN_UNIT + Constant.MIN_UNIT/2) * 10;
		
		if(currentStage == 0)
		{
			g.drawString("等级 : 自定义", dx1, dy1 + 20);
		}
		else
		{
			g.drawString("等级 : "+currentStage, dx1, dy1 + 20);
		}
		g.setColor(color);
		g.setFont(font);
		
	}
	
	/**
	 * 画地图
	 */
	public void drawMap(Graphics g)
	{
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(left, top, Constant.MAP_WIDTH, Constant.MAP_WIDTH);
		
		
		g.setColor(Constant.stage_bg_Color);
		for(int i=0;i<currentMap.length;i++)
		{
			byte[] rows = currentMap[i];
			for(int j=0;j<rows.length;j++)
			{
				
				if(rows[j] > 0)
				{
					
					if(rows[j]>=1 && rows[j]<=5)
					{
						int dx1 =  j * Constant.MIN_UNIT + left;
						int dy1 =  i * Constant.MIN_UNIT + top;
						
						int dx2 = dx1 + Constant.MIN_UNIT;
						int dy2 = dy1 + Constant.MIN_UNIT;
						
						
						int sx1 = (rows[j] - 1) * Constant.MIN_UNIT;
						int sy1 = 192;
						
						int sx2 = sx1 + Constant.MIN_UNIT;
						int sy2 = sy1 + Constant.MIN_UNIT;
						
						
						g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
					}
					else if(rows[j] == 6)
					{
						int dx1 =  j * Constant.MIN_UNIT  + left;
						int dy1 =  i * Constant.MIN_UNIT  + top;
						
						int dx2 = dx1 + Constant.MIN_UNIT * 2;
						int dy2 = dy1 + Constant.MIN_UNIT * 2;
						
						
						int sx1 = 512;
						int sy1 = 0;
						
						int sx2 = sx1 + Constant.MIN_UNIT * 2;
						int sy2 = sy1 + Constant.MIN_UNIT * 2;
						
						
						g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
					}
					else if(rows[j] == 8)
					{
						int dx1 =  j * Constant.MIN_UNIT  + left;
						int dy1 =  i * Constant.MIN_UNIT  + top;
						
						int dx2 = dx1 + Constant.MIN_UNIT * 2;
						int dy2 = dy1 + Constant.MIN_UNIT * 2;
						
						
						int sx1 = 512 + 64;
						int sy1 = 0;
						
						int sx2 = sx1 + Constant.MIN_UNIT * 2;
						int sy2 = sy1 + Constant.MIN_UNIT * 2;
						
						
						g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
					}
				}
				
				int dx1 =  j * Constant.MIN_UNIT + left;
				int dy1 =  i * Constant.MIN_UNIT + top;
				g.drawRect(dx1, dy1, Constant.MIN_UNIT, Constant.MIN_UNIT);
				
				
			}
		}
		g.setColor(color);
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_F || keyCode == KeyEvent.VK_G || keyCode == KeyEvent.VK_SPACE)
		{
			//1：普通墙  2：铁墙  3：草  4：水  5：冰
			Rectangle rectangle = new Rectangle(left + x * Constant.MIN_UNIT, top + y*Constant.MIN_UNIT, Constant.MIN_UNIT, Constant.MIN_UNIT);
			
			for(Rectangle rect:rects)
			{
				if(rect.intersects(rectangle))
				{
					JOptionPane.showMessageDialog(null, "请不要在红框内放地图块!");
					return;
				}
			}
			
			
			byte value = 1;
			if(keyCode == KeyEvent.VK_S)
			{
				value = 2;
			}
			else if(keyCode == KeyEvent.VK_D)
			{
				value = 3;
			}
			else if(keyCode == KeyEvent.VK_F)
			{
				value = 4;
			}
			else if(keyCode == KeyEvent.VK_G)
			{
				value = 5;
			}
			else if(keyCode == KeyEvent.VK_SPACE)
			{
				value = 0;
			}
			
			this.currentMap[this.y][this.x] = value;
		}
		else if(keyCode == KeyEvent.VK_UP || keyCode== KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
		{
			if(keyCode == KeyEvent.VK_UP)
			{
				y = (y - 1 + Constant.MAP_SIZE) % Constant.MAP_SIZE;
			}
			else if(keyCode == KeyEvent.VK_DOWN)
			{
				y = (y + 1) % Constant.MAP_SIZE;
			}
			else if(keyCode == KeyEvent.VK_LEFT)
			{
				x = (x - 1 + Constant.MAP_SIZE) % Constant.MAP_SIZE;
			}
			else if(keyCode == KeyEvent.VK_RIGHT)
			{
				x = (x + 1) % Constant.MAP_SIZE;
			}
			tankX = x * Constant.MIN_UNIT + Constant.MIN_UNIT / 2;
			tankY = top + (y+1) * Constant.MIN_UNIT;
		}
		else if(keyCode == KeyEvent.VK_U)
		{
			currentStage = (currentStage - 1 + StageMap.constructionList.size()) %  StageMap.constructionList.size();
			this.currentMap = StageMap.copy(StageMap.constructionList.get(currentStage));
		}
		else if(keyCode == KeyEvent.VK_J)
		{
			currentStage = (currentStage + 1) % StageMap.constructionList.size();
			this.currentMap = StageMap.copy(StageMap.constructionList.get(currentStage));
		}
		else if(keyCode == KeyEvent.VK_P)
		{
			System.out.println("public final static byte map[][] =");
			System.out.println("{");
			for(int i=0;i<this.currentMap.length;i++)
			{
				byte[] rows = this.currentMap[i];
				System.out.print("	{ ");
				for(int j=0;j<rows.length;j++)
				{
					if(j == (rows.length-1))
					{
						System.out.print(rows[j]);
					}
					else
					{
						System.out.print(rows[j]+" ,");
					}
				}
				if(i == (this.currentMap.length-1))
				{
					System.out.println(" }}; ");
				}
				else
				{
					System.out.println(" }, ");
				}
			}
		}
		else if(keyCode == KeyEvent.VK_B)
		{
			this.setAcceptEvent(false);
			Constant.currentStage = 0;
			StageMap.map0 = StageMap.copy(this.currentMap);
			MainFrame.app.showScene(new StageScene());
		}
		
	}
	@Override
	public void keyPressed(KeyEvent e)
	{}
	@Override
	public void dispatcher(MsgInfo info)
	{}
	

}
