import java.util.ArrayList;

/**
 * Lab 6: Java Collection Framework, Skip List and Apache ANT <br />
 * The {@code SkipList} class
 * @param <K>           {@code K} key of each skip list node
 * @param <V>           {@code V} value of each skip list node
 */
public class SkipList<K extends Comparable<K>, V> {

    /**
     * The {@code Node} class for {@code SkipList}
     */
    private class Node {
        public K key;
        public V value;
        public ArrayList<Node> forwards = new ArrayList<Node>();
        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            for (int i = 0; i < level; i++)
                forwards.add(null);
        }
        public String toString() {
            return String.format("%s(%s,%d)", value, key, forwards.size());
        }
    }

    /**
     * Level of the skip list. An empty skip list has a level of 1
     */
    private int level = 1;

    /**
     * Size of the skip list
     */
    private int size = 0;
    
    
    private int udpateCounter = 0;
    private boolean update = false;

    
    private Node l = null;// = new Node(LB,null,level); // our skip list
    
    /**
     * Insert an new element into the skip list
     * @param key       {@code K} key of the new element
     * @param value     {@code V} value of the new element
     */
    public void insert(K key, V value) {
        // TODO: Lab 5 Part 1-1 -- skip list insertion
    	
    	// initialization
    	int c = 0; // indicate the value from compareTo  a.com(b) == 0 if equal, -1 if a<b, 1 if a>b
    	int newlvl = 1; // temporary variable
    	
    	if(l == null) // initialization
    	{
    		newlvl = lvlIncrement();
    		l = new Node(key,value,newlvl);
    	}
    	else
    	{
    		c = l.key.compareTo(key);
    		
    		if(c == 1) // a>b, or insertLeft
    		{
    			insertLeft(l,key,value);
    		}
    		else if(c == -1) // a < b, or insertRight
    		{
    			insertRight(this.l,key,value);
    		}
    		else if(c == 0)
    		{
    			this.l.value = value;
    		}
    		
    	}
    	
    	// reset
    	this.counter = 0;
    	this.udpateCounter = 0;
    	this.update = false;
    	this.updateval = false;
    	this.insert = null;
    }
    
    private boolean updateval = false;
    public void insertRight(Node l, K k, V v)
    {
			int nodelvl = 0;
			
			nodelvl = l.forwards.size();
			
			for(int i = nodelvl -1; i >= 0; i--)
			{
				if(this.update == true)
				{
					update(l,k,v);
				}
				if(l.forwards.get(i) != null)
				{
					int cc = l.forwards.get(i).key.compareTo(k);
					
    				if(cc == 1) // a > b, move down level
    				{
    					
    				}
    				else if(cc == -1) // a<b
    				{
    					insertRight(l.forwards.get(i), k, v);
    					
    					if(this.updateval == false)
    					{
    						update(l,k,v);
    					}
    					
    					break;
    				}
    				else if(cc == 0)
    				{
    					l.forwards.get(i).value = v;
    					this.updateval = true;
    					// update value

    				}
    				
    				if(i == 0) // bottom reached, insert the element
    				{
    					if(this.updateval == false)
    					{
    						update(l,k,v);
    					}
    					
    					break;
    				}
				}
				else if(l.forwards.get(i) == null && i == 0) // bottom null encounterd
				{
					update(l,k,v);
					break;
				}
				else
				{
					
				}

			}
    }
    

    private int counter = 0;
    private Node insert = null;
    public void update(Node l, K k, V v)
    {
    	
		
		int newlvl = level + lvlIncrement();
		
		
		
		if(this.update == false)
		{
			this.udpateCounter = newlvl;
			this.insert = new Node(k,v,this.udpateCounter);
		}
		
		
		int height = l.forwards.size();
		
		Node tempL = l;
		
		
		if(this.counter < this.udpateCounter - 1)
		{
			
			for(int j = this.counter; j <= height - 1; j++)
			{
				if(j > this.udpateCounter - 1 || j > height - 1)
				{
					break;
				}
				else
				{
					this.insert.forwards.set(j, tempL.forwards.get(j));
				}
				
			}
			
			for(int j = this.counter; j<= height - 1; j++)
			{
				
				this.counter = this.counter + 1;
				if(j > this.udpateCounter - 1 || j > height - 1)
				{
					break;
				}
				else
				{
					l.forwards.set(j, this.insert);
				}
				
			}
			// all value - 1?
			if(l.key.compareTo(this.l.key) == 0 && this.counter - 1 < this.udpateCounter-1 && this.udpateCounter - 1 > height - 1)
			{
				this.counter = this.counter - 1; // temp
				for(int j = this.counter; j <= this.udpateCounter - 1; j++)
				{
					this.counter = j;
					if(j > this.udpateCounter -1)
					{
						break;
					}
					else
					{
						l.forwards.add(this.insert);
					}
					
				}
			}
			
			
			
			this.update = true;
			
		}

		
		
		
    }
    
    public void insertLeft(Node l, K k, V v)
    {
    	int newlvl = 0;
    	Node existing = null;
		int elvl = 0;
		existing = l;
		elvl = existing.forwards.size();
		
		
		Node newnode = null;
		newlvl = elvl + lvlIncrement();// or newlvl = elvl + lvlIncrement();
		newnode = new Node(k,v,newlvl);
		
		for(int i = 0; i < elvl; i++)
		{
			newnode.forwards.set(i, existing);
		}
	    this.l = newnode;
    }
    
    public int lvlIncrement()
    {
    	int lvlIncrement = 0;
    	int newlvl = 0;
		while(flipCoin() == true)
		{
			lvlIncrement = lvlIncrement + 1;
		}
		
		newlvl = lvlIncrement + level;
		return newlvl;
    }
    public boolean flipCoin()
    {
    	if(Math.random()>0.5)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    
    /**
     * Remove an element by the key
     * @param key       {@code K} key of the element
     * @return          {@code V} value of the removed element
     */
    public V remove(K key) {
        // TODO: Lab 5 Part 1-2 -- skip list deletion
        
        return null;
    }

    
    public V deleteLeft()
    {
    	return null;
    }
    
    public V deleteRight()
    {
    	return null;
    }
    
    /**
     * Search for an element by the key
     * @param key       {@code K} key of the element
     * @return          {@code V} value of the target element
     */

    public V search(K key) {
        // TODO: Lab 5 Part 1-3 -- skip list node search
        int c = this.l.key.compareTo(key);
        
        if(c == 0)
        {
        	return this.l.value;
        }
        else if(c == 1)
        {
        	return null;
        }
        else if(c == -1)
        {
        	return searchRight(this.l,key);
        }
        return null;
    }

    public V searchRight(Node l, K k)
    {
    	int currentlvl = l.forwards.size();
    	int cc = 0;
    	
    	
    	for(int i = currentlvl - 1; i >= 0; i--)
    	{
    		if(l.forwards.get(i) != null)
    		{
    			cc = l.forwards.get(i).key.compareTo(k);
    			
    			if(cc == 0)
    			{
    				return l.forwards.get(i).value;
    			}
    			else if(cc == 1)
    			{
    				
    			}
    			else if(cc == -1)
    			{
    				return searchRight(l.forwards.get(i),k);
    			}
    		}

    		else if(l.forwards.get(i) == null && i == 0)
    		{
    			return null;
    		}
    	}
    	
    	return null;
    }
    
    
    /**
     * Get the level of the skip list
     * @return          {@code int} level of the skip list
     */
    public int level() {
    	
        return this.l.forwards.size();
    }

    /**
     * Get the size of the skip list
     * @return          {@code int} size of the skip list
     */
    public int size() {
        return size;
    }

    /**
     * Print the skip list
     * @return          {@code String} the string format of the skip list
     */
    public String toString() {
        // TODO: Lab 5 Part 1-4 -- skip list printing

        return null;
    }

    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
    	
    	
        SkipList<Integer, String> list = new SkipList<Integer, String>();
        //int[] keys = new int[10];
        
        int[] keys = {188,168,178,179,90,192,1,189,85,181};

    	int[] lvl = {1,4,2,3,5,10,7,4,2,3};
    

            
            for (int i = 0; i < 10; i++) {   // Insert elements
            	
            	try {
                    //keys[i] = (int) (Math.random() * 200);
                    list.insert(keys[i], "\"" + keys[i] + "\"");
                    //list.insert(keys[i], "\"" + val[i] + "\"");
            	}
            	catch(StackOverflowError e)
            	{
            		System.out.println("here");
            	}
            }

            System.out.println(list);

            for (int i = 0; i < 10; i += 3) {
                int key = keys[i];
                // Search elements
                System.out.println(String.format("Find element             %3d: value=%s", key, list.search(key)));
                // Remove some elements
                System.out.println(String.format("Remove element           %3d: value=%s", key, list.remove(key)));
                // Search the removed elements
                System.out.println(String.format("Find the removed element %3d: value=%s", key, list.search(key)));
            }

            System.out.println(list);
    	
    

    }

}
