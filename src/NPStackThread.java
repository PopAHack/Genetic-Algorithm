import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/*
Authors:
1311972 - Jesse Whitten
1214013 - Ward Beehre
 */

public class NPStackThread extends Thread {
    //global vars
    private List<Stack> stackList = new ArrayList<>();
    private List<Box> boxList = new ArrayList<>();
    private Stack fittestStack = null;
    private String[] args;
    private String result = "";
    private String[] resultList;
    private int index;

    public NPStackThread(String[] args, String[] resultList, int index)
    {
        this.args = args;
        this.resultList = resultList;
        this.index = index;
    }

    //The main method for the GA.
    @Override
    public void run() {
        //initialize population
        initPop(args[0], Integer.parseInt(args[1]));

        //Loop here with second input
        for (int i = 0; i < Integer.parseInt(args[1]); i++) {

            //fitness calculation
            for (Stack st : stackList)
                st.calcFitness();

            //selection
            List<Stack> selectedStacks = new ArrayList<>();
            Stack currentStack = new Stack();
            int runtime = stackList.size()/2;
            for(int j = 0; j < runtime; j++)
            {
                int highestFitness = 0;
                for (Stack st : stackList) {
                    if(highestFitness < st.getFitness())
                    {
                        highestFitness = st.getFitness();
                        currentStack = st;
                    }
                }
                stackList.remove(currentStack);
                selectedStacks.add(currentStack);
            }

            stackList = selectedStacks;

            //crossover
            int runTo = stackList.size();
            Stack tempStack = new Stack();
            for(int j=0; j<runTo; j+=2 ) {

                //generate 4 quarters from each pair of selected stacks
                Stack stackA = new Stack();
                Stack stackB = new Stack();
                Stack stackC = new Stack();
                Stack stackD = new Stack();

                //a
                for (int k = 0; k < stackList.get(j).getBoxList().size() / 2; k++) {
                    stackA.addBox(stackList.get(j).getBoxList().get(k));
                }
                //b
                for (int k = stackList.get(j).getBoxList().size() / 2; k < stackList.get(j).getBoxList().size(); k++) {
                    stackB.addBox(stackList.get(j).getBoxList().get(k));
                }

                //c
                for (int k = 0; k < stackList.get(j + 1).getBoxList().size() / 2; k++) {
                    stackC.addBox(stackList.get(j + 1).getBoxList().get(k));
                }
                //d
                for (int k = stackList.get(j + 1).getBoxList().size() / 2; k < stackList.get(j + 1).getBoxList().size(); k++) {
                    stackD.addBox(stackList.get(j + 1).getBoxList().get(k));
                }


                //calc fitness so duplicate boxes are handled correctly
                stackA.calcFitness();
                stackB.calcFitness();
                stackC.calcFitness();
                stackD.calcFitness();

                //check which opposite quarter stacks are more fit than the other
                Stack input1, output1, input2, output2;
                if (stackA.getFitness() > stackD.getFitness()) {
                    input1 = stackA;
                    output1 = stackD;
                } else {
                    input1 = stackD;
                    output1 = stackA;
                }
                if (stackB.getFitness() > stackC.getFitness()) {
                    input2 = stackB;
                    output2 = stackC;
                } else {
                    input2 = stackC;
                    output2 = stackB;
                }

                //Merge quarters together
                for (int k = 0; k < output1.getBoxList().size(); k++) {
                    input1.addBox(output1.getBoxList().get(k));
                }

                for (int k = 0; k < output2.getBoxList().size(); k++) {
                    input2.addBox(output2.getBoxList().get(k));
                }

                //add new stacks back to stack pool
                stackList.add(input1);
                stackList.add(input2);
            }



            //mutation
            for(int k =0; k < stackList.size(); k++)
                stackList.get(k).mutate(boxList);

            //get fittestStack
            if(fittestStack == null)
                fittestStack=stackList.get(0);

            for(int k = 0; k < stackList.size(); k++)
            {
                stackList.get(k).calcFitness();
                if(stackList.get(k).getFitness() > fittestStack.getFitness())
                    fittestStack = stackList.get(k);
            }
            //System.out.println("Generations evolved: " + (i+1) + "/" + Integer.parseInt(args[1]));
        }

        //get stack with highest fitness
        //and output to file
        Output(fittestStack);
    }


    //inti pop
    private void initPop(String input, int numOfRuns) {
        try {

            File inputFile = new File(input);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BoxFactory boxFactory = new BoxFactory(boxList);

            String line;
            while ((line = reader.readLine()) != null)
                boxFactory.createBox(line);


            /*
            CAN DO
            numOfStacks = 16;
             */

            //INIT STACKS
            Random rand = new Random();
            //int numOfStacks = numOfRuns / 100;
            //numOfStacks = numOfStacks * 4;//make sure it is an even number
            //----------------------------------------------------------------------Change stacks being used here-----------------------------------------//
            int numOfStacks = 256;

            for (int i = 0; i < numOfStacks; i++)//per stack
            {
                //make a copy of our boxes
                List<Box> tempBoxList = new ArrayList<>(boxList);
                Stack newStack = new Stack();

                int numBoxes = rand.nextInt(boxList.size());
                for (int j = 0; j < numBoxes; j++)//per box
                {

                    //choose a random one from our available ones
                    int indexOfBoxSelected = rand.nextInt(tempBoxList.size());
                    Box newBox = (Box)tempBoxList.get(indexOfBoxSelected).clone();

                    int orientation = rand.nextInt(2);
                    int tempP;

                    switch (orientation) {
                        /*
                        Case 0 -> changes width value to equal the height, effectively, rotating the box
                        Case 1 -> Same but with the Depth valie
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

                    //assign box to the stack
                    //choose a random box


                    //add to stack and remove from available boxes
                    newStack.addBox(newBox);
                    tempBoxList.remove(newBox);

                }

                stackList.add(newStack);

            }

        } catch (Exception ex) {
            ex.toString();
        }
    }

    private void Output(Stack fittestStack){
        try {
            result += "W  D  H  height\n";
            int height = 0;
            for (Box box : fittestStack.getBoxList()) {
                height += box.getH();
                result += box.getW() + " " + box.getD() + " " + box.getH() + " " + height + " \n";
            }
            resultList[index] = result;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
