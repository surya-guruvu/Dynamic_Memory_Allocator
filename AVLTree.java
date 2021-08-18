// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    private static int max(int a,int b){
        if(a>=b){
            return a;
        }
        else{
            return b;
        }
    }
    private boolean checkHeightProperty(){ //checks for AVL tree height property at current node
        if(this.left==null && this.right==null){
            return true;
        }
        else if(this.left==null){
            if(this.right.height!=0){
                return false;
            }
        }
        else if(this.right==null){
            if(this.left.height!=0){
                return false;
            }
        }
        else{
            if(((this.left.height)-(this.right.height))>1 || ((this.left.height)-(this.right.height))<-1){
                return false;
            }
        }
        return true;
    }
    private void setCrtHeight(){ //sets correct height for the node depending on its children
        if(this.parent==null){
            return;
        }
        if(this.left==null && this.right==null){
            this.height=0;
        }
        else if(this.left==null){
            this.height=this.right.height+1;
        }
        else if(this.right==null){
            this.height=this.left.height+1;
        }
        else{
            this.height=AVLTree.max(this.left.height,this.right.height)+1;
        }
    }
    private boolean leftMax(){ //returns true if left child's height greater than right child
        if(this.left==null && this.right==null){
            return false;
        }
        else if(this.left==null){
            return false;
        }
        else if(this.right==null){
            return true;
        }
        else if(this.left.height>this.right.height){
            return true;
        }
        else{
            return false;
        }

    }
    private AVLTree leftRotate(){
        AVLTree temp=this.right;
        this.right=temp.left;
        temp.left=this;
        temp.parent=this.parent;
        if(this.right!=null){
            this.right.parent=this;
        }
        this.parent=temp;
        temp.left.setCrtHeight();
        temp.setCrtHeight();
        return temp;
    }
    private AVLTree rightRotate(){
        AVLTree temp=this.left;
        this.left=temp.right;
        temp.right=this;
        if(this.left!=null){
            this.left.parent=this;
        }
        temp.parent=this.parent;
        this.parent=temp;
        temp.right.setCrtHeight();
        temp.setCrtHeight();
        return temp;
    }
    private AVLTree leftRight(){ //first left rotate,then right rotate
        this.left=this.left.leftRotate();
        return this.rightRotate();
    }
    private AVLTree rightLeft(){ //first right rotate then left rotate
        this.right=this.right.rightRotate();
        return this.leftRotate();
    }
    private void reBalance(){
        if(this.parent==null){
            return;
        }
        else if(this.checkHeightProperty()){
            return;
        }
        else if(this.leftMax()){
            if (this.left.leftMax()){
                if(this.parent.left==this){
                    this.parent.left=this.rightRotate();
                }
                else{
                    this.parent.right=this.rightRotate();
                }

            }
            else{
                if(this.parent.left==this){
                    this.parent.left=this.leftRight();;
                }
                else{
                    this.parent.right=this.leftRight();
                }
            }
        }
        else{
            if(this.right.leftMax()){
                if(this.parent.left==this){
                    this.parent.left=this.rightLeft();;
                }
                else{
                    this.parent.right=this.rightLeft();
                }
            }
            else{
                if(this.parent.left==this){
                    this.parent.left=this.leftRotate();
                }
                else{
                    this.parent.right=this.leftRotate();
                }

            }
        }
    }


    private AVLTree InsertAtSentinel(int address, int size, int key)
    {
        if(this.parent==null && this.right==null){
            AVLTree d=new AVLTree(address,size,key);
            this.right=d;
            this.right.parent=this;
            return d;
        }   
        else if(this.parent==null && this.right!=null){
            return this.right.InsertAtSentinel(address,size,key);
        }
        else{
            AVLTree d=new AVLTree(address,size,key);
            if(this.key>key){
                if(this.left==null){
                    this.left=d;
                    this.left.parent=this;
                    AVLTree cur=d;
                    while(cur.parent!=null && cur.checkHeightProperty()){
                        cur.setCrtHeight();
                        cur=cur.parent;
                    }
                    if(cur.parent!=null){
                        cur.reBalance();
                    }
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
                    AVLTree cur=d;
                    while(cur.parent!=null && cur.checkHeightProperty()){
                        cur.setCrtHeight();
                        cur=cur.parent;
                    }
                    if(cur.parent!=null){
                        cur.reBalance();
                    }
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
                        AVLTree cur=d;          
                        while(cur.parent!=null && cur.checkHeightProperty()){
                            cur.setCrtHeight();
                            cur=cur.parent;
                        }
                        if(cur.parent!=null){
                            cur.reBalance();
                        }
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
                        AVLTree cur=d;
                        while(cur.parent!=null && cur.checkHeightProperty()){
                            cur.setCrtHeight();
                            cur=cur.parent;
                        }
                        if(cur.parent!=null){
                            cur.reBalance();
                        }
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


    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree cur=this;
        while(cur.parent!=null){
            cur=cur.parent;
        }
        return cur.InsertAtSentinel(address,size,key);

    }


    private AVLTree Search(Dictionary e){
        AVLTree cur=this;
        if(cur.parent==null && cur.right==null){
            return null;
        }
        else if(cur.parent==null){
            cur=cur.right;
            while(cur!=null){
                if(cur.key<e.key){
                    cur=cur.right;
                }
                else if(cur.key>e.key){
                    cur=cur.left;
                }
                else{
                    if(cur.address==e.address){
                        return cur;
                    }
                    else if(cur.address>e.address){
                        cur=cur.left;
                    }
                    else{
                        cur=cur.right;
                    }
                }
            }
            return null;
        }
        else{
            while(cur!=null){
                if(cur.key<e.key){
                    cur=cur.right;
                }
                else if(cur.key>e.key){
                    cur=cur.left;
                }
                else{
                    if(cur.address==e.address){
                        return cur;
                    }
                    else if(cur.address>e.address){
                        cur=cur.left;
                    }
                    else{
                        cur=cur.right;
                    }
                }
            }
            return null;
        }
    }


    private boolean DeleteAtSentinel(Dictionary e){
        AVLTree cur=this.Search(e);
        if(cur!=null){
            if(cur.left==null && cur.right==null){
                AVLTree temp=cur.parent;
                if(temp.right==cur){
                    temp.right=null;
                }
                else{
                    temp.left=null;
                }
                while(temp.parent!=null){
                    if(!(temp.checkHeightProperty())){
                        temp.reBalance();
                    }
                    temp.setCrtHeight();
                    temp=temp.parent;
                }

            }
            else if(cur.left==null){
                AVLTree temp=cur.parent;
                if(temp.left==cur){
                    temp.left=cur.right;
                    temp.left.parent=temp;
                }
                else{
                    temp.right=cur.right;
                    temp.right.parent=temp;                    
                }
                while(temp.parent!=null){
                    if(!(temp.checkHeightProperty())){
                        temp.reBalance();
                    }
                    temp.setCrtHeight();
                    temp=temp.parent;
                }

            }
            else if(cur.right==null){
                AVLTree temp=cur.parent;
                AVLTree temp1=cur.left;
                if(temp.left==cur){
                    temp.left=temp1;
                    temp1.parent=temp;
                }
                else{
                    temp.right=cur.left;
                    temp.right.parent=temp;                    
                }
                while(temp.parent!=null){
                    if(!(temp.checkHeightProperty())){
                        temp.reBalance();
                    }
                    temp.setCrtHeight();
                    temp=temp.parent;
                }

            }
            else{
                AVLTree cur1=cur.right.getFirst();
                AVLTree B=new AVLTree(cur1.address,cur1.size,cur1.key);
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
                B.right.DeleteAtSentinel(cur1);
            }
            return true;
        }
        return false;

    }


    public boolean Delete(Dictionary e)
    { 
        AVLTree cur=this;
        while(cur.parent!=null){
            cur=cur.parent;
        }
        return cur.DeleteAtSentinel(e);
    }


    public AVLTree Find(int key, boolean exact)
    { 
    AVLTree cur=this;
    while(cur.parent!=null){
        cur=cur.parent;
    }
    cur=cur.right;
    if(exact==true){
        while(cur!=null){
            if(cur.key==key){
                if(cur.left!=null && cur.left.key==key){
                    cur=cur.left;
                }
                else{
                    return cur;
                }
            }
            else if(cur.key>key){
                cur=cur.left;
            }
            else{
                cur=cur.right;
            }
        }
    }
    else{
        while(cur!=null){
            if(cur.key<key){
                cur=cur.right;
            }
            else{
                if(cur.left!=null && cur.left.key>=key){
                    cur=cur.left;
                }
                else{
                    return cur;
                }
            }
        }
    }
    return null;    
    }


    public AVLTree getFirst()
    { 
        AVLTree cur=this;
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


    public AVLTree getNext()
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
            AVLTree cur=this;
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

    private int BstStructure(){ //this function starts checking from right chid of sentinel
        int n=1;
        AVLTree cur=this; 
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
    private int BstStructure1(){ //checks for the invariant that "the parent of current node children is parent itself" 
        int n=1;
        AVLTree cur=this; 
        if(cur.left!=null && cur.right!=null){
            if(cur.left.parent!=cur && cur.right.parent!=cur){
                return 0;
            }
            return n*(cur.left.BstStructure1())*(cur.right.BstStructure1());
        }
        else if(cur.left!=null && cur.right==null){
            if(cur.left.parent!=cur){
                return 0;
            }
            return n*(cur.left.BstStructure1());
        }
        else if(cur.left==null && cur.right!=null){
            if(cur.right.parent!=cur){
                return 0;
            }
            return n*(cur.right.BstStructure1());
        }
        else{
            return n;
        }
    }
    private int AvlHeightProperty(){ //this function starts checking from right chid of sentinel(checks the height property)
        int n=1;
        AVLTree cur=this;
        if(!(cur.checkHeightProperty())){
            return 0;
        }
        else{
            if(cur.left!=null && cur.right!=null){
                return n*(cur.left.AvlHeightProperty())*(cur.right.AvlHeightProperty());
            }
            else if(cur.left!=null && cur.right==null){
                return n*(cur.left.AvlHeightProperty());
            }
            else if(cur.left==null && cur.right!=null){
                return n*(cur.right.AvlHeightProperty());
            }
            else{
                return n;
            }
        }
    }


    public boolean sanity()
    { 
        //first we check is there a sentinel node or a loop is created near sentinel node;
        AVLTree slowptr=this;
        AVLTree fastptr=this;
        while(fastptr!=null && fastptr.parent!=null){
            fastptr=fastptr.parent.parent;
            slowptr=slowptr.parent;
            if(slowptr==fastptr){
                return false;
            }
        }
        //if there was a loop at sentinel it is done above;
        //else there will be a node whose parent is null
        AVLTree cur=this;
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
        if(cur.right!=null){
            int k=cur.right.AvlHeightProperty();
            int b=cur.right.BstStructure();
            if(k*b==0){
                return false;
            }
        }
        int n=cur.BstStructure1();
        if(n==0){
            return false;
        }
        return true;
    }
}


