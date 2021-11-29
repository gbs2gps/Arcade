public class Grid {
	private int size;
	private int numColors;
	private MyNode start;

public Grid(int size, int numColors){
	this.size = size;
	this.numColors = numColors;
	this.start = new MyNode("black");
}

public Grid(int size, int numColors, MyNode start){
	this.size = size;
	this.numColors = numColors;
	this.start = start;
}

public Grid sameGrid(Grid grid){
	int size = grid.getSize();
	int numColors = grid.getNumColors();
	MyNode otherStart = grid.getStartNode();
	MyNode secondStart = grid.getStartNode();

	MyNode start = new MyNode(otherStart.getData());
	MyNode topCorner = start;
		for(int i = 0; i < size; i++){
		MyNode current = start;
		for(int j = 0; j < size; j++){
			MyNode node = new MyNode(otherStart.getNext().getData());
			current.setNext(node);
			node.setPrev(current);
			current = node;
			otherStart = otherStart.getNext();
		}
			MyNode downNode = new MyNode(secondStart.getDown().getData());
			start.setDown(downNode);
			downNode.setUp(start);
			start = downNode;
			otherStart = secondStart.getDown();
			secondStart = otherStart;
		}

	MyNode starty = topCorner;
	int count = 0;
	for(int i = 0; i < size-1; i++){
		MyNode curr = starty;
		MyNode currDown = starty.getDown();
		for(int j = 0; j < size-1; j++){
			count++;
			System.out.println(count);
			curr.getNext().setDown(currDown.getNext());
			currDown.getNext().setUp(curr.getNext());
			curr = curr.getNext();
			currDown = currDown.getNext();
		}
		starty = starty.getDown();
	}

	return new Grid(size, numColors, topCorner);
	
}


public void createGrid(){
	MyNode other = start;
	for(int i = 0; i < size; i++){
		MyNode current = other;
		for(int j = 0; j < size; j++){
			MyNode node = new MyNode(generateColor());
			current.setNext(node);
			node.setPrev(current);
			current = node;
		}
		MyNode downNode = new MyNode(generateColor());
		other.setDown(downNode);
		downNode.setUp(other);
		other = downNode;
	}
	MyNode starty = start;
	int count = 0;
	for(int i = 0; i < size-1; i++){
		MyNode curr = starty;
		MyNode currDown = starty.getDown();
		for(int j = 0; j < size-1; j++){
			count++;
			System.out.println(count);
			curr.getNext().setDown(currDown.getNext());
			currDown.getNext().setUp(curr.getNext());
			curr = curr.getNext();
			currDown = currDown.getNext();
		}
		starty = starty.getDown();
	}

}

public void changeSize(int size){
	this.size = size;
	MyNode other = start;
	for(int i = 0; i < size; i++){
		MyNode current = other;
		for(int j = 0; j < size; j++){
			MyNode node = new MyNode(generateColor());
			current.setNext(node);
			node.setPrev(current);
			current = node;
		}
		MyNode downNode = new MyNode(generateColor());
		other.setDown(downNode);
		downNode.setUp(other);
		other = downNode;
	}
}

public void changeNumColors(int numColors){
	this.numColors = numColors;
	MyNode other = start;
	for(int i = 0; i < size; i++){
		MyNode current = other;
		for(int j = 0; j < size; j++){
			MyNode node = new MyNode(generateColor());
			current.setNext(node);
			node.setPrev(current);
			current = node;
		}
		MyNode downNode = new MyNode(generateColor());
		other.setDown(downNode);
		downNode.setUp(other);
		other = downNode;
	}
}

public MyNode getStartNode(){
	return start;
}

public int getSize(){
	return size;
}

public int getNumColors(){
	return numColors;
}

public String firstLine(){
	String toReturn = "";
	MyNode other = start;
	for(int i = 0; i < size; i++){
		toReturn += other.toString();
		other = other.getNext();
	}

	return toReturn;
}

public String toString(){
	String toReturn = "";
	MyNode other = start;
	for(int i = 0; i < size; i++){
		MyNode current = other;
		for(int j = 0; j < size; j++){
			toReturn += "    " + current.toString();
			current = current.getNext();
		}
	toReturn += "\n";
	other = other.getDown();
	}

	return toReturn;
}

public String generateColor(){
	double random = Math.random() * numColors;
	String color = "";
	if(random < 1){
		color = "red";
	} else if(random < 2){
		color = "green";
	} else if(random < 3){
		color = "blue";
	} else if(random < 4){
		color = "magenta";
	} else if(random < 5){
		color = "cyan";
	} else if(random < 6){
		color = "yellow";
	} else if(random < 7){
		color = "gray";
	} else if(random < 8){
		color = "orange";
	} else if(random < 9){
		color = "pink";
	} 

	return color;
}

}
