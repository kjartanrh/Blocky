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
    private int level;
    private int length;
    private List<BlockPosition> obsticles;
    private BlockPosition escapee;

    public Challenge() {
        obsticles = new ArrayList<BlockPosition>();
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

        public BlockPosition() {
        }

        public BlockPosition(int x, int y, int blockLength, Alignment alignment) {
            this.x = x;
            this.y = y;
            this.blockLength = blockLength;
            this.alignment = alignment;
        }
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

    public List<BlockPosition> getObsticles() {
        return obsticles;
    }

    public void setObsticles(List<BlockPosition> obsticles) {
        this.obsticles = obsticles;
    }

    public BlockPosition getEscapee() {
        return escapee;
    }

    public void setEscapee(BlockPosition escapee) {
        this.escapee = escapee;
    }

}
