package com.monkeymusicchallenge.warmup;

import java.util.ArrayList;

public class Path implements Comparable<Path> {
	
	Map map;
	ArrayList<String> moves;
	int nrOfSteps;
	int heuristic;
	boolean oldPath = false;
	
	public Path(Map map, ArrayList<String> moves, int nrOfSteps, int heuristic) {
		this.map = new Map(map);
		this.moves = new ArrayList<String>(moves);
		this.nrOfSteps = nrOfSteps;
		this.heuristic = heuristic;
	}
	
	public Path(Path p) {
		this.map = new Map(p.map);
		this.moves = new ArrayList<String>(p.moves);
		this.nrOfSteps = p.nrOfSteps;
		this.heuristic = p.heuristic;
		//System.out.println(moves);
	}
	
	public int getNrOfSteps() {
		return this.nrOfSteps;
	}
	
	public ArrayList<String> getMoves() {
		return this.moves;
	}
	
	public Map getMap() {
		return this.map;
	}
	
	public int getRemainingHeuristic() {
		return this.heuristic;
	}
	
	public int getHeuristic() {
		return this.heuristic + this.nrOfSteps;
	}
	
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	
	public void applyMove(String move) {
		map.applyMove(move);
		this.nrOfSteps++;
		this.heuristic = map.getHeuristic();
		this.moves.add(move);
	}
	
	public String getStringMap() {
		return map.toString();
	}
	
	public void setOldPath(boolean oldPath){
		this.oldPath = oldPath;
	}
	
	public boolean getOldPath() {
		return this.oldPath;
	}
	
	@Override
	public int compareTo(Path p) {
		return -Integer.compare(p.getHeuristic(), this.getHeuristic());
	}
	
}
