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

