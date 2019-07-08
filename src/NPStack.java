import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class NPStack {
    //IMPORTANT
    //This will run numThreads many GA's and
    //find the best result.
    //Using less than 8(?) threads will not affect performance.  (Depending on your cpu)
    public static void main(String[] args)
    {
        int numThreads = 25;
        List<NPStackThread> threadList = new ArrayList<>();
        String[] resultList = new String[numThreads];
        //spin up the threads
        for(int i = 0; i < numThreads; i++)
        {
            NPStackThread newStack = new NPStackThread(args, resultList, i);
            threadList.add(newStack);
            newStack.start();//start each of our GA's.
        }
        while (true) {
            //check if the threads have all finished
            Boolean alive = false;
            for (int i = 0; i < numThreads; i++) {
                if (threadList.get(i).isAlive())
                    alive = true;
            }
            if (!alive)
            {
                //if they have:
                int heightestThreadIndex = 0;
                int heightestValue = 0;
                //check which one got the best result
                for (int i = 0; i < resultList.length; i++)
                {
                    String[] splitted = resultList[i].split(" ");
                    if(Integer.parseInt(splitted[splitted.length-2]) >= heightestValue)
                    {
                        heightestValue = Integer.parseInt(splitted[splitted.length-2]);
                        heightestThreadIndex = i;
                    }
                }
                //print out the best result
                try {
                    File output = new File("output.txt");
                    output.delete();
                    output.createNewFile();

                    FileWriter fw = new FileWriter(output, true);
                    BufferedWriter bw = new BufferedWriter(fw);

                    System.out.println(resultList[heightestThreadIndex]);
                    bw.write(resultList[heightestThreadIndex]);
                    bw.close();
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                break;//game over!
            }
        }
    }
}
