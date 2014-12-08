package com.monkeymusicchallenge.warmup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.json.JSONArray;
import org.json.JSONObject;

public class AI {

	private class AStarComparator implements Comparator<Path> {
		@Override
		public int compare(Path p1, Path p2) {
			return p1.getHeuristic() - p2.getHeuristic();
		}
	}
	
	Map map;
	ArrayList<String> moves = new ArrayList<String>();
	String finalMove;
	Comparator<Path> comparator = new AStarComparator();
	boolean initialize = false;
	int numberOfTurns = 0;
	
	HashMap<String, Path> hmPath = new HashMap<String, Path>();
	
	public String move(final JSONObject gameState) {
		if(!initialize) {
			numberOfTurns = gameState.getInt("turns");
			initialize = true;
		}
		// Every game has a limited number of turns. Use every turn wisely!
		final int remainingNumberOfTurns = gameState.getInt("turns");
		
		// The level layout is a 2D-matrix (an array of arrays).
		//
		// Every element in the matrix is a string. The string tells you what's
		// located at the corresponding position on the level.
		//
		// In the warmup challenge, your objective is to find all music items
		// and deliver them to the eagerly waiting Spotify user.
		//
		// "empty": an empty tile, you can move here
		// "monkey": your monkey, this is where you're currently at
		// "song" / "album" / "playlist": a music item, go get them!
		// "user": go here once you've picked up all music items
		//
		// Too easy for you? Good...
		//
		// The real fun begins when the warmup is over and the competition begins!
		final JSONArray currentLevelLayout = gameState.getJSONArray("layout");
		
		/*
		// This is an array of all music items you've currently picked up
		final JSONArray pickedUpMusicItems = gameState.getJSONArray("pickedUp");
		
		// The position attribute tells you where your monkey is
		final JSONArray currentPositionOfMonkey = gameState.getJSONArray("position");
		*/
		
		// Speaking of positions...
		//
		// X and Y coordinates can be confusing.
		// Which way is up and which way is down?
		//
		// Here is an example explaining how coordinates work in
		// Monkey Music Challenge:
		//
		// {
		//   "layout": [["empty", "monkey"]
		//              ["song",  "empty"]]
		//   "position": [0, 1],
		//   ...
		// }
		//
		// The "position" attribute tells you the location of your monkey
		// in the "layout" matrix. In this example, you're at layout[0][1].
		//
		// If you send { "command": "move", "direction": "down", ... }
		// to the server, you'll get back:
		//
		// {
		//   "layout": [["empty", "empty"]
		//              ["song",  "monkey"]]
		//   "position": [1, 1]
		// }
		//
		// If you instead send { "command": "move", "direction": "left", ... }
		// to the server, you'll get back:
		//
		// {
		//   "layout": [["monkey", "empty"]
		//              ["song",   "empty"]]
		//   "position": [0, 0]
		// }
		//
		// So what about picking stuff up then?
		//
		// It's simple!
		//
		// Just stand next to something you want to pick up and move towards it.
		//
		// For example, say our current game state looks like this:
		//
		// {
		//   "layout": [["empty", "empty"]
		//              ["song",  "monkey"]]
		//   "position": [1, 1],
		//   "pickedUp": []
		// }
		//
		// When you send { "command": "move", "direction": "left", ... }
		// to the server, you'll get back:
		//
		//   "layout": [["empty",  "empty"]
		//              ["empty",  "monkey"]]
		//   "position": [1, 1],
		//   "pickedUp": ["song"],
		//   ...
		// }
		//
		// Instead of moving, your monkey successfully picked up the song!
		//
		// Got it? Sweet! This message will self destruct in five seconds...
		
		// This will always return the string "monkey" - get it?
		
		
		/*
		 final String monkey = currentLevelLayout
		    .getJSONArray(currentPositionOfMonkey.getInt(0))
		    .getString(currentPositionOfMonkey.getInt(1));
		 */
		if(remainingNumberOfTurns > (numberOfTurns - 1)) {
			map = new Map(currentLevelLayout);
			Path currentPath = new Path(map, new ArrayList<String>(), 0, map.getHeuristic());
			PriorityQueue<Path> allPaths = new PriorityQueue<Path>(100, comparator);
			int debug = 0;
			while(currentPath.getRemainingHeuristic() != 0) {
				Map tempMap = currentPath.getMap();
				
				if(tempMap.canMove("down")) {
					Path p = new Path(currentPath);
					p.applyMove("down");
					if(p.getHeuristic() <= numberOfTurns) {
						Path p2 = hmPath.get(p.getStringMap());
						if(p2 != null && p.getNrOfSteps() < p2.getNrOfSteps()) {
							p2.setOldPath(true);
							allPaths.add(p);
						}
					}
				}
				
				if(tempMap.canMove("up")) {
					Path p = new Path(currentPath);
					p.applyMove("up");
					if(p.getHeuristic() <= numberOfTurns) {
						Path p2 = hmPath.get(p.getStringMap());
						if(p2 != null && p.getNrOfSteps() < p2.getNrOfSteps()) {
							p2.setOldPath(true);
							allPaths.add(p);
						}
					}
				}
				
				if(tempMap.canMove("left")) {
					Path p = new Path(currentPath);
					p.applyMove("left");
					if(p.getHeuristic() <= numberOfTurns) {
						Path p2 = hmPath.get(p.getStringMap());
						if(p2 != null && p.getNrOfSteps() < p2.getNrOfSteps()) {
							p2.setOldPath(true);
							allPaths.add(p);
						}
					}
				}
				
				if(tempMap.canMove("right")) {
					Path p = new Path(currentPath);
					p.applyMove("right");
					if(p.getHeuristic() <= numberOfTurns) { 
						Path p2 = hmPath.get(p.getStringMap());
						if(p2 != null && p.getNrOfSteps() < p2.getNrOfSteps()) {
							p2.setOldPath(true);
							allPaths.add(p);
						}
					}
				}
				
				allPaths.remove(currentPath);
				currentPath = allPaths.poll();
				while(currentPath.getOldPath() == true) {
					currentPath = allPaths.poll();
				}
				debug++;
				if(debug == 10000) {
					System.out.println(currentPath.moves.toString());
					debug = 0;
				}
			}
			moves = currentPath.moves;
			finalMove = currentPath.map.getFinalMove();
			System.out.println(moves.toString());
		}
		
		//System.out.println(map.toString());
		// TODO: You may want to do something smarter here
		if(remainingNumberOfTurns > 30-moves.size()) {
		} else {
			return finalMove;
		}
		
		final Map<String, Object> nextCommand = new HashMap<String, Object>();
	    nextCommand.put("command", "move");
	    nextCommand.put("direction", moves.get(numberOfTurns-remainingNumberOfTurns));
	    return nextCommand;
	}
	/*
	private String randomDirection() {
		  return new String[] {"up", "down", "left", "right"}[ThreadLocalRandom.current().nextInt(4)];
	}
	  
	int[] testInt = new int[] {0,0,3,3,1,1};
	  
	private String moveSmarter(int i) {
		String[] test = new String[] {"up", "down", "left", "right"};
		if(i > 5) {
			return "right";
		} else {
			return test[testInt[i]];
		}
	}
	*/
}
