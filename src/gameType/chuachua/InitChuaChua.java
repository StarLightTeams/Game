package gameType.chuachua;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import gameType.chuachua.entity.Ball;
import gameType.chuachua.entity.Board;
import gameType.chuachua.entity.BoardProps;
import gameType.chuachua.entity.Brick;
import gameType.chuachua.entity.Game;

public class InitChuaChua {
	
	public static Map<String, Double> bprobability = new HashMap<String, Double>();
	public static Map<Integer,Double> probability = new HashMap<Integer, Double>();
	public static Game  initData(){
		Game game =new Game();
		initSticks(game);
		initBalls(game);
		initBoard(game);
		initBoardProps(game);
		return game;
	}
	//
	public void initHard(){
		
		probability.put(1, 0.7);
		probability.put(2, 0.2);
		probability.put(3, 0.1);
	}
	//初始化道具表
	//这边需要从数据库中动态配置
	public static void initBoardProps(Game game){
		/*
		 * 选随机定值
		 * 
		 */
		/*
		 * 1 - “减速” ，2 - “连击”  ， 3 - “加速” ， 4 - “多发” 
		 */
		game.boardPropsmap.put(1,new BoardProps("1","减速","敌方减速"));
		game.boardPropsmap.put(2,new BoardProps("2","连击","小球击中连击(范围伤)"));
		game.boardPropsmap.put(3,new BoardProps("3","加速","多发加速"));
		game.boardPropsmap.put(4,new BoardProps("4","多发","小球多发"));
	}
	//初始化砖块
	public static void initSticks(Game game){
		for(int i=0;i<3;i++){
			for(int j=0;j<6;j++){
				Brick brick =new Brick();
				brick.height = 100;
				brick.width = 100;
				brick.locX = 20+j*100;
				brick.locY = i*100;
				//砖块的随机硬度和随机道具
				brick.bPropsId = getRandomBProps(bprobability);
				brick.hardness = getRandomHardness(probability);
				game.myBrickList.add(brick);
			}
		}
		for(int i=0;i<3;i++){
			for(int j=0;j<6;j++){
				Brick brick =new Brick();
				brick.height = 100;
				brick.width = 100;
				brick.locX = 20+j*100;
				brick.locY = 836+i*100;
				//砖块的随机硬度和随机道具
				brick.bPropsId = getRandomBProps(bprobability);
				brick.hardness = getRandomHardness(probability);
				game.enemyBrickList.add(brick);
			}
		}
	}
	//初始化小球
	public static void initBalls(Game game){
		Ball ball =new Ball(64,320,568,10,10,1,1,45);
		game.ball_list.add(ball);
	}
	//初始化木板
	public static void initBoard(Game game){
		game.myborad = new Board(220,60,210,434,20,5);//自己的板
		game.enemyborad = new Board(220,60,210,702,20,5);//敌方的板
		
	}
	/**
	 * 根据概率生成道具
	 * @param probability
	 * @return
	 */
	public static String getRandomBProps(Map<String,Double> bprobability) {
		Random rand = new Random();
		double randNum = rand.nextDouble();
		List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(bprobability.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,Double>>() {
			//升序排序
			public int compare(Entry<String,Double> o1, Entry<String,Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
	    }); 
		double b=0,e=0;
		String bPropsId = "";
		for(int i=0;i<list.size();i++){
			e += list.get(i).getValue();
			if(randNum>=b && randNum <=e) {
				bPropsId = list.get(i).getKey();
				break;
			}
			b = e;
        } 
		return bPropsId;
	}
	
	/**
	 * 根据概率生敲击硬度
	 * @param probability 等级,概率
	 * @return
	 */
	public static int getRandomHardness(Map<Integer,Double> probability) {
		Random rand = new Random();
		double randNum = rand.nextDouble();
		List<Map.Entry<Integer,Double>> list = new ArrayList<Map.Entry<Integer,Double>>(probability.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<Integer,Double>>() {
			//升序排序
			public int compare(Entry<Integer,Double> o1, Entry<Integer,Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
	    }); 
		double b=0,e=0;
		int hardNum = 0;
		for(int i=0;i<list.size();i++){
			e += list.get(i).getValue();
			if(randNum>=b && randNum <=e) {
				hardNum = list.get(i).getKey();
				break;
			}
			b = e;
        } 
		return hardNum;
	}
}
