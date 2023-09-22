package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.HashMap;
import nz.ac.auckland.se206.missions.*;

public class MissionManager {
  public enum MISSION {
    WINDOW,
    CONTROLLER,
    FUEL,
    THRUSTER;
  }

  public static HashMap<MISSION, Mission> missionList = new HashMap<>();
  public static ArrayList<MISSION> keyList = new ArrayList<>();

  /**
   * Add mission to list according to input integer
   *
   * @param missionNumber an integer which decides which mission to be added to list
   */
  public void addMission(int missionNumber) {
    if (missionNumber == 1) {
      missionList.put(MISSION.WINDOW, new WindowMission());
      keyList.add(MISSION.WINDOW);
    } else if (missionNumber == 2) {
      missionList.put(MISSION.FUEL, new FuelMission());
    } else if (missionNumber == 3) {
      missionList.put(MISSION.CONTROLLER, new ControllerMission());
    } else {
      missionList.put(MISSION.THRUSTER, new ThrusterMission());
    }

    // If the size of key list is greater than the size of mission list (indicating a mission is
    // added twice), remove one
    if (keyList.size() != 0 && keyList.size() > missionList.size()) {
      keyList.remove(keyList.size() - 1);
    }
  }

  /**
   * Calculate the overall percentage of missions
   *
   * @return the calculated percentage
   */
  public int getOverallPercentage() {
    int percentage = 0;
    for (MISSION m : keyList) {
      // Go through every missions in the list and get their progress
      percentage += missionList.get(m).getPercentage();
    }
    return percentage / keyList.size();
  }

  /** Print the details of each mission in format: "Name: percentage%" */
  public void printDetails() {
    for (MISSION m : keyList) {
      System.out.println(
          missionList.get(m).getName() + ": " + missionList.get(m).getPercentage() + "%");
    }
    System.out.println("Overall progress: " + getOverallPercentage() + "%");
  }

  /**
   * Get the Mission instance according to input key
   *
   * @param key a MISSION enum
   * @return the class that extends Mission abstract class
   */
  public Mission getMission(MISSION key) {
    return missionList.get(key);
  }

  public MISSION getMissionKey(int index) {
    return keyList.get(index);
  }
}
