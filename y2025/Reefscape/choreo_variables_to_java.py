
import json

def main():
    choreo_file = "y2025/Reefscape/src/main/deploy/choreo/ChoreoAutos.chor"
    output_file = "y2025/Reefscape/src/main/java/com/gos/reefscape/ChoreoPoses.java"

    choreo_data = json.load(open(choreo_file))
    print(choreo_data)
    print("\n\n\n")
    print(choreo_data["config"])


if __name__ == "__main__":
    # py -m y2025.Reefscape.choreo_variables_to_java
    main()