package maxsubarray;
import java.util.Scanner;
/**
 *
 * @author Monika Wielgus
 */
public class MaxSubarray2D{
    public static Scanner in=new Scanner(System.in);
    public static void main(String[] args){
        
        System.out.println("Number of sets: ");
        int sets=in.nextInt();
        int[][][] array=new int[sets][][];
        
        for(int z=0; z<sets; z++){
            int belowZero=0;
            int isZero=0;
            System.out.println("Number of rows: ");
            int rows=in.nextInt();
            System.out.println("Number of columns: ");
            int columns=in.nextInt();
            int k=0;
            int w=0;
            System.out.println("Array: ");
            array[z]=new int[rows][columns+2];
            for(w=0; w<rows; w++){
                for(k=0; k<columns; k++){
                    int elements=in.nextInt();
                    array[z][w][k]=elements;
                    if(array[z][w][k]==0) isZero++;
                    if(array[z][w][k]<0) belowZero++;
                }    
            }
            array[z][0][k]=isZero;
            array[z][0][k+1]=belowZero;            
        }    
        MaxSubarray(array);        
    }
    public static void MaxSubarray(int[][][] array){
        for(int z=0; z<array.length; z++){
            int left=0;
            int right=0;
            int rows=array[z].length;
            int cols=array[z][0].length-2; //-2, because isZero i belowZero
            int currentSum=0;
            int maxSum=0;
            int maxLeft=0;
            int maxRight=0;
            int maxUp=0;
            int maxDown=0;
            int isZero=array[z][0][cols];
            int belowZero=array[z][0][cols+1];

            int []temp=new int[rows];
            
            for(left=0; left<cols; left++){            
                for(int i=0; i<temp.length; i++)
                    temp[i]=0;
                for(right=left; right<cols; right++){   
                    int up=0;
                    int down=0;
                    
                    currentSum=0;
                    for(down=0; down<temp.length; down++){
                        temp[down]+=array[z][down][right];
                        currentSum+=temp[down];
                    
                        if(currentSum<0){
                            currentSum=0;
                            up=down+1;                            
                        }
                        else if(currentSum==0)
                            up=down+1;
                        else{
                            if(currentSum>maxSum||currentSum==maxSum&&((down-up+1)*(right-left+1)<(maxDown-maxUp+1)*(maxRight-maxLeft+1))){ //surface area (less elements same sum)                               
                                maxSum=currentSum;
                                maxLeft=left;
                                maxRight=right;
                                maxDown=down;
                                maxUp=up;
                            }
                            else if(currentSum==maxSum&&((down-up+1)*(right-left+1)==(maxDown-maxUp+1)*(maxRight-maxLeft+1))){ //the same surface areas so lexicographical order
                                if((up<=maxUp&&down<maxDown) || (up<maxUp&&down<=maxDown)){
                                    maxLeft=left;
                                    maxRight=right;
                                    maxUp=up;
                                    maxDown=down;
                                }
                                else if((left<=maxLeft&&right<maxRight) || (left<maxLeft&&right<=maxRight)){
                                    maxLeft=left;
                                    maxRight=right;
                                    maxUp=up;
                                    maxDown=down;
                                }
                            }
                        }
                    }                   
                }
            }            
            
            if(rows*cols==isZero){ //only 0 in array
                System.out.println("["+0+".."+0+","+0+".."+0+"]"); 
                System.out.println("max_sum="+0);
            }
            else if(rows*cols==belowZero) System.out.println("empty"); //only 0 in array
            else if(rows*cols==(isZero+belowZero)){ //only below zero and zeros so we look for the right zero
                int i1=100000; //start values
                int j1=100000;
                        
                for(int j=0; j<cols; j++){
                    for(int i=0; i<rows; i++){
                        if(array[z][i][j]==0&&(i+j<j1+i1)||array[z][i][j]==0&&i1>j1&&j>=i){
                            i1=i;
                            j1=j;
                        }       
                    }
                }
                System.out.println("["+i1+".."+i1+","+j1+".."+j1+"]");
                System.out.println("max_sum="+0);
            }
            else{
                System.out.println("["+maxUp+".."+maxDown+","+maxLeft+".."+maxRight+"]"); 
                System.out.println("max_sum="+maxSum);
            }
        }
    }
}
