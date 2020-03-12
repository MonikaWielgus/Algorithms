package arraystackqueue;
/**
 *
 * @author Monika Wielgus
 */
public class Queue{
    public static void main(String[] args) {
       
        LinkedDoublyQueue ldqueue = new LinkedDoublyQueue();
        ldqueue.insertHead(1);
        ldqueue.insertHead(2);
        ldqueue.insertTail(7);
        ldqueue.insertTail(7);
        ldqueue.insertTail(3);
        ldqueue.display();
        System.out.println(ldqueue.getHead());
        System.out.println(ldqueue.getTail());
        System.out.println(ldqueue.removeHead());
        ldqueue.display();
        System.out.println(ldqueue.size);
        ldqueue.removeTail();
        ldqueue.display();
        ldqueue.reverse();
        ldqueue.display();
        ldqueue.removeElements(7);        
        ldqueue.display();
        System.out.println(ldqueue.size);
        
    }
}


class Node{
    int data;
    Node next;
    Node prev;
    public Node()
    {
        this.data=0;
        this.prev=null;
        this.next=null;
    }
    public Node(int x)
    {
        this.data=x;
        this.prev=null;
        this.next=null;
    }
}

class LinkedDoublyQueue {
    
    Node head;
    Node tail;
    int size=0;

    public LinkedDoublyQueue(){ //creates an empty queue
        size=0;
        this.head=null;
        this.tail=null;
    }

    public void insertHead(int x){ //puts x in the front       
        Node newNode=new Node(x);
        newNode.prev=null;
        newNode.next=head;
        if(head==null){
            head=newNode;
            tail=newNode;
        }
        else{
            head=newNode;
        }
        size++;
    }
    
    public void insertTail(int x){ //puts x on the end
        Node newNode=new Node(x);
        if(head==null){
            head=newNode;
            tail=newNode;
        }
        else{
            Node temp=tail;
            tail=newNode;
            temp.next=tail;
            tail.prev=temp;            
        }
        size++;
    }
    
    public int getHead(){ //returns the first element without deleting        
        return head.data;        
    }

    public int getTail(){ //returns the last element       
        return tail.data;
    }

    public int removeHead(){ //returns and deletes the first element        
        if(head==null){
            System.out.println("empty");
            return -1;
        }
        else if(head.next==null){
            int x=head.data;
            head=null;
            size--;
            return x;
        }
        else{
            int x=head.data;
            head=head.next;
            head.prev=null;
            size--;
            return x;
        }        
    }

    public int removeTail(){ //returns and deletes the first element        
        if(size==0){
            System.out.println("empty");
            return -1;
        }
        else if(size==1){
            int x=head.data;
            head=null;
            tail=null;
            size=0;
            return x;
        }
        else{
            int x=tail.data;
            tail=tail.prev;
            tail.next=null;
            size--;
            return x;
        }        
    }

    public boolean isEmpty(){ //checks if the queue is empty
        if(head==null) return true;
        return false;
    }
    
    public int numberOfElements(){ //returns the number of elements       
        return size;
    }
    
    public void reverse(){ //reverses the queue
		Node current=head;
                Node tempprev=null;
                Node tempnext=null;
                while(current!=null){
                    tempnext=current.next;
                    current.next=tempprev;
                    current.prev=tempnext;
                    tempprev=current;
                    current=tempnext;
                }
                head=tempprev;
    }
	 
    public void removeElements(int x){ //deletes all x from the queue
        if(head==null) return;
        else{
            Node current;
            for(current=head; current!=null; current=current.next){
                if(current.data==x){                   
                    if(current.next!=null&&current.prev!=null){ //in the middle
                        current.prev.next=current.next;
                        current.next.prev=current.prev;
                    }
                    else if(current.prev==null&&current.next!=null){ //the first
                        head=current.next;
                        head.prev=null;                        
                    }  
                    else if(current.prev!=null&&current.next==null){ //the last
                        current=current.prev;
                        current.next=null;
                        tail=current;                           
                    }
                    size--; 
                }
                
            }
        }
    }
    
    public void display(){ //displays the queue
        if(head==null) System.out.println("empty");
        Node temp=head;
        while(temp!=null){
            System.out.print(temp.data+" ");
            temp=temp.next;
        }
        System.out.println();
    }
}
