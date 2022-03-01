# 剑指Offer

### 07.重建二叉树

**示例**

前序：53, 45, 6, 13, 68, 69, 94, 72, 84

中序：6, 13, 45, 53, 68, 69, 72, 84, 94

二叉树

<img src="https://gitee.com/primabrucexu/image/raw/main/image/202202021830164.png" alt="image-20220202182033939" style="zoom:33%;" />

**思路**

1. 前序遍历中，每个元素都是其所在子树的根节点
2. 根据已知的根节点，到中序找其左右子树所含的节点
3. 重复1和2

**难点**

递归容易超时，迭代时明确当前节点作为“根节点”时的中序遍历结果

**代码**

- 递归，超时。原因：树退化成了链表，需要针对性的做一下优化

  ~~~java
  private TreeNode buildTreeV1(List<Integer> preorder, List<Integer> inorder) {
      if (preorder.isEmpty()) {
          return null;
      }
  
      // 前序遍历的第一个节点一定是二叉树的根
      TreeNode root = new TreeNode(preorder.get(0));
  
      // 递归出口
      if (preorder.size() == 1) {
          return root;
      }
  
      // 找到根之后，到中序遍历的里面找根左子树和右子树
      int rootPos = 0;
      for (int i = 0; i < inorder.size(); i++) {
          if (inorder.get(i) == root.val) {
              rootPos = i;
              break;
          }
      }
  
      List<Integer> leftInorder = inorder.subList(0, rootPos);
      List<Integer> rightInorder = inorder.subList(rootPos + 1, inorder.size());
  
      List<Integer> leftPreorder = preorder.stream().filter(leftInorder::contains).collect(Collectors.toList());
      List<Integer> rightPreorder = preorder.stream().filter(rightInorder::contains).collect(Collectors.toList());
  
      TreeNode left = buildTreeV1(leftPreorder, leftInorder);
      TreeNode right = buildTreeV1(rightPreorder, rightInorder);
  
      root.left = left;
      root.right = right;
  
      return root;
  }
  ~~~

- 迭代

  ~~~java
  private TreeNode buildTreeV2(List<TreeNode> preorder, List<Integer> inorder) {
      if (preorder.isEmpty()) {
          return null;
      }
  
      // 前序遍历的第一个必是根节点
      TreeNode root = preorder.get(0);
  
      int size = preorder.size();
      if (size == 1) {
          return root;
      }
  
      // 针对链表情况的特殊优化，当前序和中序倒着一一对应时，说明是树退化成了链表
      boolean isLinkedList = true;
      for (int i = 0; i < size; i++) {
          if (!inorder.get(i).equals(preorder.get(size - 1 - i).val)) {
              isLinkedList = false;
              break;
          }
      }
  
      if (isLinkedList) {
          for (int i = 0; i < size - 1; i++) {
              preorder.get(i).left = preorder.get(i + 1);
          }
          return preorder.get(0);
      }
  
      Deque<Node> queue = new ArrayDeque<>();
      Node rootNode = getNode(root, inorder);
      queue.addLast(rootNode);
  
      while (!queue.isEmpty()) {
          Node node = queue.removeFirst();
          TreeNode treeNode = node.treeNode;
          List<Integer> left = node.leftInorder;
          if (!left.isEmpty()) {
              if (left.size() == 1) {
                  treeNode.left = new TreeNode(left.get(0));
              } else {
                  // 从前序遍历中找到第一个属于该前序的节点即为当前节点左子树的根
                  for (TreeNode n : preorder) {
                      if (left.contains(n.val)) {
                          treeNode.left = n;
                          queue.addLast(getNode(n, left));
                          break;
                      }
                  }
              }
          }
  
          List<Integer> right = node.rightInorder;
          if (!right.isEmpty()) {
              if (right.size() == 1) {
                  treeNode.right = new TreeNode(right.get(0));
              } else {
                  // 从前序遍历中找到第一个属于该前序的节点即为当前节点右子树的根
                  for (TreeNode n : preorder) {
                      if (right.contains(n.val)) {
                          treeNode.right = n;
                          queue.addLast(getNode(n, right));
                          break;
                      }
                  }
              }
          }
      }
      return root;
  }
  
  private Node getNode(TreeNode node, List<Integer> inorderList) {
      int pos = 0;
      for (Integer i : inorderList) {
          if (i.equals(node.val)) {
              break;
          }
          pos++;
      }
      return new Node(node, inorderList.subList(0, pos), inorderList.subList(pos + 1, inorderList.size()));
  }
  
  private class Node {
      TreeNode treeNode;
      List<Integer> leftInorder;
      List<Integer> rightInorder;
  
      public Node(TreeNode treeNode, List<Integer> leftInorder, List<Integer> rightInorder) {
          this.treeNode = treeNode;
          this.leftInorder = leftInorder;
          this.rightInorder = rightInorder;
      }
  }
  ~~~

### 12.矩阵中的路径

**思路**

常见的回溯算法，回溯的时候，改了什么改回去就好了

**代码**

~~~java
public boolean exist(char[][] board, String word) {
    // 找到矩阵中word所有开头的位置。从这些位置开始开始尝试遍历矩阵，寻找word
    boolean flag = false;
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (word.charAt(0) == board[i][j]) {
                flag = next(board, word, i, j, 0);
                if (flag) {
                    return true;
                }
            }
        }
    }
    return flag;
}

private boolean next(char[][] board, String word, int i, int j, int pos) {
    log.info("pos in board - [{},{}], pos in word - [{}], deque - [{}]", i, j, pos);
    if (pos == word.length()) {
        return true;
    }
    if (i >= board.length || j >= board[0].length || i < 0 || j < 0) {
        return false;
    }
    if (board[i][j] != word.charAt(pos)) {
        return false;
    } else {
        board[i][j] = ' ';
        // 向上尝试
        boolean up = next(board, word, i - 1, j, pos + 1);
        if (up) {
            return true;
        }

        // 向下尝试
        boolean down = next(board, word, i + 1, j, pos + 1);
        if (down) {
            return true;
        }

        // 向左尝试
        boolean left = next(board, word, i, j - 1, pos + 1);
        if (left) {
            return true;
        }

        // 向右尝试
        boolean right = next(board, word, i, j + 1, pos + 1);
        if (right) {
            return true;
        }
        // 改了什么都要改回来
        board[i][j] = word.charAt(pos);
        return false;
    }
}
~~~

### 13.机器人的运动范围

**思考**

经典的动态规划类型问题，状态转移方程 $$ f(i,j,k)=\begin{cases} f(i,j-1,k)\ \or \ f(i-1,j,k)\;\and\ visit(i,j,k), &i\geq1,j\geq1 \\ f(
0,j-1,k)\ \and \ visit(0,j,k), &i=0 \\ f(i,0,k)\ \and \ visit(i,0,k), &j=0 \end{cases} $$ 其中，$f(i,j,k)$表示位置[i,j]
的访问情况，$visit(i,j,k)$表示位置[i,j]是否满足访问条件

**代码**

~~~java
public int movingCount(int m, int n, int k) {
    int sum = 1;
    boolean[][] table = new boolean[m][n];
    // 需要先把第0行和第0列填充起来
    for (int i = 1; i < m; i++) {
        if (i < 10) {
            table[i][0] = i <= k;
        } else {
            table[i][0] = table[i - 1][0] && canVisit(i, 0, k);
        }
        if (table[i][0]) {
            sum++;
        }
    }
    for (int i = 1; i < n; i++) {
        if (i < 10) {
            table[0][i] = i <= k;
        } else {
            table[0][i] = table[0][i - 1] && canVisit(i, 0, k);
        }
        if (table[0][i]) {
            sum++;
        }
    }


    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            table[i][j] = (table[i - 1][j] || table[i][j - 1]) && canVisit(i, j, k);
            if (table[i][j]) {
                sum++;
            }
        }
    }
    return sum;
}

private boolean canVisit(int i, int j, int k) {
    int sum = 0;
    String s = String.valueOf(i);
    for (int n = 0; n < s.length(); n++) {
        sum += Integer.parseInt(s.substring(n, n + 1));
    }
    s = String.valueOf(j);
    for (int n = 0; n < s.length(); n++) {
        sum += Integer.parseInt(s.substring(n, n + 1));
    }
    return k >= sum;
}
~~~

### 14-1.剪绳子1

**思路**

1. 长度为$n(n>1)$的绳子被切成了$a(a>1)$段，那么就有$n=n_1+n_2+...+n_a$，那么就可以转换为数学问题 $$ f(n)=max(\prod_{i=1}^{a} n_{i})
   $$

2. 由均值不等式 $$ G_n=\sqrt[n]{\prod_{i=1}^{n} x_{i}}=\sqrt[n]{x_1x_2 \cdots x_n}\leq\frac{\sum_{i=1}^{n}
   x_i}{n}=\frac{x_1+x_2+\cdots+n_n}{n}，当且仅当x_1=x_2=\cdots=x_n时等号成立 $$ 就可以得出推论，当所有绳段长度相等时乘积最大

3. 因此假设绳子被按照长度$x$分为$a$段，即$n=ax$，乘积$f(n)=x^a=x^\frac{n}{x}=(x^\frac{1}{x})^n$。因为n为常数，所以当函数$g(x)
   =x^\frac{1}{x}$取得最大值时，函数$f(n)$取得最大值。通过数学推算，易得当$x=e\approx2.7$时函数$g(x)$取得最大值。

4. 由于每段绳子长度为整数，所以要把绳子切成3或2，且3要多

**状态转移方程**
$$ f(n) =\begin{cases} 1,&n=2 2,&n=3 \\ max(2\times f(n-2), 3\times f(n-3)),&n>3 \end{cases} $$
**代码**

~~~java
public int cuttingRope(int n) {
    if (n == 2) {
        return 1;
    }
    if (n == 3) {
        return 2;
    }
    int[] sum = new int[n+1];
    for (int i = 1; i <= n; i++) {
        if (i <= 3) {
            sum[i] = i;
        } else {
            sum[i] = Math.max(2 * sum[i-2], 3 * sum[i - 3]);
        }
    }
    return sum[n];
}
~~~

### 16. 数值的整数次方幂

**思路**

- 暴力求解

- 快速幂

  对于任何十进制整数$n$，设其二进制表达式为$n=b_mb_{m-1}\cdots b_2b_1（左边是高位，右边是低位）$，即 $$ n = \sum_{i=1}^{m}2^{i-1} b_i = 1\times
  b_1+2\times b_2 + \cdots+2^{m-1}\times b_m $$ 所以可得如下公式 $$ x^n = x^{\sum_{i=1}^{m}2^{i-1} b_i}=x^{1\times b_1+2\times
  b_2 + \cdots+2^{m-1}\times b_m}=x^{1b_1}\cdot x^{2b_2}\cdots x^{2^{m-1}b_m} $$

**代码**

~~~java
public double myPow(double x, int n) {
    if (n == 0 || x == 1) {
        return 1;
    }
    if (x == 0) {
        return 0;
    }
    String s = Long.toBinaryString(n > 0 ? n : -n);
    double res = 1;
    for (int i = s.length() - 1; i >= 0; i--) {
        if (s.charAt(i) == '0') {
            res *= 1;
        } else {
            res *= x;
        }
        x *= x;
    }
    return n > 0 ? res : 1 / res;
}
~~~

### 20. 表示数值的字符串

**思路**

- 有限状态自动机

  > 确定有限状态自动机（以下简称「自动机」）是一类计算模型。它包含一系列状态，这些状态中：
  >
  >   - 有一个特殊的状态，被称作「**初始状态**」。
  >   - 还有一系列状态被称为「**接受状态**」，它们组成了一个特殊的集合。其中，一个状态可能既是「初始状态」，也是「接受状态」。
  >
  >   起初，这个自动机处于「**初始状态**」。随后，它顺序地读取字符串中的每一个字符，并根据当前状态和读入的字符，按照某个事先约定好的「**转移规则**」，从当前状态转移到下一个状态；当状态转移完成后，它就读取下一个字符。当字符串全部读取完毕后，如果自动机处于某个「**接受状态**」，则判定该字符串「被接受」；否则，判定该字符串「被拒绝」。
  >
  >   注意：如果输入的过程中某一步转移失败了，即不存在对应的「转移规则」，此时计算将提前中止。在这种情况下我们也判定该字符串「被拒绝」。
  >
  >   ==一个自动机，总能够回答某种形式的「对于给定的输入字符串 S，判断其是否满足条件 P」的问题。在本题中，条件 P 即为「构成合法的表示数值的字符串」。==
  >
  >   自动机驱动的编程，可以被看做一种暴力枚举方法的延伸：它穷尽了在任何一种情况下，对应任何的输入，需要做的事情。
>

- 使用自动机的关键，就是找出所有的状态集合。**常用字符串处理到那个部分作为技巧**

- 自动机状态集合，其中3、4、6、9、10的状态可以正常退出，其余状态不能正常退出

    1. 开始空格
    2. 符号字符
    3. 整数部分
    4. 左侧有整数的小数点
    5. 左侧无整数的小数点
    6. 小数部分
    7. 字符E或e
    8. 符号字符
    9. 整数
    10. 末尾空格

- 状态转移图

  ![fig1](https://gitee.com/primabrucexu/image/raw/main/image/202203011643279.png)

**代码**

~~~java
public boolean isNumber(String s) {
    // 特例处理
    if (s.length() == 1) {
        return CharType.get(s.charAt(0)).equals(CharType.NUMBER);
    }

    // 初始状态集合
    Map<CharType, State> initMap = new HashMap<>();
    initMap.put(CharType.SPACE, State.INIT_SPACE);
    initMap.put(CharType.SIGNAL, State.SIGNAL_BEFORE_E);
    initMap.put(CharType.POINT, State.POINT_NO_INT_LEFT);
    initMap.put(CharType.NUMBER, State.INT_BEFORE_E);

    // 获取第一个字符所处的状态
    State state = initMap.get(CharType.get(s.charAt(0)));
    if (state == null) {
        return false;
    }

    for (int i = 1; i < s.length(); i++) {
        char c = s.charAt(i);
        CharType charType = CharType.get(c);
        if (charType.equals(CharType.ILLEGAL)) {
            return false;
        }
        state = State.trans(state, charType);
        if (state == null) {
            return false;
        }
    }

    switch (state) {
        case INT_BEFORE_E:
        case INT_AFTER_E:
        case POINT_RIGHT:
        case POINT_WITH_INT_LEFT:
        case LAST_SPACE:
            return true;
        default:
            return false;
    }

}

enum State {
    /**
     * 开头的若干空格
     */
    INIT_SPACE,
    /**
     * 整数或小数部分的符号
     */
    SIGNAL_BEFORE_E,

    /**
     * 整数部分
     */
    INT_BEFORE_E,

    /**
     * 左侧无整数数的小数点
     */
    POINT_NO_INT_LEFT,

    /**
     * 左侧有整数的小数点
     */
    POINT_WITH_INT_LEFT,

    /**
     * 小数点右侧的小数部分
     */
    POINT_RIGHT,

    /**
     * 指数符号
     */
    E,

    /**
     * 指数的符号
     */
    SIGNAL_AFTER_E,

    /**
     * 指数部分的整数
     */
    INT_AFTER_E,

    /**
     * 剩下的空格
     */
    LAST_SPACE;

    static final Map<State, Map<CharType, State>> TRANS_MAP = new HashMap<>();

    static {
        TRANS_MAP.put(INIT_SPACE, new HashMap(){{
            put(CharType.SPACE, INIT_SPACE);
            put(CharType.SIGNAL, SIGNAL_BEFORE_E);
            put(CharType.POINT, POINT_NO_INT_LEFT);
            put(CharType.NUMBER, INT_BEFORE_E);
        }});
        TRANS_MAP.put(SIGNAL_BEFORE_E, new HashMap(){{
            put(CharType.NUMBER, INT_BEFORE_E);
            put(CharType.POINT, POINT_NO_INT_LEFT);
        }});
        TRANS_MAP.put(INT_BEFORE_E, new HashMap(){{
            put(CharType.NUMBER, INT_BEFORE_E);
            put(CharType.POINT, POINT_WITH_INT_LEFT);
            put(CharType.E, E);
            put(CharType.SPACE, LAST_SPACE);
        }});
        TRANS_MAP.put(POINT_NO_INT_LEFT, new HashMap(){{
            put(CharType.NUMBER, POINT_RIGHT);
        }});
        TRANS_MAP.put(POINT_WITH_INT_LEFT, new HashMap(){{
            put(CharType.NUMBER, POINT_RIGHT);
            put(CharType.E, E);
            put(CharType.SPACE, LAST_SPACE);
        }});
        TRANS_MAP.put(POINT_RIGHT, new HashMap(){{
            put(CharType.NUMBER, POINT_RIGHT);
            put(CharType.E, E);
            put(CharType.SPACE, LAST_SPACE);
        }});
        TRANS_MAP.put(E, new HashMap(){{
            put(CharType.SIGNAL, SIGNAL_AFTER_E);
            put(CharType.NUMBER, INT_AFTER_E);
        }});
        TRANS_MAP.put(SIGNAL_AFTER_E, new HashMap(){{
            put(CharType.NUMBER, INT_AFTER_E);
        }});
        TRANS_MAP.put(INT_AFTER_E, new HashMap(){{
            put(CharType.NUMBER, INT_AFTER_E);
            put(CharType.SPACE, LAST_SPACE);
        }});
        TRANS_MAP.put(LAST_SPACE, new HashMap(){{
            put(CharType.SPACE, LAST_SPACE);
        }});
    }

    static State trans(State nowState, CharType nextCharType) {
        Map<CharType, State> stateMap = TRANS_MAP.get(nowState);
        return stateMap.get(nextCharType);
    }

}

enum CharType {
    /**
     * 小数点
     */
    POINT,

    /**
     * 数字
     */
    NUMBER,

    /**
     * E或者e
     */
    E,

    /**
     * 空格
     */
    SPACE,

    /**
     * 符号
     */
    SIGNAL,

    /**
     * 退出自动
     */
    ILLEGAL;

    static CharType get(char c) {
        if (c >= '0' && c <= '9') {
            return NUMBER;
        } else if (c == '.') {
            return POINT;
        } else if (c == 'e' || c == 'E') {
            return E;
        } else if (c == ' ') {
            return SPACE;
        } else if (c == '-' || c == '+') {
            return SIGNAL;
        } else {
            return ILLEGAL;
        }
    }

}
~~~

