import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class Chessboard extends JPanel{

	public static final int CHESSBOARD_SIZE = 15;//棋盘大小15X15
	private ArrayList<Location> locationList = new ArrayList<>();//棋盘上所有可以落子的位置坐标等信息
	private Color backgroundColor = new Color(255, 245, 186);//棋盘背景色
	private Color lineColor = new Color(66, 66, 66);//棋盘线条颜色
	private int margin = 20;//棋盘边缘长度
	//private int cellSize = (this.getWidth() - 2*margin)/(CHESSBOARD_SIZE - 1);//每个格子的边长，这么做是错的！！

	//初始化棋盘
	public void init(){
		locationList.clear();
		repaint();
	}
	
	//覆盖paint方法
	public void paint(Graphics g){
		super.paint(g);
		drawChessboard(g);
		drawChessman(g);
	}

	//画棋盘
	public void drawChessboard(Graphics g){
		//先画背景
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		//画线
		g.setColor(lineColor);
		int cellSize = (this.getWidth() - 2*margin)/(CHESSBOARD_SIZE - 1);//每个格子的边长
		for(int i = 0; i < CHESSBOARD_SIZE; i++){
			g.drawLine(margin, margin + i*cellSize, this.getWidth() - margin, margin + i*cellSize);//画横线
			g.drawLine(margin + i*cellSize, margin, margin + i*cellSize, this.getHeight() - margin);//画纵线
		}
	}
	
	//画棋子
	public void drawChessman(Graphics g){
		for(int i = 0; i < locationList.size(); i++){
			Location loc = locationList.get(i);
			//根据先后手设置棋子为黑色和白色
			if(loc.getOwner() == Chess.FIRST){
				g.setColor(Color.BLACK);	
			}else{
				g.setColor(Color.WHITE);
			}
			
			int cellSize = (this.getWidth() - 2*margin)/(CHESSBOARD_SIZE - 1);//每个格子的边长
			//画棋子
			g.fillOval(margin + cellSize*loc.getX() - cellSize/2, margin + cellSize*loc.getY() - cellSize/2, cellSize, cellSize);
			
		}
	}

	//落子
	public void addChessman(int x, int y, int owner){
		locationList.add(new Location(x, y, owner));
		repaint();
	}

	//落子
	public void addChessman(Location loc){
		locationList.add(loc);
		repaint();
	}

	//计算棋盘每个小格子的大小
	public int getCellSize(){
		return (this.getWidth() - 2*margin)/(CHESSBOARD_SIZE - 1);
	}
	
	//判断棋盘是否还没有棋子
	public boolean isEmpty(){
		return locationList.size() == 0 ? true : false;
	}
}