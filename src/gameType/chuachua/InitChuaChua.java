package gameType.chuachua;

import gameType.chuachua.entity.Ball;
import gameType.chuachua.entity.Board;
import gameType.chuachua.entity.Brick;
import gameType.chuachua.entity.Game;

public class InitChuaChua {
	
	public static Game  initData(){
		Game game =new Game();
		initSticks(game);
		initBalls(game);
		initBoard(game);
		return game;
	}
	//��ʼ��ש��
	public static void initSticks(Game game){
		for(int i=0;i<4;i++){
			for(int j=0;j<6;j++){
				Brick brick =new Brick();
				brick.height = 100;
				brick.width = 100;
				brick.locX = 20+j*100;
				brick.locY = i*100;
				brick.bPropsId = "";
				brick.hardness = 1;
				game.myBrickList.add(brick);
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
				game.enemyBrickList.add(brick);
			}
		}
	}
	//��ʼ��С��
	public static void initBalls(Game game){
		Ball ball =new Ball(64,320,568,10,10,1,1,45);
		game.ball_list.add(ball);
	}
	//��ʼ��ľ��
	public static void initBoard(Game game){
		game.myborad = new Board(220,60,210,432,20,5);//�Լ��İ�
		game.enemyborad = new Board(220,60,210,164,20,5);//�з��İ�
		
	}
}
