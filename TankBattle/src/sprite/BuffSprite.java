package sprite;

import java.awt.Graphics;

import constant.Constant;
import enums.BuffType;
import layer.MapLayer;
import msg.MsgInfo;
import msg.NewSpriteMsg;
import util.RandomUtil;
/**
 * 宝物精灵
 */

public class BuffSprite extends AbstractBaseSprite
{
	private static final long serialVersionUID = -953572910998323058L;
	/**
	 * 宝物的类型
	 */
	private BuffType buffType;
	/**
	 * 显示的时间
	 */
	private int showTime = 600;
	
	public static boolean isShow = false;
	
	public BuffSprite()
	{
		isShow = true;
		this.buffType = BuffType.valueOf(RandomUtil.nextInt(6));
		
		this.setW(60);
		this.setH(58);

		this.setX(MapLayer.left + RandomUtil.nextInt(Constant.MAP_WIDTH - 60));
		this.setY(MapLayer.top + RandomUtil.nextInt(Constant.MAP_WIDTH - 60));
		
	}
	public BuffSprite(NewSpriteMsg msg)
	{
		this.setId(msg.getId());
		isShow = true;
		this.buffType = BuffType.valueOf(msg.getDir());
		
		this.setW(60);
		this.setH(58);

		this.setX(msg.getX());
		this.setY(msg.getY());
		
	}
	@Override
	public void draw(Graphics g)
	{
		
		int dx1 = this.getX();
		int dy1 = this.getY();
		int dx2 = dx1 + this.getW();
		int dy2 = dy1 + this.getH();
		
		int sx1 = 512 + this.buffType.ordinal() * this.getW();
		int sy1 = 220;
		int sx2 = sx1 + this.getW();
		int sy2 = sy1 + this.getH();
		
		g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		
	}

	@Override
	public void dispatcher(MsgInfo info)
	{
		showTime--;
		if(showTime ==0)
		{
			isShow = false;
			this.setLive(false);
		}
	}
	public BuffType getBuffType()
	{
		return buffType;
	}
}
