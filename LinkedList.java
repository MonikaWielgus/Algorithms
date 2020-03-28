package linkedlist;
/**
 *
 * @author Monika Wielgus
 */
import java.util.Scanner;

public class LinkedList{

    public static Scanner in=new Scanner(System.in);
    
    public static void main(String[] args){
        
        int sets=in.nextInt();
        for(int i=0; i<sets; i++){           
            int lines=in.nextInt();
            Trains tr=new Trains();
            in.nextLine();
            for(int j=0; j<lines; j++){
                String s=in.nextLine();
                String data[]=s.split(" ");
                String task=data[0];
             
                switch(task){
                    case "New":
                        tr.newTrains(data[1],data[2]);
                        break;
                    case "Display":
                        tr.Display(data[1]);
                        break;
                    case "InsertFirst":
                        tr.InsertFirst(data[1], data[2]);
                        break;
                    case "InsertLast":
                        tr.InsertLast(data[1], data[2]);
                        break;
                    case "Reverse":
                        tr.Reverse(data[1]);
                        break;
                    case "Union":
                        tr.Union(data[1],data[2]);
                        break;
                    case "DelFirst":
                        tr.DeleteFirst(data[1],data[2]);
                        break;
                    case "DelLast":
                        tr.DeleteLast(data[1],data[2]);
                        break;                        
                }               
            }           
        }           
    }
}
    
class Carriage{
    Carriage next;
    Carriage prev;
    String name;
    
    Carriage(String n){
        next=null;
        prev=null;
        name=n;
    }
}

class Locomotive{
    Carriage first;
    Carriage last;
    Locomotive next;
    String name;
    
    Locomotive(String n, Carriage c){
        name=n;
        first=c;
        last=c;
        next=null;
    }
    Locomotive(String n, String c){
        name=n;
        Carriage car=new Carriage(c);
        car.next=null;
        car.prev=null;
        first=car;
        last=car;
        next=null;
    }    
}

class Trains{
    static Locomotive first;
    static Locomotive last;
    
    Trains(){
        first=null;
        last=null;        
    }        
     
    Trains(Locomotive l){
        if(first==null){
            first=l;
            last=l;
            l.next=null;
        }
        else{
            last.next=l;
            last=l;
        }
    }
    
    public static void newTrains(String loc_name, String car_name){
        Locomotive loc=new Locomotive(loc_name, car_name);
        if(first==null)
            first=loc;
        else
            last.next=loc;          
        last=loc;
    }
    
    public static void Display(String n){
        Locomotive l=Trains.find(n);
        Carriage c=l.first;
        Carriage prev=null;
        System.out.print(n+":");
        boolean reversion;
        
        if(l.first.next!=null && l.first.prev==null)
            reversion=false;
        else
            reversion=true;
        
        do{
            System.out.print(" "+c.name);
            if(reversion){//it it is reversed, we have to check the next one
                if(c.prev==prev)
                    reversion=!reversion;               
            }
            else{
                if(c.next==prev)
                    reversion=!reversion;
            } 
            
            prev=c;
            
            if(reversion) //so that we know which way to go
                c=c.prev;
            else
                c=c.next;
            
        }while(c!=null);
        
        System.out.println();        
    }
    
    public static void InsertFirst(String n, String m){
        Carriage car=new Carriage(m);
        Locomotive f=find(n);
        
        if(f.first.next==null&&f.first.prev!=null){ //reversed
            f.first.next=car;
            car.prev=f.first;
            car.next=null;
            f.first=car;
        }
        else{ //not reversed or just one carriage
            f.first.prev=car;
            car.next=f.first;
            car.prev=null;
            f.first=car;
        }   
    }
    
    public static void InsertLast(String l, String c){
        Carriage car=new Carriage(c);
        Locomotive f=find(l);
        
        if(f.last.prev==null && f.last.next!=null){ //reversed
            f.last.prev=car;
            car.next=f.last;
            car.prev=null;
            f.last=car;
        }
        else{ //not reversed or just one carriage
            f.last.next=car;
            car.prev=f.last;
            car.next=null;
            f.last=car;
        }       
    }
    
    public static void Reverse(String l){
        Locomotive t=find(l);
        Carriage temp=t.first;
        t.first=t.last;
        t.last=temp;
    }
    
    public static void Union(String l1, String l2){
        Locomotive a=find(l1);
        Locomotive b=find(l2);
        boolean reversion1;
        boolean reversion2;
        
        if(a.last.prev==null && a.last.next!=null) //first reversed
            reversion1=true;
        else
            reversion1=false;
        
        if(b.first.prev!=null && b.first.next==null) //second reversed
            reversion2=true;
        else
            reversion2=false;

        if(reversion1) //first reversed
            a.last.prev=b.first;
        else
            a.last.next=b.first;
        
        if(reversion2) //second reversed
            b.first.next=a.last;
        else
            b.first.prev=a.last;
        
        a.last=b.last;
        
        deleteTrain(l2);        
    }
    
   public static void DeleteFirst(String l1, String l2){
        Locomotive t1=find(l1);
        Locomotive t2=new Locomotive(l2, t1.first.name);        
        
        Locomotive prev=Trains.last;
        prev.next=t2;
        Trains.last=t2;
        last.next=null;
        t2.next=null;
        
        if(t1.first==t1.last){ //only one carriage, so we need to delete the train
            t1.first=t1.last=null;
            deleteTrain(l1);
        }
        else if(t1.first.prev==null&&t1.first.next!=null){ //train is not reversed
            if(t1.first.next.next==t1.first)
                t1.first.next.next=null;
            else
                t1.first.next.prev=null;
            
            t1.first=t1.first.next;                       
        }
        else if(t1.first.prev!=null&&t1.first.next==null){//train is reversed
            if(t1.first.prev.prev==t1.first)
                t1.first.prev.prev=null;
            else
                t1.first.prev.next=null;
                
            t1.first=t1.first.prev;
        }
        
    }
    
    public static void DeleteLast(String name1, String name2){
        Locomotive t1=find(name1);
        Locomotive t2=new Locomotive(name2, t1.last.name);        
        
        Locomotive temp=Trains.last;
        temp.next=t2;
        last=t2;
        last.next=null;
        t2.next=null;
        
        if(t1.first==t1.last){ //only one carriage, so we need to delete train
            t1.first=t1.last=null;
            deleteTrain(name1);
        }
        else if(t1.last.next==null&&t1.last.prev!=null){ //train is not reversed
            if(t1.last.prev.prev==t1.last)
               t1.last.prev.prev=null;
            else
                t1.last.prev.next=null;
            
           t1.last=t1.last.prev;
        }
        else if(t1.last.prev==null&&t1.last.next!=null){//train is reversed
            if(t1.last.next.prev==t1.last)
              t1.last.next.prev=null;
            else
                t1.last.next.next=null;
            
            t1.last=t1.last.next;
        }
    }
       
    public static Locomotive find(String n){
        Locomotive temp=first;
        
        while(temp.name.equals(n)==false && temp!=null){
             temp=temp.next;
        }        
        return temp;
    }
    
    public static void deleteTrain(String n){
        Locomotive temp=first;
        Locomotive previous=null;
        while(temp!=null&&temp.name.equals(n)==false){
            previous=temp;
            temp=temp.next;
        }
        
        if(temp!=null){
            if(temp==first){
                first=temp.next;
            }
            else if(temp==last){
                last=previous;
                previous.next=null;
            }
            else{
                previous.next=temp.next;
            }               
        }
    }  
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
Input:
3
10
New T1 W1
New T2 Q1
New T3 Z1
New T4 U1
Reverse T2
Reverse T3
Union T2 T3
Display T1
Display T2
Display T4
15
New T1 W1
InsertLast T1 W2
InsertFirst T1 W0
Display T1
Reverse T1
Display T1
Reverse T1
Reverse T1
Display T1
New T2 Z1
InsertFirst T2 Z0
Union T2 T1
Display T2
Reverse T2
Display T2
19
New T1 W1
New T2 Z1
New T3 Q1
New T4 R1
DelFirst T2 T5
DelLast T3 T6
Display T5
Display T6
InsertFirst T5 A1
InsertLast T5 A3
Display T5
Union T5 T1
Display T5
New T7 W0
InsertLast T7 W1
InsertLast T7 W2
InsertLast T7 W3
Display T7
Display T4

Output:
T1: W1
T2: Q1 Z1
T4: U1
T1: W0 W1 W2
T1: W2 W1 W0
T1: W2 W1 W0
T2: Z0 Z1 W2 W1 W0
T2: W0 W1 W2 Z1 Z0
T5: Z1
T6: Q1
T5: A1 Z1 A3
T5: A1 Z1 A3 W1
T7: W0 W1 W2 W3
T4: R1
*/
