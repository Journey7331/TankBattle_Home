package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import connector._Draw;
import connector._KeyAction;
import constant.Constant;

public abstract class AbstractBaseScene implements _Draw, _KeyAction, Serializable {

	private static final long serialVersionUID = -2922632940766544960L;
	private Color backgroudColor = Color.BLACK;
	private boolean isVisible = false;
	private boolean acceptEvent = false;

	private int fpsX, fpsY;

	public AbstractBaseScene() {
		this.fpsX = Constant.WIDTH - 40;
		this.fpsY = Constant.HEIGHT - 20;
	}

	public void start() {
	}

	public void remoteKeyEvent(KeyEvent e, boolean isPressed) {

	}

	public void drawFPS(Graphics g) {
		Color color = g.getColor();
		Font font = g.getFont();
		g.setColor(Color.WHITE);
		g.setFont(Constant.font_bold_16);
		g.drawString(Constant.currentFPS + "", this.fpsX, this.fpsY);

		g.setColor(color);
		g.setFont(font);

	}

	public Color getBackgroudColor() {
		return backgroudColor;
	}

	public void setBackgroudColor(Color backgroudColor) {
		this.backgroudColor = backgroudColor;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isAcceptEvent() {
		return acceptEvent;
	}

	public void setAcceptEvent(boolean acceptEvent) {
		this.acceptEvent = acceptEvent;
	}

	public int getFpsX() {
		return fpsX;
	}

	public void setFpsX(int fpsX) {
		this.fpsX = fpsX;
	}

	public int getFpsY() {
		return fpsY;
	}

	public void setFpsY(int fpsY) {
		this.fpsY = fpsY;
	}

}
