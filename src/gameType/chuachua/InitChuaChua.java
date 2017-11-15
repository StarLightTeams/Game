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
	//��ʼ�����߱�
	//�����Ҫ�����ݿ��ж�̬����
	public static void initBoardProps(Game game){
		/*
		 * ѡ�����ֵ
		 * 
		 */
		/*
		 * 1 - �����١� ��2 - ��������  �� 3 - �����١� �� 4 - ���෢�� 
		 */
		game.boardPropsmap.put(1,new BoardProps("1","����","�з�����"));
		game.boardPropsmap.put(2,new BoardProps("2","����","С���������(��Χ��)"));
		game.boardPropsmap.put(3,new BoardProps("3","����","�෢����"));
		game.boardPropsmap.put(4,new BoardProps("4","�෢","С��෢"));
	}
	//��ʼ��ש��
	public static void initSticks(Game game){
		for(int i=0;i<3;i++){
			for(int j=0;j<6;j++){
				Brick brick =new Brick();
				brick.height = 100;
				brick.width = 100;
				brick.locX = 20+j*100;
				brick.locY = i*100;
				//ש������Ӳ�Ⱥ��������
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
				//ש������Ӳ�Ⱥ��������
				brick.bPropsId = getRandomBProps(bprobability);
				brick.hardness = getRandomHardness(probability);
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
		game.myborad = new Board(220,60,210,434,20,5);//�Լ��İ�
		game.enemyborad = new Board(220,60,210,702,20,5);//�з��İ�
		
	}
	/**
	 * ���ݸ������ɵ���
	 * @param probability
	 * @return
	 */
	public static String getRandomBProps(Map<String,Double> bprobability) {
		Random rand = new Random();
		double randNum = rand.nextDouble();
		List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(bprobability.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,Double>>() {
			//��������
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
	 * ���ݸ������û�Ӳ��
	 * @param probability �ȼ�,����
	 * @return
	 */
	public static int getRandomHardness(Map<Integer,Double> probability) {
		Random rand = new Random();
		double randNum = rand.nextDouble();
		List<Map.Entry<Integer,Double>> list = new ArrayList<Map.Entry<Integer,Double>>(probability.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<Integer,Double>>() {
			//��������
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
