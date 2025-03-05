package sunset.leetcode;

public class P0121_BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        Solution solution = new P0121_BestTimeToBuyAndSellStock().new Solution();
        int result = solution.maxProfit(new int[]{7, 6, 4, 3, 1});
        System.out.println(result);
    }

    class Solution {
        public int maxProfit(int[] prices) {
            int minPrice = Integer.MAX_VALUE;
            int maxProfit = 0;
            for (int i = 0; i < prices.length; ++i) {
                minPrice = Math.min(minPrice, prices[i]);
                maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            }

            return maxProfit;
        }
    }
}
