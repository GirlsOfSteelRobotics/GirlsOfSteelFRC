import json


def main():
    filename = "y2025/Reefscape/webdash.json"
    data = json.load(open(filename))
    json.dump(data, open(filename, "w"), indent=2)


if __name__ == "__main__":
    # py -m y2025.Reefscape.format_dashboard
    main()
