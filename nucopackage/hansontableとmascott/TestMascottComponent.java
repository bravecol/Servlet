package jp.maceration.component.mascott;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import jp.maceration.component.mascott.data.MascottMoveData;
import jp.maceration.util.RandomUtil;

/**
 * Mascott Component Test Class.
 * 
 * @author Masatoshi.H
 *
 */
public class TestMascottComponent {

	/**
	 * Test Case 1 Method.
	 * execute Mascott Component.
	 */
	@Test
	public void testCase1_executeMascott_wait() {
		try {
			System.out.println("<Start> Test Case 1 Starting.");
			System.out.println("");
			
			// Create Mascott Component.
			final MascottComponent mascott = new MascottComponent();
			mascott.init(null);
			
			// Set Mascott Move data list.
			{
				// Init.
				{
					String action = "INIT";
					List<MascottMoveData> moves = new ArrayList<MascottMoveData>();
					mascott.addMascotMoveDataList(moves, "./images/Nuco.png", null, null);
					
					mascott.setMascottActionList(action, moves);
				}
				
				// move left.
				{
					String action = "MOVE_LEFT";
					List<MascottMoveData> moves = new ArrayList<MascottMoveData>();
					mascott.addMascotMoveDataList(moves, "./images/Nuco.png", "3", null);
					
					mascott.setMascottActionList(action, moves);
				}
				
				// move up.
				{
					String action = "MOVE_UP";
					List<MascottMoveData> moves = new ArrayList<MascottMoveData>();
					mascott.addMascotMoveDataList(moves, "./images/Nuco.png", null, "-3");
					
					mascott.setMascottActionList(action, moves);
				}
				
				// move right.
				{
					String action = "MOVE_RIGHT";
					List<MascottMoveData> moves = new ArrayList<MascottMoveData>();
					mascott.addMascotMoveDataList(moves, "./images/Nuco.png", "-3", null);
					
					mascott.setMascottActionList(action, moves);
				}
				
				// move down.
				{
					String action = "MOVE_DOWN";
					List<MascottMoveData> moves = new ArrayList<MascottMoveData>();
					mascott.addMascotMoveDataList(moves, "./images/Nuco.png", null, "3");
					
					mascott.setMascottActionList(action, moves);
				}
				
				// stop.
				{
					String action = "STOP";
					List<MascottMoveData> moves = new ArrayList<MascottMoveData>();
					mascott.addMascotMoveDataList(moves, "./images/Nuco.png", null, null);
					
					mascott.setMascottActionList(action, moves);
				}
			}
			
			// Execute Mascott Component.
			new Thread(new Runnable() {
				@Override
				public void run() {
					mascott.execute();
				}
			}).start();
			
			// Waiting 300sec.
			{
				int sec = 300;
				for (int i = 0; i < sec; i++) {
					if (mascott.exit) break;
					Thread.sleep(1000);
					java.lang.System.out.print(".");
					
					// Get Random move.
					int rand = RandomUtil.getRandomInt(1, 100);
					if (0 <= rand && rand < 10) {
						mascott.setAction("MOVE_DOWN");
					}
					else if (10 <= rand && rand < 20) {
						mascott.setAction("MOVE_RIGHT");
					}
					else if (20 <= rand && rand < 30) {
						mascott.setAction("MOVE_UP");
					}
					else if (30 <= rand && rand < 40) {
						mascott.setAction("MOVE_LEFT");
					}
					else {
						mascott.setAction("STOP");
					}
				}
				java.lang.System.out.println("");
			}
			
			mascott.exit();
			System.out.println("Mascott Component was exit.");
			System.out.println("");
			
			System.out.println("< End > Test Case 1 Successful.");
			
		} catch (Exception e) {
			// exception
			e.printStackTrace();
			fail();
		}
	}

}
