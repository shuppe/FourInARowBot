// // Copyright 2015 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//	
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package bot;
/**
 * BotStarter class
 * 
 * Magic happens here. You should edit this file, or more specifically
 * the makeTurn() method to make your bot do more than random moves.
 * 
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class BotStarter {	
     private Field field;
     private MoveSolver solver;

    public BotStarter() {
        this.solver = new RandomSolver();
    }

 	public static void main(String[] args) {
 		BotParser parser = new BotParser(new BotStarter());
 		parser.run();
 	}
     
    /**
      * Makes a turn. Edit this method to make your bot smarter.
      *
      * @return The column where the turn was made.
      */
     public int makeTurn() {

         int move = solver.nextMove(0, 0, field);
         return move;
     }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public MoveSolver getSolver() {
        return solver;
    }

    public void setSolver(MoveSolver solver) {
        this.solver = solver;
    }
}
