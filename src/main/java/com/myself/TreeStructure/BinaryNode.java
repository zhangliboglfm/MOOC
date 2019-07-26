package com.myself.TreeStructure;


import java.util.HashSet;

/**
 *  二叉查找树
 *
 *  非常适合范围查找，“中序遍历”可以让节点有序
 *
 * 左孩子比父节点小，右孩子比父节点大
 * @param <K>
 * @param <V>
 */
public class BinaryNode<K extends Comparable,V> {

    // 节点元素
    public  K key;

    // 节点中附加值
    public HashSet<V> attach = new HashSet<V>();

    // 左节点
    public BinaryNode<K,V> left;

    // 右节点
    public BinaryNode<K,V> right;

    public BinaryNode() {
    }

    public BinaryNode(K key,V value,BinaryNode<K, V> left, BinaryNode<K, V> right) {
        this.key = key;
        this.attach.add(value);
        this.left = left;
        this.right = right;
    }


    /**
     *
     * @param key
     * @param value
     * @param tree  二叉树顶点
     * @return      返回新的顶点
     */
    public BinaryNode<K,V> Add(K key,V value,BinaryNode<K,V> tree){
        if(tree==null||tree.key==null){
            tree = new BinaryNode<K,V>(key,value,null,null);
        }
        if (key.compareTo(tree.key) < 0){
            tree.left = Add(key, value, tree.left);
        }
        if (key.compareTo(tree.key) > 0){
            tree.right = Add(key, value, tree.right);
        }
        if (key.compareTo(tree.key) == 0){
            tree.attach.add(value);
        }
        return tree;
    }

    /**
     * 树的指定范围查找
     * @return
     */
    public HashSet<V> searchRange(K min,K max,HashSet<V> hashSet,BinaryNode<K,V> tree){

        if(tree==null){
            return hashSet;
        }
        //遍历左子树（寻找下界）
        if (min.compareTo(tree.key) < 0)
            searchRange(min, max, hashSet, tree.left);
        //当前节点是否在选定范围内
        if (min.compareTo(tree.key) <= 0 && max.compareTo(tree.key) >= 0) {
            //等于这种情况
            for (V value :tree.attach){
                hashSet.add(value);
            }
           }
        //遍历右子树（两种情况：①:找min的下限 ②：必须在Max范围之内）
        if (min.compareTo(tree.key) > 0 || max.compareTo(tree.key) > 0)
            searchRange(min, max, hashSet, tree.right);
        return hashSet;
    }


    public void remove(K key,V value,BinaryNode<K,V> tree){
        if(tree==null){
            return ;
        }
        // 左子树
        if(key.compareTo(tree.key)<0)
            remove(key,value,tree.left);
        // 右子树
        if(key.compareTo(tree.key)>0)
            remove(key,value,tree.right);
        // 相等的情况
        if(key.compareTo(tree.key)==0){
            // 判断HashSet是否有多个值
            if(tree.attach.size()>1){
                // 惰性删除
                tree.attach.remove(value);
            }else {
                // 左右子树都存在的情况
                if(tree.left!=null&&tree.right!=null){
                    findMin(tree.right).left=tree.left;
                    tree.left=null;
                }else {
                    tree =tree.left==null?tree.right:tree.left;
                }
            }
        }
    }

    public BinaryNode<K,V> findMin(BinaryNode<K,V> tree){

        if(tree==null){
            return null;
        }
        if(tree.left!=null){
           return findMin(tree.left);
        }else {
            return tree;
        }
    }


    public static void main(String[] args) {
        int [] nums = {20,16,15,18,17,19,25,24,30};
        BinaryNode<Student,Integer> tree=new BinaryNode<>();
        for (int i=0;i<nums.length;i++){
          tree=tree.Add(new Student("name"+i,nums[i]),nums[i],tree);
        }
        BinaryNode minNode= tree.findMin(tree);
        tree.remove(new Student("",15),15,tree);
        System.out.println(123);

    }

}

class Student implements Comparable<Student>{

    private String name;
    private  int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Student student) {
        return this.age-student.age;
    }
}