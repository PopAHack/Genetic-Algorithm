import java.util.List;

public class BoxFactory {
    public BoxFactory(List<Box> boxList) {
        this.boxList = boxList;
    }

    //global vars
    private List<Box> boxList;
    private int id = 0;

    public void createBox(String line)
    {
        String[] lineArray = line.split(" ");
        Box newBox = new Box(Integer.parseInt(lineArray[0]),Integer.parseInt(lineArray[1]), Integer.parseInt(lineArray[2]), id);
        boxList.add(newBox);
        id++;
    }
}