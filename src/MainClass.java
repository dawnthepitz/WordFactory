import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class MainClass extends JFrame implements ActionListener{
	JTextField tfInput=new JTextField(16);//Displays what the user wants to show
	static JButton btWan[]=new JButton[16];//Set of Dices in Viewable form
	JButton btShake=new JButton("Shake");//Shakes the Dices & Starts New Game
	JButton btCompute=new JButton("Agent's turn");
	JButton btOk=new JButton("OK");
	JButton btClr=new JButton("Clear");
	JPanel pDice=new JPanel();//Container of Dices
	DiceSet dDice=new DiceSet();//Set of Dices in Manipulative Form
	Random shitness=new Random();//Just a random number Generator
	SQL listOfWords=new SQL();//A dbase of Words
	SQL agentWords=new SQL();
	SQL inputtedWords=new SQL();//A set of words agent has entered
	MainClass(){//JFrame Constructor: GUI Implementation
		listOfWords.initWordsDB();
		setTitle("Worudo Fakkutori");
		pDice.setLayout(new GridLayout(4,4));
		setVisible(true);
		setLayout(new FlowLayout());
		add(tfInput);
		tfInput.setEditable(false);
		add(pDice);
		for(int x=0;x<16;x++){
			pDice.add(btWan[x]);
		}
		add(btShake);
		btShake.addActionListener(this);
		add(btCompute);
		btCompute.addActionListener(this);
		add(btOk);
		btOk.addActionListener(this);
		add(btClr);
		btClr.addActionListener(this);
		dDice.initDiceSet();
		dDice.Jumbles();
		for(int x=0;x<16;x++){
			btWan[x].setText(dDice.Dice[x].value[shitness.nextInt(6)]+"");
			btWan[x].addActionListener(this);
			btWan[x].setBackground(Color.YELLOW);
			btWan[x].setForeground(Color.BLACK);
		}
		btShake.setEnabled(false);
	}
	public static void main(String[]args){
		for(int x=0;x<16;x++){
			btWan[x]=new JButton();
		}
		MainClass EchosNess=new MainClass();
		EchosNess.setSize(200,300);
		EchosNess.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {
		int a;
		for(a=0;a<16;a++){
			if(btWan[a].getBackground()==Color.GRAY)break;
		}
		for(int x=0;x<16;x++){
			if(e.getSource()==btWan[x]){
				if(btWan[x].getBackground()!=Color.WHITE&(malinis()|(Math.abs(x-a)==5&oneRowDifference(x,a)|Math.abs(x-a)==4|notSameRow(x,a)&Math.abs(x-a)==3|Math.abs(x-a)==1&!notSameRow(x,a)))){//if tile is adjacent Orthogonally or Diagonally/Or start of Move
					if(!malinis())btWan[a].setBackground(Color.WHITE);//Marks Visited Tile
					btWan[x].setBackground(Color.GRAY);//Marks Recently Visited Tile
					tfInput.setText(tfInput.getText()+btWan[x].getText());//Pushes Character to String
				}
			}
		}
		if(e.getSource()==btShake){
			JOptionPane.showMessageDialog(null,"Your Score is "+inputtedWords.length()+"pts.\nAgent's Score is "+agentWords.length()+"pts.");
			dDice.Jumbles();
			for(int x=0;x<16;x++){
				btWan[x].setText(dDice.Dice[x].value[shitness.nextInt(6)]+"");
				btWan[x].setBackground(Color.YELLOW);
			}
			tfInput.setText("");
			inputtedWords.clear();
			agentWords.clear();
			btShake.setEnabled(false);
			btClr.setEnabled(true);
			btOk.setEnabled(true);
			btCompute.setEnabled(true);
		}
		if(e.getSource()==btCompute){
			System.out.println("Words the agent has found:");
			for(int x=0;x<16;x++){
				analyze(x,"",new intSQL());
			}
			btShake.setEnabled(true);
			btClr.setEnabled(false);
			btOk.setEnabled(false);
			btCompute.setEnabled(false);
		}
		if(e.getSource()==btOk){
			if((inputtedWords.search(tfInput.getText())==null&listOfWords.search(tfInput.getText())!=null)&tfInput.getText().length()>=3){
				if(inputtedWords.isEmpty())System.out.println("User's input:");
				inputtedWords.add(tfInput.getText());
				System.out.println(tfInput.getText());
				tfInput.setText("");
				for(int x=0;x<16;x++){
					btWan[x].setBackground(Color.YELLOW);
				}
				JOptionPane.showMessageDialog(null,"Input Accepted");
			}
			else JOptionPane.showMessageDialog(null,"Invalid Move");
		}
		if(e.getSource()==btClr){
			for(int x=0;x<16;x++){
				btWan[x].setBackground(Color.YELLOW);
			}
			tfInput.setText("");
		}
	}
	public boolean malinis(){
		for(int x=0;x<16;x++){
			if(btWan[x].getBackground()==Color.GRAY|btWan[x].getBackground()==Color.WHITE)
				return false;
		}
		return true;
	}
	public void analyze(int x, String s,intSQL list){
		if(list.tail()!=null){
			if(Math.abs(x-list.tail().data)==3)
				if(!notSameRow(x,list.tail().data))return;
			if(Math.abs(x-list.tail().data)==1)
				if(notSameRow(x,list.tail().data))return;
			if(Math.abs(x-list.tail().data)==5)
				if(!oneRowDifference(x,list.tail().data))return;
		}
		if(x<0|x>15)return;
		if(list.search(x)!=null)return;
		s=s+btWan[x].getText();
		if(listOfWords.noOfLookups(s)==0)return;
		list.add(x);
		if(listOfWords.search(s)!=null&s.length()>=3){
			if(agentWords.search(s)==null){
				agentWords.add(s);
				System.out.println(s);
			}
		}
		analyze(x-5,new String(s),new intSQL(list));
		analyze(x-4,new String(s),new intSQL(list));
		analyze(x-3,new String(s),new intSQL(list));
		analyze(x-1,new String(s),new intSQL(list));
		analyze(x+1,new String(s),new intSQL(list));
		analyze(x+3,new String(s),new intSQL(list));
		analyze(x+4,new String(s),new intSQL(list));
		analyze(x+5,new String(s),new intSQL(list));
	}
	public boolean notSameRow(int a, int b){
			if(a>=0&a<4)
				if(b>=0&b<4)return false;
			if(a>=4&a<8)
				if(b>=4&b<8)return false;
			if(a>=8&a<12)
				if(b>=8&b<12)return false;
			if(a>=12&a<16){
				if(b>=12&b<16)return false;
			}
			return true;
	}
	public boolean oneRowDifference(int a, int b){
		if(a>=0&a<4)
			if(b>=4&b<8)return true;
		if(a>=4&a<8)
			if(b>=0&b<4|b>=8&b<12)return true;
		if(a>=8&a<12)
			if(b>=4&b<8|b>=12&b<16)return true;
		if(a>=12&a<16){
			if(b>=8&b<12)return true;
		}
		return false;
	}
}
class DiceSet{
	Die Dice[]=new Die[16];
	void initDiceSet(){
		Dice[0]=new Die("ARELSC");
		Dice[1]=new Die("TABIYL");
		Dice[2]=new Die("EDNSWO");
		Dice[3]=new Die("BIOFXR");
		Dice[4]=new Die("MCDPAE");
		Dice[5]=new Die("IHFYEE");
		Dice[6]=new Die("KTDNUO");
	 	Dice[7]=new Die("MOQAJB");
		Dice[8]=new Die("ESLUPT");
		Dice[9]=new Die("INVTGE");
		Dice[10]=new Die("ZNDVAE");
		Dice[11]=new Die("UKGELY");
		Dice[12]=new Die("OCATAI");
		Dice[13]=new Die("ULGWIR");
		Dice[14]=new Die("SRHEIN");
		Dice[15]=new Die("MSHARO");
	}
	void Jumbles(){
		boolean []bRandSelect=new boolean[16];
		Die []temp=new Die[16];
		Random shitness=new Random();
		for(int x=0;x<16;x++){
			bRandSelect[x]=false;
		}
		for(int x=0;x<16;){
			int a=shitness.nextInt(16);
			if(!bRandSelect[a]){
				temp[x]=Dice[a];
				x++;
			}
		}
		Dice=temp;
	}
}

class Die{
	char value[]=new char[6];
	public Die(String a){
		for(int x=0;x<6;x++)
			value[x]=a.charAt(x);
	}
}
