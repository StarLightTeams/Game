package gameType.chuachua.tools;

import java.util.ArrayList;
import java.util.List;

import gameType.chuachua.config.GameConfig;
import gameType.chuachua.entity.Ball;
import gameType.chuachua.entity.Board;
import gameType.chuachua.entity.Brick;
import gameType.chuachua.entity.bfbData.BfbBall;
import gameType.chuachua.entity.bfbData.BfbBoard;
import gameType.chuachua.entity.bfbData.BfbBrick;

public class ConverTool {

	public static BfbBrick conver_brick(Brick brick){
		BfbBrick bfbbrick = new BfbBrick();
		bfbbrick.bg_height = YueFenTool.yueFen(brick.height*1.00/GameConfig.HEIGHT);//��
		bfbbrick.bg_width = YueFenTool.yueFen(brick.width*1.00/GameConfig.WIDTH);//��
		bfbbrick.bg_locX  = YueFenTool.yueFen(brick.locX*1.00/GameConfig.WIDTH);//x
		bfbbrick.bg_locY  = YueFenTool.yueFen(brick.locY*1.00/GameConfig.HEIGHT);//y
		bfbbrick.bPropsId = brick.bPropsId;//ש�����id
		bfbbrick.hardness = brick.hardness;//Ӳ��
		return bfbbrick;
	}
	public static BfbBall conver_ball(Ball ball){
		BfbBall bfbball =new BfbBall();
		bfbball.bf_d = YueFenTool.yueFen(ball.d*1.00/GameConfig.HEIGHT);//��
		bfbball.bf_bx= YueFenTool.yueFen(ball.bx*1.00/GameConfig.WIDTH);
		bfbball.bf_by= YueFenTool.yueFen(ball.by*1.00/GameConfig.HEIGHT);
		bfbball.ySpeed = ball.ySpeed;//��
		bfbball.xSpeed  = ball.xSpeed;//x
		bfbball.xA  = ball.xA;//y
		bfbball.yA = ball.yA;//ש�����id
		bfbball.degree = ball.degree;//Ӳ��
		return bfbball;
	}
	public static BfbBoard conver_board(Board board){
		BfbBoard bfbvoard =new BfbBoard();
		bfbvoard.bf_width = YueFenTool.yueFen(board.width/GameConfig.WIDTH); //��
		bfbvoard.bf_height = YueFenTool.yueFen(board.height/GameConfig.HEIGHT);//��
		bfbvoard.bf_locX = YueFenTool.yueFen(board.locX/GameConfig.WIDTH);  //λ��x
		bfbvoard.bf_locY = YueFenTool.yueFen(board.locY/GameConfig.HEIGHT);  //λ��y
		bfbvoard.ySpeed = board.ySpeed;//�ƶ��ٶ�
		bfbvoard.yA = board.yA;	  //�ƶ����ٶ�
		return bfbvoard;
	}
	
	public static Brick reduction_brick(BfbBrick bfbBrick,int width,int height) {
		Brick brick = new Brick();
		brick.height = (int) (bfbBrick.bg_height*height);//��*ClientConfig.GAMEHEIGHT
		brick.width = (int)(bfbBrick.bg_width*width);//��*ClientConfig.GAMEWIDTH
		brick.locX  = bfbBrick.bg_locX*width;;//x*ClientConfig.GAMEWIDTH
		brick.locY  = bfbBrick.bg_locY*height;//y*ClientConfig.GAMEHEIGHT
		brick.bPropsId = brick.bPropsId;//ש�����id
		brick.hardness = brick.hardness;//Ӳ��
		return brick;
	}
	
	public static Ball reduction_ball(BfbBall bfbball,int width,int height){
		Ball ball =new Ball();
		ball.d = (int)(bfbball.bf_d*height);//��*ClientConfig.GAMEHEIGHT
		ball.bx= bfbball.bf_bx*width;//*ClientConfig.GAMEWIDTH
		ball.by= bfbball.bf_by*height;//*ClientConfig.GAMEHEIGHT
		ball.ySpeed = bfbball.ySpeed;//��
		ball.xSpeed  = bfbball.xSpeed;//x
		ball.xA  = bfbball.xA;//y
		ball.yA = bfbball.yA;//ש�����id
		ball.degree = bfbball.degree;//Ӳ��
		return ball;
	}
	
	public static Board reduction_board(BfbBoard bfbvoard,int width,int height){
		Board board = new Board();
		board.width = (int) (bfbvoard.bf_width*width); //��*ClientConfig.GAMEWIDTH
		board.height = (int) (bfbvoard.bf_height*height);//��*ClientConfig.GAMEHEIGHT
		board.locX = bfbvoard.bf_locX*width;  //λ��x*ClientConfig.GAMEWIDTH
		board.locY = bfbvoard.bf_locY*height;  //λ��y*ClientConfig.GAMEHEIGHT
		board.ySpeed = board.ySpeed;//�ƶ��ٶ�
		board.yA = board.yA;	  //�ƶ����ٶ�
		return board;
	}
	
	public static List<Brick> reduction_brickList(List<BfbBrick> bfbBricks,int width,int height) {
		List<Brick> bricks = new ArrayList<Brick>();
		for(int i=0;i<bfbBricks.size();i++) {
			BfbBrick bfbBrick = bfbBricks.get(i);
			bricks.add(reduction_brick(bfbBrick,width,height));
		}
		return bricks;
	}
	
	public static List<Ball> reduction_ballList(List<BfbBall> bfbballs,int width,int height){
		List<Ball> balls = new ArrayList<Ball>();
		for(int i=0;i<bfbballs.size();i++) {
			BfbBall bfbball = bfbballs.get(i);
			balls.add(reduction_ball(bfbball,width,height));
		}
		return balls;
	}
	
	public static List<Board> reduction_boardList(List<BfbBoard> bfbBoards,int width,int height){
		List<Board> boards = new ArrayList<Board>();
		for(int i=0;i<bfbBoards.size();i++) {
			BfbBoard bfbBoard = bfbBoards.get(i);
			boards.add(reduction_board(bfbBoard,width,height));
		}
		return boards;
	}
	
	public static BfbBoard conver_board(Board board,int width,int height){
		BfbBoard bfbvoard =new BfbBoard();
		bfbvoard.bf_width = YueFenTool.yueFen(board.width*1.00/width); //��ClientConfig.GAMEREALYWIDTH
		bfbvoard.bf_height = YueFenTool.yueFen(board.height*1.00/height);//��ClientConfig.GAMEREALYHEIGHT
		bfbvoard.bf_locX = YueFenTool.yueFen(board.locX*1.00/width);  //λ��xClientConfig.GAMEREALYWIDTH
		bfbvoard.bf_locY = YueFenTool.yueFen(board.locY*1.00/height);  //λ��yClientConfig.GAMEREALYHEIGHT
		bfbvoard.ySpeed = board.ySpeed;//�ƶ��ٶ�
		bfbvoard.yA = board.yA;	  //�ƶ����ٶ�
		return bfbvoard;
	}
	

	public static BfbBrick conver_brick(Brick brick,int width,int height){
		BfbBrick bfbbrick = new BfbBrick();
		bfbbrick.bg_height = YueFenTool.yueFen(brick.height*1.00/height);//��ClientConfig.GAMEREALYHEIGHT
		bfbbrick.bg_width = YueFenTool.yueFen(brick.width*1.00/width);//��ClientConfig.GAMEREALYWIDTH
		bfbbrick.bg_locX  = YueFenTool.yueFen(brick.locX*1.00/width);//xClientConfig.GAMEREALYWIDTH
		bfbbrick.bg_locY  = YueFenTool.yueFen(brick.locY*1.00/height);//yClientConfig.GAMEREALYHEIGHT
		bfbbrick.bPropsId = brick.bPropsId;//ש�����id
		bfbbrick.hardness = brick.hardness;//Ӳ��
		return bfbbrick;
	}
	public static BfbBall conver_ball(Ball ball,int width,int height){
		BfbBall bfbball =new BfbBall();
		bfbball.bf_d = YueFenTool.yueFen(ball.d*1.00/height);//��ClientConfig.GAMEREALYHEIGHT
		bfbball.bf_bx= YueFenTool.yueFen(ball.bx*1.00/width);//ClientConfig.GAMEREALYWIDTH
		bfbball.bf_by= YueFenTool.yueFen(ball.by*1.00/height);//ClientConfig.GAMEREALYHEIGHT
		bfbball.ySpeed = ball.ySpeed;//��
		bfbball.xSpeed  = ball.xSpeed;//x
		bfbball.xA  = ball.xA;//y
		bfbball.yA = ball.yA;//ש�����id
		bfbball.degree = ball.degree;//Ӳ��
		return bfbball;
	}
	
	public static List<BfbBoard> conver_boardList(List<Board> boards,int width,int height) {
		List<BfbBoard> bfbBoards = new ArrayList<BfbBoard>();
		for(int i=0;i<boards.size();i++) {
			Board board = boards.get(i);
			bfbBoards.add(conver_board(board,width,height));
		}
		return bfbBoards;
	}
	
	public static List<BfbBall> conver_ballList(List<Ball> balls,int width,int height){
		List<BfbBall> bfbBalls = new ArrayList<BfbBall>();
		for(int i=0;i<balls.size();i++) {
			Ball ball = balls.get(i);
			bfbBalls.add(conver_ball(ball,width,height));
		}
		return bfbBalls;
	}
	
	public static List<BfbBrick> conver_brickList(List<Brick> bricks,int width,int height){
		List<BfbBrick> bfbBricks = new ArrayList<BfbBrick>();
		for(int i=0;i<bricks.size();i++) {
			Brick brick = bricks.get(i);
			bfbBricks.add(conver_brick(brick,width,height));
		}
		return bfbBricks;
	}
}
