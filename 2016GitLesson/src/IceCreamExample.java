
public class IceCreamExample {
	public static void main(String[] args) {
		int icecreamScoops = Integer.parseInt(args[0]);
		iceCream(icecreamScoops);
	}
	
	private static void iceCream(int icecreamScoops) {
		if (icecreamScoops>10) 
		{

			System.out.println("very super very very happy"); 
		} else if (icecreamScoops==10) {

			System.out.println("happy"); 

		}
		else if (icecreamScoops<=9 && icecreamScoops>=5) {

			System.out.println("sad");

		} else {

		
			System.out.println("super sad");

		}

	}
}
