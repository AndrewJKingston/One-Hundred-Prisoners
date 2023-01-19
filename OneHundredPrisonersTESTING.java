import java.util.*;

public class OneHundredPrisonersTESTING
{
	static BoxLinkedList listOfBoxes = new BoxLinkedList();
	static ArrayList<BoxLinkedList> boxLoops = new ArrayList<BoxLinkedList>();
	static int NUMBER_OF_BOXES = 0;

	Random r = new Random();

	public static void main(String[] args) 
	{
		OneHundredPrisonersTESTING main = new OneHundredPrisonersTESTING();
		if (args.length > 0)
		{
			try
			{
				NUMBER_OF_BOXES = Integer.parseInt(args[0]);
				int runTimes = Integer.parseInt(args[1]);
				main.calculateRateOfSurvival(runTimes);
			}
			catch (NumberFormatException e) {
				System.err.println("Argument " + args[0] + " must be an integer.");
			}
		}
	}

	public void createHundredRandomBoxes()
	{
		boolean placed = false;
		int nextValue;

		for (int i = 0; i < NUMBER_OF_BOXES; i++)
		{
			placed = false;
			while (!placed && listOfBoxes.getSize() < NUMBER_OF_BOXES)
			{
				nextValue = r.nextInt(NUMBER_OF_BOXES) + 1;
				if (!listOfBoxes.containsCardNumber(nextValue))
				{
					listOfBoxes.add(i + 1, nextValue);
					placed = true;
				}
			}
		}
	}

	public void createBoxLoops()
	{
		BoxLinkedList newList;
		BoxNode nextBox;
		while (!listOfBoxes.isEmpty())
		{
			newList = new BoxLinkedList();
			nextBox = new BoxNode(listOfBoxes.getBoxNumber(0), listOfBoxes.getCardNumber(0));

			newList.addBoxNode(nextBox);
			listOfBoxes.remove(0);

			if (nextBox.getBoxNumber() != nextBox.getCardNumber())
			{
				while ((newList.getLastCardNumber() != newList.getFirstBoxNumber()) && !listOfBoxes.isEmpty())
				{
					for (int i = 0; i < listOfBoxes.getSize(); i++)
					{
						if (listOfBoxes.getBoxNumber(i) == newList.getLastCardNumber())
						{
                            System.out.println("Box number equals last card number");
							newList.add(listOfBoxes.getBoxNumber(i), listOfBoxes.getCardNumber(i));
							listOfBoxes.removeCardNumber(listOfBoxes.getCardNumber(i));
						}
						else if (listOfBoxes.isEmpty())
                        {
                            System.out.println("listOfBoxes.isEmpty(), break");
							break;
                        }
					}
				}
			}
			boxLoops.add(newList);
            System.out.println("Box loop added to newList");
		}
	}

	public void printBoxLoops()
	{
		for (int i = 0; i < boxLoops.size(); i++)
		{
			System.out.println();
			for (int j = 0; j < boxLoops.get(i).getSize(); j++)
				System.out.println("Box: " + boxLoops.get(i).getBoxNumber(j) + " , Card: " + boxLoops.get(i).getCardNumber(j));

			System.out.println("Size of loop: " + boxLoops.get(i).getSize() + "\n");
		}
	}

	public void calculateRateOfSurvival(int runTimes) 
	{
		long startTime = System.currentTimeMillis();
		System.out.println("Running simulation " + runTimes + " times with " + NUMBER_OF_BOXES + " prisoners.");
		float timesFailed = 0f;

		long endTime;
		long runTime;
		String minutes;
		String seconds;
		String milliseconds;

		listOfBoxes.removeAll();
		boxLoops.clear();

		for (int i = 0; i < runTimes; i++)
		{
			listOfBoxes.removeAll();
			boxLoops.clear();

			createHundredRandomBoxes();
            System.out.println("createHundredRandomBoxes successful (112)");
			createBoxLoops();
            System.out.println("createBoxLoops successful (114)");

			for (int j = 0; j < boxLoops.size(); j++)
			{
				if (boxLoops.get(j).getSize() > (NUMBER_OF_BOXES / 2))
				{
					timesFailed++;
					break;
				}
			}

			endTime = System.currentTimeMillis();
			runTime = endTime - startTime;
			minutes = String.valueOf((runTime / 1000) / 60);
			seconds = String.valueOf((runTime / 1000) % 60);
			milliseconds = String.valueOf(runTime % 100);

			if (Long.parseLong(minutes) < 10)
				minutes = "0" + minutes;
			if (Long.parseLong(seconds) < 10)
				seconds = "0" + seconds;
			if (Long.parseLong(milliseconds) < 10)
				milliseconds = "0" + milliseconds;

			System.out.print("Runtime: " + minutes + ":" + seconds + ":" + milliseconds + "\r");
		}

		float rateOfSurvival = ((runTimes - timesFailed) / runTimes) * 100;
		System.out.println("\nRate of survival: " + rateOfSurvival + "%");
	}
}
