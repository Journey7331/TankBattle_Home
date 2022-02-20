package sprite;

import java.awt.Graphics;

import constant.Constant;
import enums.AudioType;
import enums.BombType;
import msg.MsgInfo;
import msg.NewSpriteMsg;
import util.AudioUtil;
/**
 * ±¬Õ¨¾«Áé
 */

public class BombSprite extends AbstractBaseSprite
{
	private static final long serialVersionUID = 7503725037679659738L;
	private BombType type;
	private int frameCount = 0;
	
	public BombSprite(int x,int y,BombType bombType,boolean isPlayAudio)
	{
		this.type = bombType;
		if(bombType == BombType.NORAML)
		{
			this.setX(x - 32);
			this.setY(y - 32);
			this.setW(64);
			this.setH(64);
		}
		else
		{
			this.setX(x - 66);
			this.setY(y - 66);
			this.setW(132);
			this.setH(132);
		}
		if(isPlayAudio)
		{
			if(bombType == BombType.NORAML)
			{
				AudioUtil.playAudio(AudioType.NORMAL_BOMB);
			}
			else
			{
				AudioUtil.playAudio(AudioType.BIG_BOMB);
			}
		}
	}
	
	public BombSprite(NewSpriteMsg msg)
	{
		this.setId(msg.getId());
		this.type = BombType.valueOf(msg.getDir());
		this.setX(msg.getX());
		this.setY(msg.getY());
		if(this.type == BombType.NORAML)
		{
			
			this.setW(64);
			this.setH(64);
		}
		else
		{
			this.setW(132);
			this.setH(132);
		}
	}

	@Override
	public void draw(Graphics g)
	{
		
		int dx1 = this.getX();
		int dy1 = this.getY();
		int dx2 = dx1 + this.getW();
		int dy2 = dy1 + this.getH();
		
		int sx1 = 0,sy1 =0,sx2 = 0,sy2 = 0;
		
		if(this.type == BombType.NORAML)
		{
			sx1 = 640 + (frameCount / 6) * this.getW();
			sy1 = 0;
			
			if(frameCount == 18)
			{
				this.setLive(false);
			}
		}
		else
		{
			sx1 =(frameCount / 6)*this.getW();
			sy1 = 320;
			
			if(frameCount == 24)
			{
				this.setLive(false);
			}
		}
		sx2 = sx1 + this.getW();
		sy2 = sy1 + this.getH();
		
		g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
			
	}

	@Override
	public void dispatcher(MsgInfo info)
	{
		frameCount++;
	}

	public BombType getType()
	{
		return type;
	}

	public void setType(BombType type)
	{
		this.type = type;
	}

}
