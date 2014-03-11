package edu.poker.elliott;

/**
 * @author Kristen Nielsen
 * @Description A poker playing agent with more rules
 */
public class Nielsen extends Player {

	private int sameSuitCount;
	private int desiredSuit;
	private int face1Count;
	private int face1;
	private int face2Count;
	private int face2;
	
	public Nielsen()
	{
		sameSuitCount = 0;
		desiredSuit = -1;
		face1Count = 0;
		face1 = -1;
		face2Count = 0;
		face2 = -1;
		
	}
	@Override
	public String getScreenName() {
		// TODO Auto-generated method stub
		return "nielsen";
	}

	@Override
	public String getAction(TableData data) {
		// TODO Auto-generated method stub
		int[] myCards = data.getPocket();
		face1 = myCards[0]%13;
		face2 = myCards[1]%13;
		if (data.getBettingRound() == 1)
		{
			if (myCards[0]/13==myCards[1]/13)
			{
				sameSuitCount = sameSuitCount+2;
				desiredSuit = myCards[0]/13;
				return betOrRaise(data.getValidActions());
			}
			else if (face1==face2)
			{
				face1Count = face1Count+2;
				face2Count = face2Count+2;
				return betOrRaise(data.getValidActions());
			}
			else 
			{
				sameSuitCount = sameSuitCount + 1;
				face1Count = face1Count + 1;
				face2Count = face2Count + 1;
				return checkOrCall(data.getValidActions());
				
			}
		}
		else
		{
			int[] tableCards = data.getBoard();
			int[] allCards = new int[2+tableCards.length];
			int index = 0;

	        for (int i = 0; i < 2; i++) {
	            allCards[index] = myCards[i];
	            index += 1;
	        }
	        
			for (int i = 0; i < tableCards.length; i++)
			{
				allCards[index] = tableCards[i];
				index += 1;
				if (tableCards[i]/13 == desiredSuit)
				{
					sameSuitCount = sameSuitCount + 1;
				}
				else if (myCards[0]/13 == tableCards[i]/13)
				{
					sameSuitCount += 1;
					desiredSuit = myCards[0]/13;
				}
				else if (myCards[1]/13 == tableCards[i]/13)
				{
					sameSuitCount += 1;
					desiredSuit = myCards[1]/13;
				}
				if (tableCards[i]%13 == face1)
				{
					face1Count = face1Count+1;
				}
				if (tableCards[i]%13 == face2)
				{
					face2Count = face2Count+1;
				}
			}
			if (tableCards.length == 3)
			{
				if (sameSuitCount >= 3)
				{
					return checkOrCall(data.getValidActions());
				}
				else if ((face1Count>=2) || (face2Count>=2))
				{
					return checkOrCall(data.getValidActions());
				}
				else
				{
					return "fold";
				}
			}
			else if (tableCards.length == 4)
			{
				if (sameSuitCount >= 4)
				{
					return checkOrCall(data.getValidActions());
				}
				else if ((face1Count>=2) || (face2Count>=2))
				{
					return checkOrCall(data.getValidActions());
				}
				else
				{
					return "fold";
				}
			}
			else if (tableCards.length == 5)
			{
				if (sameSuitCount == 5)
				{
					return betOrRaise(data.getValidActions());
				}
				else if ((face1Count==4) || (face2Count==4))
				{
					return betOrRaise(data.getValidActions());
				}
				else if (((face1Count==3) && (face2Count==2)) || ((face1Count==2) && (face2Count==3)))
				{
					return betOrRaise(data.getValidActions());
				}
				else if ((face1Count==2) && (face2Count==2))
				{
					return checkOrCall(data.getValidActions());
				}
				else
				{
					return "fold";
				}
			}
		}
		return "fold";
	}
	
	@Override
	public void newHand(int handNumber, int[] cashBalances)
	{
		sameSuitCount = 0;
		desiredSuit = -1;
		face1Count = 0;
		face1 = -1;
		face2Count = 0;
		face2 = -1;
	}
	
	private String betOrRaise(String validActions) 
	{
		if (validActions.contains("bet"))
			return "bet";
		else if (validActions.contains("raise"))
			return "raise";
		else
			return "call";
	}
	
	private String checkOrCall(String validActions)
	{
		if (validActions.contains("check"))
			return "check";
		else
			return "call";
	}
}
