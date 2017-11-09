package gameType.chuachua.tools;

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
		bfbbrick.bg_height = YueFenTool.yueFen(brick.height*1.00/GameConfig.HEIGHT);//高
		bfbbrick.bg_width = YueFenTool.yueFen(brick.width*1.00/GameConfig.WIDTH);//宽
		bfbbrick.bg_locX  = YueFenTool.yueFen(brick.locX*1.00/GameConfig.WIDTH);//x
		bfbbrick.bg_locY  = YueFenTool.yueFen(brick.locY*1.00/GameConfig.HEIGHT);//y
		bfbbrick.bPropsId = brick.bPropsId;//砖块道具id
		bfbbrick.hardness = brick.hardness;//硬度
		return bfbbrick;
	}
	public static BfbBall conver_ball(Ball ball){
		BfbBall bfbball =new BfbBall();
		bfbball.bf_d = YueFenTool.yueFen(ball.d*1.00/GameConfig.HEIGHT);//高
		bfbball.bf_bx= YueFenTool.yueFen(ball.bx*1.00/GameConfig.WIDTH);
		bfbball.bf_by= YueFenTool.yueFen(ball.by*1.00/GameConfig.HEIGHT);
		bfbball.ySpeed = ball.ySpeed;//宽
		bfbball.xSpeed  = ball.xSpeed;//x
		bfbball.xA  = ball.xA;//y
		bfbball.yA = ball.yA;//砖块道具id
		bfbball.degree = ball.degree;//硬度
		return bfbball;
	}
	public static BfbBoard conver_board(Board board){
		BfbBoard bfbvoard =new BfbBoard();
		bfbvoard.bf_width = YueFenTool.yueFen(board.width/GameConfig.WIDTH); //宽
		bfbvoard.bf_height = YueFenTool.yueFen(board.height/GameConfig.HEIGHT);//高
		bfbvoard.bf_locX = YueFenTool.yueFen(board.locX/GameConfig.WIDTH);  //位置x
		bfbvoard.bf_locY = YueFenTool.yueFen(board.locY/GameConfig.HEIGHT);  //位置y
		bfbvoard.ySpeed = board.ySpeed;//移动速度
		bfbvoard.yA = board.yA;	  //移动加速度
		return bfbvoard;
	}
}
