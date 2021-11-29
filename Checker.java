public class Checker{
	private boolean isRed;
	private int height;
	private boolean isInPlay;

	public Checker(String color){
		if(color.toLowerCase().equals("red")){
			isRed=true;
		}else{
			isRed=false;
		}
		height=1;
		isInPlay=true;
	}

	public boolean isRed(){
		if(isRed){
			return true;
		}else{
			return false;
		}
	}

	public boolean isBlack(){
		if(!isRed){
			return true;
		}else{
			return false;
		}
	}

	public String getColor(){
		if(isRed){
			return "red";
		}else{
			return "black";
		}
	}

	public boolean isCrowned(){
		if(height>1){
			return true;
		}else{
			return false;
		}
	}

	public boolean isInPlay(){
		return isInPlay;
	}

	public void setTaken(){
		isInPlay=false;
	}

	public void crown(){
		height=2;
	}

	public String toString(){
		String toReturn=""+getColor();
		return toReturn;
	}


}