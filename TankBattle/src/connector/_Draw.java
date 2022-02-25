package connector;

import java.awt.Graphics;

import msg.MsgInfo;

public interface _Draw {
	// draw things
	public void draw(Graphics g);
	// change info
	public void dispatcher(MsgInfo info);
}
