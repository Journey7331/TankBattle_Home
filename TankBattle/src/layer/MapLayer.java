package layer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import constant.Constant;
import constant.StageMap;
import enums.DirectionType;
import msg.HitMapMsg;
import msg.MsgInfo;
import scene.StageScene;
import sprite.AbstractTankSprite;

public class MapLayer extends BaseLayer
{
	private static final long serialVersionUID = -6285145308894017553L;
    private List<Point> list = new ArrayList<MapLayer.Point>();
	public MapLayer(StageScene stageScene)
	{
		super(stageScene);
	}
	public static int left = Constant.MIN_UNIT;
	public static int top = Constant.MIN_UNIT + Constant.CLIENT_TOP;
	public static int bottom = top + Constant.MAP_WIDTH;
	public static int right = left + Constant.MAP_WIDTH;
	private byte[][] currentMap = null;
	private int[][] homeLocal = {{23,11},{23,12},{23,13},{23,14},{24,11},{24,14},{25,11},{25,14}};
	
	/**
	 * home������ʱ��
	 */
	private int protectedTime = -1;
	
	@Override
	public void draw(Graphics g)
	{
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(left, top, Constant.MAP_WIDTH, Constant.MAP_WIDTH);
		
		g.setColor(color);
		
		
		for(int i=0;i<currentMap.length;i++)
		{
			byte[] rows = currentMap[i];
			for(int j=0;j<rows.length;j++)
			{
				
				if(rows[j] > 0)
				{
					if(rows[j]==3 || rows[j]==4)
					{
						list.add(new Point(j, i));
					}
					else if(rows[j]>=1 && rows[j]<=5)
					{	
						//�����λ��
						int dx1 =  j * Constant.MIN_UNIT + left;
						int dy1 =  i * Constant.MIN_UNIT + top;
						//��&��
						int dx2 = dx1 + Constant.MIN_UNIT;
						int dy2 = dy1 + Constant.MIN_UNIT;
						
						//����ͼƬ��ui.gif��λ��
						int sx1 = (rows[j] - 1) * Constant.MIN_UNIT;
						int sy1 = 192;
						//��С
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
			}
		}
		
		super.draw(g);
		
		for(Point p:list)
		{
			int dx1 =  p.x * Constant.MIN_UNIT + left;
			int dy1 =  p.y * Constant.MIN_UNIT + top;
			
			int dx2 = dx1 + Constant.MIN_UNIT;
			int dy2 = dy1 + Constant.MIN_UNIT;
			
			byte[] rows = currentMap[p.y];
			
			int sx1 = (rows[p.x] - 1) * Constant.MIN_UNIT;
			int sy1 = 192;
			
			int sx2 = sx1 + Constant.MIN_UNIT;
			int sy2 = sy1 + Constant.MIN_UNIT;
			
			
			g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
		list.clear();
		
	}
	@Override
	public void dispatcher(MsgInfo info)
	{
		super.dispatcher(info);
		if(protectedTime >= 0)
		{
			protectedTime --;
			if(protectedTime == 0)
			{
				cancelProtected();
			}
		}
		if(this.getHeroTanks().size() == 0)
		{
			this.getStageScene().showGameOver();
		}
		
	}
	public void hitMaps(MsgInfo info)
	{
		if(info!=null)
		{
			for(HitMapMsg msg:info.getHitMaps())
			{
				currentMap[msg.getX()][msg.getY()] = 0;
			}
		}
	}
	public void initMap()
	{
		if(Constant.currentStage == 0 )
		{
			currentMap = StageMap.copy(StageMap.map0);
		}
		else
		{
			currentMap = StageMap.copy(StageMap.list.get(Constant.currentStage -1));
		}
		
	}

	/**
	 * ���̹�����ͼ�Ƿ���ײ
	 * x�� ̹�˵�x
	 * y�� ̹�˵�y
	 * type�� ̹�˵ķ���
	 * true�� ����ײ false û����ײ
	 */
	public boolean tankHitMap(int x,int y,DirectionType type)
	{
		int row = 0; //��
		int col = 0;//��
		int overlap = 4;//�غ�����Ĵ�С���ݴ��С
		int blockNum = 2;//Ҫ���ĸ���
		
		if(DirectionType.UP == type)
		{
			//���ݴ�
			row = (y - top + overlap) / Constant.MIN_UNIT;
			col = (x - left + overlap) / Constant.MIN_UNIT;
		}
		else if(DirectionType.DOWN == type)
		{
			//���ݴ�
			row = (y - top + Constant.TANK_SIZE - overlap)/Constant.MIN_UNIT;
			col = (x - left + overlap) / Constant.MIN_UNIT;
		}
		else if(DirectionType.LEFT == type)
		{
			//���ݴ�
			row = (y - top + overlap) / Constant.MIN_UNIT;
			col = (x - left + overlap) / Constant.MIN_UNIT;
		}
		else if(DirectionType.RIGHT == type)
		{
			//���ݴ�
			row = (y - top + overlap)/Constant.MIN_UNIT;
			col = (x - left + Constant.TANK_SIZE - overlap) / Constant.MIN_UNIT;
		}
		
		if(row < 0 || row>=Constant.MAP_SIZE || col <0 || col >=Constant.MAP_SIZE)
		{
			return true;
		}
		if(DirectionType.UP == type || DirectionType.DOWN == type)
		{
			//����̹�˵��Ϸ������·��ٽ�����������λ��
			int w = x - left - col * Constant.MIN_UNIT - overlap;
			if(w > 0)
			{
				blockNum = 3;
			}
			//��������λ�ã��Ƿ񲻿�ͨ��
			for(int i=0;i<blockNum && (col+i)< Constant.MIN_UNIT;i++)
			{
				byte value = this.currentMap[row][col+i];
				if(value == 1 || value ==2 ||value ==4 || value ==6 ||value==7)
				{
					return true;
				}
			}
		}
		else
		{
			//���ҵ�λ��
			int w = y - top - row * Constant.MIN_UNIT - overlap;
			if(w > 0)
			{
				blockNum = 3;
			}

			for(int i=0;i<blockNum && (i+row)<Constant.MAP_SIZE ;i++)
			{
				byte value = this.currentMap[row+i][col];
				if(value == 1 || value ==2 ||value ==4 || value ==6 ||value==7)
				{
					return true;
				}
			}
		}

		return false;
	}
	/**
	 * ����ӵ����ͼ�Ƿ���ײ
	 * x�� �ӵ���x
	 * y�� �ӵ���y
	 * type�� �ӵ��ķ���
	 * true�� ����ײ false û����ײ
	 */
	public boolean bulletHitMap(int x,int y,DirectionType type,AbstractTankSprite sprite)
	{
		int row = 0; //��
		int col = 0;//��
		int blockNum = 1;//��С���ĸ���
		
		if(DirectionType.UP == type)
		{
			row = (y - top) / Constant.MIN_UNIT;
			col = (x - left) / Constant.MIN_UNIT;
		}
		else if(DirectionType.DOWN == type)
		{
			row = (y - top + Constant.BULLET_SIZE) / Constant.MIN_UNIT;
			col = (x - left)/ Constant.MIN_UNIT;
		}
		else if(DirectionType.LEFT == type)
		{
			row = (y - top) / Constant.MIN_UNIT;
			col = (x - left) / Constant.MIN_UNIT;
		}
		else if(DirectionType.RIGHT == type)
		{
			row = (y - top) /Constant.MIN_UNIT;
			col = (x - left + Constant.BULLET_SIZE) / Constant.MIN_UNIT;
		}
		if(row <0 || row >= Constant.MAP_SIZE || col < 0  || col >= Constant.MAP_SIZE)
		{
			return true;
		}
		boolean flag = false;
		if(DirectionType.UP == type || DirectionType.DOWN == type)
		{
			int w = x - left - col * Constant.MIN_UNIT;
			if((w + Constant.BULLET_SIZE) > Constant.MIN_UNIT)
			{
				blockNum = 2;
			}
			for(int i=0;i<blockNum && (i+col)<Constant.MAP_SIZE;i++)
			{
				byte value = this.currentMap[row][col+i];
				if(value ==2 || value ==1 || value==6||value==7)
				{
					flag  = true;
					if(value == 1)
					{
						this.currentMap[row][col+i] = 0;
					}
					else if(value==2 && sprite.isCanEatFe())
					{
						this.currentMap[row][col+i] = 0;
					}
					else if(value==6||value ==7)
					{
						this.getStageScene().showGameOver();
					}
				}
			}
		}
		else
		{
			int w = y - top - row * Constant.MIN_UNIT;
			if((w + Constant.BULLET_SIZE) > Constant.MIN_UNIT)
			{
				blockNum = 2;
			}
			for(int i=0;i<blockNum && (i+row)<Constant.MAP_SIZE;i++)
			{
				byte value = this.currentMap[row+i][col];
				if(value ==2 || value ==1 || value==6||value==7)
				{
					flag  = true;
					if(value == 1)
					{
						this.currentMap[row+i][col] = 0;
					}
					else if(value==2 && sprite.isCanEatFe())
					{
						this.currentMap[row+i][col] = 0;
					}
					else if(value==6||value ==7)
					{
						this.getStageScene().showGameOver();
					}
				}
				
				
			}
		}
		if(flag)
		{
			return true;
		}
		
		return false;
	}
	
	//��ӥ�ı仯  ���Ƿ�GameOver
	public void showDie()
	{
		this.currentMap[24][12] = 8;
	}
	/**
	 * ���home����
	 */
	public void protectedHome()
	{
		protectedTime = 500;
		for(int i=0;i<homeLocal.length;i++)
		{
			int [] p = homeLocal[i];
			this.currentMap[p[0]][p[1]] = 2;
		}
	}
	/**
	 * ���home����
	 */
	public void cancelProtected()
	{
		protectedTime = -1;
		for(int i=0;i<homeLocal.length;i++)
		{
			int [] p = homeLocal[i];
			this.currentMap[p[0]][p[1]] = 1;
		}
	}
	
	class Point 
	{
		private int x;
		private int y;
		public int getX()
		{
			return x;
		}
		public void setX(int x)
		{
			this.x = x;
		}
		public int getY()
		{
			return y;
		}
		public void setY(int y)
		{
			this.y = y;
		}
		public Point(int x, int y)
		{
			super();
			this.x = x;
			this.y = y;
		}
	}
}
 