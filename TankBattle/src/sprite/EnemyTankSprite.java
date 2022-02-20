package sprite;

import java.awt.Graphics;

import constant.Constant;
import enums.AudioType;
import enums.DirectionType;
import enums.TankType;
import layer.MapLayer;
import msg.MsgInfo;
import msg.NewSpriteMsg;
import util.AudioUtil;
import util.RandomUtil;

public class EnemyTankSprite extends AbstractTankSprite
{
	private static final long serialVersionUID = 6633968570711574866L;
	private int localX[] = {0,384,768};
	private int frameCount = 0;
	private double turnRate = 0;
	private double fireRate = 0;

	public EnemyTankSprite(MapLayer mapLayer)
	{
		super(mapLayer);
		
		this.setTankType(TankType.valueOf(RandomUtil.nextInt(3)));;
		this.setY(MapLayer.top);
		this.setX(MapLayer.left + localX[RandomUtil.nextInt(3)]);
		this.setDirectionType(DirectionType.valueOf(RandomUtil.nextInt(4)));
		
		this.setW(Constant.TANK_SIZE);
		this.setH(Constant.TANK_SIZE);
		
		if(this.getTankType() == TankType.NORAML_ENEMY)
		{
			this.setSpeed(3);
			this.setLife(1);
			this.turnRate = 0.008;
			this.fireRate = 0.4;
		}
		else if(this.getTankType() == TankType.MIDDLE_ENEMY)
		{
			this.setSpeed(2);
			this.setLife(2);
			this.turnRate = 0.004;
			this.fireRate = 0.5;
		}
		else if(this.getTankType()== TankType.HEIGHT_ENEMY)
		{
			this.setSpeed(1);
			this.setLife(3);
			this.turnRate = 0.002;
			this.fireRate = 0.6;
		}
		
	}
	public EnemyTankSprite(MapLayer mapLayer, NewSpriteMsg msg)
	{
		super(mapLayer);
		this.setId(msg.getId());
		this.setTankType(TankType.valueOf(msg.getType()));;
		this.setY(msg.getY());
		this.setX(msg.getX());
		this.setDirectionType(DirectionType.valueOf(msg.getDir()));
		
		this.setW(Constant.TANK_SIZE);
		this.setH(Constant.TANK_SIZE);
		
		if(this.getTankType() == TankType.NORAML_ENEMY)
		{
			this.setSpeed(3);
			this.setLife(1);
			this.turnRate = 0.008;
			this.fireRate = 0.4;
		}
		else if(this.getTankType()== TankType.MIDDLE_ENEMY)
		{
			this.setSpeed(2);
			this.setLife(2);
			this.turnRate = 0.004;
			this.fireRate = 0.5;
		}
		else if(this.getTankType() == TankType.HEIGHT_ENEMY)
		{
			this.setSpeed(1);
			this.setLife(3);
			this.turnRate = 0.002;
			this.fireRate = 0.6;
		}
	}
	@Override
	public void draw(Graphics g)
	{
		int dx1 = this.getX();
		int dy1 = this.getY();
		int dx2 = dx1 + this.getW();
		int dy2 = dy1 + this.getH();
		
		int sx1 = 0,sy1 =0,sx2=0,sy2=0;
		
		if(frameCount < 98)
		{
			sx1 = 512 + (frameCount % 49 / 7) * this.getW();
			sy1 = 64;
			sx2 = sx1 + this.getW();
			sy2 = sy1 +  this.getH();
			
			frameCount ++;
		}
		else
		{
			if(this.isProtected())
			{
				this.setProtected(false);
			}
			if(this.getTankType() == TankType.NORAML_ENEMY)
			{
				sx1 = this.getDirectionType().ordinal() * this.getW();
				sy1 = 64;
			}
			else if(this.getTankType() == TankType.MIDDLE_ENEMY)
			{
				sx1 = (4 + this.getDirectionType().ordinal()) * this.getW();
				sy1 = 64;
			}
			else if(this.getTankType() == TankType.HEIGHT_ENEMY)
			{
				sx1 =  (this.getDirectionType().ordinal() + (3 - this.getLife()) * 4 ) * this.getW();
				sy1 = 128;
			}
			sx2 = sx1 + this.getW();
			sy2 = sy1 + this.getH();
			
		}
		g.drawImage(Constant.uiImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	@Override
	public void dispatcher(MsgInfo info)
	{
		if(!this.isProtected())
		{
			move();
		}
		
	}

	@Override
	public void fire()
	{
		if(!this.isShooting() && frameCount % 60 ==0)
		{
			double rate = RandomUtil.nextDouble();
			if(this.fireRate > rate)
			{
				AudioUtil.playAudio(AudioType.ENEMY_FIRE);
				this.getMapLayer().addSprite(new BulletSprite(getMapLayer(), this));
			}
		}
		
	}

	@Override
	public void move()
	{
		int tempX = this.getX();
		int tempY = this.getY();
		if(this.getDirectionType() == DirectionType.UP)
		{
			tempY = tempY - this.getSpeed();
		}
		else if(this.getDirectionType() == DirectionType.DOWN)
		{
			tempY = tempY + this.getSpeed();
		}
		else if(this.getDirectionType() == DirectionType.LEFT)
		{
			tempX = tempX - this.getSpeed();
		}
		else if(this.getDirectionType() == DirectionType.RIGHT)
		{
			tempX = tempX + this.getSpeed();
		}
		if(!hitBorder(tempX, tempY, this.getDirectionType()) && !this.getMapLayer().tankHitMap(tempX, tempY, this.getDirectionType()))
		{
			frameCount++;
			this.setX(tempX);
			this.setY(tempY);
			
			double rate = RandomUtil.nextDouble();
			if(this.turnRate > rate)
			{
				this.setDirectionType(DirectionType.randomType());
			}
			fire();
		}
		else
		{
			this.setDirectionType(DirectionType.randomType(this.getDirectionType().ordinal()));
		}

	}
	@Override
	public void hit()
	{
		this.setLife(this.getLife() -1);
		if(this.getLife() <=0)
		{
			this.setLive(false);
		}
	}

}
