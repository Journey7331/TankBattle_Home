package constant;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import enums.GameModel;
import util.ImageUtil;

public class Constant {
	/**
	 * ���ڱ���
	 */
	public static String MAIN_TITLE = "HUA&WEI Battle City";

	/**
	 * С����Ĵ�С
	 */
	public final static int MIN_UNIT = 32;
	/**
	 * ��ͼ�Ĵ�С
	 */
	public final static int MAP_WIDTH = 832;
	/**
	 * ̹�˵Ĵ�С
	 */
	public final static int TANK_SIZE = 64;
	/**
	 * �ӵ��Ĵ�С
	 */
	public final static int BULLET_SIZE = 12;
	/**
	 * ��ͼ�Ĵ�С
	 */
	public final static int MAP_SIZE = 26;
	/**
	 * ���ڿͻ�������Ĵ�С�� ���ڱ������ĸ�+�˵����ĸ�
	 */
	public static int CLIENT_TOP;
	/**
	 * ���ڵĿ�
	 */
	public static int WIDTH;
	/**
	 * ���ڵĸ�
	 */
	public static int HEIGHT;
	/**
	 * ����ͼƬ�ز�
	 */
	public static Image uiImage = ImageUtil.loadImage("ui.gif");
	/**
	 * ��ǰ��֡��
	 */
	public static long currentFPS = 0;
	/**
	 * 16�Ŵ���
	 */
	public static Font font_bold_16 = new Font("����", Font.BOLD, 16);
	public static Font font_16 = new Font("����", Font.PLAIN, 16);

	/**
	 * 26�Ŵ���
	 */
	public static Font font_bold_26 = new Font("����", Font.BOLD, 26);
	/**
	 * 40�Ŵ���
	 */
	public static Font font_bold_40 = new Font("����", Font.BOLD, 40);
	/**
	 * ѡ�����Ϸģʽ
	 */
	public static GameModel selectModel = GameModel.PLAYER;
	/**
	 * ��̨�ı���ɫ
	 */
	public static Color stage_bg_Color = new Color(127, 127, 127);
	/**
	 * ��ǰ�Ĺؿ�
	 */
	public static int currentStage = 2;

	/**
	 * �Ƿ���ͣ��Ϸ
	 */
	public static boolean isPause = false;
	/**
	 * �Ƿ�����Ч
	 */
	public static boolean isPlayAudio = true;

}
