// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.
interface Deque<T>{    
    int size();
    boolean isEmpty();
    void enqueue(T obj);
    T front();
    T top();
    T pop();
    T dequeue();
}
class Node<T>{
    T data;
    Node<T> next;
    Node<T> prev;
    Node(T data){
        this.data=data;
    }
}
class LinkedQueue<T> implements Deque<T>{
    Node<T> head;
    Node<T> tail;
    public boolean isEmpty(){
        if(tail==null){
            return true;
        }
        return false;
    }
    public int size(){
        Node<T> temp=tail;
        int count=0;
        while(temp!=null){
            count+=1;
            temp=temp.prev;
        }
        return count;
    }
    public void enqueue(T obj){
        if(tail==null){
            head=new Node<T>(obj);
            tail=head;
        }
        else{
            Node<T> temp=new Node<T>(obj);
            tail.next=temp;
            temp.prev=tail;
            tail=temp;
        }
    }
    public T dequeue(){
        if(tail!=null){
            Node<T> temp=head;
            head=head.next;
            if(head!=null){
                head.prev=null;
            }
            else{
                tail=head;
            }

            return temp.data;
        }
        return null;
    }
    public T front(){
        if(head!=null){
            return head.data;
        }
        return null;
    }
    public T top(){
        if(tail!=null){
            return tail.data;
        }
        return null;
    }
    public T pop(){
        if(tail!=null){
            Node<T> temp=tail;
            tail=tail.prev;
            if(tail!=null){
                tail.next=null;
            }
            else{
                head=tail;
            }

            return temp.data;
        }
        return null;

    }


}
public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    private static int partition(Dictionary l[],int first,int last){
        int x=l[first].address;
        int i=first+1;
        int j=last;
        while (i<j && l[i].address<=x){
            i+=1;
        }
        while (i<j && l[j].address>x){
            j-=1;
        }
        if (l[j].address>=x){
            j=j-1;
        }
        Dictionary temp;
        while (i<j){
            temp=l[i];
            l[i]=l[j];
            l[j]=temp;
            i+=1;
            j-=1;
            while (l[i].address<=x){
                i=i+1;
            }
            while (l[j].address>x){
                j=j-1;
            }
        }
        temp=l[first];
        l[first]=l[j];
        l[j]=temp;
        return j;
    }
    private static void quicksort(Dictionary l[],int left,int right){
        if (left<right){
            int pindex=partition(l,left,right);
            quicksort(l,left,pindex-1);
            quicksort(l,pindex+1,right);
        }
    }
    public int Allocate(int blockSize) {
        if(blockSize<=0){
            return -1;
        }
        Dictionary newdict=this.freeBlk.Find(blockSize, false);
        if(newdict!=null){
            if(newdict.size==blockSize){
                this.freeBlk.Delete(newdict);
                this.allocBlk.Insert(newdict.address,newdict.size,newdict.address);
                return newdict.address;
            }
            else{
                this.allocBlk.Insert(newdict.address,blockSize,newdict.address);
                Dictionary p=newdict;
                this.freeBlk.Delete(newdict);
                this.freeBlk.Insert(p.address+blockSize,p.size-blockSize,p.size-blockSize);

                return p.address;
            }
        }
        return -1;
    } 
    public int Free(int startAddr) {
        Dictionary newdict=this.allocBlk.Find(startAddr,true);
        if(newdict!=null){
            allocBlk.Delete(newdict);
            freeBlk.Insert(newdict.address,newdict.size,newdict.size);
            return 0;
        }

        return -1;
    }
    public void Defragment() {
        Dictionary a=this.freeBlk.getFirst();
        if(a==null){
            return;
        }
        int n=0;
        for(Dictionary d=freeBlk.getFirst();d!=null;d=d.getNext()){
            n+=1;
        }
        Dictionary[] temparray=new Dictionary[n];
        for(int i=0;i<n;i++){
            temparray[i]=a;
            a=a.getNext();
        }
        Deque<Dictionary> a1=new LinkedQueue<Dictionary>();
        A2DynamicMem.quicksort(temparray,0,n-1);
        int i=0;
        while(i<n){
            if(a1.isEmpty()){
                a1.enqueue(temparray[i]);
            }
            else{
                Dictionary c=temparray[i];
                Dictionary b=a1.top();
                if((b.address)+(b.size)!=(c.address)){
                        a1.enqueue(c);
                }
                else{
                    a1.pop();
                    Dictionary d=new BSTree(b.address,(c.size)+(b.size),(c.size)+(b.size));
                    a1.enqueue(d);
                }
            }
            i=i+1;
        }
        if(type==2){
            this.freeBlk=new BSTree();
        }
        if(type==3){
            this.freeBlk=new AVLTree();
        }
                    
        while(!(a1.isEmpty())){
            freeBlk.Insert(a1.front().address,a1.front().size,a1.front().key);
            a1.dequeue();
        }

    }
}