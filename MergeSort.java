package testmergesort;
/**
 *
 * @author Monika Wielgus
 */
import java.util.Arrays;
import java.util.Random;

public class MergeSort{

    public static void main(String[] args){
        int size=20;
	Random rnd=new Random();
        int []arr=new int[size];

	System.out.println("Recursive merge sort: ");
        for (int i = 0; i < size; i++)
            arr[i] = rnd.nextInt(1000);
        
	System.out.println(Arrays.toString(arr));
	MergeSortRec(arr, 0, arr.length-1);
	System.out.println(Arrays.toString(arr));
	findDuplicates(arr);
        
	System.out.println("Iterative merge sort: ");
            for (int i = 0; i < size; i++)
		arr[i] = rnd.nextInt(1000);
	System.out.println(Arrays.toString(arr));
	MergeSortIter(arr);
	System.out.println(Arrays.toString(arr));           
    }

    static void Merge(int[] arr, int l, int mid, int r){ //merges array [1,mid] with [mid+1,r]
	int []b=new int[arr.length];
        int i;
        for(i=l; i<=r; i++)
            b[i]=arr[i];
        
        i=l;
        int j=mid+1;
        int k=l;
                
        while(i<=mid&&j<=r){
            if(b[i]<=b[j])
                arr[k++]=b[i++];
            else            
                arr[k++]=b[j++];     
        }
                
        while(i<=mid)
            arr[k++]=b[i++];
        while(j<=r)
            arr[k++]=b[j++];
               
    }

    static void swap(int[] arr, int i, int j){
	int tmp = arr[i];
	arr[i] = arr[j];
	arr[j] = tmp;
    }

    static void MergeSortRec(int[] arr, int l, int r){ //recursive merge sort
	if(l>=r)
            return;
        int mid=(l+r)/2;
        MergeSortRec(arr,l,mid);
        MergeSortRec(arr,mid+1,r);
        Merge(arr,l,mid,r);
    }

    static void MergeSortIter(int[] arr){ //iterative merge sort
        int i=2;
        int j;         
        int n=arr.length;
        while(i<n){ //as long as lenght is bigger
            j=0;           
            while(j<n-1){
                int r=(j+i)-1;
                int l=j;   
                if(r>n) //right bigger so we decrease
                    r=n-1;
                     
                int mid=(r+l)/2; 
                Merge(arr, l, mid, r); //scalamy
                j+=i;
            }
            i*=2;
            if(i>=n){
                i/=2; //we decrease i
                Merge(arr, 0, (i-1), n-1); //merging
                i=n;
            }
        }
    }
    
    static void findDuplicates(int[] arr){
        for(int i=0; i<arr.length-1; i++){
            if(arr[i]==arr[i+1])
                System.out.println("Duplicate: "+arr[i]);
        }
    }
}
