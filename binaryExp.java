public class binaryExp{
	//int num;
	//int exp;
	//public binaryExp(num,exp){
		//this.num=num;
		//this.exp=exp;
	//}
	public static long power1(int num,int exp){
		if(exp==0){
			return 1;
		}
		else if(num==0){
			return 0;
		}
		else if(((exp)%2)==0){
			return power1(num,(exp/2))*power1(num,(exp/2));
		}
		else{
			return power1(num,(exp/2))*power1(num,(exp/2))*num;
		}
	}
	public static void main(String[] args){
		int a=10;
		int b=9;
		long c=power1(a,b);
		System.out.println(c);
		System.out.println("a");
	}
}
