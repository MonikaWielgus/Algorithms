package extendedbst;

import java.util.Scanner;

/**
 *
 * @author Monika Wielgus
 */

public class ExtendedBST{
    public static Scanner in=new Scanner(System.in);
    
    public static void main(String[] args){
        
        int sets=in.nextInt();
        for(int i=0; i<sets; i++){
            System.out.println("SET: "+(i+1));
            int nodes=in.nextInt();
            int []n=new int[nodes];
            String order=in.next();
            
            for(int j=0; j<nodes; j++)
                n[j]=in.nextInt();
            
            BST.Node node;
            
            if(order.equals("PREORDER"))
                node=BST.createPreorderBST(n);
            else
                node=BST.createPostorderBST(n);
            
            BST tree=new BST(node);
            int numberOfTasks=in.nextInt();
            int task2;

            for(int l=0; l<numberOfTasks; l++){
                String task=in.next();
                
                switch(task){
                    case "PREORDER":
                        System.out.print("PREORDER: ");
                        tree.preorder(tree.root, nodes);
                        System.out.println();
                        break;
                    case "INORDER":
                        System.out.print("INORDER: ");
                        tree.inorder(tree.root, nodes);
                        System.out.println();
                        break;
                    case "POSTORDER":
                        System.out.print("POSTORDER: ");
                        tree.postorder(tree.root, nodes);
                        System.out.println();
                        break;
                    case "LEVELORDER":
                        System.out.print("LEVELORDER: ");
                        tree.levelorder(tree.root, nodes);
                        System.out.println();
                        break;
                    case "PARENT":
                        task2=in.nextInt();
                        tree.parent(tree.root, task2);
                        break;
                    case "INSERT":
                        task2=in.nextInt();
                        tree.insert(tree.root, task2);
                        break;
                    case "DELETE":
                        task2=in.nextInt();
                        tree.delete(task2);
                        break;
                    case "SUCCESSOR":
                        task2=in.nextInt();
                        BST.Node nod=tree.search(tree.root, task2);
                        tree.successor(tree.root, nod, task2);
                        break;
                    case "PREDECESSOR":
                        task2=in.nextInt();
                        BST.Node no=tree.search(tree.root, task2);
                        tree.predecessor(tree.root, no, task2);
                        break;
                    case "DISPLAY":
                        tree.display();
                        break;
                }     
            }
        }
    }
}  

class BST{
    static class Node{
    
        public int info;
        public Node left;
        public Node right;
    
        public Node(int info){            
            this.info = info;
            this.left = null;
            this.right = null;
        }       
    }
    
    static Node root;
    
    public BST(Node r){
        root=r;
        root.info=r.info;
        root.left=r.left;
        root.right=r.right;
    }   
    static class Index{
        int value;
        
        public Index(int v){
            this.value=v;
        }
    }
    public static Node createPostorderBST(int []array){
        Index index=new Index(array.length-1);
        return createPostorderBSTHelp(array, index, Integer.MIN_VALUE, Integer.MAX_VALUE);                
    }    
    public static Node createPostorderBSTHelp(int []array, Index id, int min, int max){ 
        if(id.value<0) //index below zero
            return null;
        
        int k=array[id.value]; //setting value to index
        
        Node root=null;
        
        if(k>min && k<max){ //if we fit in range
            root=new Node(k); //creating node
            id.value--;
            
            if(id.value>=0){ //if we are still in array
                root.right=createPostorderBSTHelp(array, id, k, max); //it must be bigger than parent
                root.left=createPostorderBSTHelp(array, id, min, k); //it must be smaller than parent
            }
        }        
        return root;
    }    
    public static Node createPreorderBST(int []array){
        Index index=new Index(0);
        return createPreorderBSTHelp(array, index, Integer.MIN_VALUE, Integer.MAX_VALUE);                
    }    
    public static Node createPreorderBSTHelp(int []array, Index id, int min, int max){
        if(id.value>=array.length) //index bigger than array, because we start at zero
            return null;
        
        int k=array[id.value];
        
        Node root=null;
        
        if(k>min && k<max){
            root=new Node(k);
            id.value++;
            
            if(id.value<array.length){
                root.left=createPreorderBSTHelp(array, id, min, k); //from min to k, because it must be smaller than parent
                root.right=createPreorderBSTHelp(array, id, k, max); //here bigger than parent                
            }
        }        
        return root;
    }    
    public static void preorder(Node node, int p){ //root->left subtree->right subtree
        if(node==null)
            return;
        
        ArrayStack stack=new ArrayStack(100);
        stack.push(root);
        
        while(stack.isEmpty()==false){
            Node current=stack.top(); //root from the top
            System.out.print(current.info+" "); //value
            stack.pop();
            
            if(current.right!=null) //right child if it exists
                stack.push(current.right);
            
            if(current.left!=null) //then left one
                stack.push(current.left);
        }
        //and so on until the stack is empty which means we've written out all
    }   
    public static void inorder(Node node, int p){ //left subtree->root->right subtree
        if(node==null) //tree is empty
            return;
        
        ArrayStack stack=new ArrayStack(p+1);
        Node current=node;
        
        while(current!=null||stack.isEmpty()==false){
            while(current!=null){ //we search for root at the left
                stack.push(current); //we push everything on our way on the stack
                current=current.left;
            }
            
            current=stack.pop(); //new current from the top
            
            System.out.print(current.info+" "); //we write out the value
            //now the right subtree
            current = current.right;
        }
    }    
    public static void postorder(Node node, int p){ //left subtree->right subtree->root
        Node prev=null;
        ArrayStack stack=new ArrayStack(p+1);
        
        stack.push(node);
        
        while(stack.isEmpty()==false){
            Node current=stack.top();
            if(prev==null || (prev.left==current || prev.right==current)){ //previous is null or current is its child
                if(current.left!=null)
                    stack.push(current.left);
                else if(current.right!=null)
                    stack.push(current.right);
            }
            else if(prev==current.left){ //previous is the left child of current
                if(current.right!=null) stack.push(current.right);
            }
            else{
                System.out.print(current.info+" ");
                stack.pop();
            }            
            prev=current;
        }
    }   
    public static void levelorder(Node node, int p){
        if(root==null)
            return;
        
        LinkedDoublyQueue queue=new LinkedDoublyQueue();
        
        queue.insertTail(node);
        
        while(true){
            int number=queue.size; //number of nodes on this level
            if(number==0)
                break;
            
            while(number>0){
                Node n=queue.getHead();
                System.out.print(n.info+" ");
                queue.removeHead();
                if(n.left!=null)
                    queue.insertTail(n.left);
                if(n.right!=null)
                    queue.insertTail(n.right);
                
                number--;
            }
        }
    }   
    public static void insert(Node node, int p){
        Node newNode=new Node(p);
        Node x=node;
        Node temp=null;
        
        while(x!=null){
            temp=x;
            if(p<x.info)
                x=x.left;
            else if(p>x.info)
                x=x.right;
            else
                return;
        }

        if(temp==null)
            temp=newNode;
        else if(p<temp.info)
            temp.left=newNode;       
        else
            temp.right=newNode;
    }    
    public static void parent(Node node, int p){
        Node temp=node;
        Node prev=null;

        while(temp!=null){
            if(temp.info==p&&prev!=null){ //we've found the child and it has parent
                System.out.println("PARENT "+p+": "+prev.info);
                return;
            }
            else if(temp.info==p&&prev==null){ //we've found the child and it doesn't have parent
                System.out.println("PARENT "+p+": THERE IS NOT");
                return;
            }
            
            prev=temp;
            if(temp.info>p)
                temp=temp.left;
            else if(temp.info<p)
                temp=temp.right;           
        }
        
        System.out.println("PARENT "+p+": THERE IS NOT");
        return;
    }    
    public static void delete(int info){
        Node parent=null;
        Node child=root;
        Node node=root;
        while(node!=null && node.info!=info){
            parent=node;
            if(info<node.info)
                node=node.left;
            else
                node=node.right;
        }       
        if(node==null)
            return;
             
        if(node.left==null && node.right==null){ 
            if(parent==null)
                root=null;
            else if(info<parent.info)
                parent.left=null;
        else
            parent.right=null;
        }
        else if(node.left==null){
            child=node.right;
            swap(node, child);
            node.left=child.left;
            node.right=child.right;
        }
        else if(node.right==null){
            child=node.left;
            swap(node, child);
            node.left=child.left;
            node.right=child.right;
        }
        else{
            child=node.right;
            parent=null;
            while(child.left!=null){
                parent=child;
                child=parent.left;
            }
            if(parent==null){
                swap(node, child);
                node.right=child.right; 
            }
            else{
                swap(node, child);
                parent.left=child.right; 
            }
        }
    } 
    static void swap(Node node, Node child){
        int temp=node.info;
        node.info=child.info;
        child.info=temp;
    }    
    public static Node search(Node node, int p){
        while(node!=null){
            if(node.info<p)
                node=node.right;
            else if(node.info>p)
                node=node.left;
            else
                return node;
        }
        return node;
    }
    public static Node min(Node r){
    	if(r==null)
            return null;
    	
    	while(r.left!=null)
            r=r.left;
	
    	return r;
    }                    
    public static void successor(Node root, Node node, int p){ //minimum value in the right subtree
        if(node==null){
            System.out.println("SUCCESSOR "+p+": THERE IS NOT");
            return;
        }
        if(node.right!=null){
            System.out.println("SUCCESSOR "+node.info+": "+min(node.right).info);
            return;
        }
        else{
            Node successor=null;
            while(root!=null){
                if(root.info>node.info){
                    successor=root;
                    root=root.left;
                }
                else if(root.info<node.info)
                    root=root.right;
                else if(successor!=null){
                    System.out.println("SUCCESSOR "+node.info+": "+successor.info);
                    return;
                }
                else{
                    System.out.println("SUCCESSOR "+node.info+": THERE IS NOT");
                    return;
                }
            }
            
            System.out.println("SUCCESSOR "+node.info+": THERE IS NOT");
            return;
        }           
    }    
    public static Node max(Node r){
    	if(r==null)
            return null;
    	
    	while(r.right!=null)
            r=r.right;
	
    	return r;
    }    
    public static void predecessor(Node root, Node node, int p){ //maximum value in the right subtree
        if(node==null){
            System.out.println("PREDECESSOR "+p+": THERE IS NOT");
            return;
        }
        if(node.left!=null){
            System.out.println("PREDECESSOR "+node.info+": "+max(node.left).info);
            return;
        }        
        else{
            Node predecessor=null;
            while(root!=null){
                if(root.info>node.info)
                    root=root.left;
                else if(root.info<node.info){
                    predecessor=root;
                    root=root.right;
                }
                else if(predecessor!=null){
                    System.out.println("PREDECESSOR "+node.info+": "+predecessor.info);
                    return;
                }
                else{
                    System.out.println("PREDECESSOR "+node.info+": THERE IS NOT");
                    return;
                }
            }
            System.out.println("PREDECESSOR "+node.info+": THERE IS NOT");
            return;
        }
    }
    public int height(Node node){
        if(node==null)
            return 0;
        else
            return 1+Math.max(height(node.left),height(node.right)); //longest way down
    }    
    int spaces(int wysokosc){
        int s=2;
        for(int i=1; i<wysokosc; i++)
            s=2*s+2;

        return s;
    }
    public void display(){
        if(this.root!=null){
            int levels=height(this.root);
            int space=spaces(levels);
            int roots=1;
            int out=0;
            LinkedDoublyQueue queue=new LinkedDoublyQueue();
            queue.insertTail(this.root);
            Node current;
            
            while(queue.isEmpty()==false){
                current=queue.removeHead();
                for(int i=0; i<space; i++)
                    System.out.print(" ");
                
                if(current!=null)
                    System.out.print(current.info);
                else
                    System.out.print("--");
                
                for(int i=0; i<space; i++)
                    System.out.print(" ");
                
                out++;
                if(out==roots){
                    roots=roots*2;
                    out=0;
                    System.out.println();
                    if(roots==Math.pow(2,levels))
                        return;
                    space=space/2;
                                        
                }
                
                if(current!=null && current.left!=null)
                    queue.insertTail(current.left);
                else
                    queue.insertTail(null);
                    
                if(current!=null && current.right!=null)
                    queue.insertTail(current.right);
                else
                    queue.insertTail(null);
            }            
        }
    }   
    static class ArrayStack{

        Node []array;
        int firstFree;
    
        public ArrayStack(int maxSize){
            array=new Node[maxSize];
            firstFree=0;
        }
        public boolean isEmpty(){
            if(firstFree==0) return true;
            else return false;
        }
        public int numberOfElements(){
            return firstFree;
        }
        public void push(Node x){
            if(firstFree<array.length){
                array[firstFree]=x;
                firstFree++;
            }
            else
                System.out.println("Stack overloaded");
        }

        public Node top(){
            if(firstFree!=0)
                return array[firstFree-1];
            else{
                System.out.println("Stack is empty");
                return null;
            }
        }
        public Node pop(){
            if(firstFree==0){
                System.out.println("Stack is empty");
                return null;
            }
            else{
                Node temp=array[firstFree-1];
                firstFree--;
                return temp;
            }
        }
    }

    static class N{
        Node data;
        N next;
        N prev;
        public N(){
            this.data=null;
            this.prev=null;
            this.next=null;
        }
        public N(Node x){
            this.data=x;
            this.prev=null;
            this.next=null;
        }
    }

    static class LinkedDoublyQueue{   
        N head;
        N tail;
        int size=0;

        public LinkedDoublyQueue(){        
            size=0;
            this.head=null;
            this.tail=null;
        }
        public void insertTail(Node x){
            N newOne=new N(x);
            if(head==null){
                head=newOne;
                tail=newOne;
            }
            else{
                N temp=tail;
                tail=newOne;
                temp.next=tail;
                tail.prev=temp;  
            }
            size++;
        }
        public Node getHead(){        
            return head.data;        
        }
        public Node removeHead(){        
            if(head==null){
                System.out.println("empty");
                return null;
            }
            else if(head.next==null){
                Node x=head.data;
                head=null;
                size--;
                return x;
            }
            else{
                Node x=head.data;
                head=head.next;
                head.prev=null;
                size--;
                return x;
            }       
        }   
        public boolean isEmpty(){
            if(head==null) return true;
            return false;
        }
    }   
}


/*input:
2
10
PREORDER
50 25 12 37 30 33 43 75 87 93 
13
POSTORDER 
LEVELORDER 
PARENT 33 
SUCCESSOR 50 
PREDECESSOR 50
PARENT 50 
DELETE  50 
PARENT 50 
POSTORDER 
LEVELORDER 
INSERT 35 
INORDER 
LEVELORDER 
10
POSTORDER
13 34 31 44 38 26 94 88 76 51
6
PREORDER
INORDER
LEVELORDER
POSTORDER
DELETE 44
POSTORDER


output:
SET: 1
POSTORDER: 12 33 30 43 37 25 93 87 75 50 
LEVELORDER: 50 25 75 12 37 87 30 43 93 33 
PARENT 33: 30
SUCCESSOR 50: 75
PREDECESSOR 50: 43
PARENT 50: THERE IS NOT
PARENT 50: THERE IS NOT
POSTORDER: 12 33 30 43 37 25 93 87 75 
LEVELORDER: 75 25 87 12 37 93 30 43 33 
INORDER: 12 25 30 33 35 37 43 75 87 93 
LEVELORDER: 75 25 87 12 37 93 30 43 33 35 
SET: 2
PREORDER: 51 26 13 38 31 34 44 76 88 94 
INORDER: 13 26 31 34 38 44 51 76 88 94 
LEVELORDER: 51 26 76 13 38 88 31 44 94 34 
POSTORDER: 13 34 31 44 38 26 94 88 76 51 
POSTORDER: 13 34 31 38 26 94 88 76 51
*/

