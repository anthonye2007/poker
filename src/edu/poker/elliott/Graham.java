package edu.poker.elliott;

public class Graham extends Player {
   
   
   public Graham(){
   }

   public String getScreenName() {
      return "Graham";
   }

   public String getAction(TableData data) {
      int[] pocket = data.getPocket();
      int[] board = data.getBoard();
      int best = EstherTools.getBestHand(pocket,board).getCombo();
      String pull = data.getValidActions();
      String[] choices = pull.split(","); 
      if (data.getBettingRound() < 3)
      {
      	if (pull.contains("call")) {
	  return "call";
	}
	if (pull.contains("raise")) {
	  return "raise";
	}

      }
      else
      {
	if ((data.getHandsRemaining() < 10) && (data.getCashBalances()[data.getMySeatNumber() - 1] < 0)){
          if (pull.contains("raise")){
             return "raise";
          } else if (pull.contains("bet")) {
             return "bet";
          } else {
             return "call";
          }
        } else if (best > 1){
          if (pull.contains("raise")){
             return "raise";
          } else if (pull.contains("bet")) {
             return "bet";
          } else {
             return "call";
          }
        } else if (best > 0) {
	  if (pull.contains("bet")) {
  	     return "bet";
          } else {
   	     return "call";
          }
        } else {
   	  return "fold";
        }
      }
   return "fold";
   } 
} 

