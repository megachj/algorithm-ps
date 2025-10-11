package sunset.leetcode;

import java.util.LinkedList;
import java.util.List;

public class P0706_DesignHashMap {

    public static void main(String[] args) {
        MyHashMap myHashMap = new P0706_DesignHashMap().new MyHashMap();
        myHashMap.put(1, 1);
        myHashMap.put(2, 2);
        int value = myHashMap.get(1);
        System.out.println(value);
    }

    /**
     * 결과: 71ms / 48.03MB
     */
    class MyHashMap {
        private final int bucketSize = 10^4;

        // [0]: key, [1]: value
        private List<int[]>[] bucketArray = new LinkedList[bucketSize];
        private int dataSize = 0;

        public MyHashMap() {
        }

        public void put(int key, int value) {
            int hash = hash(key);
            List<int[]> bucket = bucketArray[hash];
            if (bucket == null) {
                bucket = new LinkedList<>();
                bucket.add(new int[]{key, value});
                dataSize++;

                bucketArray[hash] = bucket;
            } else {
                boolean changed = false;
                for (int[] ints : bucket) {
                    if (ints[0] == key) {
                        ints[1] = value;
                        changed = true;
                    }
                }
                if (!changed) {
                    bucket.add(new int[]{key, value});
                    dataSize++;
                }
            }
        }

        public int get(int key) {
            int hash = hash(key);
            List<int[]> bucket = bucketArray[hash];
            if (bucket == null) {
                return -1;
            }

            for (int[] ints : bucket) {
                if (ints[0] == key) {
                    return ints[1];
                }
            }
            return -1;
        }

        public void remove(int key) {
            int hash = hash(key);
            List<int[]> bucket = bucketArray[hash];
            if (bucket == null) {
                return;
            }

            for (int i = 0; i < bucket.size(); ++i) {
                if (bucket.get(i)[0] == key) {
                    bucket.remove(i);
                    dataSize--;
                    break;
                }
            }
        }

        private int hash(int key) {
            return key % bucketSize;
        }
    }
}
