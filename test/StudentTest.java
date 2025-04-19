/**
 * To test with JUnit, add JUnit to your project. To do this, go to
 * Project->Properties. Select "Java Build Path". Select the "Libraries"
 * tab and "Add Library". Select JUnit, then JUnit 4.
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

  void validate_cache(SequenceAligner sa, Judge judge) {
    int num_rows = sa.getS().length() + 1;
    int num_cols = sa.getT().length() + 1;
    for (int i = 0; i != num_rows; ++i) {
      for (int j = 0; j != num_cols; ++j) {
        Result result = sa.getResult(i, j);
        if (i == 0 && j == 0) {
          assertEquals(0, result.getScore());
          assertEquals(Direction.NONE, result.getParent());
        } else if (i == 0) {
          assertEquals(j * judge.getGapCost(), result.getScore());
          assertEquals(Direction.LEFT, result.getParent());
        } else if (j == 0) {
          assertEquals(i * judge.getGapCost(), result.getScore());
          assertEquals(Direction.UP, result.getParent());
        } else {
          int diag_score = sa.getResult(i - 1, j - 1).getScore()
                  + judge.score(sa.getS().charAt(i - 1), sa.getT().charAt(j - 1));
          int left_score = sa.getResult(i, j - 1).getScore() + judge.getGapCost();
          int up_score = sa.getResult(i - 1, j).getScore() + judge.getGapCost();
          if (result.getParent() == Direction.DIAGONAL) {
            assertEquals(diag_score, result.getScore());
            assertTrue(left_score <= diag_score);
            assertTrue(up_score <= diag_score);
          } else if (result.getParent() == Direction.LEFT) {
            assertEquals(left_score, result.getScore());
            assertTrue(diag_score <= left_score);
            assertTrue(up_score <= left_score);
          } else if (result.getParent() == Direction.UP) {
            assertEquals(up_score, result.getScore());
            assertTrue(diag_score <= up_score);
            assertTrue(left_score <= up_score);
          }
        }
      }
    }
  }

  void validate_path(SequenceAligner sa) {
    int i = sa.getS().length();
    int j = sa.getT().length();
    String alignedX = sa.getAlignedS();
    String alignedY = sa.getAlignedT();
    while (i >= 0 && j >= 0) {
      Result result = sa.getResult(i, j);
      assertTrue(result.onPath());
      if (result.getParent() == Direction.DIAGONAL) {
        assertEquals(sa.getS().charAt(i - 1), alignedX.charAt(alignedX.length() - 1));
        assertEquals(sa.getT().charAt(j - 1), alignedY.charAt(alignedY.length() - 1));
        alignedX = alignedX.substring(0, alignedX.length() - 1);
        alignedY = alignedY.substring(0, alignedY.length() - 1);
        --i;
        --j;
      } else if (result.getParent() == Direction.LEFT) {
        assertEquals('_', alignedX.charAt(alignedX.length() - 1));
        assertEquals(sa.getT().charAt(j - 1), alignedY.charAt(alignedY.length() - 1));
        alignedX = alignedX.substring(0, alignedX.length() - 1);
        alignedY = alignedY.substring(0, alignedY.length() - 1);
        --j;
      } else if (result.getParent() == Direction.UP) {
        assertEquals(sa.getS().charAt(i - 1), alignedX.charAt(alignedX.length() - 1));
        assertEquals('_', alignedY.charAt(alignedY.length() - 1));
        alignedX = alignedX.substring(0, alignedX.length() - 1);
        alignedY = alignedY.substring(0, alignedY.length() - 1);
        --i;
      } else if (result.getParent() == Direction.NONE) {
        break;
      }
    }
  }

  /**
   * Expand and write more tests. You might want to additionally test
   * for cases commented out.
   */

  @Test
  public void test() {
    defaultJudgeTest();
    customJudgeTest();
    empties();
    singletons();
    simpleAlignment();
    //singletonsWithCustomJudge();
    //doubletons();
    //oneTwos();
    //bigBases();
    //pathMarks();
  }

  @Test
  public void defaultJudgeTest() {
    Judge judge = new Judge();
    assertEquals(2, judge.score('A', 'A'));
    assertEquals(2, judge.score("A", "A"));
  }

  @Test
  public void customJudgeTest() {
    Judge judge = new Judge(3, -3, -2);
    assertEquals(3, judge.score('A', 'A'));
    assertEquals(3, judge.score("A", "A"));
  }

  /**********************************************
   * Testing SequenceAligner.fillCache()
   **********************************************/

  @Test
  public void empties() {
    SequenceAligner sa;
    Result result;
    sa = new SequenceAligner("", "");
    validate_cache(sa, new Judge());
    validate_path(sa);
  }

  @Test
  public void singletons() {
    SequenceAligner sa;
    Result result;
    sa = new SequenceAligner("A", "A");
    validate_cache(sa, new Judge());
    validate_path(sa);
    sa = new SequenceAligner("A", "C");
    validate_cache(sa, new Judge());
    validate_path(sa);
  }

  /**********************************************
   * Testing SequenceAligner.traceback()
   **********************************************/

  @Test
  public void simpleAlignment() {
    SequenceAligner sa;
    sa = new SequenceAligner("ACGT", "ACGT");
    assertTrue(sa.isAligned());
    assertEquals("ACGT", sa.getAlignedS());
    assertEquals("ACGT", sa.getAlignedT());
  }

}