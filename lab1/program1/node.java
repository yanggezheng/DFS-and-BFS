package AI.program1;
public class node {
        private int value;
        private final char name;
        private boolean inList;
        public char getName(){
            return this.name;
        }
        public node(char name, int value) {
            this.name = name;
            this.value = value;
        }
        public boolean getInList(){
            return inList;
        }
        public void setInList(boolean inList){
            this.inList = inList;
        }
        public void setValue(int value){
            this.value = value;
        }
        public int getValue(){
            return this.value;
        }
        public void changeInList(){
            this.inList = !inList;
        }
}
