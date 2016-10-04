package bot;

import java.util.Random;

/**
 * Created by sylvain on 16-10-03.
 */
public class RandomSolver implements MoveSolver {

    @Override
    public int nextMove(int player, int level, Field field) {

        return new Random().nextInt(field.getNrColumns());

    }
}
