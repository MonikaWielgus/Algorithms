package arraystack;

/**
 *
 * @author Monika Wielgus
 */
public class Stack{
    public static void main(String[] args){
        ArrayStack stack1 = new ArrayStack(3);
        System.out.println(stack1.isEmpty());
        stack1.push(2);
        System.out.println(stack1.isEmpty());
        System.out.println(stack1.isFull());
        stack1.push(1);
        stack1.push(3);
        stack1.display();
        System.out.println(stack1.isFull());
        System.out.println(stack1.numberOfElements());
        System.out.println(stack1.pop());
        System.out.println(stack1.numberOfElements());
        stack1.top();
        stack1.display();
    }
       
}

class ArrayStack{

    int []array;
    int firstFree;
   
    public ArrayStack(int maxSize){ //creates an empty stack
        array=new int[maxSize];
        firstFree=0;
    }
    
    public ArrayStack(int maxSize, int[]values){
        array=new int[maxSize];
        for(int i=0; i<values.length; i++){
            array[i]=values[i];
        }
    }

    public boolean isEmpty(){ //checks if stack is empty
        if(firstFree==0) return true;
        else return false;
    }

    public boolean isFull(){ //checks if stack is full
        if(firstFree==array.length) return true;
        else return false;
    }

    public int numberOfElements(){ //returns number of elements of stack
        return firstFree;
    }
    
    public void push(int x){ //pushes x on the top
        if(firstFree<array.length)
        {
            array[firstFree]=x;
            firstFree++;
        }
        else
            System.out.println("Stack overflowed");
    }
    
    public int top(){ //returns value on the top
        if(firstFree!=0)
            return array[firstFree-1];
        else 
        {
            System.out.println("Stack is empty");
            return -1;
        }
    }
    

    public int pop(){ //returns value on the top and deletes it
        if(firstFree==0)
        {
            System.out.println("Stack is empty");
            return -1;
        }
        else
        {
            int temp=array[firstFree-1];
            firstFree--;
            return temp;
        }
    }
    
    public void display(){ //prints stack
        if(firstFree==0) System.out.println("Stack is empty");
        else
        {
            int temp=firstFree-1;
            do 
            {
                System.out.print(array[temp]+" ");
                temp--;
            }while(temp>-1);
            System.out.println();
        }
    }
    
}
