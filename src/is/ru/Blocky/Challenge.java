package is.ru.Blocky;

import android.util.Log;

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
        private int index;
        private int x;
        private int y;
        private int blockLength;
        private Alignment alignment;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

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
            return index == 0;
        }

        public BlockPosition() {
        }

        public BlockPosition(int x, int y, int blockLength, Alignment alignment) {
            this.x = x;
            this.y = y;
            this.blockLength = blockLength;
            this.alignment = alignment;
        }

        public boolean contains(int x, int y) {
            boolean contains = false;
            if(this.alignment == Alignment.Horizontal) {
                if( (( x >= this.x ) && ( x <= (this.x + (this.blockLength - 1) )) ) && y == this.y )  {
                    contains = true;
                }
            }else {
                if( (( y >= this.y ) && ( y <= (this.y + (this.blockLength - 1) )) ) && x == this.x )  {
                    contains = true;
                }
            }
            return contains;
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

    public BlockPosition getBlockAtXY(int x, int y) throws BlockNotFoundException {
        int index = -1;

        for(int i = 0; i < blocks.size(); i++) {
            if(blocks.get(i).contains(x,y)) {
                index = i;
            }
        }

        if(index == -1) {
            throw new BlockNotFoundException();
        } else {
            return blocks.get(index);
        }
    }

    public void moveBlock(int blockId, int offset ) {
        if( blockId < blocks.size()) {
            boolean canMove = canMoveBlock( blocks.get(blockId), offset );

            if( canMove ){
                if(blocks.get(blockId).getAlignment() == Alignment.Horizontal) {
                    blocks.get(blockId).setX(blocks.get(blockId).getX() + offset);
                } else {
                    blocks.get(blockId).setY(blocks.get(blockId).getY() + offset);
                }
            }
        }
    }

    /**
     * Athugar hvort megi færa blokkina, með tilliti til jaðars spilaborðs og árekstrar við aðra kubba.
     *
     * @todo Virkar ekki ef blokkin er dregin of hratt
     * @param pos
     * @param offset
     * @return
     */
    private boolean canMoveBlock(BlockPosition pos, int offset){
        //Check if we are moving out of bounds
        if( pos.getAlignment() == Alignment.Horizontal ){
            if((pos.getX() == 0) && (offset == -1)){
                return false;
            }

            if((pos.getX()+pos.getBlockLength() == 6) && (offset == 1)){
                return false;
            }
        }
        else{
            if((pos.getY() == 0) && (offset == -1)){
                return false;
            }

            if((pos.getY()+pos.getBlockLength() == 6) && (offset == 1)){
                return false;
            }
        }

        boolean found = false;

        for( BlockPosition block : blocks ){
            //Check to see if we're colliding with ourselves
            if(block.getIndex() != pos.getIndex() ){
                if( pos.getAlignment() == Alignment.Horizontal ){
                    if( offset == -1 ) {
                        found = occupies(block, (pos.getX() - 1), pos.getY());
                    }
                    else{
                        found = occupies(block, (pos.getX() + pos.getBlockLength() ), pos.getY());
                    }
                }
                else{
                    if( offset == -1 ) {
                        found = occupies(block, pos.getX(), (pos.getY() - 1) );
                    }
                    else{
                        found = occupies(block, pos.getX(), (pos.getY() + pos.getBlockLength() ));
                    }
                }
            }
        }

        return !found;
    }

    private boolean occupies(BlockPosition checkedBlock, int x, int y){
        if( checkedBlock.getAlignment() == Alignment.Horizontal ){

            int startingX = checkedBlock.getX();
            int finishingX = checkedBlock.getX() + checkedBlock.getBlockLength() - 1;

            for( int i = startingX; i <= finishingX; i++){
                if( i == x ){
                    if( y == checkedBlock.getY()){
                        Log.v("Tag", "Block @index " + checkedBlock.getIndex() + " is blocking (" + x + ", " + y + ")");
                        return true;
                    }
                }
            }
        }
        else{
            int startingY = checkedBlock.getY();
            int finishingY = checkedBlock.getY() + checkedBlock.getBlockLength() - 1;

            for( int i = startingY; i <= finishingY; i++){
                if( i == y ){
                    if( x == checkedBlock.getX()){
                        Log.v("Tag", "Block @index " + checkedBlock.getIndex() + " is blocking (" + x + ", " + y + ")");
                        return true;
                    }
                }
            }
        }
        return false;
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
