package vanier.com.programmingpatternslia.VarEngine.Animation;


import java.awt.image.BufferedImage;

public class Sheet {
    //Will Contain Data to be able to render animation

    private int columns;
    private int rows;
    private int count;
    private int offsetX, offSetY;
    private int width, height;
    private BufferedImage sheetImg;

    public Sheet(int count, int height, int width, int offSetY, int offsetX, int rows, int columns, BufferedImage img) {
        this.height = height;
        this.width = width;
        this.offSetY = offSetY;
        this.offsetX = offsetX;
        this.count = count;
        this.rows = rows;
        this.columns = columns;
        sheetImg = img;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getCount() {
        return count;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffSetY() {
        return offSetY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
