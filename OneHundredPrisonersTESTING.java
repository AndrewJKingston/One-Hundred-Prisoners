import java.util.*;
import java.text.NumberFormat;

public class OneHundredPrisonersTESTING
{
	static BoxLinkedList listOfBoxes = new BoxLinkedList();
	static ArrayList<BoxLinkedList> boxLoops = new ArrayList<BoxLinkedList>();
	static int NUMBER_OF_BOXES = 0;
	int counterOne = 1, counterTwo = 1;

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
		// Incorrectly generating two 128 cards

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
					System.out.println(counterOne++ + ": " + nextValue);
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
						if (listOfBoxes.getBoxNumber(i).equals(newList.getLastCardNumber()))
						{
                            System.out.println(counterTwo++ + ": Box number equals last card number");
							newList.add(listOfBoxes.getBoxNumber(i), listOfBoxes.getCardNumber(i));
							listOfBoxes.removeCardNumber(listOfBoxes.getCardNumber(i));
						}
						else if (listOfBoxes.isEmpty())
                        {
                            System.out.println("listOfBoxes.isEmpty(), break");
							break;
                        }
						//System.out.println("broken");
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
		int timesRun = 1;
		long startTime = System.currentTimeMillis();
		String fRunTimes = NumberFormat.getNumberInstance(Locale.US).format(runTimes);
		System.out.println("Running simulation " + fRunTimes + " times with " + NUMBER_OF_BOXES + " prisoners.");
		float timesFailed = 0f;
		int timesSurvived = 0;

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
            System.out.println("createHundredRandomBoxes successful (124)");
			createBoxLoops();
            System.out.println("createBoxLoops successful (126)");

			float tempFailed = timesFailed;

			for (int j = 0; j < boxLoops.size(); j++)
			{
				if (boxLoops.get(j).getSize() > (NUMBER_OF_BOXES / 2))
				{
					timesFailed++;
					break;
				}
			}
			if (tempFailed == timesFailed)
				timesSurvived++;

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

			String fTimesRun = NumberFormat.getNumberInstance(Locale.US).format(timesRun++);
			String fTimesFailed = NumberFormat.getNumberInstance(Locale.US).format(timesFailed);
			String fTimesSurvived = NumberFormat.getNumberInstance(Locale.US).format(timesSurvived);
	
			//System.out.print("Runtime: " + minutes + ":" + seconds + ":" + milliseconds + "   Times run: " + fTimesRun + "   Times failed: " + fTimesFailed + "   Times survived: " + fTimesSurvived + "\r");
		}

		float rateOfSurvival = ((runTimes - timesFailed) / runTimes) * 100;
		System.out.println("\nRate of survival: " + rateOfSurvival + "%");
	}
}
