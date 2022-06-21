from urllib.request import urlopen


def main():
    url = "https://raw.githubusercontent.com/wpilibsuite/allwpilib/main/wpiutil/examples/printlog/datalog.py"
    data = urlopen(url).read()
    with open("datalog.py", "wb") as f:
        f.write(data)


if __name__ == "__main__":
    main()
