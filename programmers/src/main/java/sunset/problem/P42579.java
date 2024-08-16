package sunset.problem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class P42579 {

    class Solution {

        /**
         *
         * @param genres    고유번호가 i인 노래의 장르. plays 와 길이가 같음(1이상 10000이하)
         * @param plays     고유번호가 i인 노래가 재생된 횟수
         * @return 베스트 앨범 리스트<br>
         * 1. 속한 노래가 많이 재생된 장르를 먼저 수록한다.<br>
         * 2. 장르 내에서 많이 재생된 노래를 먼저 수록한다.<br>
         * 3. 장르 내에서 재생 횟수가 같으면 고유 번호가 낮은 노래를 먼저 수록한다.<br>
         * 기타: 장르마다 최대 2곡만 넣을 수 있다.<br>
         */
        public int[] solution(String[] genres, int[] plays) {
            Map<String, GenreTotal> genreMap = new HashMap<>(genres.length);
            for (int i = 0; i < genres.length; i++) {
                if (!genreMap.containsKey(genres[i])) {
                    genreMap.put(genres[i], new GenreTotal(genres[i]));
                }
                GenreTotal genreTotal = genreMap.get(genres[i]);
                genreTotal.addSong(new Song(i, plays[i]));
            }

            List<GenreTotal> genreTotals = genreMap.values()
                    .stream()
                    .sorted((g1, g2) -> {
                        if (g1.totalPlay > g2.totalPlay)
                            return -1;
                        else
                            return 1;
                    })
                    .collect(Collectors.toList());

            int size = genreTotals.stream().map(GenreTotal::maxSongCount).reduce(0, Integer::sum);

            int[] results = new int[size];
            int resultIndex = 0;
            for (int i = 0; i < genreTotals.size(); i++) {
                GenreTotal genreTotal = genreTotals.get(i);
                for (int j = 0; j < genreTotal.maxSongCount(); j++) {
                    results[resultIndex++] = genreTotal.maxSongsDesc[j].no;
                }
            }
            return results;
        }
    }

    public static class GenreTotal {
        public int totalPlay = 0;
        public int songCount = 0;
        public Song[] maxSongsDesc = new Song[2];

        public GenreTotal(String genre) {}

        public void addSong(Song song) {
            totalPlay += song.plays;
            for (int i = 0; i < 2; i++) {
                if (maxSongsDesc[i] == null) {
                    maxSongsDesc[i] = song;
                    break;
                }

                int compare = song.compareTo(maxSongsDesc[i]);
                if (compare < 0) {
                    Song oldSong = maxSongsDesc[i];
                    maxSongsDesc[i] = song;
                    if (i == 0) {
                        maxSongsDesc[i + 1] = oldSong;
                    }
                    break;
                }
            }
            songCount++;
        }

        public int maxSongCount() {
            return Math.min(songCount, 2);
        }
    }

    public static class Song implements Comparable<Song> {
        public int no;
        public int plays;

        public Song(int no, int plays) {
            this.no = no;
            this.plays = plays;
        }

        @Override
        public int compareTo(Song o) {
            if (this.plays > o.plays) {
                return -1;
            } else if (this.plays < o.plays) {
                return 1;
            } else {
                if (this.no < o.no) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }
}
