
public class IceCreamExample {
	public static void main(String[] args) {
		int icecreamScoops = Integer.parseInt(args[0]);
		iceCream(icecreamScoops);
	}
	
	private static void iceCream(int icecreamScoops) {
		if (icecreamScoops>10) 
		{
			System.out.println("VERY VERY VERY HAPPY"); 
		} else if (icecreamScoops==10) {

			System.out.println("really happy"); 

			System.out.println("HAPPY"); 
 
		}
		else if (icecreamScoops<=9 && icecreamScoops>=5) {
			int i = 1;
			if (i<=10) {
				i++;
				System.out.println("SAD");
			}
			else {
				
			}
		} else {
			int i = 1;
			if (i<=10) {
				i++;
			System.out.println("SUPER SAD");
			}

		}

	}
}
