public class Block {
    private int x;
    private int y;
    private int width;
    private int height;

    private boolean hasCoin;
    private boolean hasArrival;
    
    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        hasCoin = false;
        this.hasArrival = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public boolean hasCoin() {
        return hasCoin;
    }

    public void setHasCoin(boolean hasCoin) {
        this.hasCoin = hasCoin;
    }
    // Add a method to set the hasArrival property
    public void setHasArrival(boolean hasArrival) {
        this.hasArrival = hasArrival;
    }

    // Add a method to check if the block has the arrival image
    public boolean hasArrival() {
        return hasArrival;
    }

    
    
}