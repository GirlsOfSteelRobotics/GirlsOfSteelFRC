
public class IceCreamExample {
	public static void main(String[] args) {
		int icecreamScoops = Integer.parseInt(args[0]);
		if (icecreamScoops>10) 
		{
			System.out.println("VERY VERY VERY HAPPY"); 
		} else if (icecreamScoops==10) {
			System.out.println("HAPPY"); 
		}
		else if (icecreamScoops<=9 && icecreamScoops>=5) {
			System.out.println("SAD");
		} else {
			System.out.println("SUPER SAD");
		}

	}
}
