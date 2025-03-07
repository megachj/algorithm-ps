package sunset.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P0937_ReorderDataInLogFiles {

    public static void main(String[] args) {
        Solution solution = new P0937_ReorderDataInLogFiles().new Solution();
        String[] input = new String[]{"a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo"};
        String[] result = solution.reorderLogFiles(input);
        System.out.println(Arrays.toString(result));
    }

    class Solution {

        public String[] reorderLogFiles(String[] logs) {
            List<String> digitLogs = new ArrayList<>(logs.length);
            List<String> letterLogs = new ArrayList<>(logs.length);

            for (int i = 0; i < logs.length; ++i) {
                String id = logs[i].split(" ")[0];
                String content = logs[i].substring(id.length());

                boolean isDigitLog = content.matches("[0-9 ]+");
                if (isDigitLog) {
                    digitLogs.add(logs[i]);
                } else {
                    letterLogs.add(logs[i]);
                }
            }

            letterLogs.sort((a, b) -> {
                String aId = a.split(" ")[0];
                String aContent = a.substring(aId.length());

                String bId = b.split(" ")[0];
                String bContent = b.substring(bId.length());

                int compareContent = aContent.compareTo(bContent);
                if (compareContent != 0) {
                    return compareContent;
                } else {
                    return aId.compareTo(bId);
                }
            });

            String[] results = new String[logs.length];
            for (int i = 0; i < letterLogs.size(); ++i) {
                results[i] = letterLogs.get(i);
            }
            for (int i = 0; i < digitLogs.size(); ++i) {
                results[letterLogs.size() + i] = digitLogs.get(i);
            }

            return results;
        }
    }
}
