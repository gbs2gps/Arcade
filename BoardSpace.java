public class BoardSpace{
	private Checker data;
	private BoardSpace above;
	private BoardSpace below;
	private BoardSpace right;
	private BoardSpace left;
	private boolean isDark;
	private int upLeftX;
	private int upLeftY;

	public BoardSpace(Checker data, BoardSpace above, BoardSpace below,BoardSpace right,BoardSpace left,String shade,int xCoord, int yCoord){
        this.data=data;
        this.above=above;
        this.below=below;
        this.right=right;
        this.left=left;
        if(shade!=null){
        	if(shade.toLowerCase().equals("dark")){
        		isDark=true;
        	}else{
        		isDark=false;
        	}
    	}else{
    		//default set to dark, will correct if necessary
    		isDark=true;
    	}
    	upLeftX=xCoord;
    	upLeftY=yCoord;
    }

    public BoardSpace(){
    	this(null,null,null,null,null,null,0,0);
    }

    //getters

    public BoardSpace getAbove(){
    	return above;
    }
    public BoardSpace getBelow(){
    	return below;
    }
    public BoardSpace getRight(){
    	return right;
    }
    public BoardSpace getLeft(){
    	return left;
    }
    public Checker getChecker(){
    	return data;
    }
    public int getXCoord(){
    	return upLeftX;
    }
    public int getYCoord(){
    	return upLeftY;
    }
    public boolean isDark(){
    	return isDark;
    }
    public boolean isLight(){
    	return !isDark;
    }
    public boolean isOccupied(){
    	if(data!=null){
    		return true;
    	}else{
    		return false;
    	}
    }

    //setters
    public void setAbove(BoardSpace toSet){
    	above=toSet;
    	if (toSet!=null) toSet.setBelow(this,"stop");
    }
    public void setBelow(BoardSpace toSet){
    	below=toSet;
    	if (toSet!=null) toSet.setAbove(this,"stop");
    }

    private void setBelow(BoardSpace toSet, String stop){
    	below=toSet;
    }

    private void setAbove(BoardSpace toSet, String stop){
    	above=toSet;
    }

    public void setRight(BoardSpace toSet){
    	right=toSet;
    	if(toSet!=null) toSet.setLeft(this,"stop");	
    }
    public void setLeft(BoardSpace toSet){
    	left=toSet;
    	if(toSet!=null) toSet.setRight(this,"stop");
    }
    private void setRight(BoardSpace toSet, String stop){
    	right=toSet;
    }
    private void setLeft(BoardSpace toSet, String stop){
    	left=toSet;
    }
    public void setDark(){
    	isDark=true;
    }
    public void setLight(){
    	isDark=false;
    }
    public void setOccupied(Checker resident){
    	if(data==null||resident==null){
    		data=resident;
    	}else{
    		throw new IllegalArgumentException("This space is already occupied by a "+data.getColor()+" checker");
    	}
    }
    public void setCoords(int x,int y){
    	upLeftY=y;
    	upLeftX=x;
    }

    public String toString(){
    	String toReturn="";
    	if(data!=null){
    		toReturn=data.toString();
    	}else{
    		toReturn="Empty ";
    		if(isDark){
    			toReturn+= "dark ";
    		}else{
    			toReturn+= "light ";
    		}
    		toReturn+="space";
    	}
    	return toReturn;
    }
}