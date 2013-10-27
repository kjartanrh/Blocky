package is.ru.Blocky;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Notandi
 * Date: 22.10.2013
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
public class Challenge {

    private int id;
    private int level;
    private int length;
    private List<BlockPosition> blocks;

    public Challenge() {
        blocks = new ArrayList<BlockPosition>();
    }

    public Challenge(int id) {
        this.id = id;
        blocks = new ArrayList<BlockPosition>();
    }

    public enum Alignment {
        Vertical,
        Horizontal
    }

    public class BlockPosition {
        private int x;
        private int y;
        private int blockLength;
        private Alignment alignment;
        private boolean isEscapee;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getBlockLength() {
            return blockLength;
        }

        public void setBlockLength(int blockLength) {
            this.blockLength = blockLength;
        }

        public Alignment getAlignment() {
            return alignment;
        }

        public void setAlignment(Alignment alignment) {
            this.alignment = alignment;
        }

        public boolean isEscapee() {
            return isEscapee;
        }

        public void setEscapee(boolean escapee) {
            isEscapee = escapee;
        }

        public BlockPosition() {
        }

        public BlockPosition(int x, int y, int blockLength, Alignment alignment) {
            this.x = x;
            this.y = y;
            this.blockLength = blockLength;
            this.alignment = alignment;
        }

        public boolean occupies( int x, int y) {
            boolean occupies = false;
            if(this.alignment == Alignment.Horizontal) {
                if( (( x >= this.x ) && ( x <= (this.x + (this.blockLength - 1) )) ) && y == this.y )  {
                    occupies = true;
                }
            }else {
                if( (( y >= this.y ) && ( y <= (this.y + (this.blockLength - 1) )) ) && x == this.x )  {
                    occupies = true;
                }
            }
            return occupies;
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append( ((alignment == Alignment.Horizontal) ? "Horizontal" : "Vertical")  + "\n");
            sb.append("X: " + x + "\n");
            sb.append("Y: " + y + "\n");
            sb.append("Length: " + blockLength + "\n");

            return sb.toString();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<BlockPosition> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockPosition> blocks) {
        this.blocks = blocks;
    }

    public BlockPosition getEscapee() {
        if(blocks.size() > 0) {
            return blocks.get(0);
        } else {
            return null;
        }
    }



    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: " + id + "\n");
        sb.append("Level: " + level + "\n");
        sb.append("Length: " + length + "\n");
        sb.append("Blocks: " + "\n");
        for (BlockPosition block : blocks) {
            sb.append(block.toString());
        }

        return sb.toString();
    }
}
