package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.HashMap;
import nz.ac.auckland.se206.missions.ControllerMission;
import nz.ac.auckland.se206.missions.FuelMission;
import nz.ac.auckland.se206.missions.Mission;
import nz.ac.auckland.se206.missions.ThrusterMission;
import nz.ac.auckland.se206.missions.WindowMission;

/** This class manages the mission, records the selected mission. */
public class MissionManager {

  /** The enum of mission types. */
  public enum MissionType {
    WINDOW,
    CONTROLLER,
    FUEL,
    THRUSTER;
  }

  // The hash map is storing the mission class using the MISSION enum
  public static HashMap<MissionType, Mission> missionList = new HashMap<>();
  public static ArrayList<MissionType> keyList = new ArrayList<>();

  /**
   * Add mission to list according to input integer.
   *
   * @param missionNumber an integer which decides which mission to be added to list.
   */
  public void addMission(int missionNumber) {
    // If the mission numer is 1, add window mission
    if (missionNumber == 1) {
      missionList.put(MissionType.WINDOW, new WindowMission());
      keyList.add(MissionType.WINDOW);
    } else if (missionNumber == 2) {
      // If the mission numer is 2, add fuel mission
      missionList.put(MissionType.FUEL, new FuelMission());
    } else if (missionNumber == 3) {
      // If the mission numer is 3, add controller mission
      missionList.put(MissionType.CONTROLLER, new ControllerMission());
    } else {
      // If the mission numer is 4, add thruster mission
      missionList.put(MissionType.THRUSTER, new ThrusterMission());
    }

    // If the size of key list is greater than the size of mission list (indicating a mission is
    // added twice), remove one
    if (keyList.size() != 0 && keyList.size() > missionList.size()) {
      keyList.remove(keyList.size() - 1);
    }
  }

  /**
   * Calculate the overall percentage of missions.
   *
   * @return the calculated percentage.
   */
  public int getOverallPercentage() {
    int percentage = 0;
    for (MissionType m : keyList) {
      // Go through every missions in the list and get their progress
      percentage += missionList.get(m).getPercentage();
    }
    return percentage / keyList.size();
  }

  /** Print the details of each mission in format: "Name: percentage%". */
  public void printDetails() {
    for (MissionType m : keyList) {
      System.out.println(
          missionList.get(m).getName() + ": " + missionList.get(m).getPercentage() + "%");
    }
    System.out.println("Overall progress: " + getOverallPercentage() + "%");
  }

  /**
   * Get the Mission instance according to input key.
   *
   * @param mission a MISSION enum.
   * @return the class that extends Mission abstract class.
   */
  public Mission getMission(MissionType mission) {
    return missionList.get(mission);
  }

  /**
   * Get the Mission key according to input index.
   *
   * @param index an int that represnents the id of mission.
   * @return the MISSION enum that is the key.
   */
  public MissionType getMissionKey(int index) {
    return keyList.get(index);
  }
}
