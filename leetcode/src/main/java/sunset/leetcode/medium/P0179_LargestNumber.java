package sunset.leetcode.medium;

import java.util.Arrays;
import java.util.Comparator;

public class P0179_LargestNumber {

    public static void main(String[] args) {
        // {10, 2} -> 210
        // {3, 30, 34, 5, 9} -> 9534330
        // {34323, 3432} -> 343234323
        // {0, 0} -> 0
        int[] inputs = new int[]{34323, 3432};

        Solution solution = new P0179_LargestNumber().new Solution();
        String output = solution.largestNumber(inputs);

        System.out.println(output);
    }

    class Solution {
        private Comparator<String> sameLengthComparator = Comparator.reverseOrder();

        private Comparator<String> differentLengthComparator = (a, b) -> {
            String aConcatB = a + b;
            String bConcatA = b + a;
            return bConcatA.compareTo(aConcatB);
        };

        public String largestNumber(int[] nums) {
            String largestNumber = Arrays.stream(nums)
                    .mapToObj(Integer::toString)
                    .sorted((a, b) -> {
                        if (a.length() == b.length()) {
                            return sameLengthComparator.compare(a, b);
                        } else {
                            return differentLengthComparator.compare(a, b);
                        }
                    })
                    .reduce(String::concat)
                    .get();

            if (largestNumber.charAt(0) == '0') {
                return "0";
            } else {
                return largestNumber;
            }
        }
    }
}
