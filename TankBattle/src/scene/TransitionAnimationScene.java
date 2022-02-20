package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import constant.Constant;
import enums.AudioType;
import enums.TransitionAnimationType;
import msg.MsgInfo;
import util.AudioUtil;
/**
 	* 转场动画场情
 */

public class TransitionAnimationScene extends AbstractBaseScene
{

	private TransitionAnimationType animationType;
	private int h = 0;
	private TransitionAnimationDelegate delegate;
	private long enterCompletedTime = -1;
	private boolean isConstruction = false;
	
	
	public TransitionAnimationScene()
	{
		
	}
	public void addDelegate(TransitionAnimationDelegate delegate)
	{
		this.delegate = delegate;
	}
	@Override
	public void draw(Graphics g)
	{

		if(animationType == TransitionAnimationType.ENTER_ANIMATION)
		{
			fillRect(g);
			if(h >= Constant.HEIGHT / 2)
			{
				Font font = g.getFont();
				
				g.setFont(Constant.font_bold_26);
				if(this.isConstruction())
				{
					g.drawString("构建地图中...", Constant.WIDTH / 2-45, Constant.HEIGHT / 2  + 8);
				}
				else
				{
					if(Constant.currentStage == 0)
					{
						g.drawString("构建中...", Constant.WIDTH / 2-65, Constant.HEIGHT / 2  + 8);
					}
					else
					{
						g.drawString("STAGE "+Constant.currentStage, Constant.WIDTH / 2-65, Constant.HEIGHT / 2  + 8);
					}
					
				}
			
				g.setFont(font);
				if(enterCompletedTime == -1)
				{
					enterCompletedTime  = System.currentTimeMillis();
				}
				else
				{
					long time = System.currentTimeMillis();
					if(time - enterCompletedTime > 1000)
					{
						if(delegate != null)
						{
							delegate.enterCompleted();
						}
					}
				}
				
			}
			
		}
		else if(animationType == TransitionAnimationType.EXIT_ANIMATION)
		{
			fillRect(g);
			if(h == 0)
			{
				if(delegate !=null)
				{
					delegate.exitCompleted();
				}
			}
			
		}
	}

	@Override
	public void dispatcher(MsgInfo info)
	{
		if(animationType == TransitionAnimationType.ENTER_ANIMATION)
		{
			h = h + 10;
			if(h >= Constant.HEIGHT / 2)
			{
				h = Constant.HEIGHT / 2;
			}
		}
		else if(animationType == TransitionAnimationType.EXIT_ANIMATION)
		{
			h = h - 10;
			if(h <=0)
			{
				h = 0;
			}
		}

	}
	public void fillRect(Graphics g)
	{
		
		Color color = g.getColor();
		g.setColor(Constant.stage_bg_Color);
		
		g.fillRect(0, 0, Constant.WIDTH, this.h + 1);
		
		g.fillRect(0, Constant.HEIGHT - this.h, Constant.WIDTH, this.h + 1);
		
		g.setColor(color);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{

	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

	public TransitionAnimationType getAnimationType()
	{
		return animationType;
	}

	public void setAnimationType(TransitionAnimationType animationType)
	{
		this.animationType = animationType;
		
		if(animationType == TransitionAnimationType.ENTER_ANIMATION)
		{
			this.h = 0;
			if(!this.isConstruction)
			{
				AudioUtil.playAudio(AudioType.GAME_START);
			}
			
		}
		else
		{
			this.h = Constant.HEIGHT / 2;
		}
		
	}
	public boolean isConstruction()
	{
		return isConstruction;
	}
	public void setConstruction(boolean isConstruction)
	{
		this.isConstruction = isConstruction;
	}
	

}
