package gameType.chuachua;

import gameType.chuachua.data.ChuaChuaGameMainData;
import gameType.chuachua.entity.Ball;
import gameType.chuachua.entity.Board;
import gameType.chuachua.entity.Brick;

public class InitChuaChua {

	public static void  initData(){
		initSticks();
		initBalls();
		initBoard();
	}
	//初始化砖块
	public static void initSticks(){
		for(int i=0;i<4;i++){
			for(int j=0;j<6;j++){
				Brick brick =new Brick();
				brick.height = 100;
				brick.width = 100;
				brick.locX = 20+j*100;
				brick.locY = i*100;
				brick.bPropsId = "";
				brick.hardness = 1;
				ChuaChuaGameMainData.myBrickList.add(brick);
			}
		}
		for(int i=0;i<4;i++){
			for(int j=0;j<6;j++){
				Brick brick =new Brick();
				brick.height = 100;
				brick.width = 100;
				brick.locX = 20+j*100;
				brick.locY = 836+i*100;
				brick.bPropsId = "";
				brick.hardness = 1;
				ChuaChuaGameMainData.enemyBrickList.add(brick);
			}
		}
	}
	//初始化小球
	public static void initBalls(){
		Ball ball =new Ball(64,320,568,10,10,1,1,45);
		ChuaChuaGameMainData.ball_list.add(ball);
	}
	//初始化木板
	public static void initBoard(){
		ChuaChuaGameMainData.myborad = new Board(220,60,210,432,20,5);//自己的板
		ChuaChuaGameMainData.enemyborad = new Board(220,60,210,164,20,5);//敌方的板
		
	}
}
