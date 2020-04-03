package selection;

/**
 *
 * @author Monika Wielgus
 */
import java.util.Scanner;

public class Selection{
    public static Scanner in=new Scanner(System.in);
    
    public static void main(String[] args){
        
        int sets=in.nextInt();
        for(int i=0; i<sets; i++){
            int data=in.nextInt();
            int []array=new int[data];
            for(int j=0; j<data; j++){
                array[j]=in.nextInt();
            }
            int tasks=in.nextInt();
            int []array_of_tasks=new int[tasks];
            for(int j=0; j<tasks; j++){
                array_of_tasks[j]=in.nextInt();
            }
            for(int j=0; j<tasks; j++){
                int answer=Select(array, array_of_tasks[j]-1, 0, array.length-1);
                if(answer!=-1)
                    System.out.println(array_of_tasks[j]+" "+answer);
                else
                    System.out.println(array_of_tasks[j]+" there is not");
            }           
        }
    }
    
    static void Swap(int []array,int x, int y){
        int temp=array[x];
        array[x]=array[y];
        array[y]=temp;
    }
    
    public static void InsertionSort(int []array, int begin, int end){
        int v;
        int j;
        for(int i=begin+1; i<=end ; i++){
            v=array[i];
            j=i-1;
            while (j>=begin&&v<array[j]){
                array[j+1] = array[j];
                j--;
            }
            array[j+1]=v ; 
        }        
    }
    
    static int Partition(int []array, int left, int right, int pivot){                                                        
        int l=left;
        int p=right;
        while(true){
            while(array[l]<pivot)
                l++;
            
            while(array[p]>pivot)
                p--;
            
            if(l<p){
                Swap(array, l, p);
                l++;
                p--;
            }
            else break;
        }
        if(array[p]>pivot)
            p--;
        
        return p;
    }
    
    public static int Select(int []array, int searched, int left, int right){
        int lenght=right-left+1; //lenght of our array
        if(searched<0||searched+1>array.length){ //there is not such element
            return -1;
        }       
        if(lenght<=50){ //array is short so we can sort now
            InsertionSort(array, left, right);
            return array[searched+left]; //element found
        }
        //array is long
        for(int i=left; i<right-(lenght)%5-5; i=i+5){
            InsertionSort(array, i, i+5-1); //sorting every five of elements
        }
        if(lenght%5!=0){
            InsertionSort(array, right+1-(lenght)%5, right); //sorting the end
        }
            
        int count=left;
        int i=0;
        for(i=left; i<right+1-lenght%5-5; i=i+5){
            Swap(array, count, i+2); //swaping median of each five to the beginning
            count++;
        }
        if(lenght%5>=3) //there is a median in the last part
            Swap(array, count, i+2);
            
        else count--; //decreasing count because we haven't found the element
            
        int pivot=Select(array, lenght/2, left, count); //we search the middle one
        //we've found it
        int piv=Partition(array, left, right, pivot); //we divide with pivot
        
        if(searched<=piv-left) return Select(array, searched, left, piv); //left subarray
        else return Select(array, searched+left-piv-1, piv+1, right); //right subarray    
        
    }
    
}

/*
Input
4
40
89 45 2 45 78 65 33 1 57 48 32 11 5 77 89 45 12 4 78 87 89 45 2 45 78 65 33 1 57 48 32 11 5 77 89 45 12 4 78 87
5
1
35
17
42
-10
1
78
3
-1
0
1
8
98 45 21 32 74 86 89 45
1
5
80
89 45 2 45 78 65 33 1 57 48 32 11 5 77 89 45 12 4 78 87 89 45 2 45 78 65 33 1 57 48 32 11 5 77 89 45 12 4 78 87 89 45 2 45 78 65 33 1 57 48 32 11 5 77 89 45 12 4 78 87 89 45 2 45 78 65 33 1 57 48 32 11 5 77 89 45 12 4 78 87
2
75
36

Output
1 1
35 87
17 45
42 there is not
-10 there is not
-1 there is not
0 there is not
1 78
5 74
75 89
36 45
*/
