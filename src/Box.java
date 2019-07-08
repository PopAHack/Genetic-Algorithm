public class Box implements Cloneable {
    private int x;
    private int y;
    private int z;
    private char orientation;
    private int id;

    //constructor
    public Box(int x, int y, int z, int id) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

//    public int[] getWidthandDepth(char q){
//        int x=0,y=0;
//        switch (q)
//        {
//            case 'x': {
//                x = getD();
//                y = getH();
//                break; }
//            case 'y': {
//                x = getW();
//                y = getH();
//                break; }
//            case 'z': {
//                x = getW();
//                y = getD();
//                break; }
//        }
//        int[] points = new int[]{x,y};
//        return points;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getD() {
        return y;
    }

    public void setD(int y) {
        this.y = y;
    }

    public int getH() {
        return z;
    }

    public void setH(int z) {
        this.z = z;
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    public int getW() {
        return x;
    }

    public void setW(int x) {
        this.x = x;
    }
}
