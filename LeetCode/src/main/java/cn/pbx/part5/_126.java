package cn.pbx.part5;

import java.util.*;

/**
 * @author BruceXu
 * @date 2020/12/14
 */
public class _126 {

    public static void main(String[] args) {
        test1();
        System.out.println("===");
        test2();
        System.out.println("===");
        test3();
        System.out.println("===");
        test4();
    }

    public static void test1() {
        String beginWord = "hit";
        String endWord = "cog";
        String[] wordList = {"hot", "dot", "dog", "lot", "log", "cog"};

        List<List<String>> res = findLadders(beginWord, endWord, Arrays.asList(wordList));
        for (List<String> list : res) {
            System.out.println(list);
        }
    }

    public static void test2() {
        String beginWord = "qa";
        String endWord = "sq";

        String[] wordList = {"si", "go", "se", "cm", "so",
                "ph", "mt", "db", "mb", "sb", "kr", "ln",
                "tm", "le", "av", "sm", "ar", "ci", "ca",
                "br", "ti", "ba", "to", "ra", "fa", "yo",
                "ow", "sn", "ya", "cr", "po", "fe", "ho",
                "ma", "re", "or", "rn", "au", "ur", "rh",
                "sr", "tc", "lt", "lo", "as", "fr", "nb",
                "yb", "if", "pb", "ge", "th", "pm", "rb",
                "sh", "co", "ga", "li", "ha", "hz", "no",
                "bi", "di", "hi", "qa", "pi", "os", "uh",
                "wm", "an", "me", "mo", "na", "la", "st",
                "er", "sc", "ne", "mn", "mi", "am", "ex",
                "pt", "io", "be", "fm", "ta", "tb", "ni",
                "mr", "pa", "he", "lr", "sq", "ye"};

        List<List<String>> res = findLadders(beginWord, endWord, Arrays.asList(wordList));
        for (List<String> list : res) {
            System.out.println(list);
        }
    }

    public static void test3() {
        String begin = "red";
        String end = "tax";
        String[] list = {"ted", "tex", "red", "tax", "tad", "den", "rex", "pee"};
        List<List<String>> list1 = findLadders(begin, end, Arrays.asList(list));
        for (List<String> strings : list1) {
            System.out.println(strings);
        }
    }

    public static void test4() {
        String[] s =
                {"kid", "tag", "pup", "ail", "tun", "woo", "erg", "luz", "brr", "gay", "sip", "kay", "per", "val", "mes", "ohs", "now", "boa", "cet", "pal", "bar", "die", "war", "hay", "eco", "pub", "lob", "rue", "fry", "lit", "rex", "jan", "cot", "bid", "ali", "pay", "col", "gum", "ger", "row", "won", "dan", "rum", "fad", "tut", "sag", "yip", "sui", "ark", "has", "zip", "fez", "own", "ump", "dis", "ads", "max", "jaw", "out", "btu", "ana", "gap", "cry", "led", "abe", "box", "ore", "pig", "fie", "toy", "fat", "cal", "lie", "noh", "sew", "ono", "tam", "flu", "mgm", "ply", "awe", "pry", "tit", "tie", "yet", "too", "tax", "jim", "san", "pan", "map", "ski", "ova", "wed", "non", "wac", "nut", "why", "bye", "lye", "oct", "old", "fin", "feb", "chi", "sap", "owl", "log", "tod", "dot", "bow", "fob", "for", "joe", "ivy", "fan", "age", "fax", "hip", "jib", "mel", "hus", "sob", "ifs", "tab", "ara", "dab", "jag", "jar", "arm", "lot", "tom", "sax", "tex", "yum", "pei", "wen", "wry", "ire", "irk", "far", "mew", "wit", "doe", "gas", "rte", "ian", "pot", "ask", "wag", "hag", "amy", "nag", "ron", "soy", "gin", "don", "tug", "fay", "vic", "boo", "nam", "ave", "buy", "sop", "but", "orb", "fen", "paw", "his", "sub", "bob", "yea", "oft", "inn", "rod", "yam", "pew", "web", "hod", "hun", "gyp", "wei", "wis", "rob", "gad", "pie", "mon", "dog", "bib", "rub", "ere", "dig", "era", "cat", "fox", "bee", "mod", "day", "apr", "vie", "nev", "jam", "pam", "new", "aye", "ani", "and", "ibm", "yap", "can", "pyx", "tar", "kin", "fog", "hum", "pip", "cup", "dye", "lyx", "jog", "nun", "par", "wan", "fey", "bus", "oak", "bad", "ats", "set", "qom", "vat", "eat", "pus", "rev", "axe", "ion", "six", "ila", "lao", "mom", "mas", "pro", "few", "opt", "poe", "art", "ash", "oar", "cap", "lop", "may", "shy", "rid", "bat", "sum", "rim", "fee", "bmw", "sky", "maj", "hue", "thy", "ava", "rap", "den", "fla", "auk", "cox", "ibo", "hey", "saw", "vim", "sec", "ltd", "you", "its", "tat", "dew", "eva", "tog", "ram", "let", "see", "zit", "maw", "nix", "ate", "gig", "rep", "owe", "ind", "hog", "eve", "sam", "zoo", "any", "dow", "cod", "bed", "vet", "ham", "sis", "hex", "via", "fir", "nod", "mao", "aug", "mum", "hoe", "bah", "hal", "keg", "hew", "zed", "tow", "gog", "ass", "dem", "who", "bet", "gos", "son", "ear", "spy", "kit", "boy", "due", "sen", "oaf", "mix", "hep", "fur", "ada", "bin", "nil", "mia", "ewe", "hit", "fix", "sad", "rib", "eye", "hop", "haw", "wax", "mid", "tad", "ken", "wad", "rye", "pap", "bog", "gut", "ito", "woe", "our", "ado", "sin", "mad", "ray", "hon", "roy", "dip", "hen", "iva", "lug", "asp", "hui", "yak", "bay", "poi", "yep", "bun", "try", "lad", "elm", "nat", "wyo", "gym", "dug", "toe", "dee", "wig", "sly", "rip", "geo", "cog", "pas", "zen", "odd", "nan", "lay", "pod", "fit", "hem", "joy", "bum", "rio", "yon", "dec", "leg", "put", "sue", "dim", "pet", "yaw", "nub", "bit", "bur", "sid", "sun", "oil", "red", "doc", "moe", "caw", "eel", "dix", "cub", "end", "gem", "off", "yew", "hug", "pop", "tub", "sgt", "lid", "pun", "ton", "sol", "din", "yup", "jab", "pea", "bug", "gag", "mil", "jig", "hub", "low", "did", "tin", "get", "gte", "sox", "lei", "mig", "fig", "lon", "use", "ban", "flo", "nov", "jut", "bag", "mir", "sty", "lap", "two", "ins", "con", "ant", "net", "tux", "ode", "stu", "mug", "cad", "nap", "gun", "fop", "tot", "sow", "sal", "sic", "ted", "wot", "del", "imp", "cob", "way", "ann", "tan", "mci", "job", "wet", "ism", "err", "him", "all", "pad", "hah", "hie", "aim", "ike", "jed", "ego", "mac", "baa", "min", "com", "ill", "was", "cab", "ago", "ina", "big", "ilk", "gal", "tap", "duh", "ola", "ran", "lab", "top", "gob", "hot", "ora", "tia", "kip", "han", "met", "hut", "she", "sac", "fed", "goo", "tee", "ell", "not", "act", "gil", "rut", "ala", "ape", "rig", "cid", "god", "duo", "lin", "aid", "gel", "awl", "lag", "elf", "liz", "ref", "aha", "fib", "oho", "tho", "her", "nor", "ace", "adz", "fun", "ned", "coo", "win", "tao", "coy", "van", "man", "pit", "guy", "foe", "hid", "mai", "sup", "jay", "hob", "mow", "jot", "are", "pol", "arc", "lax", "aft", "alb", "len", "air", "pug", "pox", "vow", "got", "meg", "zoe", "amp", "ale", "bud", "gee", "pin", "dun", "pat", "ten", "mob"};
        List<List<String>> ladders = findLadders("cet", "ism", Arrays.asList(s));
        for (List<String> ladder : ladders) {
            System.out.println(ladder);
        }
    }

    public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        if (beginWord.length() == 1) {
            res.add(Arrays.asList(beginWord, endWord));
            return res;
        }

        Map<String, Set<String>> map = new HashMap<>();
        Set<String> visited = new HashSet<>();
        generate(map, beginWord, wordList);
        if (!map.containsKey(endWord)) {
            return res;
        }
        // bfs找到最短路径长度
        bfs2(res, beginWord, endWord, map, visited);

        return res;
    }

    // 在BFS的时候同时记录路径，唯一不同的就是把BFS的队列从待选元素改成待选路径
    private static void bfs2(List<List<String>> res, String beginWord, String endWord, Map<String, Set<String>> map,
                             Set<String> visited) {
        // 把beginword放到路径中，然后放到队列中
        Deque<Deque<String>> queue = new ArrayDeque<>();
        Deque<String> p = new LinkedList<>();
        p.add(beginWord);
        visited.add(beginWord);
        queue.add(p);
        int size = queue.size();
        int len = 1;
        // 因为bfs进行搜索的时候，第一次找到的一定是最短的路径，此时记录最短路径长度
        // 再找到最短的之后，就没有必要在继续向下一层去搜索了，只要横向搜索即可
        int min = map.size() + 1;
        while (size-- > 0 && !queue.isEmpty() && min >= len) {
            // 出队，获得原路径上最后一个点，再从这个点开始进行搜索
            Deque<String> path = queue.removeFirst();
            String last = path.getLast();
            for (String s : map.get(last)) {
                if (visited.contains(s)) {
                    continue;
                }
                if (s.equals(endWord) && min >= len) {
                    path.addLast(s);
                    min = len;
                    res.add(new ArrayList<>(path));
                    continue;
                }
                Deque<String> nextPath = new ArrayDeque<>(path);

                nextPath.addLast(s);
                queue.addLast(nextPath);
            }
            if (size == 0) {
                size = queue.size();
                len++;
                for (Deque<String> strings : queue) {
                    visited.addAll(strings);
                }
            }
        }
    }


    public static void dfs(List<List<String>> res, String beginWord, String endWord, Map<String, Set<String>> map,
                           Deque<String> stack, Set<String> visited, int step) {

        if (beginWord.equals(endWord) && stack.size() == step - 1) {
            stack.addLast(beginWord);
            res.add(new ArrayList<>(stack));
            stack.removeLast();
            return;
        }
        if (visited.contains(beginWord) || stack.size() >= step) {
            return;
        }
        visited.add(beginWord);
        stack.addLast(beginWord);
        for (String s : map.get(beginWord)) {
            dfs(res, s, endWord, map, stack, visited, step);
        }
        visited.remove(beginWord);
        stack.removeLast();
    }

    public static int bfs(String beginWord, String endWord,
                          Map<String, Set<String>> map, Deque<String> queue, Set<String> visited) {
        queue.add(beginWord);
        visited.add(beginWord);
        int len = 1;
        int size = queue.size();
        while (size-- > 0 && !queue.isEmpty()) {
            String current = queue.removeFirst();
            Set<String> set = map.get(current);
            for (String s : set) {
                if (visited.contains(s)) {
                    continue;
                }
                if (s.equals(endWord)) {
                    return len + 1;
                }
                queue.addLast(s);
                visited.add(s);
            }
            if (size == 0) {
                size = queue.size();
                len++;
            }
        }
        return 0;
    }

    public static void generate(Map<String, Set<String>> map, String beginWord, List<String> wordList) {
        List<String> list = new ArrayList<>(wordList);
        list.add(beginWord);
        for (String s1 : list) {
            Set<String> set = new HashSet<>();
            for (String s2 : list) {
                if (check(s1, s2)) {
                    set.add(s2);
                }
            }
            map.put(s1, set);
        }
    }

    public static boolean check(String word1, String word2) {
        if (word1.equals(word2)) {
            return false;
        }
        int p = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                p++;
            }
        }
        return p < 2;

    }

}
