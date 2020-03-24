package infixpostfix;
/**
 *
 * @author Monika Wielgus
 */
import java.util.Scanner;
class ArrayStack{ //stack for char
    char []array;
    String stack[];
    int firstFree;
    public ArrayStack(int maxSize){
        array=new char[maxSize];
        firstFree=0;
    }
    public void push(char x){
        if(firstFree<array.length){
            array[firstFree]=x;
            firstFree++;
        }
    }
    public char top(){
        if(firstFree!=0)
            return array[firstFree-1];
        else 
            return 0;
    }
    public boolean isEmpty(){
        if(firstFree==0)
            return true;
        else
            return false;
    }
    public char pop(){
        if(firstFree==0)
            return 0;
        else{
            char temp = array[firstFree-1];
            firstFree--;
            return temp;
        }
    }
}
class ArrayStackString{ //stack for strings

    String stack[];
    int firstFree;
    public ArrayStackString(int maxSize){
        stack=new String[maxSize];
        firstFree=0;
    }
    public void push(String x){
        if(firstFree<stack.length){
            stack[firstFree]=x;
            firstFree++;
        }
    }
    public String top(){
        if(firstFree!=0)
            return stack[firstFree-1];
        else 
            return "0";      
    }
    public String pop(){
        if(firstFree==0)
            return "0";
        else{
            String temp=stack[firstFree-1];
            firstFree--;
            return temp;
        }
    }
}

class ArrayStackInt{ //stack for int

    int stack[];
    int firstFree;
    public ArrayStackInt(int maxSize){
        stack = new int[maxSize];
        firstFree=0;
    }
    public void push(int x){
        if(firstFree<stack.length){
            stack[firstFree]=x;
            firstFree++;
        }
    }    
    public int top(){
        if(firstFree!=0)
            return stack[firstFree-1];
        else 
            return 0;
    }
    public int pop(){
        if(firstFree==0)
            return 0;
        else{
            int temp = stack[firstFree-1];
            firstFree--;
            return temp;
        }
    }
}
public class InfixPostfix{
    
    public static Scanner in=new Scanner(System.in);
    public static void main(String[] args){

        int lines=in.nextInt();
        in.nextLine();
        for(int i=0; i<lines; i++){
            String s=in.nextLine();
            
            char ch[]=s.toCharArray();
            int size=ch.length;
            
            if(ch[0]=='I'&&ch[1]=='N'&&ch[2]=='F'){ //inf to onp
                
                System.out.print("ONP: ");
                for(int j=5; j<ch.length; j++){
                    ch[j-5]=ch[j];               
                }
                size-=5;
                System.out.println(infixToPostfix(ch,size));
            }
            else if(ch[0]=='O'&&ch[1]=='N'&&ch[2]=='P'){ //onp to inf
                System.out.print("INF: ");
                for(int j=5; j<ch.length; j++){
                    ch[j-5]=ch[j];                   
                }
                size-=5;
                System.out.println(posfixToInfix(ch,size));
            }            
        }        
    }

    public static String posfixToInfix(char ch[], int size){

        ArrayStackString st=new ArrayStackString(ch.length);
        ArrayStackInt Priorities=new ArrayStackInt(ch.length);
        
        int Operands=0; //number of letters
        int Operators=0; //number of operators
        
        int l=0; //to check if there is not a single letter before operand for two letters
        for(int i=0; i<size; i++){
            if(ch[i]>='a'&&ch[i]<='z'){
                st.push(Character.toString(ch[i]));
                Priorities.push(getPriority(ch[i]));
                Operands++;
                l++;
            }
            else if(ch[i]=='+'||ch[i]=='-'||ch[i]=='*'||ch[i]=='/'||ch[i]=='<'||ch[i]=='>'||ch[i]=='%'){
                String tmp="";
                Operators++;
                l--;
                if(l==0)
                    return "error";
              
                if(Priorities.top()<=getPriority(ch[i]))
                    tmp='('+st.pop()+')';
                else
                    tmp=st.pop();
                    
                Priorities.pop();
                    
                if(Priorities.top()<getPriority(ch[i]))
                    tmp='('+st.pop()+')'+ch[i]+tmp;
                else
                    tmp=st.pop()+ch[i]+tmp;
                 
                Priorities.pop();
                st.push(tmp);
                Priorities.push(getPriority(ch[i]));
            }
            else if(ch[i]=='~'){
                String tmp="";
                if(Priorities.top()<getPriority(ch[i]))
                    tmp=ch[i]+"("+st.pop()+")";
                else
                    tmp=ch[i]+st.pop();
                 
                Priorities.pop();
                
                st.push(tmp);
                Priorities.push(getPriority(ch[i]));
            }
            else if(ch[i]=='='||ch[i]=='^'){
                String tmp="";
                Operators++;
                l--;
                if(l==0)
                    return "error";
                if(Priorities.top()<getPriority(ch[i]))
                    tmp="("+st.pop()+")";
                else
                    tmp=st.pop();
                
                Priorities.pop();
                
                if(Priorities.top()<getPriority(ch[i]))
                    tmp="("+st.pop()+")"+ch[i]+tmp;
                else
                    tmp=st.pop()+ch[i]+tmp;
                
                Priorities.pop();
                
                st.push(tmp);
                Priorities.push(getPriority(ch[i]));
            }      
        }
        
        if(Operands!=Operators+1)
            return "error";
        
        return st.pop();
    }
    public static String infixToPostfix(char ch_temp[], int size){
        ArrayStack st = new ArrayStack(256);
        String temp = "";
        String postfix = "";
        for(int i=0; i<size; i++){ //rewrite correct signs to string
            if((ch_temp[i]>='a'&&ch_temp[i]<='z')||ch_temp[i]=='+'||ch_temp[i]=='-'||ch_temp[i]=='*'||ch_temp[i]=='/'||ch_temp[i]=='<'||ch_temp[i]=='>'||ch_temp[i]=='%'||ch_temp[i]== '='||ch_temp[i]=='~'||ch_temp[i]=='^'||ch_temp[i]=='('||ch_temp[i]==')')
                temp+=ch_temp[i];          
        }
        char ch[]=temp.toCharArray();
        int parenthesis=0;
        int operands=0;
        int operands_inside=0;
        int operators=0;
        int operators_inside=0;
        int state=0;
        int num_of_par=0;
        
        for(int j=0; j<ch.length; j++){
            if(ch[j]=='('){
                parenthesis++;
                num_of_par++;
                state=1;
            }
            else if(ch[j]==')'){
                parenthesis--;
                if(operands_inside!=operators_inside+1) return "error";
                if(num_of_par==1){
                    state=0;
                    operands_inside=0;
                    operators_inside=0;
                    num_of_par=0;
                }               
            }
            else if(ch[j]>='a'&&ch[j]<='z') {
                operands++;
                if(state==1) operands_inside++;            
            }
            else if(ch[j]=='+'||ch[j]=='-'||ch[j]=='*'||ch[j]=='/'||ch[j]=='<'||ch[j]=='>'||ch[j]=='%'||ch[j]== '='||ch[j]=='^'){
                operators++;
                if(state==1) operators_inside++;
            }
            else if(j<ch.length-1){
                if(ch[j]=='~'&&(ch[j+1]=='+'||ch[j+1]=='-'||ch[j+1]=='*'||ch[j+1]=='/'||ch[j+1]=='<'||ch[j+1]=='>'||ch[j+1]=='%'||ch[j+1]== '='||ch[j+1]=='^')) return "error"; //znak po ~
                if(ch[j]>='a'&&ch[j]<='z'&&ch[j+1]>='a'&&ch[j+1]<='z') return "error";  //dwie litery obok siebie
            }
            if(parenthesis<0)
                return "error";            
        }
        if(ch[ch.length-1]=='~') //on the last place
            return "error";
        if(operands!=(operators+1))
            return "error";
        if(parenthesis!=0) //uneven paranthesis
            return "error";
        if(ch[ch.length-1]=='=') //'=' at the and
            return "error";
        
        for(int c=0; c<ch.length; c++){
            if(ch[c]>='a'&&ch[c]<='z')
                postfix=postfix+ch[c];
            else if(ch[c]=='(')
                st.push(ch[c]);
            else if(ch[c]==')'){
                while(!st.isEmpty()){
                    char t = st.pop();
                    if(t != '(')
                        postfix = postfix +t;
                    else
                        break;
                }
            }
            else if(ch[c]=='+'||ch[c]=='-'||ch[c]=='*'||ch[c]=='/'||ch[c]=='<'||ch[c]=='>'||ch[c]=='%'||ch[c]== '='||ch[c]=='~'||ch[c]=='^'){
                if(st.isEmpty())
                    st.push(ch[c]);
                else{
                    while(!st.isEmpty()){
                        char t = st.pop();
                        if(t=='('){
                            st.push(t);
                            break;
                        }
                        else if(t=='+'||t=='-'||t=='*'||t=='/'||t=='<'||t== '>'||t=='%'){
                            if(getPriority(t)<getPriority(ch[c])){
                                st.push(t);
                                break;
                            }
                            else{
                                postfix = postfix + t;
                            }
                        }
                        else if(t=='='||t=='~'||t=='^'){
                            if(getPriority(t)<=getPriority(ch[c])){
                                st.push(t);
                                break;
                            }
                            else{
                                postfix = postfix + t;
                            }
                        }
                    }
                    st.push(ch[c]);
                }
            }
        }
        while(!st.isEmpty()){
            postfix = postfix + st.pop();
        }
        return postfix;
    }
    
    
    public static int getPriority(char c){
        switch (c) {
            case '=':
                return 0;
            case '<':
            case '>':
                return 1;
            case '+':
            case '-':
                return 2;
            case '*':
            case '%':
            case '/':
                return 3;
            case '~':
                return 5;
            default:
                return 6;               
        }        
    }
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
Input:
20
INF: c)+(d
ONP: az+b~b-+
INF: d=~a<r<~b
INF: ~a-~~v<c+d&!p|!!q
INF: x=~a*b/c-d+e%~f
ONP: yebcrefg++++++=
ONP: abc++def++g+++
INF: x=a=b=c
ONP: xabs===
INF: x=(a=(f=c^(d^e)))
INF: x=~~~~a
INF: x=~(~(~(~a)))
INF: x=w+(t-u+y)
ONP: xabc-q++=
INF: x=a+(((a-b)+c))
INF: ~a*(~s+~c)/~(~d-e)
ONP: ab~f+*de-~/
INF: ,g,h<x.>b!=
ONP: a*ba+
ONP: er+tt%^

Output:
ONP: error
INF: a+z+(~b-b)
ONP: da~r<b~<=
ONP: error
ONP: xa~b*c/d-ef~%+=
INF: y=e+(b+(c+(r+(e+(f+g)))))
INF: error
ONP: xabc===
INF: x=a=b=s
ONP: xafcde^^===
ONP: xa~~~~=
ONP: xa~~~~=
ONP: xwtu-y++=
INF: x=a+(b-c+q)
ONP: xaab-c++=
ONP: a~s~c~+*d~e-~/
INF: a*(~b+f)/~(d-e)
ONP: error
INF: error
INF: (e+r)^(t%t)
*/