package layer;

import java.awt.Graphics;
import java.io.Serializable;

import connector._Draw;
import constant.Constant;
import msg.MsgInfo;

public class GameOverLayer implements _Draw,Serializable
{
	private static final long serialVersionUID = 3208884615330440501L;
	private int y = Constant.HEIGHT - Constant.MIN_UNIT * 3;
	private int y1 = Constant.HEIGHT / 2 - 2* Constant.MIN_UNIT;
	
	private GameOverLayerDelegate delegate;
	private boolean isCompleted = false;
	
	public void addDelegate(GameOverLayerDelegate delegate)
	{
		this.delegate = delegate;
	}
	@Override
	public void draw(Graphics g)
	{
		int dx1 = MapLayer.left + 11 * Constant.MIN_UNIT;
		int dy1 = y;
		int dx2 = dx1 + 128;
		int dy2 = dy1 + 64;
		
		int sx1 = 768;
		int sy1 = 128;
		int sx2 = sx1 + 128;
		int sy2 = sy1 + 64;
		
		g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

	}

	@Override
	public void dispatcher(MsgInfo info)
	{
		if(y <= y1)
		{
			y = y1;
			if(!isCompleted)
			{
				isCompleted = true;
				if(delegate != null)
				{
					delegate.animationCompleted();
				}
			}
			
		}
		else
		{
			y = y - 10;
		}
	}

}
