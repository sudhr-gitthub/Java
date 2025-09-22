import java.util.*;

class kex1{
public static void main(String args[]){
double a1=0;

Scanner n1=new Scanner(System.in);
System.out.println("Enter the consumer name:");
String name=n1.nextLine();
Scanner n2=new Scanner(System.in);
System.out.println("Enter the consumer number:");
int number=n2.nextInt();
Scanner n3=new Scanner(System.in);
System.out.println("Enter the consumer Type:");
String type=n3.nextLine();

Scanner n4=new Scanner(System.in);
System.out.println("1.Domestic\n2.Commercial\nEnter(1/2):");
int choice=n4.nextInt();

if(choice==1){
System.out.println("Enter no of units used:");
int a=n1.nextInt();
if(a<=100){
a1=a*1;
}else if(a>100 && a<=200){
a1=a*2.50;
}else if(a>200 && a<=500){
a1=a*4;
}else{
a1=a*6;
}
System.out.println("Domestic amount:"+a1);
}


else if(choice==2){
System.out.println("Enter no of units used:");
int a=n1.nextInt();
if(a<=100){
a1=a*2;
}else if(a>100 && a<=200){
a1=a*4.50;
}else if(a>200 && a<=500){
a1=a*6;
}else{
a1=a*7;
}
System.out.println("commercial amount:"+a1);
}

else{
System.out.println("Invalid choice:");
}
}
}
