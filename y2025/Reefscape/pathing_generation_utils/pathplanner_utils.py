def write_pathplanner_auto(path_names, output_file, folder):
    print(f"Writing paths to {output_file} - {path_names}")

    contents = """{
  "version": "2025.0",
  "command": {
    "type": "sequential",
    "data": {
      "commands": ["""
    for i in range(len(path_names)):
        pathName = path_names[i]

        contents += (
            """
        {
          "type": "path",
          "data": {
            "pathName": \""""
            + pathName
            + """\"
          }
        }"""
        )
        if not (i == len(path_names) - 1):
            contents += ","

    contents += (
        """
      ]
    }
  },
  "resetOdom": true,
  "folder": \""""
        + folder
        + """\",
  "choreoAuto": true
}"""
    )
    output_file.write_text(contents)
