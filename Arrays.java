package arrays;
import java.util.Scanner;
/**
 *
 * @author Monika Wielgus
 */
public class Arrays {
    public static Scanner in=new Scanner(System.in);
    public static void main(String[] args){
        int sets=in.nextInt();
        for(int s=0; s<sets; s++){
            int data=in.nextInt();
            long []array=new long[data];
            for(int i=0; i<data; i++) 
                array[i]=in.nextLong();
            int tasks=in.nextInt();
            long []arrayoftasks=new long[tasks];
            for(int j=0; j<tasks; j++)
                arrayoftasks[j]=in.nextLong();

            for(int k=0; k<arrayoftasks.length; k++){
                int result=BinarySearch(arrayoftasks[k],array); //result(in case it doesnt exist, it returns index of the next one)
                int result1=BinarySearch(arrayoftasks[k]+1,array); //result of the next one(in case it doesnt exist, it returns index of the next one)
                int repeat=result1-result;
                System.out.print("("+result+","+repeat+")"); //(smaller elements,repeats)
                System.out.print(" ");                
            } 
            
            System.out.println();
            DeleteDuplicates(array); //prints array without duplicates
            System.out.println();            
        }       
    }
  
    public static int BinarySearch(long number, long []array){
        int left=0;
        int right=array.length-1;
        int current;
    
        while(left<=right){
            current=(left+right)/2;
            if(array[current]==number){
                if(current==0)
                    return current;
                if(array[current-1]==number)
                    right=current-1;
                else 
                    return current;
            }
            else{
                if(array[current]<number)
                    left=current+1;
                else 
                    right=current-1;
            }
        }    
        return left; // it couldnt find this number so returns index of the next one
    }

    public static void DeleteDuplicates(long[]array)
    {
        if(array.length!=0){
            int j=0;
            for(int i=1; i<array.length; i++)
                if(array[i]!=array[i-1])
                    array[++j]=array[i];

            if(j>200){
                for(int i=0; i<200; i++){
                    if((i+1)%50==0)            
                        System.out.println(array[i]); 
                    else
                        System.out.print(array[i]+" ");
                }
            }
            else{ 
                for(int i=0; i<=j; i++){
                    if((i+1)%50==0)
                        System.out.println(array[i]);
                    else
                        System.out.print(array[i]+" ");
                }   
            }  
        }
    }
}

/*example input
6
5
0 0 0 0 0
2
0 1
5
2 2 3 3 4
3
1 2 5
5
-1 1 2 5 8
2
3 4
5
10
1 2 3 4 6 7 8 9 10 10
1
10
3
10 10 11
1
10
4
1 1 4 4
2
2 3

output
(0,5) (5,0) 
0 
(0,0) (0,2) (5,0) 
2 3 4 
(3,0) (3,0) 
-1 1 2 5 8 
(5,0) (5,0) (5,0) (5,0) (5,0) (0,2) 
10 1 2 3 4 
*/