
public class Location{

	private int x;//某个棋盘位置横坐标，0-14
	private int y;//某个棋盘位置纵坐标，0-14
	
	private int owner;//占据该位置的棋手方，1是人类，-1是机器，0是空
	private int score;//对该位置的打的分数

	public Location(){}
	public Location(int x, int y, int owner){
		this.x = x;
		this.y = y;
		this.owner = owner;
	}
	public Location(int x, int y, int owner, int score){
		this(x, y, owner);
		this.score = score;
	}

	public int getX(){return this.x;}
	public void setX(int x){this.x = x;}
	
	public int getY(){return this.y;} 
	public void setY(int y){this.y = y;}

	public int getOwner(){return this.owner;}
	public void setOwner(int owner){this.owner = owner;}

	public int getScore(){return this.score;}
	public void setScore(int score){this.score = score;}
	

}