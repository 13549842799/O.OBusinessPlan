package temptest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Test4 {

	public static void main(String[] args) {
		LinkedList<String> list=new LinkedList<String>();
		ReadThread rt=new ReadThread(list); 
		Thread rth=new Thread(rt);
		rth.start();
		WriteThread wt=new WriteThread(list,new File("test4.txt"));
		Thread wth=new Thread(wt);
		wth.setDaemon(true);//设置写线程为守护线程
		wth.start();
	}

}


class ReadThread extends Thread{
   private LinkedList list;

   public LinkedList getList() {
      return list;
   }

   public void setList(LinkedList list) {
      this.list = list;
   }

   public ReadThread() {
      super();
   }

   public ReadThread(LinkedList list) {
      super();
      this.list = list;
   }

   @Override
   public void run() {
      read(list);
   }

	public  void read(LinkedList list){
	BufferedReader br =null;
	
	br=new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("请输入：");
	    String str=null;
	    while(true){
	    try {
	       str=br.readLine();
	      if(!str.equals("quit")){
	          synchronized (list) {
			  list.addLast(str);//添加到链表最后面  
		      }	    
	      }else{
	         br.close();
	         System.out.println("结束！");
	         break;
	      }
	    } catch (IOException e) {
	// TODO Auto-generated catch block
	       e.printStackTrace();
	    }
	    }
	    
	}



}


class WriteThread  extends Thread{

private LinkedList list;
private File file;

public WriteThread() {
super();
}

public WriteThread(LinkedList list,File file) {
super();
this.list = list;
this.file=file;
}

@Override
public void run() {
write(list,file);
}

public void write(LinkedList<String> list,File file){
	BufferedWriter bw=null;
	try {
		bw=new BufferedWriter(new FileWriter(file,true));
		Thread.sleep(5000);
		String str=null; 
		while(true){
			synchronized (list) {		   
			   if(list.size()>0&&(str=list.removeFirst())!=null){//判断链表的第一个元素是否为空,获取并移除链表的第一个元素
		          bw.write(str);//
		          bw.newLine();
		          bw.flush();
		       }else{
		          Thread.sleep(5000);//当链表第一个元素为空时，写线程休眠5秒钟
		       }
			}
		}
    } catch (IOException e) {
	// TODO Auto-generated catch block
	   e.printStackTrace();
	} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	   e.printStackTrace();
	}finally{
		try {
		   bw.close();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		   e.printStackTrace();
		}
     }
  }
}

