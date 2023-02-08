public class BoxLinkedList
{
	private BoxNode head;
	private BoxNode tail;
	private int numberOfItems;

	// Create an empty list
	public BoxLinkedList()
	{
		this.head = null;
		this.tail = this.head;
		this.numberOfItems = 0;
	}

	// Determine if the list is empty
	public boolean isEmpty()
	{
		return 0 == this.numberOfItems;
	}

	// Return the number of items in the list
	public int getSize()
	{
		return this.numberOfItems;
	}

	// Add a new node to the end of the list
	public void add(Object newBoxNumber, Object newCardNumber)
	{
		BoxNode newBoxNode = new BoxNode(newBoxNumber, newCardNumber);
		this.addBoxNode(newBoxNode);
	}

	// Add a specific node to the end of the list
	public void addBoxNode(BoxNode newBoxNode)
	{
		if (this.isEmpty())
		{
			this.head = newBoxNode;
			newBoxNode.setNext(this.head);
			this.tail = this.head;
		}
		else
		{
			this.tail.setNext(newBoxNode);
			newBoxNode.setNext(head);
			this.tail = newBoxNode;
		}
		this.numberOfItems++;
	}

	// Remove and return a specified box
	public Object remove(int index) throws ListException
	{
		if ((index < 0) || (index >= this.numberOfItems))
			throw new ListException("Error: Cannot remove from empty list.");

		BoxNode temp;	
		if (index == 0)
		{
			temp = this.head;
			this.head = this.head.getNext();
		}
		else if (index == (this.numberOfItems - 1))
		{
			temp = this.head;
			while(temp.getNext() != this.tail)
				temp = temp.getNext();
			
			temp.setNext(this.head);
			BoxNode rVal = this.tail;
			this.tail = temp;

			return rVal;
		}
		else
		{
			BoxNode current = this.head;
			BoxNode previous = null;

			for (int i = 0; i < index; i++)
			{
				previous = current;
				current = current.getNext();
			}

			temp = current;
			previous.setNext(current.getNext());
		}
		this.numberOfItems--;

		return temp.getCardNumber();
	}

	// Remove a specified card value from the list
	public Object removeCardNumber(Object cardNumber) throws ListException
	{
		BoxNode temp = null;

		if (this.head.getCardNumber() == cardNumber)
		{
			temp = this.head;
			this.head = this.head.getNext();
		}
		else if (this.tail.getCardNumber() == cardNumber)
		{
			BoxNode current = this.head;
			BoxNode previous = null;
			
			while (current != this.tail)
			{
				previous = current;
				current = current.getNext();
			}

			temp = current;
			previous.setNext(this.tail.getNext());
			this.tail = previous;
		}
		else 
		{
			BoxNode current = this.head;
			BoxNode previous = null;

			while (current != this.tail)
			{
				if (current.getCardNumber() == cardNumber)
				{
					temp = current;
					previous.setNext(current.getNext());
					break;
				}
				previous = current;
				current = current.getNext();
			}
		}

		if (null == temp)
			throw new ListException("Error: cannot remove card number " + cardNumber + " from list, does not exist.");

		this.numberOfItems--;
		return temp.getCardNumber();
	}

	// Remove all the items within the list
	public void removeAll()
	{
		this.head = null;
		this.tail = head;
		this.numberOfItems = 0;
	}

	// Retrieve the box number of a specified box
	public Object getBoxNumber(int index)
	{
		return getHelper(index).getBoxNumber();
	}

	// Retrieve the card number of a specified box
	public Object getCardNumber(int index)
	{
		return getHelper(index).getCardNumber();
	}

	// Helper method for get methods
	private BoxNode getHelper(int index) throws ListException
	{
		if ((index < 0) || (index >= this.getSize()))
			throw new ListException("Error: Index out of bounds in get method.");

		BoxNode current = this.head;
		for (int i = 0; i < index; i++)
			current = current.getNext();

		return current;
	}

	// Retrieve the box number of the last box node
	public Object getFirstBoxNumber() throws ListException
	{
		if (this.isEmpty())
			throw new ListException("Error: Cannot return last item, list empty.");
		return this.head.getBoxNumber();
	}

	// Retrieve the card number of the last box node
	public Object getLastCardNumber() throws ListException
	{
		if (this.isEmpty())
			throw new ListException("Error: Cannot return last card number, list empty");
		return this.tail.getCardNumber();
	}

	// Return a reference to the first BoxNode in the list
	public BoxNode getFirstBoxNode() throws ListException
	{
		if (this.isEmpty())
			throw new ListException("Error: List is empty, no first box node.");
		return this.head;
	}

	// Determine if the list contains a node with 
	// a specified item
	public boolean containsCardNumber(Object item)
	{
		if (this.isEmpty())
			return false;

		boolean contains = false;
		BoxNode current = this.head;

		for (int i = 0; i < this.numberOfItems; i++)
		{
			if (current.getCardNumber().equals(item))
			{
				contains = true;
				break;
			}
			current = current.getNext();
		}
		return contains;
	}
}
