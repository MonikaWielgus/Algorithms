package wektorapp;
/**
 *
 * @author Monika Wielgus
 */
import java.util.*;

public class VectorApp{
    public static void main(String[] args){
        Vector a;
	a = new Vector(6);
        int m=a.getInt();
        a.insert(m);
        a.display();
        System.out.println(a.isSorted());
        a.replace(2,2);
        a.display();
        a.insert(2,3);
        a.append(2);
        a.display();
        a.remove(0);
        a.display();
        a.removeElement(3);
        a.display();
                
        System.out.println(a.isSorted());
        System.out.println(a.getMax());
        System.out.println(a.getMin());
        a.deleteDuplicates();
        a.sort();
        a.display();
        System.out.println(a.isSorted());
               
        Vector b=a.longestNondecreasingSequence();
        b.display();
        Vector c=a.getMaxSublist();
        c.display();

                
    }
    
}

class Vector{
    private int[] a;
    private int maxSize;
    private int n; //length
    private boolean isSorted;
	
    public Vector(int m){
        maxSize=m;
	n=0;
	a=new int[maxSize];
    }
    
    public int getSize(){
	return n;
    }

    public int getMaxSize(){
	return maxSize;
    }

    public void insert(int m){
	if(0<m && m<=maxSize){
            System.out.println("Reading "+m+"  integer numbers");
            n = m;
            for(int i=0; i<n; i++)
                a[i]= getInt();
	}
        else
            System.out.println("Length incorrect");
                
        isSorted();
    }

    public void randvect(int m, int mini, int maxi){
	if((0<m && m<=maxSize) && (mini<maxi)){
            System.out.println("Choosing "+m+" numbers of ["+mini +","+maxi +"]");
            n = m;
            for( int i=0; i<n; i++)
		a[i]= (int)(java.lang.Math.random()*(maxi-mini)+mini);
	}
        else
            System.out.println("Incorrect");
                
        isSorted();
    }

    public void display(){
	System.out.println("Number of elements = " + n);
	System.out.println("Vector:  ");
	for(int i=0; i<n; i++ ){
            System.out.print(a[i]+", ");
            if((i+1)%10==0)
		System.out.println("") ;
	}
	System.out.println("") ;
    }

    public int getInt(){
	Scanner cons = new Scanner(System.in);
	System.out.print("Provide integer: ");
	while(!cons.hasNextInt()){
            System.out.println("Error");
            System.out.print("Provide integer: ");
            cons.next();
        }
	int i=cons.nextInt();
	return i;
    }
        
    public void append(int x){
        if(n<maxSize){
            a[n]=x;
            n++;
            if(isSorted==true)
                if(a[n-1]>x) isSorted=false;                
        }
    }
        
    public void insert(int x, int i){
        if(n<maxSize){
            if(i<=n){
                for(int j=n; j>=i; j--)
                    a[j]=a[j-1];
                n++;
                a[i]=x;
            }
            if(isSorted==true)
                if(a[i-1]>x) isSorted=false;
        }
    }
        
    public void replace(int x, int i){
        if(i<=n)
            a[i]=x;
        if(isSorted==true){
            if(n>1)
                if(x<a[i-1])
                    isSorted=false;
            else
                if(x<a[i+1])
                    isSorted=false;
               
        }
        else if(isSorted==false){
            if(n>1&&n<maxSize)    
                if(x>=a[i-1]&&x<=a[i+1]) isSorted=true;
            else if(n==maxSize)               
                    if(x>=a[i-1]) isSorted=true;
                
        }
    }
        
    public void remove(int i){
        if(i<=n){
            for(int j=i; j<n-1; j++)
                a[j]=a[j+1];              
                
            n--;
            isSorted();
        }            
    }
        
    public void removeElement(int x){
        for(int i=0; i<n; i++){
            if(a[i]==x){
                remove(i);
                i--;
            }
        }
    }
       
    public boolean isSorted(){
        for(int i=0; i<n-1; i++){
            if(a[i]>a[i+1]){
                isSorted=false;
                return false;
             }                
        }
        isSorted=true;
        return true;
    }
        
    public int getMax(){
        int max=a[0];
        for(int i=0; i<n; i++)
            if(a[i]>max) max=a[i];
            
        return max;
    }
        
    public int getMin(){
        int min=a[0];
        for(int i=0; i<n; i++)
            if(a[i]<min) min=a[i];
            
        return min;
    }
        
    public void deleteDuplicates(){

        int j=0;
        for(int i=1; i<a.length; i++){
            if(a[i]!=a[i-1])
                a[++j]=a[i];
            else
                n--;
        }
    }
    public Vector getMaxSublist(){
        Vector b=new Vector(maxSize);
        int start1=0;
        int end1=0;
        int best=0;
        int current=0;
        int start2=0;
        
        for (int i=0; i<n; i++){
            current=current+a[i];
            if(current<=0){
                current=0;
                start2=i+1;
            }
            else{
                if(current>best){
                    best=current;
                    start1=start2;
                    end1=i;
                }
            }
        } 
        int []tab=new int[end1-start1+1];
        int i=0;
        for(int j=start1; j<=end1; j++){
            tab[0+i]=a[j];
             i++;
        }
        b.maxSize=end1-start1+1;
        b.n=end1-start1+1;
        b.a=tab;
        return b;
    }
            
    public Vector longestNondecreasingSequence(){
        Vector b=new Vector(maxSize);
        if(n==0){
            b.maxSize=0;
            b.n=0;
        }
        else{
            int start=0;
            int length=1;
            int curr_best_start=0;
            
            for(int i=1; i<a.length; i++){
                if(a[i]>a[i-1]){
                    if(i-curr_best_start+1>length){
                        start=curr_best_start;
                        length=i-curr_best_start+1;
                    }  
                }
                else curr_best_start=i;       
            }
            System.out.println(length);
            
            int i=0;
            int []newa=new int[length];
            for(int j=start; j<start+length; j++){
                  newa[0+i]=a[j];
                  i++;
            }            
            b.maxSize=length;
            b.n=length;
            b.a=newa;
        }
        return b;
    }
        
    public void sort(){ //insertion sort
        if(isSorted==false){
            isSorted=true;
            int loc=0;
            int selected;
                
            for(int i=1; i<n; i++){
                int j=i-1;
                selected=a[i]; //we choose an element
                loc=BinarySearch(a, selected, 0, j); //we search its position between 0 and j
                while(j>=loc){
                    a[j+1]=a[j];
                    --j;
                }
                a[j+1]=selected;
            }
        }
    }
        
    public static int BinarySearch(int array[], int x, int low, int high){
        if(high<=low){
            if(x>array[low])
                return (low+1);
            else       
                return low;
        }           
        int mid = (low+high)/2;
            
        if(x==array[mid]) return mid+1; //we return index+1
            
        if(x>array[mid])
            return BinarySearch(array, x, mid+1, high); // we replace beginning with middle+1
        else
            return BinarySearch(array, x, low, mid-1); // we replace beginning with middle-1  
    }
}
