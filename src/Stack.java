import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stack {
    private List<Box> boxList = new ArrayList<Box>();
//    private List<Integer> id = new ArrayList<>();//
////    private List<Character> ori = new ArrayList<>();//
    //private Node root;
    private int fitness;



    //mutate if chance
    public void mutate(List<Box> boxList)
    {
        try {
            //CHANCE OF MUTATION:
            Random rand = new Random();
            //----------------------------------------------------------------------Change mutate chance here-----------------------------------------//
            int chanceOfMutation = 100; //out of 100

            int roll = rand.nextInt(100);
            if (roll > chanceOfMutation) return;//failed chance and returning
            //successfully chanced the mutation

            //choose a random one from our available ones
            int indexOfBoxSelected = rand.nextInt(boxList.size());
            Box newBox = (Box) boxList.get(indexOfBoxSelected).clone();
            int orientation = rand.nextInt(2);
            int tempP;

            switch (orientation) {
                        /*
                        Case 0 -> changes width value to equal the height, effectively, rotating the box
                        Case 1 -> Same but with the Depth value
                        Case 2 -> All stays the same
                         */
                case 0: {
                    //ori = 'w'
                    tempP = newBox.getW();
                    newBox.setW(newBox.getH());
                    newBox.setH(tempP);
                    break;
                }
                case 1: {
                    //ori = 'd'
                    tempP = newBox.getD();
                    newBox.setD(newBox.getH());
                    newBox.setH(tempP);
                    break;
                }
                case 2: {
                    //ori = 'h'
                    break;
                }
            }

            for(int i = 0; i < boxList.size(); i++)
                if(boxList.get(i).getId() == newBox.getId())
                   this.boxList.remove(boxList.get(i));


            addBox(newBox);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void calcFitness()
    {
        fitness = 0;
        for (Box box: boxList) {
            fitness += box.getH();
        }
    }

    public void addBox(Box box)
    {
        //Checks if box already exists in stack, then doesn't add new box
        //to meet only use box once condition
        for (Box b: boxList) {
            if(box.getId() == b.getId()){
                return;
            }
        }

        //boxList.add(box);


        if(!sort(box))//in this case we must delete
        {
            int tempP = box.getW();
            box.setW(box.getD());
            box.setD(tempP);
            sort(box);
        }
    }

    //returns false if doesn't fit
    private boolean sort(Box newBox)
    {
        if(boxList.size() == 0){
            boxList.add(newBox);
            return true;
        }

        //loops through stack from end of list to front
        //(top of stack to bottom)
        for(int i=boxList.size()-1; i>=0; i--){
            //quick check the area is good
            if(newBox.getW()*newBox.getD() >= boxList.get(i).getW()*boxList.get(i).getD()){
                continue;
            }

            //looking at top of stack
            if(i == boxList.size()-1){
                if(newBox.getW() < boxList.get(i).getW() && newBox.getD() < boxList.get(i).getD()){
                    boxList.add(i+1, newBox);
                    return true;
                }
                continue;
            }

            //looking at any other point but at the bottom
            if(newBox.getW() < boxList.get(i).getW() && newBox.getD() < boxList.get(i).getD()
                    && newBox.getW() > boxList.get(i+1).getW() && newBox.getD() > boxList.get(i+1).getD()){
                boxList.add(i+1, newBox);
                return true;
            }
        }

        //check the bottom
        if(newBox.getW() > boxList.get(0).getW() && newBox.getD() > boxList.get(0).getD()){
            boxList.add(0, newBox);
            return true;
        }

        return false;
            /*
            //check area of the new is not greater then the current box
           if(newBox.getW()*newBox.getD() >= boxList.get(i).getW()*boxList.get(i).getD())
               continue;
           else if((newBox.getW() < boxList.get(i).getW() && newBox.getD() < boxList.get(i).getD()))

               return true;
           else  {
               //Rotates box
               int tempP = newBox.getW();
               newBox.setW(newBox.getD());
               newBox.setD(tempP);
               return true;
           }
        }
        return false;*/
    }

    public List<Box> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<Box> boxList) {
        this.boxList = boxList;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

//    private class Node
//    {
//        public Node(int id, char data)
//        {
//            this.id = id;
//            this.data = data;
//        }
//        private int id;
//        private char data;
//
//        private Node leftChild;
//        private Node rightChild;
//
//        public char findKey(int id)
//        {
//            if(this.id == id)
//            {
//                return data;
//            }
//            else if(id < this.id)
//            {
//                if(leftChild == null) return 0;
//                leftChild.findKey(id);
//            }
//            else if(id > this.id)
//            {
//                if(rightChild == null) return 0;
//                rightChild.findKey(id);
//            }
//            return 0;
//        }
//
//        public void addNode(Node newNode)
//        {
//            if(newNode.getId() < id)
//            {
//                if(leftChild==null)
//                {
//                    leftChild = newNode;
//                    return;
//                }
//                leftChild.addNode(newNode);
//            }
//            else{
//                if(rightChild==null)
//                {
//                    rightChild = newNode;
//                    return;
//                }
//                rightChild.addNode(newNode);
//            }
//            return;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public char getData() {
//            return data;
//        }
//
//        public void setData(char data) {
//            this.data = data;
//        }
//
//        public Node getLeftChild() {
//            return leftChild;
//        }
//
//        public void setLeftChild(Node leftChild) {
//            this.leftChild = leftChild;
//        }
//
//        public Node getRightChild() {
//            return rightChild;
//        }
//
//        public void setRightChild(Node rightChild) {
//            this.rightChild = rightChild;
//        }
//    }
}
