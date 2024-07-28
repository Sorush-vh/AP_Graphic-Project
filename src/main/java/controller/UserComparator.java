package controller;

import java.util.*;

import model.User;  

public class UserComparator implements Comparator{  

    public static int comparingMode=0;

    public int compare(Object o1,Object o2){  
        User u1=(User)o1;  
        User u2=(User)o2;  
        return compareUsersByMode(u2, u1);  
    }  

    private int compareUsersByMode(User user1, User user2){
        
        switch (comparingMode) {
            case 0:
                int totalScore1=0,totalScore2=0;
                int totalTime1=0,totalTime2=0;
                for (int i = 0; i < 3; i++){ 
                    totalScore1+=user1.getMissionScores()[i];
                    totalTime1+=user1.getMissionTimes()[i];
                    totalScore2+=user2.getMissionScores()[i];
                    totalTime2+=user2.getMissionTimes()[i];
                }
                if(totalScore1>totalScore2)
                    return 1;
                else if(totalScore2>totalScore1)
                    return -1;
                else if(totalTime1<totalTime2)
                    return 1;
                else return -1;
        
            case 1:
                if(user1.getDifficultyScores()[0]>user2.getDifficultyScores()[0])
                    return 1;
                else return -1;

            case 2:
                if(user1.getDifficultyScores()[1]>user2.getDifficultyScores()[1])
                    return 1;
                else return -1;

            case 3:
                if(user1.getDifficultyScores()[2]>user2.getDifficultyScores()[2])
                    return 1;
                else return -1;

            default:
                return 0;
        }
    }
}  
