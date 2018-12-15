import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//界面类，这是游戏主体框架
public class UI{
	
	private JFrame frame;//五子棋游戏窗口
	
	//五子棋盘【关键】
	private Chessboard chessboard = new Chessboard();//五子棋盘
	//*****五子棋业务逻辑【关键】	
	private Chess chess = new Chess();	

	private JMenuBar menu;//菜单栏
	private JMenu option;//菜单栏中的“选项”菜单
	private Action replayOption;//“选项”下拉项中的“重玩一盘”选项
	private Action AIFirstOption;//“选项”下拉项中的“机器先手”选项
	private Action HumanFirstOption;//“选项”下拉项中的“人类先手”选项

	//游戏运行入口
	public static void main(String[] args){
		new UI().init();
	}	

	//完成五子棋游戏界面
	public void init(){

		frame = new JFrame("人机对战五子棋");//创建游戏界面窗口
		menu = new JMenuBar();//创建菜单栏
		option = new JMenu("选项");//创建菜单栏中的“选项”菜单
		
		//把“选项”菜单加入到菜单栏
		menu.add(option);
		
		//把“重玩一盘”、“机器先手”、“人类先手”加入“选项”下拉项中
		replayOptionInit();
		option.add(replayOption);
		AIFirstOptionInit();
		option.add(AIFirstOption);
		HumanFirstOptionInit();
		option.add(HumanFirstOption);		

		frame.setJMenuBar(menu);//把menu设置为frame的菜单栏
		frame.add(chessboard);//把五子棋盘加入到frame

		//初始化棋盘
		chessboard.init();
		chess.init();	
		
		//【【【最核心】】】绑定鼠标事件，要下棋了，为了避免写无用的抽象方法的实现，用适配器
		chessboard.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				//鼠标点击引发下棋事件，处理下棋事件比较繁琐，为此开一个方法
				play(e);
			}
		});	

		//设置frame窗口左上角图标
		frame.setIconImage(frame.getToolkit().getImage("image/gobang.png"));
		frame.setSize(518, 565);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		frame.setVisible(true);
	}
	
	

	//“重玩一盘”选项绑定相应的处理事件
	public void replayOptionInit(){
		replayOption = new AbstractAction("重玩一盘", new ImageIcon("image/replay.png")){
			public void actionPerformed(ActionEvent e){
				chessboard.init();//界面方面：初始化重来
				chess.init();//逻辑业务方面：初始化重来
			}
		};
	}

	//“机器先手”选项绑定相应的处理事件
	public void AIFirstOptionInit(){
		AIFirstOption = new AbstractAction("机器先手", new ImageIcon("image/robot.png")){
			public void actionPerformed(ActionEvent e){
				//棋盘还没有落子的时候可以选择“机器先手”，一旦有落子，选择“机器先手”失效
				if(chessboard.isEmpty()){
					Chess.FIRST = -1;
					//机器先手，则先在中间位置下一个棋子
					chessboard.addChessman(7, 7, -1);
					chess.addChessman(7, 7, -1);
				}
			}
		};
	}

	//“人类先手”选项绑定相应的处理事件
	public void HumanFirstOptionInit(){
		HumanFirstOption = new AbstractAction("人类先手", new ImageIcon("image/human.png")){
			public void actionPerformed(ActionEvent e){
				//棋盘还没有落子的时候可以选择“人类先手”，一旦有落子，选择“人类先手”失效
				if(chessboard.isEmpty()){	
					Chess.FIRST = 1;
				}
			}
		};
	}
	
	//【【【核心业务逻辑】】】处理鼠标落子事件
	public void play(MouseEvent e){
		int cellSize = chessboard.getCellSize();//每个格子的边长
		int x = (e.getX() - 5) / cellSize;//像素值转换成棋盘坐标
		int y = (e.getY() - 5) / cellSize;//像素值转换成棋盘坐标
		//判断落子是否合法
		boolean isLegal = chess.isLegal(x, y);
		//如果落子合法
		if(isLegal){
			chessboard.addChessman(x, y, 1);//界面方面加一个棋子
			chess.addChessman(x, y, 1);//逻辑业务方面加一个棋子
			
			//判断人类是否胜利
			if(chess.isWin(x, y, 1)){
				JOptionPane.showMessageDialog(frame, "人类获胜", "Congratulations，您赢了！", JOptionPane.PLAIN_MESSAGE);
				chessboard.init();
				chess.init();
				return;
			}
			
			//机器落子
			Location loc = chess.searchLocation();
			chessboard.addChessman(loc);
			chess.addChessman(loc.getX(), loc.getY(), loc.getOwner());

			//判断机器是否胜利
			if(chess.isWin(loc.getX(), loc.getY(), -1)){
				JOptionPane.showMessageDialog(frame, "机器获胜", "Congratulations，您输了！", JOptionPane.PLAIN_MESSAGE);
				chessboard.init();
				chess.init();
				return;
			}
		}
	}
	
}