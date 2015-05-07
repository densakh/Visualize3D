package list;

public class CircularDoublyLinkedList<T> {
    private DLNode<T> head;
    public T get(int idx){
    	if (head == null)
    		return null;
    	DLNode<T> tmp = head;
    	int count = 1;
    	while (count != idx){
            tmp = tmp.getNextNode();
            count++;
        };
        return tmp.getData();
    }
    // Returns the no. of nodes in circular Doubly linked list
    public int getSize() {
        int count = 0;
        if (head == null)
            return count;
        else {
            DLNode<T> temp = head;
            do {
                temp = temp.getNextNode();
                count++;
            } while (temp != head);
        }
        return count;
    }
    
    // Traversal of circular doubly linked list
    public void traverse() {
        if (head == null) {
            System.out.println("List is empty!");
        } else {
            DLNode<T> temp = head;
            do {
                System.out.print(temp.getData() + " ");
                temp = temp.getNextNode();
            } while (temp != head);
        }
    }
    
    /* methods related to insertion in circular doubly linked list starts. */
    public void insertAtBeg(T data) {
        DLNode<T> newnode = new DLNode<T>(data);
        if (head == null) {
            newnode.setNextNode(newnode);
            newnode.setPrevNode(newnode);
            head = newnode;
        } else {
            DLNode<T> temp = head.getPrevNode();
            temp.setNextNode(newnode);
            newnode.setPrevNode(temp);
            newnode.setNextNode(head);
            head.setPrevNode(newnode);
            head = newnode;
        }
    }
    
    public void insertAtEnd(T data) {
        DLNode<T> newnode = new DLNode<T>(data);
        if (head == null) {
            newnode.setNextNode(newnode);
            newnode.setPrevNode(newnode);
            head = newnode;
        } else {
            DLNode<T> temp = head.getPrevNode();
            temp.setNextNode(newnode);
            newnode.setNextNode(head);
            head.setPrevNode(newnode);
            newnode.setPrevNode(temp);
        }
    }
    
    public void insertAtPosition(T data, int position) {
        if(position<0||position==0){
            insertAtBeg(data);
        }    
        else 
        	if(position>getSize()||position==getSize()){
        		insertAtEnd(data);
        	}
        	else{
        		DLNode<T>temp=head;
        		DLNode<T> newnode=new DLNode<T>(data);
        		for(int i=0;i<position;i++){
        			temp=temp.getNextNode();
        		}
          
        		newnode.setNextNode(temp.getNextNode());
        		temp.getNextNode().setPrevNode(newnode);
        		temp.setNextNode(newnode);
        		newnode.setPrevNode(temp);
        	}
    }
    
    /* methods related to insertion in circular doubly linked list ends. */
    /* methods related to deletion in circular doubly linked list starts. */
    // Removal based on a given node for internal use only
    @SuppressWarnings("unused")
    private void remove(DLNode<T> node) {
        if(node.getPrevNode()==node||node.getNextNode()==node)
            head=null;
        else{
            DLNode<T> temp=node.getPrevNode();
            temp.setNextNode(node.getNextNode());
            node.getNextNode().setPrevNode(temp);
            }
        node=null;
    }
    
    public void removeAtBeg(){
        if(head==null)
            System.out.println("List is already empty!");
        else{
            DLNode<T> temp=head.getNextNode();
            head.getPrevNode().setNextNode(temp);
            temp.setPrevNode(head.getPrevNode());
            head=temp;
        }
    }
    
    public void removeAtEnd(){
        if(head==null)
            System.out.println("List is already empty!");
        else{
            DLNode<T> temp=head.getPrevNode();
            temp.getPrevNode().setNextNode(head);
            head.setPrevNode(temp.getPrevNode());
            temp=null;
        }
    }
    
    // Removal based on a given position
    public T remove(int position) {
        T data = null;
        if (position == 0) {
            data = head.getData();
            removeAtBeg();
        } else if (position == getSize()-1) {
            data=head.getPrevNode().getData();
            removeAtEnd();
        } else {
            DLNode<T> temp = head;
            for (int i = 0; i < position; i++) {
                temp = temp.getNextNode();
            }
            data=temp.getData();
            DLNode<T> node = temp.getNextNode();
            node.setPrevNode(temp.getPrevNode());
            temp.getPrevNode().setNextNode(node);
            temp = null;
        }
        return data; //Deleted node's data
    }
    /* methods related to deletion in circular doubly linked list ends. */
}