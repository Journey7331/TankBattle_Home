package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import constant.Constant;
import enums.GameModel;
import mainframe.MainFrame;
import msg.MsgInfo;
import sprite.SelectTankSprite;
import util.ImageUtil;

public class MenuScene extends AbstractBaseScene {
    private Image bgImage = ImageUtil.loadImage("menu.gif");

    private int y = Constant.HEIGHT;
    private SelectTankSprite selectTank = new SelectTankSprite();

    private int selectIndex = 0;
    /**
     * 代理对象
     */
    private MenuDelegate delegate;

    private static String showMsg = null;
    private static boolean isSelect = false;

    public MenuScene() {

    }

    /**
     * 绘制画面上的所有组件
     * @Param [g]
     * @Return void
     **/
    @Override
    public void draw(Graphics g) {
        g.drawImage(bgImage, 0, y, null);
        if (y == Constant.CLIENT_TOP) {
            selectTank.draw(g);
            drawFPS(g);
        }
        if (showMsg != null) {
            Color color = g.getColor();
            Font font = g.getFont();
            g.setColor(Color.RED);
            g.setFont(Constant.font_16);


            g.drawString(showMsg, 10, Constant.HEIGHT - 20);

            g.setColor(color);
            g.setFont(font);
        }

    }

    /**
     * 添加代理
     */
    public void addDelegate(MenuDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void dispatcher(MsgInfo info) {
        if (y <= Constant.CLIENT_TOP) {
            y = Constant.CLIENT_TOP;
            if (!this.isAcceptEvent()) {
                this.setAcceptEvent(true);
                if (delegate != null) {
                    delegate.showCompleted();
                }
            }
        } else {
            y = y - 20;
        }


    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (showMsg != null && keyCode == KeyEvent.VK_ENTER && "准备完成,按回车键开始游戏...".equals(showMsg)) {
            if (delegate != null) {
                delegate.selectCompleted();
            }

            return;
        }
        if (isSelect) {
            return;
        }

        if (keyCode == KeyEvent.VK_UP) {
            selectIndex = (selectIndex - 1 + 2) % 2;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            selectIndex = (selectIndex + 1) % 2;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            if (selectModel(selectIndex)) {
                if (Constant.selectModel != GameModel.INTERNET) {
                    if (delegate != null) {
                        delegate.selectCompleted();
                    }
                }

            }
        }
        selectTank.setY(500 + Constant.CLIENT_TOP + (selectIndex * 60));

    }

    public boolean selectModel(int select) {
        MainFrame.app.showTitle(Constant.MAIN_TITLE);
        if (0 == select) {
            Constant.selectModel = GameModel.PLAYER;
            return true;
        } else if (1 == select) {
            Constant.selectModel = GameModel.PLAYERS;

            return true;
        }
        return false;
    }

    public static void showMsg(String msg) {
        showMsg = msg;
    }
    
    public static void showMsg(String msg, boolean select) {
        showMsg = msg;
        isSelect = select;
    }
}
