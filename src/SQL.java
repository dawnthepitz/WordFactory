import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
class ListNode{//ListNode is a node of the SQL
	String data;
	ListNode next;
	ListNode prev;
	ListNode(String data){
		this.data=data;
		next=prev=null;
	}
	//next and prev are class recursions of  ListNode that serves as poStringers of the linked list
}
public class SQL{//SQL=Stack-Queue-List, an overall linear data structure that has generic operations, Some methods are of same function yet of same purpose so that it would be friendly to fellow programmers to identify the data structure based on utiized operations
	private ListNode head,tail,util;
	void add(String data){
		util=new ListNode(data);
		if(head==null)head=tail=util;
		else{
			tail.next=util;
			util.prev=tail;
			tail=util;
		}
	}
	void enqueue(String data){
		add(data);
	}
	void push(String data){
		util=new ListNode(data);
		if(head==null)head=tail=util;
		else{
			tail.next=util;
			util.prev=tail;
			tail=util;
		}
	}
	ListNode search(String data){
		for(ListNode x=head;x!=null;x=x.next)
			if(x.data.equalsIgnoreCase(data))return x;
		return null;
	}
	ListNode head(){
		return head;
	}
	ListNode tail(){
		return tail;
	}
	ListNode front(){
		return head;
	}
	ListNode rear(){
		return tail;
	}
	ListNode bottom(){
		return head;
	}
	ListNode top(){
		return tail;
	}
	boolean isEmpty(){//To determine if there is content in the SQL
		return head==null;
	}
	void delete(String data){//Removal of data in Linked Lists
		for(ListNode x=head;x!=null;x=x.next)
			if(data.equals(x.data))
				if(x==head)dequeue();
				else if(x==tail)pop();
				else{
					x.next.prev=x.prev;
					x.prev.next=x.next;
				}
	}
	void pop(){
		if(!isEmpty()){
			tail=tail.prev;
		}
	}
	void dequeue(){
		if(!isEmpty()){
			head=head.next;
		}
	}
	void clear(){
		head=tail=null;
	}
	void initWordsDB(){
		try {
			File file = new File("wordsEn.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				add(line);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	String OutContent(){
		String s="";
		for(ListNode x=head;x!=null;x=x.next)
			s+=x.data+"\n";
		return s;
	}
	int length(){
		int x=0;
		for(ListNode util=head;util!=null;util=util.next)
			x++;
		return x;
	}
	int noOfLookups(String s){
		int x=0;
		for(ListNode util=head;util!=null;util=util.next){
			if(util.data.startsWith(s.toLowerCase())){
				x++;
				//JOptionPane.showMessageDialog(null,util.data);
			}
		}
		return x;
	}
}