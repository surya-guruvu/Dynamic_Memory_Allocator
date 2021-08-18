// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }
    private BSTree InsertAtSentinel(int address, int size, int key)
    {
        if(this.parent==null && this.right==null){
            BSTree d=new BSTree(address,size,key);
            this.right=d;
            this.right.parent=this;
            return d;
        }   
        else if(this.parent==null && this.right!=null){
            return this.right.InsertAtSentinel(address,size,key);
        }
        else{
            BSTree d=new BSTree(address,size,key);
            if(this.key>key){
                if(this.left==null){
                    this.left=d;
                    this.left.parent=this;
                    return d;
                }
                else{
                    return this.left.InsertAtSentinel(address,size,key);
                }
            }
            else if(this.key<key){
                if(this.right==null){
                    this.right=d;
                    this.right.parent=this;
                    return d;
                }
                else{
                    return this.right.InsertAtSentinel(address,size,key);
                }
            }
            else{
                if(this.address>address){
                    if(this.left==null){
                        this.left=d;
                        this.left.parent=this;
                        return d;
                    }
                    else{
                        return this.left.InsertAtSentinel(address,size,key);
                    }
                }
                else if(this.address<address){
                    if(this.right==null){
                        this.right=d;
                        this.right.parent=this;
                        return d;
                    }
                    else{
                        return this.right.InsertAtSentinel(address,size,key);
                    }
                }
            }
        }
        return null;
    }

    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree cur=this;
        while(cur.parent!=null){
            cur=cur.parent;
        }
        return cur.InsertAtSentinel(address,size,key);

    }
    private BSTree Search(Dictionary e){
        BSTree cur=this.getFirst();
        if(cur!=null){
            while(cur!=null){
                if(cur.key==e.key && cur.address==e.address){
                    return cur;
                }
                cur=cur.getNext();
            }
        }
        return null;
    }

    private boolean DeleteAtSentinel(Dictionary e){
        BSTree cur=this.Search(e);
        if(cur!=null){
            if(cur.left==null && cur.right==null){
                BSTree temp=cur.parent;
                if(temp.right==cur){
                    temp.right=null;
                }
                else{
                    temp.left=null;
                }
            }
            else if(cur.left==null){
                BSTree temp=cur.parent;
                if(temp.left==cur){
                    temp.left=cur.right;
                    temp.left.parent=temp;
                }
                else{
                    temp.right=cur.right;
                    temp.right.parent=temp;                    
                }
            }
            else if(cur.right==null){
                BSTree temp=cur.parent;
                BSTree temp1=cur.left;
                if(temp.left==cur){
                    temp.left=temp1;
                    temp1.parent=temp;
                }
                else{
                    temp.right=cur.left;
                    temp.right.parent=temp;                    
                }
            }
            else{
                BSTree cur1=cur.right.getFirst();
                cur.right.DeleteAtSentinel(cur1);
                BSTree B=new BSTree(cur1.address,cur1.size,cur1.key);
                B.left=cur.left;
                B.right=cur.right;
                B.parent=cur.parent;
                B.left.parent=B;
                if(B.right!=null){
                    B.right.parent=B;
                }
                if(cur.parent.left==cur){
                    cur.parent.left=B;
                }
                if(cur.parent.right==cur){
                    cur.parent.right=B;
                }
            }
            return true;
        }
        return false;

    }
    public boolean Delete(Dictionary e)
    { 
        BSTree cur=this;
        while(cur.parent!=null){
            cur=cur.parent;
        }
        return cur.DeleteAtSentinel(e);
    }
        
    public BSTree Find(int key, boolean exact)
    { 
        BSTree cur=this.getFirst();
        if(exact==true){
            if(cur!=null){
                while(cur!=null){
                    if(cur.key==key){
                        return cur;
                    }
                    cur=cur.getNext();
                }
            }
        }
        else{
            if(cur!=null){
                while(cur!=null){
                    if(cur.key>=key){
                        return cur;
                    }
                    cur=cur.getNext();
                }
            }
        }
        return null;
    }

    public BSTree getFirst() //doubt
    { 
        BSTree cur=this;
        if (cur.parent == null) {
            if (cur.right == null) {
                return null;
            }
            cur = cur.right;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur;

        }
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    public BSTree getNext() //doubt
    { 
        if(this.parent==null){
            return null;
        }
        else if(this.parent.parent==null && this.right!=null){
            return this.right.getFirst();
        }
        else if(this.right==null && this.parent.left==this){
            return this.parent;
        }
        else if(this.right==null && this.parent.right==this){
            BSTree cur=this;
            while(cur.parent.left!=cur && cur.parent.parent!=null){
                cur=cur.parent;
            }
            if(cur.parent.parent==null){
                return null;
            }
            else{
                return cur.parent;
            }
        }
        else if(this.right!=null){
            return this.right.getFirst();
        }
        return null; 
    }
    //detects loop other than the loop formed by sentinel(if present)
    private int detectLoop(){
        int n=1;
        BSTree cur=this; 
        if(cur.left!=null && cur.right!=null){
            if(cur.left.parent!=cur && cur.right.parent!=cur){
                return 0;
            }
            return n*(cur.left.detectLoop())*(cur.right.detectLoop());
        }
        else if(cur.left!=null && cur.right==null){
            if(cur.left.parent!=cur){
                return 0;
            }
            return n*(cur.left.detectLoop());
        }
        else if(cur.left==null && cur.right!=null){
            if(cur.right.parent!=cur){
                return 0;
            }
            return n*(cur.right.detectLoop());
        }
        else{
            return n;
        }
    }
    private int BstStructure(){ //this function starts checking from right chid of sentinel
        int n=1;
        BSTree cur=this; 
        if(cur.left!=null && cur.right!=null){
            if(cur.left.key>cur.key || cur.right.key<cur.key){
                return 0;
            }
            else if(cur.left.key==cur.key){
                if(cur.left.address>cur.address || cur.left.address==cur.address){
                    return 0;
                }
            }
            else if(cur.right.key==cur.key){
                if(cur.right.address<cur.address || cur.right.address==cur.address){
                    return 0;
                }
            }
            return n*(cur.left.BstStructure())*(cur.right.BstStructure());
        }
        else if(cur.left!=null && cur.right==null){
            if(cur.left.key>cur.key){
                return 0;
            }
            else if(cur.left.key==cur.key){
                if(cur.left.address>cur.address || cur.left.address==cur.address){
                    return 0;
                }
            }
            return n*(cur.left.BstStructure());
        }   
        else if(cur.right!=null && cur.left==null){
            if(cur.right.key<cur.key){
                return 0;
            }
            else if(cur.right.key==cur.key){
                if(cur.right.address<cur.address || cur.right.address==cur.address){
                    return 0;
                }
            }
            return n*(cur.right.BstStructure());
        }
        else{
            return n;
        }              
    }
    public boolean sanity()
    { 
        //first we check is there a sentinel node or a loop is created near sentinel node;
        BSTree slowptr=this;
        BSTree fastptr=this;
        while(fastptr!=null && fastptr.parent!=null){
            fastptr=fastptr.parent.parent;
            slowptr=slowptr.parent;
            if(slowptr==fastptr){
                return false;
            }
        }
        //if there was a loop at sentinel it is done above;
        //else there will be a node whose parent is null
        BSTree cur=this;
        while(cur.parent!=null){
            cur=cur.parent;
        }
        //check if left child of sentinal is null or not
        if(cur.left!=null){
            return false;
        }
        //checks if the sentinel has value required
        if(cur.address!=-1 && cur.size!=-1 && cur.key!=-1){
            return false;
        }
        int n=cur.detectLoop();
        if(n==0){
            return false;
        }
        if(cur.right!=null){
            int k=cur.right.BstStructure();
            if(k==0){
                return false;
            }
        }

        return true;
    }
}


 


