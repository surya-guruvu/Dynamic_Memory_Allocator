// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    { 
        //gets inserted after current node if current node is not tail
    	if(this.next!=null){
    		A1List newDict=new A1List(address,size,key);
            A1List temp=this.next;
    		this.next=newDict;
    		this.next.prev=this;
    		this.next.next=temp;
    		temp.prev=newDict;
    		return newDict;
    	}
        return null;
    }

    public boolean Delete(Dictionary d) 
    {
        A1List cur=this;
        //checks from current node to head,deletes if found
        while(cur!=null){
            if(cur.key==d.key){
                if(cur.address==d.address && cur.size==d.size && cur.prev!=null && cur.next!=null){
                    A1List temp=cur.prev;
                    cur.next.prev=temp;
                    temp.next=cur.next;
                    return true;
                }
            }
            cur=cur.next;
        }
        cur=this.next;
        // checks from current node to tail,deletes if found
        while(cur!=null){
            if(cur.key==d.key){
                if(cur.address==d.address && cur.size==d.size && cur.next!=null && cur.prev!=null){
                    A1List temp=cur.prev;
                    cur.next.prev=temp;
                    temp.next=cur.next;
                    return true;
                }
            }
            cur=cur.next;
        }
        return false;
    }

    public A1List Find(int k, boolean exact)
    { 
        A1List curr=this;
        //Traverse until head
        while(curr.prev!=null){
            curr=curr.prev;
        }
        //checks from head to tail
        if(exact==true){
            while(curr!=null){
                if(curr.key==k && curr.prev!=null && curr.next!=null){
                    return curr;
                }
                curr=curr.next;
            }
        }
        //checks from head to tail
        else{
            while(curr!=null){
                if(curr.key>=k && curr.prev!=null && curr.next!=null){
                    return curr;
                }
                curr=curr.next;
            }
        }

        return null;
    }

    public A1List getFirst()
    {
    	A1List cur=this;
        //Traverse until head
    	while(cur.prev!=null){
    		cur=cur.prev;
    	}
        //returns next to head,if next is not tail
    	if(cur.next.next!=null){
    		return cur.next;
    	}
    	return null;
    }
    
    public A1List getNext() 
    {
        //returns next if next is not tail
    	if(this.next!=null && this.next.next!=null){
    		return this.next;
    	}
        return null;
    }

    public boolean sanity()
    {
        //detect loop;
        A1List slowptr1=this;
        A1List fastptr1=this;
        while(fastptr1!=null && fastptr1.next!=null){
            fastptr1=fastptr1.next.next;
            slowptr1=slowptr1.next;
            if(slowptr1==fastptr1){
                return false;
            }

        }

        A1List slowptr2=this;
        A1List fastptr2=this;
        while(fastptr2!=null && fastptr2.prev!=null){
            fastptr2=fastptr2.prev.prev;
            slowptr2=slowptr2.prev;
            if(slowptr2==fastptr2){
                return false;
            }

        }
        if(this.prev==null && this.next==null){
            return false;
        }
        if(this.prev==null){
            if(this.next.prev!=this){
                return false;
            }
        }
        if(this.next==null){
            if(this.prev.next!=this){
                return false;
            }
        }
        A1List cur1=this.prev;
        A1List cur2=this.next;

        //checks from cur1 to head
        while(cur1!=null && cur1.prev!=null){
            //returns false when some nodes prev points to cur1 but cur1.next==null
            if(cur1.next==null){
                return false;
            }
            else if(cur1.prev.next!=cur1 && cur1.next.prev!=cur1){
                break;
            }
            else{
                cur1=cur1.prev;
            }
        }
        if(cur1!=null && cur1.prev==null){
            //returns false when head next points to null
            if(cur1.next==null){
                return false;
            }
            if(cur1.next.prev!=cur1){
                return false;
            }
            //checks the whether cur1 is head or not
            if(cur1.key!=-1 && cur1.address!=-1 && cur1.size!=-1){
                return false;
            }
        }
        if(cur1!=null && cur1.prev!=null){
            return false;
        }
        //checks from cur2 to tail
        while(cur2!=null && cur2.next!=null){
            //returns false when some nodes next points to cur2 but cur1.next==null
            if(cur2.prev==null){
                return false;
            }
            if(cur2.prev.next!=cur2 || cur2.next.prev!=cur2){
                break;
            }
            else{
                cur2=cur2.next;
            }
        }
        if(cur2!=null && cur2.next==null){
            //return false when tail prev points to null
            if(cur2.prev==null){
                return false;
            }
            if(cur2.prev.next!=cur2){
                return false;
            }
            //checks whether cur2 is tail or not
            if(cur2.key!=-1 && cur2.address!=-1 && cur2.size!=-1){
                return false;
            }
        }
        if(cur2!=null && cur2.next!=null){
            return false;
        }        


        return true;
    }

}


