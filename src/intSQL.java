class iListNode{//ListNode is a node of the SQL
	int data;
	iListNode next;
	iListNode prev;
	iListNode(int data){
		this.data=data;
		next=prev=null;
	}
	//next and prev are class recursions of  ListNode that serves as pointers of the linked list
}
public class intSQL{//SQL=Stack-Queue-List, an overall linear data structure that has generic operations, Some methods are of same function yet of same purpose so that it would be friendly to fellow programmers to identify the data structure based on utiized operations
	private iListNode head,tail,util;
	intSQL(intSQL a){
		for(iListNode util=a.head();util!=null;util=util.next)
			add(util.data);
	}
	intSQL(){
			head=tail=null;
	}
	static int value(iListNode x){
		if(x==null)return 16;//Force truing of notSameRow Condition
		return x.data;
	}
	void add(int data){
		util=new iListNode(data);
		if(head==null)head=tail=util;
		else{
			tail.next=util;
			util.prev=tail;
			tail=util;
		}
	}
	void enqueue(char data){
		add(data);
	}
	void push(int data){
		add(data);
	}
	iListNode search(int data){
		for(iListNode x=head;x!=null;x=x.next)
			if(x.data==data)return x;
		return null;
	}
	iListNode head(){
		return head;
	}
	iListNode tail(){
		return tail;
	}
	iListNode front(){
		return head;
	}
	iListNode rear(){
		return tail;
	}
	iListNode bottom(){
		return head;
	}
	iListNode top(){
		return tail;
	}
	boolean isEmpty(){//To determine if there is content in the SQL
		return head==null;
	}
	void delete(int data){//Removal of data in Linked Lists
		for(iListNode x=head;x!=null;x=x.next)
			if(data==x.data)
				if(x==head)dequeue();
				else if(x==tail)pop();
				else{
					x.next.prev=x.prev;
					x.prev.next=x.next;
					x=null;
				}
	}
	void pop(){
		if(!isEmpty()){
			tail=tail.prev;
			tail.next=null;
		}
	}
	void dequeue(){
		if(!isEmpty()){
			head=head.next;
			head.prev=null;
		}
	}
	void sort(){//Sorts data in Ascending Order
		for(iListNode x=head;x!=null;x=x.next)
			for(iListNode y=x.next;y!=null;y=y.next)
				if(x.data>y.data){
					int temp=x.data;
					x.data=y.data;
					y.data=temp;
				}
	}
}
