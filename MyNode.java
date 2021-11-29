public class MyNode {
    private String color;
    private MyNode next;
    private MyNode prev;
    private MyNode down;
    private MyNode up;


        public MyNode(String color, MyNode next, MyNode prev, MyNode down, MyNode up) {
        this.color = color;
        this.next = next;
        this.prev = prev;
        this.down = down;
        this.up = up;

    }

        public MyNode(MyNode node){
            this(node.getData(), node.getNext(), node.getPrev(), node.getDown(), node.getUp());
        }       

        public MyNode(String color) {
        this(color, null, null, null, null);
    }

        public String getData() {
        return color;
    }

        public void setData(String color) {
        this.color = color;
    }

        public MyNode getNext() {
        return next;
    }

        public void setNext(MyNode next) {
        this.next = next;
    }

        public MyNode getPrev() {
        return prev;
    }

        public void setPrev(MyNode prev) {
        this.prev = prev;
    }
        
        public MyNode getDown() {
        return down;
    }

        public void setDown(MyNode down) {
        this.down = down;
    }
        public MyNode getUp() {
        return up;
    }

        public void setUp(MyNode up) {
        this.up = up;
    }


    public String toString() {
        return "" + color;
    }
}   