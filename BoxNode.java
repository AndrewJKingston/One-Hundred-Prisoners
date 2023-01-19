public class BoxNode
{
	// Node class attributes
	private Object boxNumber;
	private Object cardNumber;
	private BoxNode next;

	// Constructs a new node
	public BoxNode(Object newNumber, Object newCardNumber)
	{
		this.boxNumber = newNumber;
		this.cardNumber = newCardNumber;
		this.next = null;
	}

	// Getter for box node number
	public Object getBoxNumber()
	{
		return this.boxNumber;
	}

	// Getter for box node card number
	public Object getCardNumber()
	{
		return this.cardNumber;
	}

	// Getter for box node next
	public BoxNode getNext()
	{
		return this.next;
	}

	// Setter for box node next
	public void setNext(BoxNode nextNode)
	{
		this.next = nextNode;
	}
}
