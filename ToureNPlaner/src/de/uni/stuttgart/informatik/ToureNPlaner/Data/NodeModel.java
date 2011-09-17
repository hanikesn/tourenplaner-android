package de.uni.stuttgart.informatik.ToureNPlaner.Data;

import java.util.Observable;
import java.util.Vector;
public class NodeModel extends Observable{
	
	private static Vector<Node> nodeVector = new Vector<Node>();
	public static NodeModel instance = null;
	
	
	public static NodeModel getInstance() {
	      if(instance == null) {
	         instance = new NodeModel();
	      }
	      return instance;
	   }
	
	public Vector<Node> getNodeVector(){
		return nodeVector;
	}
	public Node get (Integer i ){
		return nodeVector.get(i);
	}
	public Integer size(){
		return nodeVector.size();
	}
	public  void addNodeToVector(Node node){
		nodeVector.add(node);
		setChanged();
		notifyObservers();
	}
	
	public  void removeNodeFromVector(Integer pos){
		nodeVector.remove(pos);
		setChanged();
		notifyObservers();
	}
	
	public  void swapNodes(Integer pos1, Integer pos2){
		Node tempNode = nodeVector.get(pos1);
		nodeVector.set(pos1, nodeVector.get(pos2));
		nodeVector.set(pos2, tempNode);
		setChanged();
		notifyObservers();
	}

}
