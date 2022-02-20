package sprite;

import java.awt.Graphics;

import constant.Constant;
import msg.MsgInfo;

public class SelectTankSprite extends AbstractBaseSprite
{
	private static final long serialVersionUID = 3185524889304766002L;

	public SelectTankSprite()
	{
		this.setX(280);
		this.setY(500 + Constant.CLIENT_TOP);
		this.setW(54);
		this.setH(54);
	}
	
	@Override
	public void draw(Graphics g)
	{
		int dx1 = this.getX();
		int dy1 = this.getY();
		int dx2 = this.getX() + this.getW();
		int dy2 = this.getY() + this.getH();
		
		int sx1 = 256;
		int sy1 = 192;
		int sx2 = sx1 + this.getW();
		int sy2 = sy1 + this.getH();
		
		g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

	}

	@Override
	public void dispatcher(MsgInfo info)
	{

	}

}
