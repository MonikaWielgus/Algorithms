package modifiedquicksort;

/**
 *
 * @author Monika Wielgus
 */
import java.util.Scanner;

public class ModifiedQuickSort{

    public static Scanner in=new Scanner(System.in);
    public static void main(String[] args){
        
        int sets=in.nextInt();
        for(int i=0; i<sets; i++){
            int data=in.nextInt();
            long []numbers=new long[data];
            for(int j=0; j<data; j++){
                numbers[j]=in.nextLong();
            }
         
            QuickSort(numbers, 0, data-1);
            for(int t=0; t<data; t++)
                System.out.print(numbers[t]+" ");
            System.out.println();            
        }
    }
    static int Partition(long []array, int left, int right) {                                                        
        int i=left-1;                
        long piv=array[right];
        
        for(int j=left; j<right; j++){
            if(array[j]<piv){
                i++;
                Swap(array, i, j);
            }
        }
        Swap(array, i+1, right);
        return (i+1); 
    }
    static void Swap(long []array,int x, int y){
        long temp=array[x];
        array[x]=array[y];
        array[y]=temp;
    }

    public static void QuickSort(long []array, int left, int right){
        while(left<right || left<array.length-1){                    
            if(left<right){
                int pivot=Partition(array, left, right);              
                long max=array[left];
                int position=left;
                for(int i=left+1; i<pivot; i++){
                    if(array[i]>max){
                        max=array[i];
                        position=i;
                    }
                }
                if(pivot==left){
                    left=left+1;
                }
                else if(pivot==right){
                    right=right-1;
                }
                else{
                    long right_value=array[right];
                    array[right]=max;
                    long pivot_value=array[pivot];
                    array[pivot]=right_value;
                    long prev_value=array[pivot-1];
                    array[pivot-1]=pivot_value;
                    if(pivot-1!=position)
                        array[position]=prev_value;
                    
                    right=pivot-2;
                }
            }
            else{
                long temp=array[right+1];
                
                int end=right+1;
                for(int i=right+1; i<array.length; i++){
                    if(array[i]<temp){
                        end=i;
                        break;
                    }
                }       
                if(array[end]<temp){
                    array[right+1]=array[end];
                    array[end]=array[right+2];
                    array[right+2]=temp;
                    left=right+3;
                    right=end;
                }      
                else{
                    left=left+1;
                    right=right+1;
                }
            }
        }      
    }
}

/* 
Input:
6
10
10 9 8 7 6 5 4 3 2 1
10
-10 -9 -8 -7 -6 -5 -4 -3 -2 -1
1
1
10
78 45 96 85 25 15 35 65 45 73
10 
-85 -96 -4 -25 6 7 -9 41 36 5
5
1 2 0 3 5
Output:
1 2 3 4 5 6 7 8 9 10 
-10 -9 -8 -7 -6 -5 -4 -3 -2 -1 
1 
15 25 35 45 45 65 73 78 85 96 
-96 -85 -25 -9 -4 5 6 7 36 41 
0 1 2 3 5 
*/