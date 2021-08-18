// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
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
}