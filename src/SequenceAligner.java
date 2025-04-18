import java.util.Random;

/**
 * TODO: Implement the fillCache(), getResult(), and traceback() methods, in
 * that order. This is the biggest part of this project.
 */

public class SequenceAligner {
    private static Random gen = new Random();

    private String S, T;
    private int n, m;
    private String alignedS, alignedT;
    private Result[][] cache;
    private Judge judge;

    /**
     * Generates a pair of random DNA strands, where S is of length n and
     * T has some length between n/2 and 3n/2, and aligns them using the
     * default judge.
     */
    public SequenceAligner(int n) {
        this(randomDNA(n), randomDNA(n - gen.nextInt(n / 2) * (gen.nextInt(2) * 2 - 1)));
    }


    /**
     * Aligns the given strands using the default judge.
     */
    public SequenceAligner(String S, String T) {
        this(S, T, new Judge());
    }

    /**
     * Aligns the given strands using the specified judge.
     */
    public SequenceAligner(String S, String T, Judge judge) {
        this.S = S.toUpperCase();
        this.T = T.toUpperCase();
        this.judge = judge;
        n = S.length();
        m = T.length();
        cache = new Result[n + 1][m + 1];
        fillCache();
        traceback();
    }

    /**
     * Returns the S strand.
     */
    public String getS() {
        return S;
    }

    /**
     * Returns the T strand.
     */
    public String getT() {
        return T;
    }

    /**
     * Returns the judge associated with this pair.
     */
    public Judge getJudge() {
        return judge;
    }

    /**
     * Returns the aligned version of the S strand.
     */
    public String getAlignedS() {
        return alignedS;
    }

    /**
     * Returns the aligned version of the T strand.
     */
    public String getAlignedT() {
        return alignedT;
    }

    /**
     * TODO: Solve the alignment problem using bottom-up dynamic programming
     * algorithm described in lecture. When you're done, cache[i][j] will hold
     * the result of solving the alignment problem for the first i characters
     * in S and the first j characters in T.
     * <p>
     * Your algorithm must run in O(n * m) time, where n is the length of S
     * and m is the length of T.
     */
    private void fillCache() {
        // delete this line and add your code
    }

    /**
     * TODO: Returns the result of solving the alignment problem for the
     * first i characters in S and the first j characters in T. You can
     * find the result in O(1) time by looking in your cache.
     */
    public Result getResult(int i, int j) {
        return null;  // delete this line and add your code
    }

    /**
     * TODO: Mark the path by tracing back through parent pointers, starting
     * with the Result in the lower right corner of the cache. Call Result.markPath()
     * on each Result along the path. The GUI will highlight all such marked cells
     * when you check 'Show path'. As you're tracing back along the path, build
     * the aligned strings in alignedS and alignedT (using Constants.GAP_CHAR
     * to denote a gap in the strand).
     * <p>
     * Your algorithm must run in O(n + m) time, where n is the length of S
     * and m is the length of T.
     */
    private void traceback() {
        // delete this line and add your code
    }

    /**
     * Returns true iff these strands are seemingly aligned.
     */
    public boolean isAligned() {
        return alignedS != null && alignedT != null &&
                alignedS.length() == alignedT.length();
    }

    /**
     * Returns the score associated with the current alignment.
     */
    public int getScore() {
        if (isAligned())
            return judge.score(alignedS, alignedT);
        return 0;
    }

    /**
     * Returns a nice textual version of this alignment.
     */
    public String toString() {
        if (!isAligned())
            return "[S=" + S + ",T=" + T + "]";
        final char GAP_SYM = '.', MATCH_SYM = '|', MISMATCH_SYM = ':';
        StringBuilder ans = new StringBuilder();
        ans.append(alignedS).append('\n');
        int n = alignedS.length();
        for (int i = 0; i < n; i++)
            if (alignedS.charAt(i) == Constants.GAP_CHAR || alignedT.charAt(i) == Constants.GAP_CHAR)
                ans.append(GAP_SYM);
            else if (alignedS.charAt(i) == alignedT.charAt(i))
                ans.append(MATCH_SYM);
            else
                ans.append(MISMATCH_SYM);
        ans.append('\n').append(alignedT).append('\n').append("score = ").append(getScore());
        return ans.toString();
    }

    /**
     * Returns a DNA strand of length n with randomly selected nucleotides.
     */
    private static String randomDNA(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append("ACGT".charAt(gen.nextInt(4)));
        return sb.toString();
    }

}