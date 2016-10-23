package bot;

import java.util.Random;

/**
 * Created by sylvain on 16-10-03.
 */
public class RandomSolver implements MoveSolver {

    private Field field;

    public RandomSolver(Field field) {
        this.field = field;
    }

    @Override
    public int nextMove(int player, int level) {

        return new Random().nextInt(field.getNrColumns());

    }
}
