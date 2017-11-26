package gameType.chuachua.entity;

import config.GameConfig;
import tool.ClientTools;

public class Ball {

	/**
	 * 球
	 */
	public int d;//半径
	public double bx;//球的x坐标
	public double by;//球的y坐标
	public double ySpeed;//球的y轴速度
	public double xSpeed;//球的x轴速度
	public double xA;//球的x轴加速度
	public double yA;//球的y轴加速的
	public double degree;//球的角度
	
	public Ball() {
	}
	public Ball(int d, double bx, double by, double ySpeed, double xSpeed,
			double xA, double yA, double degree) {
		super();
		this.d = d;
		this.bx = bx;
		this.by = by;
		this.ySpeed = ySpeed;
		this.xSpeed = xSpeed;
		this.xA = xA;
		this.yA = yA;
		this.degree = degree;
	}
	
	public void move(double t) {
		if(bx<=0 || bx>=gameType.chuachua.config.GameConfig.WIDTH-d){
			degree = Math.PI-degree;
		}
		if(by<=0 || by>=gameType.chuachua.config.GameConfig.HEIGHT-2*d){
			degree = -degree;
		}
		bx = bx+xSpeed*Math.cos(degree);
		by = by+ySpeed*Math.sin(degree);
	}
	
	public void ballHitBoard2(Ball ball ,Board board){
		
		int change = 0;
		
		double bx = ball.getBx()+ball.getD()/2;
		double by = ball.getBy()+ball.getD()/2;
		
		//左范围块
		double leftSX = board.getLocX()-ball.getD()/2;
		double leftEX = board.getLocX()+ball.getD()/2;
		double leftSY = board.getLocY();
		double leftEY = board.getLocY()+board.getHeight();
		if(bx>leftSX && bx<leftEX && by>=leftSY&&by<=leftEY){
			if(change==0){				
				ball.setDegree(Math.PI - ball.getDegree());
				change = 1;
			}
		}
		
		//上范围块
		double upSX = board.getLocX();
		double upEX = board.getLocX()+board.getWidth();
		double upSY = board.getLocY()-ball.getD()/2;
		double upEY = board.getLocY()+ball.getD()/2;
		if(bx>=upSX && bx<=upEX && by>upSY && by<upEY){
			if(change==0){
				ball.setDegree(-ball.getDegree());
				change =1;
			}
		}
		
		//右范围块
		double rightSX = board.getLocX()+board.getWidth()-ball.getD()/2;
		double rightEX = board.getLocX()+board.getWidth()+ball.getD()/2;
		double rightSY = board.getLocY();
		double rightEY = board.getLocY()+board.getHeight();
		if(bx>rightSX && bx <rightEX && by>=rightSY && by<=rightEY){
			if(change==0){
				ball.setDegree(Math.PI - ball.getDegree());
				change =1;
			}
		}
		
		//下范围块
		double downSX = board.getLocX();
		double downEX = board.getLocX()+board.getWidth();
		double downSY = board.getLocY()+board.getHeight()-ball.getD()/2;
		double downEY = board.getLocY()+board.getHeight()+ball.getD()/2;
		if(bx >= downSX && bx<=downEX && by >downSY &&by<downEY){
			if(change == 0){				
				ball.setDegree(-1*ball.getDegree());
				change = 1;
			}
		}
		
		//左上角范围块
		double leftUpSX = board.getLocX()-ball.getD()/2;
		double leftUpEX = board.getLocX();
		double leftUpSY = board.getLocY() - ball.getD()/2;
		double leftUpEY = board.getLocY();
		double leftUpBBX = board.getLocX();
		double leftUpBBY = board.getLocY();
		if(bx>=leftUpSX && bx<=leftUpEX && by>=leftUpSY && by<=leftUpEY){
			double distance = Math.sqrt((bx-leftUpBBX)*(bx-leftUpBBX)+(by-leftUpBBY)*(by-leftUpBBY));
			if(ball.getD()<=distance){
				if(change == 0){
					ball.setDegree(Math.PI-Math.abs(ball.getDegree()));
					change =1;
				}
			}
		}
		
		//右上角范围块
		double rightUpSX = board.getLocX()+board.getWidth();
		double rightUpEX = board.getLocX()+board.getWidth()+ball.getD()/2;
		double rightUpSY = board.getLocY() - ball.getD()/2;
		double rightUpEY = board.getLocY();
		double rightUpBBX = board.getLocX()+board.getWidth();
		double rightUpBBY = board.getLocY();
		if(bx>=rightUpSX && bx<=rightUpEX && by>=rightUpSY&& by<=rightUpEY){
			double distance = Math.sqrt((bx-rightUpBBX)*(bx-rightUpBBX)+(by-rightUpBBY)*(by-rightUpBBY));
			if(ball.getD()<=distance){
				if(change == 0){
					ball.setDegree(Math.PI-Math.abs(ball.getDegree()));
					change = 1;
				}
				
			}
		}
		
		//左下角范围块
		double leftDownSX = board.getLocX() - ball.getD()/2;
		double leftDownEX = board.getLocX();
		double leftDownSY = board.getLocY()+board.getHeight();
		double leftDownEY = board.getLocY()+board.getHeight()+ball.getD()/2;
		double leftDownBBX = board.getLocX();
		double leftDownBBY = board.getLocY()+board.getHeight();
		if(bx>=leftDownSX && bx<=leftDownEX && by>=leftDownSY&&by<=leftDownEY){
			double distance = Math.sqrt((bx-leftDownBBX)*(bx-leftDownBBX)+(by-leftDownBBY)*(by-leftDownBBY));
			if(ball.getD()<=distance){
				if(change == 0){
					ball.setDegree(Math.PI-Math.abs(ball.getDegree()));
					change = 1;
				}
			}
		}
		
		//右下角范围块
		double rightDownSX = board.getLocX()+board.getWidth();
		double rightDownEX = board.getLocX()+board.getWidth()+ball.getD()/2;
		double rightDownSY = board.getLocY()+board.getHeight();
		double rightDownEY = board.getLocY()+board.getHeight()+ball.getD()/2;
		double rightDownBBX = board.getLocX()+board.getWidth();
		double rightDownBBY = board.getLocY()+board.getHeight();
		if(bx >=rightDownSX && bx <=rightDownEX && by>=rightDownSY && by<=rightDownEY){
			double distance = Math.sqrt((bx-rightDownBBX)*(bx-rightDownBBX)+(by-rightDownBBY)*(by-rightDownBBY));
			if(ball.getD()<=distance){
				if(change == 0){
					ball.setDegree(Math.PI-Math.abs(ball.getDegree()));
					change =1;
				}
			}
		}
			
	}
	
	
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	public double getBx() {
		return bx;
	}
	public void setBx(double bx) {
		this.bx = bx;
	}
	public double getBy() {
		return by;
	}
	public void setBy(double by) {
		this.by = by;
	}
	public double getySpeed() {
		return ySpeed;
	}
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	public double getxSpeed() {
		return xSpeed;
	}
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	public double getxA() {
		return xA;
	}
	public void setxA(double xA) {
		this.xA = xA;
	}
	public double getyA() {
		return yA;
	}
	public void setyA(double yA) {
		this.yA = yA;
	}
	public double getDegree() {
		return degree;
	}
	public void setDegree(double degree) {
		this.degree = degree;
	}
	@Override
	public String toString() {
		return "Ball [d=" + d + ", bx=" + bx + ", by=" + by + ", ySpeed=" + ySpeed + ", xSpeed=" + xSpeed + ", xA=" + xA
				+ ", yA=" + yA + ", degree=" + degree + "]";
	}	
	
	
}
