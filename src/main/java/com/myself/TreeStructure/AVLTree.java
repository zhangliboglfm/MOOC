package com.myself.TreeStructure;


import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *   https://blog.csdn.net/qq_25940921/article/details/82183093
 *  平衡二叉树
 *
 *  左孩子比父节点小，右孩子比父节点大
 *
 *  它是一颗空树或者它的左右子树的高度差的绝对值不超过1，并且左右两个子树都是一颗平衡树
 *
 * 平衡二叉树是一棵高度平衡的二叉树，所以查询的时间复杂度是 O(logN) 。插入的话上面也说，失衡的情况有4种，左左，左右，右左，右右，即一旦插入新节点导致失衡需要调整，最多也只要旋转2次，所以，插入复杂度是 O(1) ，但是平衡二叉树也不是完美的，也有缺点，从上面删除处理思路中也可以看到，就是删除节点时有可能因为失衡，导致需要从删除节点的父节点开始，不断的回溯到根节点，如果这棵平衡二叉树很高的话，那中间就要判断很多个节点。所以后来也出现了综合性能比其更好的树—-红黑树，
 *
 *
 * java代码实现：https://blog.csdn.net/u014165620/article/details/82492099
 *
 *
 *
 */
public class AVLTree<T extends Comparable<T>> {

    //允许出现的高度差，超过该值视为不平衡
    private static final int ALLOWED_HEIGHT_DIFF=1;

    // 使用内部类定义AVL树节点
    private static class AVLNode<T extends Comparable<T>>{

        T key;                  //节点值，需要支持比较大小
        AVLNode<T> left;        // 左节点
        AVLNode<T> right;       // 右节点
        int height;             // 高度

        public AVLNode(T key){
            this(key,null,null );
        }
        public AVLNode(T key, AVLNode<T> left, AVLNode<T> right){
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 0;
        }


        private int height(AVLNode<T> tree){
            if(tree!=null)
                return tree.height;
            return 0;
        }

        public int height() {
            return height();
        }
    }

    public static void main(String[] args) {

    }
}
