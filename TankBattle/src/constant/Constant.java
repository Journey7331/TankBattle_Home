package constant;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import enums.GameModel;
import util.ImageUtil;

public class Constant {
	/**
	 * 窗口标题
	 */
	public static String MAIN_TITLE = "HUA&WEI Battle City";

	/**
	 * 小方块的大小
	 */
	public final static int MIN_UNIT = 32;
	/**
	 * 地图的大小
	 */
	public final static int MAP_WIDTH = 832;
	/**
	 * 坦克的大小
	 */
	public final static int TANK_SIZE = 64;
	/**
	 * 子弹的大小
	 */
	public final static int BULLET_SIZE = 12;
	/**
	 * 地图的大小
	 */
	public final static int MAP_SIZE = 26;
	/**
	 * 窗口客户区顶点的大小即 窗口标题栏的高+菜单栏的高
	 */
	public static int CLIENT_TOP;
	/**
	 * 窗口的宽
	 */
	public static int WIDTH;
	/**
	 * 窗口的高
	 */
	public static int HEIGHT;
	/**
	 * 精灵图片素材
	 */
	public static Image uiImage = ImageUtil.loadImage("ui.gif");
	/**
	 * 当前的帧率
	 */
	public static long currentFPS = 0;
	/**
	 * 16号粗体
	 */
	public static Font font_bold_16 = new Font("宋体", Font.BOLD, 16);
	public static Font font_16 = new Font("宋体", Font.PLAIN, 16);

	/**
	 * 26号粗体
	 */
	public static Font font_bold_26 = new Font("宋体", Font.BOLD, 26);
	/**
	 * 40号粗体
	 */
	public static Font font_bold_40 = new Font("宋体", Font.BOLD, 40);
	/**
	 * 选择的游戏模式
	 */
	public static GameModel selectModel = GameModel.PLAYER;
	/**
	 * 舞台的背景色
	 */
	public static Color stage_bg_Color = new Color(127, 127, 127);
	/**
	 * 当前的关卡
	 */
	public static int currentStage = 2;

	/**
	 * 是否暂停游戏
	 */
	public static boolean isPause = false;
	/**
	 * 是否开启音效
	 */
	public static boolean isPlayAudio = true;

}
