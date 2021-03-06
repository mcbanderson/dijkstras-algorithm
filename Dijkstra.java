package dijkstra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Dijkstra {
    public static void main(String args[]) {

    	try {
			Node head = createGraphFromFile("./p018_triangle_test.txt");
			head.setSum(head.getValue());
			Node smallestPathEndNode = findPath(head);
	        printPath(smallestPathEndNode);
		} catch (Exception e) {
			System.out.println("File p018_triangle_test.txt not found");
		}
        
        try {
			Node head = createGraphFromFile("./p018_triangle.txt");
			head.setSum(head.getValue());
			Node smallestPathEndNode = findPath(head);
	        printPath(smallestPathEndNode);
		} catch (Exception e) {
			System.out.println("File p018_triangle.txt not found");
		}
        
        try {
			Node head = createGraphFromFile("./p067_triangle.txt");
			head.setSum(head.getValue());
			Node smallestPathEndNode = findPath(head);
	        printPath(smallestPathEndNode);
		} catch (Exception e) {
			System.out.println("File p067_triangle.txt not found");
		}
    }
    
    public static Node findPath(Node head) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(1000, new NodeComparator());
    	int smallestSum = 1000000;
        Node smallestPathEndNode = new Node();
		queue.add(head);
        
    	while(!queue.isEmpty()) {
            Node temp = queue.remove();
            
            ArrayList<Node> edges = temp.getEdges();
            for(int i = 0; i < edges.size(); i++) {
        		if(temp.getSum() + edges.get(i).getValue() < edges.get(i).getSum()) {
            		queue.add(edges.get(i));
        			edges.get(i).setSum(temp.getSum() + edges.get(i).getValue());
        			edges.get(i).setParent(temp);
        		}
            }
            
            if(temp.getEdges().size() == 0) {
	            if(temp.getSum() < smallestSum) {
	            	smallestSum = temp.getSum();
	            	smallestPathEndNode = temp;
	            }
            }
        }
    	
    	return smallestPathEndNode;
    }
    
    public static void printPath(Node n) {
    	Node temp = n;
    	Stack<Node> stack = new Stack<Node>();
        System.out.println("The minimum total from top to bottom is " + temp.getSum());
        while(temp.hasParent()) {
    		stack.push(temp);
    		temp = temp.getParent();
    	}
        stack.push(temp);
        
        while(!stack.isEmpty()) {
    		System.out.print(stack.pop().getValue());
    		if(stack.size() > 0) {
    			System.out.print("->");
    		}
    	}
        System.out.println("\n");
    }
    
    public static Node createGraphFromFile(String file) throws Exception {
    	Node head = new Node();
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	
    	try {
    	    String line = br.readLine();
    	    ArrayList<Node> previousRow = new ArrayList<Node>();

    	    while (line != null) {
    	    	String[] items = line.split(" ");
    	    	ArrayList<Node> currentRow = new ArrayList<Node>();
    	    	
    	    	for(int i = 0; i < items.length; i++) {
    	    		currentRow.add(new Node(items[i]));
    	    	}
    	    	if(previousRow.size() != 0) {
    	    		for(int i = 0; i < previousRow.size(); i++) {
    	    			previousRow.get(i).connect(currentRow.get(i));
    	    			previousRow.get(i).connect(currentRow.get(i + 1));
    	    		}
    	    	}
    	    	else {
    	    		head = currentRow.get(0);
    	    	}
    	    	previousRow = currentRow;
    	        line = br.readLine();
    	    }
    	} finally {
    	    br.close();
    	}
    	
    	return head;
    }
}

class Node {
	private ArrayList<Node> edges;
    private int value;
    private int sum;
    private Node parent;

    public Node() {
        this("0");
        this.sum = this.getValue();
    }

    public Node(String value) {
    	this.edges = new ArrayList<Node>();
        this.value = Integer.parseInt(value);
        this.sum = 10000;
    }

    public Node connect(Node n) {
        this.edges.add(n);
        return this;
    }

    public ArrayList<Node> getEdges() {
        return this.edges;
    }

    public int getValue() {
        return this.value;
    }
    
    public Node setValue(int value) {
    	this.value = value;
    	return this;
    }
    
    public Node getParent() {
    	return this.parent;
    }
    
    public Node setParent(Node n) {
    	this.parent = n;
    	return this;
    }
    
    public boolean hasParent() {
    	if(this.parent == null) {
    		return false;
    	}
    	return true;
    }
    
    public int getSum() {
    	return this.sum;
    }
    
    public Node setSum(int sum) {
    	this.sum = sum;
    	return this;
    }
}

class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node node1, Node node2) {
		return node1.getSum() - node2.getSum();
	}
	
}
