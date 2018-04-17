	import java.util.Collections;
/**
 * This class is used to store information on possible moves.
 */
	import java.util.Vector;
public class move implements Comparable {
	
	/**
	 * The following variables are the store the information on the features of each move.
	 */
		public int row;
		public int column;
		public int inRowLeft = 0;
		public int inRowLeftUp = 0;
		public int inRowLeftDown = 0;
		public int inRowRight= 0;
		public int inRowRightUp= 0;
		public int inRowRightDown= 0;
		public int inRowUp= 0;
		public int inRowDown= 0;
		public int inRowLeftEnemy = 0;
		public int inRowLeftUpEnemy = 0;
		public int inRowLeftDownEnemy = 0;
		public int inRowRightEnemy= 0;
		public int inRowRightUpEnemy= 0;
		public int inRowRightDownEnemy= 0;
		public int inRowUpEnemy= 0;
		public int inRowDownEnemy= 0;
		public int ThreeHorz = 0;
		public int ThreeVert = 0;
		public int ThreeDiagPlus =0;
		public int ThreeDiagMinus =0;
		public int FourHorz = 0;
		public int FourVert = 0;
		public int FourDiagPlus =0;
		public int FourDiagMinus =0;
		public int winHorz = 0;
		public int winVert = 0;
		public int winDiagPlus =0;
		public int winDiagMinus =0;
		public int ThreeHorzEnemy = 0;
		public int ThreeVertEnemy = 0;
		public int ThreeDiagPlusEnemy =0;
		public int ThreeDiagMinusEnemy =0;
		public int FourHorzEnemy = 0;
		public int FourVertEnemy = 0;
		public int FourDiagPlusEnemy =0;
		public int FourDiagMinusEnemy =0;
		public int winHorzEnemy = 0;
		public int winVertEnemy = 0;
		public int winDiagPlusEnemy =0;
		public int winDiagMinusEnemy =0;
		public Double strength= 0.0;
		
		/**
		 * This constructor creates an instance of a move and initializes it with its row and column coordinates.
		 * @param rowcoord - the row position
		 * @param columncoord - the column position
		 */
		public move(int rowcoord, int columncoord) {
			row = rowcoord;
			column = columncoord;
			}

		/**
		 * This method allows moves to be compared (and organized) by their strength.
		 */
		public int compareTo(Object c) {
			move e = (move) c;

			int result = this.strength.compareTo(e.strength);

			if (result > 0) {
				return -1;
			} else if (result == 0) {
				return 0;
			} else {
				return 1;
			}
		}
}
