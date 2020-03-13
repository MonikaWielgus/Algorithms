package bst;

/**
 *
 * @author Monika Wielgus
 */
public class BST{

    public static void main(String[] args) {
        BinarySearchTree <Integer> tree = new BinarySearchTree <Integer>(9); // 9
        tree.insert(5);   //                                              5           12
        tree.insert(4); //                                            4           10
        tree.insert(4);//                                         3
        tree.insert(12);
        tree.insert(10);
        tree.insert(3);
        tree.printInOrder(tree.root);
        System.out.println();
        tree.deleteElem(4);
        tree.printInOrder(tree.root);
        System.out.println();
        System.out.println(tree.size());
        System.out.println(tree.height(tree.root));
        System.out.println(tree.root.value); 
        BinarySearchTree<Integer> tree2=tree.cut(5);
        System.out.println("New");
        tree2.printInOrder(tree2.root);
        System.out.println();
        tree.printInOrder(tree.root);
        System.out.println();
        System.out.println(tree.size());
        tree.merge(tree2);
        tree.printInOrder(tree.root);
        System.out.println();
    }
    
}

class BinarySearchTree<T extends Comparable<T>>{
    int size;
    public class Node<T extends Comparable<T>>{
        Node<T> left,right;
        T value;
        int number;
		
        Node(T value){
            this.value = value;
            this.number=1;
            this.right=null;
            this.left=null;
        }
    }
	
    Node<T> root;

    public BinarySearchTree(){ //creates an empty BST
	root=null;       
    }

    public BinarySearchTree(T root_value){ //creates BST with element in the root
        root = new Node<T>(root_value);
        root.number=1;
        root.left=null;
        root.right=null;
        size=1;
    }

    public Node insertRecursive(Node<T> current, T value){ //inserts alement to the BST; if it is already there, the number increases
        if(current==null)
            return new Node(value);

        if(value.compareTo(current.value)<0)
            current.left=insertRecursive(current.left, value);
            
        else if(value.compareTo(current.value)>0)
            current.right=insertRecursive(current.right, value);
            
        else if(value.equals(current.value)){
            current.number++;
            return current;
        }
 
        return current;
    }
    
    public void insert(T v){  
        size++;
        root=insertRecursive(root, v);
    }
	
    public Node<T> minimumKey(Node<T> curr){ //returns minimum element
	while(curr.left != null)
            curr = curr.left;
                
	return curr;
    }
    
    public Node<T> deleteElem(T v){ //deletes one element v
	Node<T> parent = null;
	Node<T> curr = root; //we begin in the root

	while(curr!=null&&curr.value!=v){ //while we don't find the element of go through the whole array
            parent=curr;

            if(v.compareTo(curr.value)<0)
                curr = curr.left;
            else
                curr = curr.right;
	}

	if(curr==null) //there is no such element
            return root;
                
        size--;
        if(curr.number==1){
            if(curr.left==null&&curr.right==null){ //it has no children
		if(curr!=root){ //it is not a root
                    if(parent.left==curr) //on a left of the root
                       	parent.left=null;
                    else //on the right of the roof
			parent.right=null;
		}
                else //it is a root
                    root=null;
            }
            else if(curr.left!=null && curr.right!=null){//it has two children
		Node <T> newCurrent=minimumKey(curr.right); //we find the next minimum

		T val=newCurrent.value; //value and number of the successor
                int num=newCurrent.number;

		deleteElem(newCurrent.value); //we remove the successor from its place

                curr.value = val; //we copy value
                curr.number=num;
            }
            else{ //it has one child
                Node <T> child;
                if(curr.left!=null)
                    child=curr.left;
                else
                    child=curr.right;
                    
		if(curr!=root){ //if it is not a root
                    if(curr==parent.left) //it is on the left
                        parent.left = child;
                    else //it is on the right
                        parent.right = child;	
		}
                else //it is a root
                    root = child;
			
            }
        }
        else
            curr.number--;
        
	return root;
    }

    private Node searchRecursive(Node<T> current, T value){ //returns node with value 
        if(current==null){
                return null;
        }            
        if(value.equals(current.value)){
            return current;
        }
        if(value.compareTo(current.value)<0){
            return searchRecursive(current.left, value);
        } 
        else if(value.compareTo(current.value)>0){
            return searchRecursive(current.right, value);
        }

        return null;
    }
	
    public Node search(T v){
        return searchRecursive(root,v);
    }
	
    public int size(){//returns number of elements 
        return size(root); 
    } 
     
    public int size(Node<T> node){ 
        if (node==null) 
            return 0; 
        else
            return(size(node.left)+node.number+size(node.right)); 
    } 
  
    public int height(Node <T> node){ //returns the height of the BST
        if(node==null) 
             return 0;
        else{ 
            int lHeight=height(node.left); 
            int rHeight=height(node.right); 
            if (lHeight > rHeight) 
                return (lHeight+1);
             else 
                return (rHeight+1); 
        } 
    } 
	
    public void printInOrder(Node node){ //prints in order (each element*number)
        if(node!=null){
            printInOrder(node.left);
            for(int i=0; i<node.number; i++)
                System.out.print(node.value+" ");
            printInOrder(node.right);               
        }
    
    }

    public BinarySearchTree copy(Node <T> node, BinarySearchTree cTree){ //removes tree starting from the node and returns it as a new tree
        if(node!=null){
            for(int i=0; i<node.number; i++)
                cTree.insert(node.value);
                copy(node.left, cTree);
                copy(node.right, cTree);
            }

            return cTree;
    }
        
    public BinarySearchTree copy(BinarySearchTree tree){
        BinarySearchTree BST = new BinarySearchTree();
            
        if(root==null)
            return BST;
        else
                return copy(root, BST);
    }

    public BinarySearchTree cut(T v){
        BinarySearchTree <T> newBST=copy(this);
                
        newBST.root=newBST.search(v);
        Node temp=this.search(v);

        temp.left=null;
        temp.right=null;

        int liczba=temp.number;
                
        for(int i=0; i<liczba; i++)
            this.deleteElem((T) temp.value);
               
        return newBST;
    }

    public void goThrough(Node<T> node){ //inserts all elements of BST
        if(node!=null){
            goThrough(node.left);
            for(int i=0; i<node.number; i++)
                this.insert(node.value);
            goThrough(node.right);               
        }
    }
    
    public void merge(BinarySearchTree tree){
        goThrough(tree.root);
    }
}
