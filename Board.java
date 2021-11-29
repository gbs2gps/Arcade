public class Board{
	//need to keep track of corners
	private BoardSpace upLeft;
	private BoardSpace bottomRight;
	private boolean redTurn;
	

	public Board(){
		redTurn=true;
		//need to create an 8x8 board
		BoardSpace[][] arr= new BoardSpace[8][8];
		//makes 64 blank boardSpaces
		upLeft=new BoardSpace();
		arr[0][0]=upLeft;
		arr[0][0].setAbove(null);
		arr[0][0].setLeft(null);
		arr[0][0].setCoords(55,35);
		bottomRight=new BoardSpace();
		arr[7][7]=bottomRight;
		arr[7][7].setRight(null);
		arr[7][7].setBelow(null);
		arr[7][7].setCoords(455,435);
		for (int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if((i!=0||j!=0)&&(i!=7||j!=7)){
					arr[i][j]=new BoardSpace();
				}
				//shade the spaces appropriately
				if((i%2==0&&j%2==0)||(i%2==1&&j%2==1)){
					arr[i][j].setLight();
				}else{
					arr[i][j].setDark();
				}
			}
		}	

		for (int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				//go through and link all of the board spaces appropriately
					//top row
				if(i==0){
					if(j==7){
						arr[i][j].setRight(null);
					}else{
						arr[i][j].setAbove(null);
						arr[i][j].setRight(arr[i][j+1]);
					}
				}
					//bottom row
				else if(i==7){
					if(j==7){
						arr[i][j].setRight(null);
					}else{
						arr[i][j].setBelow(null);
						arr[i][j].setRight(arr[i][j+1]);
					}
				}
					//middle
				else{
					if (j!=7) arr[i][j].setRight(arr[i][j+1]);
					arr[i][j].setAbove(arr[i-1][j]);
					if(i==6) arr[i][j].setBelow(arr[i+1][j]);
				}	
			}
		}
		//populate the board with checkers in correct starting positions
		for(int i=0;i<3;i++){
			for(int j=0;j<8;j++){
				if(arr[i][j].isLight()){
					arr[i][j].setOccupied(new Checker("red"));
				}
			}
		}
		for(int i=5;i<8;i++){
			for(int j=0;j<8;j++){
				if(arr[i][j].isLight()){
					arr[i][j].setOccupied(new Checker("black"));
				}
			}
		}
		//set pixel coordinates for each space
		int y=35;
		int x;
		for(int i=0;i<8;i++){
			x=55;
			for(int j=0;j<8;j++){
				arr[i][j].setCoords(x,y);
				x+=50;
			}
			y+=50;
		}

	}

	public void update(){
		BoardSpace curr=upLeft;
		BoardSpace currColumnHolder=upLeft;
		while(curr!=bottomRight){
			if(curr.getChecker()!=null&&!(curr.getChecker().isInPlay())) curr.setOccupied(null);
			if(curr.getRight()==null){
				curr=currColumnHolder.getBelow();
				currColumnHolder=currColumnHolder.getBelow();
			}else{
				curr=curr.getRight();
			}
		}

	}

	public void move(BoardSpace from, BoardSpace to){
		System.out.println("NEW MOVE");
		System.out.println("FROM: "+from);
		System.out.println("TO: "+to);
		boolean jump=justCheckJump(from,to,"stop");
		if(isValidMove(from,to)){
			System.out.println("FROM 2: "+from);
			System.out.println("JUMP: "+jump);
			if(!jump){
				System.out.println("FROM INSIDE IF: "+from+" Coords:("+from.getXCoord()+","+from.getYCoord()+")");
				Checker toMove=from.getChecker();
				System.out.println("TO MOVE: "+toMove);
				from.setOccupied(null);
				to.setOccupied(toMove);
				if((toMove.isRed()&&to.getBelow()==null)||(toMove.isBlack()&&to.getAbove()==null)){
					toMove.crown();
				}
				redTurn=!redTurn;
			}else{
				checkJumpAndExecute(from, to);
			}
		}else{
			throw new IllegalArgumentException ("Move is invalid");
		}		
	}

	private boolean isValidMove(BoardSpace from, BoardSpace to){
		Checker checker=from.getChecker();
		//the to spot is not occupied
		if(to.isOccupied()){
			return false;
		}
		//the move keeps the checkers on light spaces
		if(to.isDark()){
			return false;
		}
		if(checker==null){
			return false;
		}
		//the move is forwards unless the checker is crowned and can go backwards
		if(checker.isRed()&&(!checker.isCrowned())){
			if(from.getYCoord()>to.getYCoord()){
				return false;
			}else if((from.getLeft()!=null&&from.getLeft().getBelow().equals(to))||(from.getRight()!=null&&from.getRight().getBelow().equals(to))){
				//do nothing;
			}else{
				if(!checkJumpAndExecute(from,to)) return false;
			}
		}
		if(checker.isBlack()&&!checker.isCrowned()){
			if(from.getYCoord()<to.getYCoord()){
				return false;
			}else if((from.getLeft()!=null&&from.getLeft().getAbove().equals(to))||(from.getRight()!=null&&from.getRight().getAbove().equals(to))){
				//do nothing
			}else{
				if(!checkJumpAndExecute(from,to)) return false;
			}
		}
		return true;
	}

	private boolean checkJumpAndExecute(BoardSpace from, BoardSpace to){
		Checker checker=from.getChecker();
		BoardSpace toJump;
		Checker toTake;
		if(from==to){
			return false;
		}else if(checker==null){
			return false;
		}
		//regular jump
		if(checker.isBlack()){
			if(from.getBelow()==null&&from.getRight()==null){
				toJump=from.getAbove().getLeft();
			}else if(from.getAbove()==null&&to.getRight()==null){
				return false;
			}else if(!checker.isCrowned()&&(from.getRight()!=null&&from.getLeft()!=null)){
				if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();
				}else if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
					System.out.println("HEY ABIGAIL");
				}else{
					System.out.println("Not a valid jump");
					return false;
				}
			}else if(!checker.isCrowned()&&from.getRight()==null){
				if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
				toJump=from.getAbove().getLeft();
				}else{
					System.out.println("Not a valid jump");
				return false;
				}

			}else if(!checker.isCrowned()&&from.getLeft()==null){
				if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
				toJump=from.getAbove().getRight();
				}else{
					System.out.println("Not a valid jump");
				return false;
				}
			}else{
				if(from.getAbove()==null&&from.getLeft()==null){
					toJump=from.getBelow().getRight();
				}else if(from.getBelow()==null&&from.getRight()==null){
					toJump=from.getAbove().getLeft();	
				}else if(from.getRight()==null){
					if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
						toJump=from.getAbove().getLeft();
					}else{
						System.out.println("Not a valid jump 9");
					return false;
					}
				}else if(from.getLeft()==null){
					if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
						toJump=from.getAbove().getRight();
					}else if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
						toJump=from.getBelow().getRight();
					}else{
						System.out.println("Not a valid jump 10");
						return false;
					}
				}else if(to.getBelow()==null){
					if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
						toJump=from.getBelow().getRight();
					}else if(from.getBelow().getLeft().equals(to.getAbove().getRight())){
						toJump=from.getBelow().getLeft();
					}else{
						System.out.println("Not a valid jump 10");
						return false;
					}
				}else if (to.getAbove()==null){
					if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
						toJump=from.getAbove().getRight();
					}else if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
						toJump=from.getAbove().getLeft();
					}else{
						System.out.println("Not a valid jump 10");
						return false;
					}
				}else if(from.getAbove()==null&&from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();

				}else if(from.getAbove()==null&&from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
				}else if(from.getBelow()==null&&from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();

				}else if(from.getBelow()==null&&from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
				}else if(from.getAbove()!=null&&from.getLeft()!=null&&from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();
				}else if(from.getAbove()!=null&&from.getRight()!=null&&from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
				}else if(from.getBelow()!=null&&from.getLeft()!=null&&from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();
				}else if(from.getBelow()!=null&&from.getRight()!=null&&from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
				}else{
					System.out.println("Not a valid jump");
					return false;
				}
				}
		}else if(checker.isRed()){
			if(from.getAbove()==null&&from.getLeft()==null){
				toJump=from.getBelow().getRight();
			}else if(from.getAbove()==null&&to.getRight()==null){
				return false;
			}else if(!checker.isCrowned()&&(from.getRight()!=null&&from.getLeft()!=null)){
				if(from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();
				}else if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
				}else{
					return false;
				}
			}else if(!checker.isCrowned()&&from.getRight()==null){
				if(from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();
				}else{
					System.out.println("Not a valid jump");
				return false;
				}
			}else if(!checker.isCrowned()&&from.getLeft()==null){
				if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
				}else{
					System.out.println("Not a valid jump");
				return false;
				}
			}else{
				if(from.getAbove()==null&&from.getLeft()==null){
					toJump=from.getBelow().getRight();
				}else if(from.getBelow()==null&&from.getRight()==null){
					toJump=from.getAbove().getLeft();	
				}else if(from.getRight()==null){
					if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
						toJump=from.getAbove().getLeft();
					}else{
						System.out.println("Not a valid jump 9");
					return false;
					}
				}else if(from.getLeft()==null){
					if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
						toJump=from.getAbove().getRight();
					}else if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
						toJump=from.getBelow().getRight();
					}else{
						System.out.println("Not a valid jump 10");
						return false;
					}
				}else if(to.getBelow()==null){
					if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
						toJump=from.getBelow().getRight();
					}else if(from.getBelow().getLeft().equals(to.getAbove().getRight())){
						toJump=from.getBelow().getLeft();
					}else{
						System.out.println("Not a valid jump 10");
						return false;
					}
				}else if (to.getAbove()==null){
					if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
						toJump=from.getAbove().getRight();
					}else if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
						toJump=from.getAbove().getLeft();
					}else{
						System.out.println("Not a valid jump 10");
						return false;
					}
				}else if(from.getAbove()==null&&from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();

				}else if(from.getAbove()==null&&from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
				}else if(from.getBelow()==null&&from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();
				}else if(from.getBelow()==null&&from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
				}else if(from.getAbove()!=null&&from.getLeft()!=null&&from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();
				}else if(from.getAbove()!=null&&from.getRight()!=null&&from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
				}else if(from.getBelow()!=null&&from.getLeft()!=null&&from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getAbove().getLeft();
				}else if(from.getBelow()!=null&&from.getRight()!=null&&from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getAbove().getRight();
				}else{
					System.out.println("Not a valid jump");
					return false;
				}
				}
			}else{
				//unreachable, so ignore this
				toJump=new BoardSpace();
			}
		System.out.println("TO JUMP: "+toJump);
		toTake=toJump.getChecker();
		if(toTake==null){
			return false;
		}
		System.out.println("TO TAKE: "+toTake);
		if(checker.getColor().equals(toTake.getColor())){
			System.out.println("You cannot take your own pieces");
			return false;
		}
		//THE JUMP IS VALID
		//move original checker
		from.setOccupied(null);
		to.setOccupied(checker);
		if(checker.isRed()&&to.getBelow()==null){
			checker.crown();
		}else if(checker.isBlack()&&to.getAbove()==null){
			checker.crown();
		}
		//set taken checker to stolen
		toTake.setTaken();
		System.out.println("JUMPING");
		return true;
	}

	private boolean justCheckJump(BoardSpace from, BoardSpace to,String stop){
		Checker checker=from.getChecker();
		BoardSpace toJump;
		Checker toTake;
		if(from==to){
			return false;
		}else if(checker==null){
			return false;
		}
		//regular jump
		if(checker.isBlack()){
			if(from.getBelow()==null&&from.getRight()==null){
				toJump=from.getAbove().getLeft();
			}else if(from.getAbove()==null&&to.getRight()==null){
				return false;
			}else if(!checker.isCrowned()&&(from.getRight()!=null&&from.getLeft()!=null)){
				if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();
				}else if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
					System.out.println("HEY ABIGAIL");
				}else{
					System.out.println("Not a valid jump 1");
					return false;
				}
			}else if(!checker.isCrowned()&&from.getRight()==null){
				if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
				toJump=from.getAbove().getLeft();
				}else{
					System.out.println("Not a valid jump 2");
				return false;
				}

			}else if(!checker.isCrowned()&&from.getLeft()==null){
				if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
				toJump=from.getAbove().getRight();
				}else{
					System.out.println("Not a valid jump 3");
				return false;
				}
			}else{
				if(from.getAbove()==null&&from.getLeft()==null){
					toJump=from.getBelow().getRight();
				}else if(from.getBelow()==null&&from.getRight()==null){
					toJump=from.getAbove().getLeft();	
				}else if(from.getRight()==null){
					if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
						toJump=from.getAbove().getLeft();
					}else{
						System.out.println("Not a valid jump 4");
					return false;
					}
				}else if(from.getLeft()==null){
					if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
						toJump=from.getAbove().getRight();
					}else if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
						toJump=from.getBelow().getRight();
					}else{
						System.out.println("Not a valid jump 5");
						return false;
					}
				}else if(from.getAbove()==null&&from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();

				}else if(from.getAbove()==null&&from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
				}else if(from.getBelow()==null&&from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();

				}else if(from.getBelow()==null&&from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
				}else if(from.getAbove()!=null&&from.getLeft()!=null&&from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();
				}else if(from.getAbove()!=null&&from.getRight()!=null&&from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
				}else if(from.getBelow()!=null&&from.getLeft()!=null&&from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();
				}else if(from.getBelow()!=null&&from.getRight()!=null&&from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
					System.out.println("correct if statement");
				}else{
					System.out.println("Not a valid jump 6");
					return false;
				}
				}
		}else if(checker.isRed()){
			if(from.getAbove()==null&&from.getLeft()==null){
				toJump=from.getBelow().getRight();
			}else if(from.getAbove()==null&&to.getRight()==null){
				return false;
			}else if(!checker.isCrowned()&&(from.getRight()!=null&&from.getLeft()!=null)){
				if(from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();
				}else if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
				}else{
					return false;
				}
			}else if(!checker.isCrowned()&&from.getRight()==null){
				if(from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();
				}else{
					System.out.println("Not a valid jump 7");
				return false;
				}
			}else if(!checker.isCrowned()&&from.getLeft()==null){
				if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
				}else{
					System.out.println("Not a valid jump 8");
				return false;
				}
			}else{
				if(from.getAbove()==null&&from.getLeft()==null){
					toJump=from.getBelow().getRight();
				}else if(from.getBelow()==null&&from.getRight()==null){
					toJump=from.getAbove().getLeft();	
				}else if(from.getRight()==null){
					if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
						toJump=from.getAbove().getLeft();
					}else{
						System.out.println("Not a valid jump 9");
					return false;
					}
				}else if(from.getLeft()==null){
					if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
						toJump=from.getAbove().getRight();
					}else if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
						toJump=from.getBelow().getRight();
					}else{
						System.out.println("Not a valid jump 10");
						return false;
					}
				}else if(to.getBelow()==null){
					if(from.getBelow().getRight().equals(to.getAbove().getLeft())){
						toJump=from.getBelow().getRight();
					}else if(from.getBelow().getLeft().equals(to.getAbove().getRight())){
						toJump=from.getBelow().getLeft();
					}else{
						System.out.println("Not a valid jump 10");
						return false;
					}
				}else if (to.getAbove()==null){
					if(from.getAbove().getRight().equals(to.getBelow().getLeft())){
						toJump=from.getAbove().getRight();
					}else if(from.getAbove().getLeft().equals(to.getBelow().getRight())){
						toJump=from.getAbove().getLeft();
					}else{
						System.out.println("Not a valid jump 10");
						return false;
					}
				}else if(from.getAbove()==null&&from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getBelow().getLeft();

				}else if(from.getAbove()==null&&from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getBelow().getRight();
				}else if(from.getBelow()==null&&from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();

				}else if(from.getBelow()==null&&from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
				}else if(from.getAbove()!=null&&from.getLeft()!=null&&from.getAbove().getLeft().equals(to.getBelow().getRight())){
					toJump=from.getAbove().getLeft();
				}else if(from.getAbove()!=null&&from.getRight()!=null&&from.getAbove().getRight().equals(to.getBelow().getLeft())){
					toJump=from.getAbove().getRight();
				}else if(from.getBelow()!=null&&from.getLeft()!=null&&from.getBelow().getLeft().equals(to.getAbove().getRight())){
					toJump=from.getAbove().getLeft();
				}else if(from.getBelow()!=null&&from.getRight()!=null&&from.getBelow().getRight().equals(to.getAbove().getLeft())){
					toJump=from.getAbove().getRight();
				}else{
					System.out.println("Not a valid jump 11");
					return false;
				}
				}
			}else{
				//unreachable, so ignore this
				toJump=new BoardSpace();
			}
		System.out.println("TO JUMP: "+toJump);
		toTake=toJump.getChecker();
		if(toTake==null){
			return false;
		}
		System.out.println("TO TAKE: "+toTake);
		if(checker.getColor().equals(toTake.getColor())){
			System.out.println("You cannot take your own pieces");
			return false;
		}
		//THE JUMP IS VALID
		
		System.out.println("CHECKED AND VALID");
		return true;
	}

	public BoardSpace getBoardSpace(int pixelX, int pixelY){
		//returns the boardSpace object that starts at the given coordinates
		BoardSpace curr=upLeft;
		BoardSpace currColumnHolder=upLeft;
		while(curr!=bottomRight){
			if((curr.getXCoord()<=pixelX&&curr.getXCoord()+50>=pixelX)&&(curr.getYCoord()<=pixelY&&curr.getYCoord()+50>=pixelY)) return curr;
			if(curr.getRight()==null){
				curr=currColumnHolder.getBelow();
				currColumnHolder=currColumnHolder.getBelow();
			}else{
				curr=curr.getRight();
			}
		}
		if((bottomRight.getXCoord()<=pixelX&&bottomRight.getXCoord()+50>=pixelX)&&(bottomRight.getYCoord()<=pixelY&&bottomRight.getYCoord()+50>=pixelY)) return bottomRight;
		throw new IllegalArgumentException("There is no board space at these coordinates");
	}

	public BoardSpace getUpLeft(){
		return upLeft;
	}
	public BoardSpace getBottomRight(){
		return bottomRight;
	}

	public boolean gameOver(){
		int numRed=0;
		int numBlack=0;
		BoardSpace curr=upLeft;
		BoardSpace currColumnHolder=upLeft;
		while(curr!=bottomRight){
			if(curr.getChecker()!=null&&curr.getChecker().isRed()){
				numRed++;
			}else if(curr.getChecker()!=null&&curr.getChecker().isBlack()){
				numBlack++;
			}
			if(curr.getRight()==null){
				curr=currColumnHolder.getBelow();
				currColumnHolder=currColumnHolder.getBelow();
			}else{
				curr=curr.getRight();
			}
		}
		if(bottomRight.isOccupied()){
			Checker checker=bottomRight.getChecker();
			if(checker.isBlack()){
				numBlack++;
			}else{
				numRed++;
			}
		}
		if(numRed==0||numBlack==0){
			String winner = (numBlack==0) ? "RED" : "BLACK";
			System.out.println(winner+" WINS!");
			return true;
		}else{
			return false;
		}
	}

	public void reset(){
		BoardSpace curr=upLeft;
		BoardSpace currColumnHolder=upLeft;
		while(curr!=bottomRight){
			curr.setOccupied(null);
			if(curr.getRight()==null){
				curr=currColumnHolder.getBelow();
				currColumnHolder=currColumnHolder.getBelow();
			}else{
				curr=curr.getRight();
			}
		}
		bottomRight.setOccupied(null);

		curr=upLeft;
		currColumnHolder=upLeft;
		int rowCounter=0;
		while(curr!=bottomRight){
			if(rowCounter<3&&curr.isLight()) curr.setOccupied(new Checker("red"));
			if(rowCounter>4&&curr.isLight()) curr.setOccupied(new Checker("black"));
			if(curr.getRight()==null){
				curr=currColumnHolder.getBelow();
				currColumnHolder=currColumnHolder.getBelow();
				rowCounter++;
			}else{
				curr=curr.getRight();
			}
		}
		bottomRight.setOccupied(new Checker("black"));
	}

	public String getTurn(){
		String toReturn=(redTurn) ? "Red" : "Black";
		return toReturn;
	}

	public String toString(){
		String toReturn="";
		BoardSpace curr=upLeft;
		BoardSpace currColumnHolder=upLeft;
		while(curr!=bottomRight){
			toReturn+=curr.toString();
			toReturn+="   ";
			if(curr.getRight()==null){
				curr=currColumnHolder.getBelow();
				currColumnHolder=currColumnHolder.getBelow();
				toReturn+="\n\n";
			}else{
				curr=curr.getRight();
			}
		}
		toReturn+=bottomRight.toString();
		return toReturn;
	}
}